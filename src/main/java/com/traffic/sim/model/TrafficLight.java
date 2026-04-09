package com.traffic.sim.model;

import java.util.ArrayList;
import java.util.List;

public class TrafficLight {

    private TrafficLightState currentState;

    private List<TrafficObserver> observers = new ArrayList<>();

    public TrafficLight(TrafficLightState initState) {
        this.currentState = initState;
    }

    public void change() {
        /**
         * when the TrafficLight change color it that action will be noitice
         */

        currentState = currentState.getNextState();
        notifyObservers();
    }

    private void notifyObservers() {

        for (TrafficObserver observer : observers) {
            observer.updateState(currentState);
        }
    }

    public void addObserver() {

        // TODO
    }

    public void removeObserver(TrafficObserver observer) {

        // TODO
    }
}
