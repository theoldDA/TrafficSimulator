package com.traffic.sim.view;

import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import java.util.*;

public class TrafficView {
    private final Group root;
    private final PerspectiveCamera camera = new PerspectiveCamera(true);
    private final Group carGroup = new Group();
    private final Map<Integer, Group> npcCars = new HashMap<>();
    private final List<Sphere[]> signals = new ArrayList<>();

    public TrafficView(Group root) {
        this.root = root;
        setupCamera();
        setupLights();
        buildProfessionalEnvironment();
        createUltraCar(carGroup, Color.web("#b22222")); // سيارتك (أحمر ملكي)
    }

    private void setupCamera() {
        camera.setNearClip(1.0); camera.setFarClip(40000.0); camera.setFieldOfView(45);
        root.getChildren().add(camera);
    }

    private void setupLights() {
        DirectionalLight sun = new DirectionalLight(Color.rgb(255, 250, 220));
        sun.setDirection(new javafx.geometry.Point3D(0.4, 1, 0.6));
        root.getChildren().addAll(sun, new AmbientLight(Color.rgb(120, 120, 150)));
    }

    private void buildProfessionalEnvironment() {
        // الأرضية الخضراء
        Box grass = new Box(30000, 1, 40000);
        grass.setTranslateY(35); grass.setMaterial(new PhongMaterial(Color.web("#347c2c")));
        
        // الشارع مع خطوط المسارات
        Group roadGroup = new Group();
        Box asphalt = new Box(1000, 2, 40000);
        asphalt.setMaterial(new PhongMaterial(Color.web("#1a1a1a")));
        roadGroup.getChildren().add(asphalt);

        // إضافة خطوط الشارع (الخطوط البيضاء المقطعة)
        for (int z = -20000; z < 20000; z += 400) {
            Box line = new Box(15, 3, 150);
            line.setTranslateZ(z);
            line.setMaterial(new PhongMaterial(Color.WHITE));
            roadGroup.getChildren().add(line);
        }
        root.getChildren().addAll(grass, roadGroup);

        // الرصيف والبيوت بتفاصيل (الباب والطاقة)
        for (int x : new int[]{-650, 650}) {
            // رصيف مخطط (أسود وأبيض)
            for (int z = -20000; z < 20000; z += 200) {
                Box curb = new Box(120, 20, 200);
                curb.setTranslateX(x > 0 ? 560 : -560); curb.setTranslateZ(z); curb.setTranslateY(25);
                curb.setMaterial(new PhongMaterial((z/200) % 2 == 0 ? Color.web("#333333") : Color.WHITE));
                root.getChildren().add(curb);
            }
            // البيوت الواقعية
            for (int z = -18000; z < 18000; z += 3500) {
                root.getChildren().add(createRealisticBuilding(x * 1.8, z));
            }
        }
        for (int z : new int[]{4000, 10000, 16000}) createModernSignal(580, z);
    }

    private Group createRealisticBuilding(double x, double z) {
        Group house = new Group();
        Box body = new Box(800, 1000, 800);
        body.setMaterial(new PhongMaterial(Color.web("#f5f5dc")));
        
        // الباب (واقعي)
        Box door = new Box(10, 200, 100);
        door.setTranslateX(x > 0 ? -405 : 405); door.setTranslateY(400);
        door.setMaterial(new PhongMaterial(Color.web("#4b2d12")));

        // الطاقة (الشبابيك) مع براويز
        for (int y = -350; y <= 150; y += 250) {
            for (int wz = -250; wz <= 250; wz += 500) {
                Box window = new Box(810, 120, 120);
                window.setTranslateY(y); window.setTranslateZ(wz);
                window.setMaterial(new PhongMaterial(Color.web("#add8e6"))); // لون زجاجي
                house.getChildren().add(window);
            }
        }
        house.setTranslateX(x); house.setTranslateZ(z); house.setTranslateY(-465);
        house.getChildren().add(body); house.getChildren().add(door);
        return house;
    }

    private void createUltraCar(Group g, Color bodyColor) {
        // جسم السيارة الانسيابي
        Box mainBody = new Box(90, 35, 190); mainBody.setMaterial(new PhongMaterial(bodyColor));
        Box cabin = new Box(70, 30, 100); cabin.setTranslateY(-30); cabin.setTranslateZ(-15);
        cabin.setMaterial(new PhongMaterial(Color.web("#1a1a1a")));

        // الكفرات (4 كفرات بارزة)
        double[][] wPos = {{-50, 20, 60}, {50, 20, 60}, {-50, 20, -60}, {50, 20, -60}};
        for (double[] p : wPos) {
            Cylinder wheel = new Cylinder(25, 18);
            wheel.setRotationAxis(javafx.scene.transform.Rotate.Z_AXIS); wheel.setRotate(90);
            wheel.setTranslateX(p[0]); wheel.setTranslateY(p[1]); wheel.setTranslateZ(p[2]);
            wheel.setMaterial(new PhongMaterial(Color.web("#111111")));
            g.getChildren().add(wheel);
        }

        // الأنوار الأمامية (قوية)
        Sphere lightL = new Sphere(12); lightL.setTranslateX(-30); lightL.setTranslateY(-5); lightL.setTranslateZ(95);
        lightL.setMaterial(new PhongMaterial(Color.web("#ffffe0")));
        Sphere lightR = new Sphere(12); lightR.setTranslateX(30); lightR.setTranslateY(-5); lightR.setTranslateZ(95);
        lightR.setMaterial(new PhongMaterial(Color.web("#ffffe0")));

        g.getChildren().addAll(mainBody, cabin, lightL, lightR);
        if (g == carGroup) root.getChildren().add(carGroup);
    }

    public void updatePlayer(double x, double z) {
        carGroup.setTranslateX(x); carGroup.setTranslateZ(z);
        camera.setTranslateX(x); camera.setTranslateZ(z - 1200);
        camera.setTranslateY(-700);
        camera.setRotationAxis(javafx.scene.transform.Rotate.X_AXIS); camera.setRotate(-25);
    }

    public void updateNPC(int id, double x, double z) {
        Group npc = npcCars.computeIfAbsent(id, k -> {
            Group g = new Group();
            createUltraCar(g, Color.DARKSLATEGRAY);
            root.getChildren().add(g);
            return g;
        });
        npc.setTranslateX(x); npc.setTranslateZ(z);
    }

    private void createModernSignal(double x, double z) {
        Group g = new Group();
        Box pole = new Box(15, 550, 15); pole.setMaterial(new PhongMaterial(Color.web("#444444")));
        Box head = new Box(80, 240, 80); head.setTranslateY(-410); head.setMaterial(new PhongMaterial(Color.web("#111111")));
        Sphere r = lamp(-480); Sphere y = lamp(-410); Sphere gL = lamp(-340);
        signals.add(new Sphere[]{r, y, gL});
        g.getChildren().addAll(pole, head, r, y, gL);
        g.setTranslateX(x); g.setTranslateZ(z);
        root.getChildren().add(g);
    }

    private Sphere lamp(double y) {
        Sphere s = new Sphere(24); s.setTranslateY(y); s.setTranslateZ(-42);
        s.setMaterial(new PhongMaterial(Color.BLACK)); return s;
    }

    public void updateSignals(int state) {
        for (Sphere[] s : signals) {
            s[0].setMaterial(new PhongMaterial(state == 0 ? Color.RED : Color.web("#200000")));
            s[1].setMaterial(new PhongMaterial(state == 1 ? Color.YELLOW : Color.web("#202000")));
            s[2].setMaterial(new PhongMaterial(state == 2 ? Color.LIME : Color.web("#002000")));
        }
    }
    public PerspectiveCamera getCamera() { return camera; }
}
