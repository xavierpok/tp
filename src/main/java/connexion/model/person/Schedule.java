package connexion.model.person;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

import static connexion.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;


/**
 * Class representing a schedule in Person. TO BE IMPLEMENTED
 */
public class Schedule implements PersonListDetailField<LocalDateTime> {

    public static final String MESSAGE_CONSTRAINTS = "Schedule time should be in YYYY-MM-DD-HH-MM";
    private static final String VALIDATION_REGEX = "\\d{4}-\\d{2}-\\d{2}-\\d{2}-\\d{2}";
    public static final DateTimeFormatter SCHEDULE_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm");
    private final LocalDateTime scheduleTime;

    public Schedule(String scheduleTime) {
        requireNonNull(scheduleTime);
        checkArgument(isValidScheduleTime(scheduleTime), MESSAGE_CONSTRAINTS);
        this.scheduleTime = LocalDateTime.parse(scheduleTime, SCHEDULE_FORMATTER);
    }

    public static boolean isValidScheduleTime(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return this.scheduleTime.format(SCHEDULE_FORMATTER);
    }


    /**
     * Returns the user-facing string representation of this field in a detail view.
     */
    @Override
    public String getDetailString() {
        return this.scheduleTime.format(SCHEDULE_FORMATTER);
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
        return this.scheduleTime.format(SCHEDULE_FORMATTER);
    }
}
