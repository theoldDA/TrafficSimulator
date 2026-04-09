package com.traffic.sim.model;

@FunctionalInterface
public interface TrafficObserver {

    /**
     * this method will be triggered.
     * when the light changes
     */

    public void updateState(TrafficLightState state);
}
