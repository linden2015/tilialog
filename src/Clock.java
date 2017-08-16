import java.time.LocalTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Clock {
    public static DateTimeFormatter HHmm =
        DateTimeFormatter.ofPattern("HH:mm")
    ;
    private LocalTime lt;
    public Clock(LocalTime lt) {
        this.lt = lt;
    }
    String asString() {
        return lt.format(HHmm);
    }
    String asStringRoundedToFives() {
        Integer minute = lt.getMinute();
        LocalTime roundedLocalTime;
        if (minute <= 2) {
            roundedLocalTime = lt.minusMinutes(minute);
        } else if (minute <= 7) {
            roundedLocalTime = lt.plusMinutes(5 - minute);
        } else {
            roundedLocalTime = lt.plusMinutes(10 - minute);
        }
        return roundedLocalTime.format(HHmm);
    }
}
