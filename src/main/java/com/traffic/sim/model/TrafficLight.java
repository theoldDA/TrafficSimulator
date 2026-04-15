package com.traffic.sim.model;

import java.util.ArrayList;
import java.util.List;

public class TrafficLight {

    private TrafficLightState currentState;

    private List<TrafficObserver> observers = new ArrayList<>();

    public TrafficLight(TrafficLightState initState) {
        this.currentState = initState;
    }

    /**
     * when the TrafficLight change color it that action will be noitice
     */

    public void change() {

        currentState = currentState.getNextState();
        notifyObservers();
    }

    private void notifyObservers() {

        for (TrafficObserver observer : observers) {
            observer.updateState(currentState.getColor());
        }
    }

    public void addObserver(TrafficObserver observer) {

        observers.add(observer);

    }

    public void removeObserver(TrafficObserver observer) {

        observers.remove(observer);
    }
}
