package com.traffic.sim;

import java.time.LocalDateTime;

import com.traffic.sim.datastore.TrafficDataStore;
import com.traffic.sim.datastore.TrafficEvent;
import com.traffic.sim.model.RedState;
import com.traffic.sim.model.TrafficLight;

public class Main {

    public static void main(String[] args) {

        /**
         * 1,
         * here is just a simple test just to let the ~M under stand the logic of this,
         * and Tariq would to.
         */

        // we start the light at RED
        TrafficLight test = new TrafficLight(new RedState());

        /**
         * 2,
         * Tariq and Mohammed both should initialized there work here.
         * Example
         * TrafficView view = new TrafficView(stage);
         * TrafficController controller = new TrafficController(Light, view);
         */

        test.addObserver(color -> System.out.println("DEBUG: the UI should now turn " + color));

        // for further explanations (this acts as your job Tariq)
        test.addObserver(color -> {
            System.out.println("DEBUG: the UI should now turn " + color);
        });

        // So here am testing my logic as console:

        test.addObserver(color -> System.out.println("System notification: Light is now " + color));

        test.addObserver(color -> {
            TrafficEvent event = new TrafficEvent(color, 5, LocalDateTime.now());
            TrafficDataStore.saveEvent(event);
        });

        System.out.println("Model Engine Started.");

        // just small loop to see state transitions
        for (int i = 0; i < 5; i++) {
            System.out.println("\n--- click " + (i + 1) + "---");
            test.change();
        }

        test.change(); // DEBUG: should be Green
        test.change(); // DEBUG: should be Yellow
    }
}
