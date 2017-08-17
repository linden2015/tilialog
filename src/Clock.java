import java.time.format.DateTimeFormatter;
import java.time.LocalTime;
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
    /**
     * Loop from the lowest possible minute (0) to the highest possible
     * minute in steps of the requested unit.
     * If the absolute distance between the stepper and the minute-of-the-
     * hour is smaller than half a step, then the step is the closest
     * possible.
     * If the non-absoluted distance is the same as half a step, then
     * the step is the closest possible; assuming rounding up.
     * If no closest step was found, then the closest step is the following
     * whole hour. If the current hour was 23 then we closest step is the
     * next rotation of the clock.
     */
    String asStringRounded(int roundUnit) {
        if ((60 % roundUnit) != 0) {
            throw new IllegalArgumentException(
                "Cannot divide an hour evenly into parts of " + roundUnit
            );
        }
        Integer minute = lt.getMinute();
        for (int i = 0; i <= 59; i = i + roundUnit) {
            if (
                (Math.abs(i - minute) < (roundUnit / 2.0)) ||
                ((i - minute) == (roundUnit / 2.0))
            ) {
                return lt
                    .withMinute(i)
                    .format(HHmm)
                ;
            }
        }
        if (lt.getHour() == 23) {
            return lt
                .withHour(0)
                .withMinute(0)
                .format(HHmm)
            ;
        } else {
            return lt
                .withHour(lt.getHour() + 1)
                .withMinute(0)
                .format(HHmm)
            ;
        }
    }
}
