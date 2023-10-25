package connexion.testutil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import connexion.logic.commands.ScheduleCommand.ScheduleDescriptor;
import connexion.model.person.LastModifiedDateTime;
import connexion.model.person.Schedule;
import connexion.model.person.ScheduleName;

/**
 * A utility class to help with building ScheduleDescriptor objects.
 */
public class ScheduleDescriptorBuilder {
    public static final String DEFAULT_SCHEDULE_BUILDER = "2023-12-14-08-00";
    public static final String DEFAULT_SCHEDULE_NAME_BUILDER = "Seminar";
    public static final LocalDateTime DEFAULT_LOCAL_DATE_TIME = LocalDateTime.parse("2023-12-27-12-14",
            DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm"));

    private ScheduleDescriptor descriptor;

    /**
     * Initializes the ScheduleDescriptorBuilder with default schedule and schedule name.
     */
    public ScheduleDescriptorBuilder() {
        descriptor = new ScheduleDescriptor(new Schedule(DEFAULT_SCHEDULE_BUILDER),
                new ScheduleName(DEFAULT_SCHEDULE_NAME_BUILDER), new LastModifiedDateTime(DEFAULT_LOCAL_DATE_TIME));
    }

    /**
     * Initializes the ScheduleDescriptorBuilder with the data of {@code scheduleDescriptor}.
     */
    public ScheduleDescriptorBuilder(ScheduleDescriptor scheduleDescriptor) {
        this.descriptor = new ScheduleDescriptor(scheduleDescriptor);
    }

    /**
     * Sets the {@code Schedule} of the {@code ScheduleDescriptor} that we are building.
     */
    public ScheduleDescriptorBuilder withSchedule(String schedule) {
        descriptor.setSchedule(new Schedule(schedule));
        return this;
    }

    /**
     * Sets the {@code ScheduleName} of the {@code ScheduleDescriptor} that we are building.
     */
    public ScheduleDescriptorBuilder withScheduleName(String scheduleName) {
        descriptor.setScheduleName(new ScheduleName(scheduleName));
        return this;
    }

    /**
     * Sets the {@code LastModifiedDateTime} of the {@code ScheduleDescriptor} that we are building.
     */
    public ScheduleDescriptorBuilder withLastModifiedDateTime(LocalDateTime lastModifiedDateTime) {
        descriptor.setLastModifiedDateTime(new LastModifiedDateTime(lastModifiedDateTime));
        return this;
    }

    public ScheduleDescriptor build() {
        return descriptor;
    }

}
