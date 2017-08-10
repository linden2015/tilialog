import java.time.Duration;
import java.time.LocalTime;
public class Log {
    private String description;
    private String storyCode;
    private LocalTime startTime;
    private LocalTime endTime;
    public Log(
        String storyCode, LocalTime startTime, LocalTime endTime
    ) {
        this.storyCode = storyCode;
        this.startTime = startTime;
        this.endTime = endTime;
    }
    public String storyCode() {
        if (storyCode.length() == 0) {
            throw new IllegalStateException("Description is empty.");
        }
        return storyCode;
    }
    public Duration duration() {
        Duration duration = Duration.between(startTime, endTime);
        if (duration.isNegative()) {
            throw new IllegalStateException("Duration is negative.");
        }
        return duration;
    }
}
