package com.traffic.sim.model;

public class GreenState implements TrafficLightState {

    @Override
    public TrafficLightState getNextState() {
        return new YellowState();
    }

    @Override
    public String getColor() {
        return "GREEN";
    }
}
