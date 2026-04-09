package com.traffic.sim.model;

public class YellowState implements TrafficLightState {

    @Override
    public TrafficLightState getNextState() {
        return new RedState();
    }

    @Override
    public String getColor() {

        return "YELLOW";
    }
}
