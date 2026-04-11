package project;

import java.time.LocalDateTime;
import java.util.List;

public class SimulatorExample {

    public static void main(String[] args) {

        // Load previous data at startup
        List<TrafficEvent> events = TrafficDataStore.loadEvents();

        // During simulation (example)
        TrafficEvent event1 = new TrafficEvent("RED", 30, LocalDateTime.now());
        TrafficDataStore.saveEvent(event1);

        TrafficEvent event2 = new TrafficEvent("GREEN", 45, LocalDateTime.now());
        TrafficDataStore.saveEvent(event2);

        // Reload updated data
        events = TrafficDataStore.loadEvents();

        // Analytics
        TrafficAnalytics analytics = new TrafficAnalytics(events);

        System.out.println("Count by State: " + analytics.countByState());
        System.out.println("Average Duration: " + analytics.averageDuration());
        System.out.println("Most Frequent: " + analytics.mostFrequentState().orElse("None"));
        System.out.println("Long Durations: " + analytics.filterLongDurations(40));
    }
}