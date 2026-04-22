package com.traffic.sim.model;

import java.time.LocalDateTime;

//يمثل حدث لإشارة المرور
public class TrafficEvent {
    private String state; // RED, YELLOW, GREEN
    private int duration; // بالثواني
    private LocalDateTime timestamp;

    public TrafficEvent(String state, int duration, LocalDateTime timestamp) {
        this.state = state;
        this.duration = duration;
        this.timestamp = timestamp;
    }

    public String getState() {
        return state;
    }

    public int getDuration() {
        return duration;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return state + "," + duration + "," + timestamp;
    }

    // تحويل من سطر نصي إلى كائن
    public static TrafficEvent fromString(String line) {
        String[] parts = line.split(",");
        return new TrafficEvent(
                parts[0],
                Integer.parseInt(parts[1]),
                LocalDateTime.parse(parts[2]));
    }
}
