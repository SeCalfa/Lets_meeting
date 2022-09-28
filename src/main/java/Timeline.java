import java.sql.Time;

public class Timeline {

    private Time start;
    private Time end;

    public Timeline() {
    }

    public Timeline(Time start, Time end) {
        this.start = start;
        this.end = end;
    }

    public Time getStart() {
        return start;
    }

    public void setStart(Time start) {
        this.start = start;
    }

    public Time getEnd() {
        return end;
    }

    public void setEnd(Time end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "\nPossible meeting time: {" +
                "start=" + start +
                ", end=" + end +
                '}';
    }
}
