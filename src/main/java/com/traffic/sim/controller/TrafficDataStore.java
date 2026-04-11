package project;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

// مسؤول عن حفظ واسترجاع البيانات
public class TrafficDataStore {

    private static final String FILE_NAME = "traffic_data.txt";

    // حفظ حدث جديد (يُستدعى أثناء المحاكاة)
    public static void saveEvent(TrafficEvent event) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write(event.toString());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // تحميل كل البيانات السابقة
    public static List<TrafficEvent> loadEvents() {
        List<TrafficEvent> events = new ArrayList<>();

        File file = new File(FILE_NAME);
        if (!file.exists()) return events;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                events.add(TrafficEvent.fromString(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return events;
    }
}
