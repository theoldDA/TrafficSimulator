package com.traffic.sim.view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class TrafficView {

    // إشارات المرور
    private Circle topRed, topYellow, topGreen;
    private Circle bottomRed, bottomYellow, bottomGreen;
    private Circle leftRed, leftYellow, leftGreen;
    private Circle rightRed, rightYellow, rightGreen;

    // سيارات
    private Rectangle carTop;
    private Rectangle carBottom;
    private Rectangle carLeft;
    private Rectangle carRight;

    // زر تشغيل
    private Button startButton;

    public TrafficView(Stage stage) {

        Pane root = new Pane();
        root.setPrefSize(800, 800);

        // الخلفية
        root.setStyle("-fx-background-color: lightgray;");

        // ===== الطرق =====

        Rectangle verticalRoad = new Rectangle(300, 0, 200, 800);
        verticalRoad.setFill(Color.BLACK);

        Rectangle horizontalRoad = new Rectangle(0, 300, 800, 200);
        horizontalRoad.setFill(Color.BLACK);

        root.getChildren().addAll(verticalRoad, horizontalRoad);

        // ===== الخطوط الصفراء =====

        Rectangle vLine = new Rectangle(395, 0, 10, 800);
        vLine.setFill(Color.GOLD);

        Rectangle hLine = new Rectangle(0, 395, 800, 10);
        hLine.setFill(Color.GOLD);

        root.getChildren().addAll(vLine, hLine);

        // ===== إشارات المرور =====

        // فوق
        topRed = createLight(340, 250, Color.RED);
        topYellow = createLight(370, 250, Color.DARKGOLDENROD);
        topGreen = createLight(400, 250, Color.DARKGREEN);

        // تحت
        bottomRed = createLight(340, 520, Color.DARKRED);
        bottomYellow = createLight(370, 520, Color.DARKGOLDENROD);
        bottomGreen = createLight(400, 520, Color.DARKGREEN);

        // يسار
        leftRed = createLight(250, 340, Color.DARKRED);
        leftYellow = createLight(250, 370, Color.DARKGOLDENROD);
        leftGreen = createLight(250, 400, Color.DARKGREEN);

        // يمين
        rightRed = createLight(520, 340, Color.DARKRED);
        rightYellow = createLight(520, 370, Color.DARKGOLDENROD);
        rightGreen = createLight(520, 400, Color.DARKGREEN);

        root.getChildren().addAll(
                topRed, topYellow, topGreen,
                bottomRed, bottomYellow, bottomGreen,
                leftRed, leftYellow, leftGreen,
                rightRed, rightYellow, rightGreen);

        // ===== السيارات =====

        carTop = createCar(360, 120, Color.BLUE);
        carBottom = createCar(420, 620, Color.GREEN);
        carLeft = createCarHorizontal(120, 360, Color.ORANGE);
        carRight = createCarHorizontal(620, 420, Color.PURPLE);

        root.getChildren().addAll(carTop, carBottom, carLeft, carRight);

        // ===== زر التشغيل =====

        startButton = new Button("Start Simulation");
        startButton.setLayoutX(320);
        startButton.setLayoutY(730);

        root.getChildren().add(startButton);

        Scene scene = new Scene(root);

        stage.setScene(scene);
    }

    // إنشاء إشارة
    private Circle createLight(double x, double y, Color color) {
        Circle light = new Circle(12);
        light.setCenterX(x);
        light.setCenterY(y);
        light.setFill(color);
        return light;
    }

    // سيارة عمودية
    private Rectangle createCar(double x, double y, Color color) {
        Rectangle car = new Rectangle(35, 60);
        car.setX(x);
        car.setY(y);
        car.setFill(color);
        return car;
    }

    // سيارة أفقية
    private Rectangle createCarHorizontal(double x, double y, Color color) {
        Rectangle car = new Rectangle(60, 35);
        car.setX(x);
        car.setY(y);
        car.setFill(color);
        return car;
    }

    // ===== Getters =====

    public Button getStartButton() {
        return startButton;
    }

    public Circle getTopRed() {
        return topRed;
    }

    public Circle getTopYellow() {
        return topYellow;
    }

    public Circle getTopGreen() {
        return topGreen;
    }

    public Circle getBottomRed() {
        return bottomRed;
    }

    public Circle getBottomYellow() {
        return bottomYellow;
    }

    public Circle getBottomGreen() {
        return bottomGreen;
    }

    public Circle getLeftRed() {
        return leftRed;
    }

    public Circle getLeftYellow() {
        return leftYellow;
    }

    public Circle getLeftGreen() {
        return leftGreen;
    }

    public Circle getRightRed() {
        return rightRed;
    }

    public Circle getRightYellow() {
        return rightYellow;
    }

    public Circle getRightGreen() {
        return rightGreen;
    }

    public Rectangle getCarTop() {
        return carTop;
    }

    public Rectangle getCarBottom() {
        return carBottom;
    }

    public Rectangle getCarLeft() {
        return carLeft;
    }

    public Rectangle getCarRight() {
        return carRight;
    }

    public void updateLights(String state) {

        // Top layer

        topRed.setFill(Color.DARKRED);
        topYellow.setFill(Color.DARKGOLDENROD);
        topGreen.setFill(Color.DARKGREEN);

        // bottom layer

        bottomRed.setFill(Color.DARKRED);
        bottomYellow.setFill(Color.DARKGOLDENROD);
        bottomGreen.setFill(Color.DARKGREEN);

        // left layer

        leftRed.setFill(Color.DARKRED);
        leftYellow.setFill(Color.DARKGOLDENROD);
        leftGreen.setFill(Color.DARKGREEN);

        // right layer

        rightRed.setFill(Color.DARKRED);
        rightYellow.setFill(Color.DARKGOLDENROD);
        rightGreen.setFill(Color.DARKGREEN);

        switch (state.toUpperCase()) {
            case "RED":
                topRed.setFill(Color.RED);
                bottomRed.setFill(Color.RED);
                rightRed.setFill(Color.RED);
                leftRed.setFill(Color.RED);
                break;
            case "YELLOW":
                topYellow.setFill(Color.YELLOW);
                bottomYellow.setFill(Color.YELLOW);
                rightYellow.setFill(Color.YELLOW);
                leftYellow.setFill(Color.YELLOW);
                break;
            case "GREEN":
                topGreen.setFill(Color.LIME);
                bottomGreen.setFill(Color.LIME);
                rightGreen.setFill(Color.LIME);
                leftGreen.setFill(Color.LIME);
        }
    }
}
