import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Time;
import java.util.List;

// :)
public class Main {
    public static void main(String[] args) throws IOException {
        JsonReader jsonReader = new JsonReader();
        MeetingCalculator meetingCalculator;

        String cal1_plannedMeeting_json = jsonToString("src/main/resources/json/calendar_1/planned_meeting.json");
        String cal1_workingHours_json = jsonToString("src/main/resources/json/calendar_1/working_hours.json");
        String cal2_plannedMeeting_json = jsonToString("src/main/resources/json/calendar_2/planned_meeting.json");
        String cal2_workingHours_json = jsonToString("src/main/resources/json/calendar_2/working_hours.json");

        List<Timeline> cal1_plannedMeeting = jsonReader.getTimelines(cal1_plannedMeeting_json);
        List<Timeline> cal1_workingHours = jsonReader.getTimelines(cal1_workingHours_json);
        List<Timeline> cal2_plannedMeeting = jsonReader.getTimelines(cal2_plannedMeeting_json);
        List<Timeline> cal2_workingHours = jsonReader.getTimelines(cal2_workingHours_json);

        meetingCalculator = new MeetingCalculator(
                new Time(0, 30 ,0), // meeting duration
                cal1_workingHours.get(0),
                cal2_workingHours.get(0),
                cal1_plannedMeeting,
                cal2_plannedMeeting
        );

        meetingCalculator.calculateFreeTime();
        meetingCalculator.calculateCrossingFreeTime();
        System.out.println(meetingCalculator.coordination());
    }

    public static String jsonToString(String file) throws IOException {
        return new String(Files.readAllBytes(Paths.get(file)));
    }
}
