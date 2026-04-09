package com.traffic.sim;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        Label status = new Label("Traffic simulator");
        VBox root = new VBox(status);
        root.setAlignment(javafx.geometry.Pos.CENTER);

        Scene scene = new Scene(root, 400, 300);
        stage.setTitle("Traffic simulator");
        stage.setScene(scene);
        stage.show();
    }
}
