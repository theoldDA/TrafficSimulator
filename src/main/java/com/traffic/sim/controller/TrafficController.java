package com.traffic.sim.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.traffic.sim.model.GreenState;
import com.traffic.sim.model.TrafficDataStore;
import com.traffic.sim.model.TrafficEvent;
import com.traffic.sim.model.TrafficLight;
import com.traffic.sim.view.TrafficView;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

public class TrafficController {
    private final TrafficView view;
    private double x = 200, z = 0, speed = 0;
    private int signalState = 2;
    private final Set<KeyCode> keys = new HashSet<>();
    private final List<double[]> npcs = new ArrayList<>();
    private boolean isAccelerating = false;
    private final TrafficLight trafficLight;

    public TrafficController(TrafficView view) {
        this.view = view;

        this.trafficLight = new TrafficLight(new GreenState());

        /**
         * The change will happen automatically, so that when a state has change
         * It trigger this Block automatically
         */

        this.trafficLight.addObserver(color -> {
            int duration = 0;
            switch (color) {
                case "RED":
                    signalState = 0;
                    duration = 6;
                    break;
                case "YELLOW":
                    signalState = 1;
                    duration = 2;
                    break;
                case "GREEN":
                    signalState = 2;
                    duration = 8;
                    break;
            }
            this.view.updateSignals(signalState);

            TrafficDataStore
                    .saveEvent(new TrafficEvent("SIGNAL_" + color, duration, java.time.LocalDateTime.now()));
        });

        for (int i = 0; i < 7; i++) {
            npcs.add(new double[] { -200, i * 4500 - 10000, 9 + Math.random() * 4 });
        }

        view.updateSignals(signalState);

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                long cycle = (now / 1_000_000_000) % 16;

                int expectedState;
                if (cycle < 8)
                    expectedState = 2;// Green
                else if (cycle < 10)
                    expectedState = 1;// yellow
                else
                    expectedState = 0;// red

                // now here the state machine
                // if current state doesnt match with time, push the model forward
                if (signalState != expectedState) {
                    trafficLight.change();
                }
                updatePhysics();
                updateNPCs();
                view.updatePlayer(x, z);
            }
        }.start();
    }

    private void updatePhysics() {
        boolean atSignal = (z % 6000 > 3600 && z % 6000 < 4000);
        if (keys.contains(KeyCode.W)) {
            if (signalState == 0 && atSignal)
                speed *= 0.8;
            else
                speed = Math.min(32, speed + 0.8);

            /**
             * Log for the user: actively driving
             * And only save the event if weren't already accelerating
             * Long story short (fixes the memory leak)
             */

            if (!isAccelerating) {
                TrafficDataStore
                        .saveEvent(new TrafficEvent("User_Accelerated", (int) speed, java.time.LocalDateTime.now()));
                isAccelerating = true;
            }

        } else if (keys.contains(KeyCode.S)) {
            isAccelerating = false;
            speed = Math.max(-12, speed - 1.2);
        } else {
            isAccelerating = false;
            speed *= 0.98;
        }
        z += speed;

        if (z > 19000) {
            System.out.println("End of road reached!");

            TrafficDataStore
                    .saveEvent(new TrafficEvent("Simulation_Complete", (int) z, java.time.LocalDateTime.now()));

            javafx.application.Platform.exit();
            return;
        }
    }

    private void updateNPCs() {

        for (int i = 0; i < npcs.size(); i++) {
            double[] n = npcs.get(i);

            boolean atRedLight = (n[1] % 6000 > 3600 && n[1] % 6000 < 4000) && (signalState == 0);

            boolean statcksCars = false;
            for (double[] otherCar : npcs) {
                // if any cars is in fornt of me (higher Z) and distance is less than 400
                if (n != otherCar && otherCar[1] > n[1] && (otherCar[1] - n[1]) < 400) {
                    statcksCars = true;
                    break;
                }
            }

            // the cars only moves forward if it's not at a red light AND there is no
            // collision risk
            if (!atRedLight && !statcksCars) {
                n[1] += n[2]; // move forward
            }

            // if they reached the end recycle them
            if (n[1] > 20000)
                n[1] = -20000;

            view.updateNPC(i, n[0], n[1]);
        }
    }

    public void bind(Scene s) {
        s.setOnKeyPressed(e -> keys.add(e.getCode()));
        s.setOnKeyReleased(e -> keys.remove(e.getCode()));
    }
}
