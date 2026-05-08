package com.traffic.sim;

import com.traffic.sim.view.TrafficView;
import com.traffic.sim.controller.TrafficController;
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {
    
    static {
        // لضمان عدم ظهور شاشة بيضاء في بعض الأجهزة
        System.setProperty("prism.order", "sw");
    }

    @Override
    public void start(Stage stage) {
        Group root = new Group();
        TrafficView view = new TrafficView(root);
        
        Scene scene = new Scene(root, 1280, 720, true, SceneAntialiasing.BALANCED);
        scene.setFill(Color.web("#87CEEB"));
        scene.setCamera(view.getCamera());

        TrafficController controller = new TrafficController(view);
        controller.bind(scene);

        stage.setTitle("Saudi Urban Drive 2026 - Ultra Pro");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
