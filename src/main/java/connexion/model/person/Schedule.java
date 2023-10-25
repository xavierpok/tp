package connexion.model.person;

import static connexion.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

/**
 * Class representing a schedule in Person.
 */
public class Schedule implements PersonListDetailField<LocalDateTime> {

    public static final String MESSAGE_CONSTRAINTS = "Schedule time should be in YYYY-MM-DD-HH-MM";

    public static final DateTimeFormatter SCHEDULE_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm");
    public static final DateTimeFormatter SCHEDULE_OUTPUT_FORMATTER =
            DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).withLocale(Locale.UK);
    private static final String VALIDATION_REGEX = "\\d{4}-\\d{2}-\\d{2}-\\d{2}-\\d{2}";

    private final LocalDateTime scheduleTime;

    /**
     * Constructs an {@code Schedule}.
     *
     * @param scheduleTime A valid scheduleTime.
     */
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
        return this.scheduleTime
                .truncatedTo(ChronoUnit.MINUTES)
                .format(SCHEDULE_OUTPUT_FORMATTER);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Schedule)) {
            return false;
        }

        Schedule otherSchedule = (Schedule) other;
        return scheduleTime.equals(otherSchedule.scheduleTime);
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
        return this.scheduleTime
                .truncatedTo(ChronoUnit.MINUTES)
                .format(SCHEDULE_OUTPUT_FORMATTER);
    }
}
