package com.traffic.sim.model;

/**
 * this interface for a state of
 * TrafficLight Pattern
 * which they are (Red, Green, Yellow) will be implemented here
 */
public interface TrafficLightState {

    public TrafficLightState getNextState();

    public String getColor();
}
