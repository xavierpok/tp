package connexion.model.person;

import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;

/**
 * Temp stub for detail implementation injection
 */
public class ScheduleStub extends Schedule {

    private static final LocalDateTime DEFAULT_SCHEDULE_TIME = LastModifiedDateTime.DEFAULT_LAST_MODIFIED;

    private static final DateTimeFormatter LIST_FORMATTER = DateTimeFormatter.ofPattern("dd MMM");
    private static final Period IN_TIME = Period.of(1, 1, 1);

    private static final DateTimeFormatter DETAIL_FORMATTER = DateTimeFormatter.ofPattern("dd/MMM/yyyy");
    public ScheduleStub() {
        // does nothing
    }


    /**
     * Returns the user-facing string representation of this field in a detail view.
     */
    @Override
    public String getDetailString() {
        return DEFAULT_SCHEDULE_TIME.format(DETAIL_FORMATTER) + String.format(" (in %s year)", IN_TIME.getYears());
    }

    /**
     * Returns the value within this field.
     */
    @Override
    public LocalDateTime getValue() {
        return DEFAULT_SCHEDULE_TIME;
    }

    /**
     * Returns the user-facing string representation of this field in an at-a-glance view.
     */
    @Override
    public String getListString() {
        return DEFAULT_SCHEDULE_TIME.format(LIST_FORMATTER) + String.format(" (in %s year)", IN_TIME.getYears());
    }
}
