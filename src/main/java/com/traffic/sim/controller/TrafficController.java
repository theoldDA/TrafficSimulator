package com.traffic.sim.controller;

import com.traffic.sim.view.TrafficView;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import java.util.*;

public class TrafficController {
    private final TrafficView view;
    private double x = 200, z = 0, speed = 0;
    private int signalState = 0;
    private final Set<KeyCode> keys = new HashSet<>();
    private final List<double[]> npcs = new ArrayList<>();

    public TrafficController(TrafficView view) {
        this.view = view;
        for(int i=0; i<7; i++) {
            npcs.add(new double[]{-200, i*4500 - 10000, 9 + Math.random()*4});
        }
        
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                long cycle = (now / 1_000_000_000) % 16;
                if (cycle < 8) signalState = 2;
                else if (cycle < 10) signalState = 1;
                else signalState = 0;
                
                view.updateSignals(signalState);
                updatePhysics();
                updateNPCs();
                view.updatePlayer(x, z);
            }
        }.start();
    }

    private void updatePhysics() {
        boolean atSignal = (z % 6000 > 3600 && z % 6000 < 4000);
        if (keys.contains(KeyCode.W)) {
            if (signalState == 0 && atSignal) speed *= 0.8;
            else speed = Math.min(32, speed + 0.8);
        } else if (keys.contains(KeyCode.S)) {
            speed = Math.max(-12, speed - 1.2);
        } else {
            speed *= 0.98;
        }
        z += speed;
    }

    private void updateNPCs() {
        for (double[] n : npcs) {
            boolean npcStop = (n[1] % 6000 > 3600 && n[1] % 6000 < 4000);
            if (signalState == 0 && npcStop) {} 
            else n[1] += n[2];
            if (n[1] > 20000) n[1] = -20000;
            view.updateNPC(npcs.indexOf(n), n[0], n[1]);
        }
    }

    public void bind(Scene s) {
        s.setOnKeyPressed(e -> keys.add(e.getCode()));
        s.setOnKeyReleased(e -> keys.remove(e.getCode()));
    }
}
