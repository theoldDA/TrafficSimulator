package com.traffic.sim.model;

public class RedState implements TrafficLightState {

    @Override
    public TrafficLightState getNextState() {
        return new GreenState();
    }

    @Override
    public String getColor() {
        return "RED";
    }
}
