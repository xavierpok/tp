package connexion.model.person;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static connexion.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;


/**
 * Class representing a schedule in Person. TO BE IMPLEMENTED
 */
public class Schedule implements PersonListDetailField<LocalDateTime> {

    public static final String MESSAGE_CONSTRAINTS = "Schedule time should be in ";
    private static final String VALIDATION_REGEX = "^\\d{4}-\\d{2}-\\d{2}-\\d{2}-\\d{2}$";
    private final LocalDateTime scheduleTime;

    public Schedule(String scheduleTime) {
        requireNonNull(scheduleTime);
        checkArgument(isValidScheduleTime(scheduleTime), MESSAGE_CONSTRAINTS);
        this.scheduleTime = LocalDateTime.parse(scheduleTime, DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm"));
    }

    public static boolean isValidScheduleTime(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    /**
     * Returns the user-facing string representation of this field in a detail view.
     */
    @Override
    public String getDetailString() {
        return this.scheduleTime.toString();
    }

    /**
     * Returns the value within this field.
     */
    @Override
    public LocalDateTime getValue() {
        return this.scheduleTime;
    }

    /**
     * Returns the user-facing string representation of this field in an at-a-glance view.
     */
    @Override
    public String getListString() {
        return this.scheduleTime.toString();
    }
}
