import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class MeetingCalculator {

    private final Time meetingDuration;
    private final Timeline cal1_workingHours;
    private final Timeline cal2_workingHours;
    private final List<Timeline> cal1_plannedMeeting;
    private final List<Timeline> cal2_plannedMeeting;

    private final List<Timeline> freeTimeCalendar1 = new ArrayList<>();
    private final List<Timeline> freeTimeCalendar2 = new ArrayList<>();
    private final List<Timeline> crossingFreeTime = new ArrayList<>();

    public MeetingCalculator(Time meetingDuration,
                             Timeline cal1_workingHours,
                             Timeline cal2_workingHours,
                             List<Timeline> cal1_plannedMeeting,
                             List<Timeline> cal2_plannedMeeting) {
        this.meetingDuration = meetingDuration;
        this.cal1_workingHours = cal1_workingHours;
        this.cal2_workingHours = cal2_workingHours;
        this.cal1_plannedMeeting = cal1_plannedMeeting;
        this.cal2_plannedMeeting = cal2_plannedMeeting;
    }

    public void calculateFreeTime() {
        // Filling free time spaces for calendar 1
        fillFreeTimeSpaces(freeTimeCalendar1, cal1_plannedMeeting, cal1_workingHours);

        // Filling free time spaces for calendar 2
        fillFreeTimeSpaces(freeTimeCalendar2, cal2_plannedMeeting, cal2_workingHours);
    }

    // Calculating crossing free time based on 4 possible options
    public void calculateCrossingFreeTime(){
        for (int t1 = 0; t1 < freeTimeCalendar1.size(); t1++){
            for (int t2 = 0; t2 < freeTimeCalendar2.size(); t2++){
                if(freeTimeCalendar1.get(t1).getStart().toLocalTime().toSecondOfDay() < freeTimeCalendar2.get(t2).getStart().toLocalTime().toSecondOfDay()
                        && freeTimeCalendar2.get(t2).getStart().toLocalTime().toSecondOfDay() < freeTimeCalendar1.get(t1).getEnd().toLocalTime().toSecondOfDay()){
                    crossingFreeTime.add(new Timeline(freeTimeCalendar2.get(t2).getStart(), freeTimeCalendar1.get(t1).getEnd()));
                }
                else if(freeTimeCalendar1.get(t1).getStart().toLocalTime().toSecondOfDay() > freeTimeCalendar2.get(t2).getStart().toLocalTime().toSecondOfDay()
                && freeTimeCalendar1.get(t1).getStart().toLocalTime().toSecondOfDay() < freeTimeCalendar2.get(t2).getEnd().toLocalTime().toSecondOfDay()){
                    crossingFreeTime.add(new Timeline(freeTimeCalendar1.get(t1).getStart(), freeTimeCalendar2.get(t2).getEnd()));
                }
                else if(freeTimeCalendar1.get(t1).getStart().toLocalTime().toSecondOfDay() < freeTimeCalendar2.get(t2).getStart().toLocalTime().toSecondOfDay()
                && freeTimeCalendar1.get(t1).getEnd().toLocalTime().toSecondOfDay() > freeTimeCalendar2.get(t2).getEnd().toLocalTime().toSecondOfDay()){
                    crossingFreeTime.add(new Timeline(freeTimeCalendar2.get(t2).getStart(), freeTimeCalendar2.get(t2).getEnd()));
                }
                else if (freeTimeCalendar1.get(t1).getStart().toLocalTime().toSecondOfDay() > freeTimeCalendar2.get(t2).getStart().toLocalTime().toSecondOfDay()
                && freeTimeCalendar1.get(t1).getEnd().toLocalTime().toSecondOfDay() < freeTimeCalendar2.get(t2).getEnd().toLocalTime().toSecondOfDay()){
                    crossingFreeTime.add(new Timeline(freeTimeCalendar1.get(t1).getStart(), freeTimeCalendar1.get(t1).getEnd()));
                }
            }
        }
    }

    // Coordination between free time and my meeting duration
    public List<Timeline> coordination(){
        for (int t = 0; t < crossingFreeTime.size(); t++){
            int freeTimeDuration = crossingFreeTime.get(t).getEnd().toLocalTime().toSecondOfDay() - crossingFreeTime.get(t).getStart().toLocalTime().toSecondOfDay();
            int myMeetingDuration = meetingDuration.toLocalTime().toSecondOfDay();

            if(freeTimeDuration < myMeetingDuration)
                crossingFreeTime.remove(t);
        }

        return crossingFreeTime;
    }

    private void fillFreeTimeSpaces(List<Timeline> freeTimeCalendar, List<Timeline> plannedMeeting, Timeline workingHorse){
        for (int t = 0; t < plannedMeeting.size(); t++) {
            if (t == 0) {
                if (plannedMeeting.get(0).getStart().toLocalTime().toSecondOfDay() > workingHorse.getStart().toLocalTime().toSecondOfDay())
                    freeTimeCalendar.add(new Timeline(workingHorse.getStart(), plannedMeeting.get(0).getStart()));

                continue;
            }

            if (plannedMeeting.get(t).getStart().toLocalTime().toSecondOfDay() > plannedMeeting.get(t - 1).getEnd().toLocalTime().toSecondOfDay())
                freeTimeCalendar.add(new Timeline(plannedMeeting.get(t - 1).getEnd(), plannedMeeting.get(t).getStart()));

            if(t == plannedMeeting.size() - 1){
                if (plannedMeeting.get(t).getEnd().toLocalTime().toSecondOfDay() < workingHorse.getEnd().toLocalTime().toSecondOfDay())
                    freeTimeCalendar.add(new Timeline(plannedMeeting.get(t).getEnd(), workingHorse.getEnd()));
            }
        }
    }

    @Override
    public String toString() {
        return "MeetingCalculator{" +
                "\ncal1_workingHours=" + cal1_workingHours +
                ", \ncal2_workingHours=" + cal2_workingHours +
                ", \ncal1_plannedMeeting=" + cal1_plannedMeeting +
                ", \ncal2_plannedMeeting=" + cal2_plannedMeeting +
                '}';
    }
}
