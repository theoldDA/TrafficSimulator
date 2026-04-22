package com.traffic.sim.model;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

// تحليل بيانات الإشارات
public class TrafficAnalytics {

    private List<TrafficEvent> events;

    public TrafficAnalytics(List<TrafficEvent> events) {
        this.events = events;
    }

    // عدد مرات تشغيل كل حالة
    public Map<String, Long> countByState() {
        return events.stream()
                .collect(Collectors.groupingBy(
                        TrafficEvent::getState,
                        Collectors.counting()));
    }

    // متوسط مدة كل حالة
    public Map<String, Double> averageDuration() {
        return events.stream()
                .collect(Collectors.groupingBy(
                        TrafficEvent::getState,
                        Collectors.averagingInt(TrafficEvent::getDuration)));
    }

    // أكثر حالة تكرارًا
    public Optional<String> mostFrequentState() {
        return events.stream()
                .collect(Collectors.groupingBy(
                        TrafficEvent::getState,
                        Collectors.counting()))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey);
    }

    // فلترة الأحداث (مثلاً المدد الطويلة)
    public List<TrafficEvent> filterLongDurations(int minDuration) {
        return events.stream()
                .filter(e -> e.getDuration() > minDuration)
                .collect(Collectors.toList());
    }
}
