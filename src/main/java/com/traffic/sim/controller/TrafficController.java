package com.traffic.sim.controller;

import com.traffic.sim.model.TrafficLight;
import com.traffic.sim.view.TrafficView;

public class TrafficController {

    private TrafficLight model;
    private TrafficView view;

    public TrafficController(TrafficLight model, TrafficView view) {
        this.model = model;
        this.view = view;
        initController();
    }

    private void initController() {
        view.getStartButton().setOnAction(event -> {

            model.change();

            System.out.println("DEBUG: COntroller only handled button click. and the state only.");

            // TODO: Mohammed: your DataStore and Analytics save here
        });

    }
}
