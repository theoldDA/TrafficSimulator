package com.traffic.sim;

import com.traffic.sim.controller.TrafficController;
import com.traffic.sim.model.RedState;
import com.traffic.sim.model.TrafficLight;
import com.traffic.sim.view.TrafficView;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        // Here I initialize my work (the model) [Ghalib sharef]

        TrafficLight lightModel = new TrafficLight(new RedState());

        // Here initialize the View (Tariq's GUI)
        TrafficView trafficView = new TrafficView(stage);

        // initialize the controller (Ghalib & Mohammed)
        TrafficController controller = new TrafficController(lightModel, trafficView);

        /**
         * Whenever the model changes state. it automatically tells the View to update.
         */

        lightModel.addObserver(color -> trafficView.updateLights(color));

        stage.setTitle("Traffic Simulator");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
