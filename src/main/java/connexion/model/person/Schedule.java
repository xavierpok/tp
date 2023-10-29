package connexion.model.person;

import static connexion.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.FormatStyle;
import java.time.format.ResolverStyle;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

/**
 * Class representing a schedule in Person.
 */
public class Schedule implements PersonListDetailField<LocalDateTime> {

    public static final String MESSAGE_CONSTRAINTS = "Schedule time should be in YYYY-MM-DD-HH-MM, and be valid."
            + " E.g. 2020-09-30-23-59. Please check to make sure the date & time exist.";

    public static final DateTimeFormatter SCHEDULE_FORMATTER =
            DateTimeFormatter.ofPattern("uuuu-MM-dd-HH-mm").withResolverStyle(ResolverStyle.STRICT);
    //u is how java refers to year vs year of era (which is y)
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

    /**
     * Tests if the given string is a valid Schedule in the correct format.
     */

    public static boolean isValidScheduleTime(String test) {
        try {
            LocalDateTime testTime = LocalDateTime.parse(test, SCHEDULE_FORMATTER);
            // value irrelevant, just see if can parse
            return true;
        } catch (DateTimeParseException e) {
            return false;
            // While normally not recommended, there is no method exposed in the java.time API
            // to validate parsing without resorting to a try-catch block.
            // So we have to do this.
            // The closest is DateTimeFormatter#parse(CharSequence text, ParsePosition position)
            // But no good way to validate that all expected fields are there
            // Except to go even deeper into time API
            // Which will probably obfuscate code even more
        }
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
