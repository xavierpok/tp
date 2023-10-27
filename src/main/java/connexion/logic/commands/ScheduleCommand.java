package connexion.logic.commands;

import static connexion.commons.util.CollectionUtil.requireAllNonNull;
import static connexion.logic.parser.CliSyntax.PREFIX_SCHEDULE;
import static connexion.logic.parser.CliSyntax.PREFIX_SCHEDULE_NAME;
import static connexion.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import connexion.commons.core.index.Index;
import connexion.commons.util.ToStringBuilder;
import connexion.logic.Messages;
import connexion.logic.commands.exceptions.CommandException;
import connexion.model.Model;
import connexion.model.person.Company;
import connexion.model.person.Email;
import connexion.model.person.Job;
import connexion.model.person.LastModifiedDateTime;
import connexion.model.person.Name;
import connexion.model.person.Person;
import connexion.model.person.Phone;
import connexion.model.person.Schedule;
import connexion.model.person.ScheduleName;
import connexion.model.tag.Tag;

/**
 * Adds a scheduled meeting of an existing person in the address book.
 * Overrides the previous scheduled meeting if there existed one before.
 */
public class ScheduleCommand extends Command {
    public static final String COMMAND_WORD = "schedule";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds a schedule to the person identified by the index number used in the displayed person list\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_SCHEDULE + "MEETING DATE (must be in the form YYYY-MM-DD-HH-MM) "
            + "[" + PREFIX_SCHEDULE_NAME + "SCHEDULE NAME" + "] "
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_SCHEDULE + "2023-12-27-08-00 "
            + PREFIX_SCHEDULE_NAME + "Morning Interview";

    public static final String SCHEDULE_ADD_SUCCESS = "Schedule added: %1$s";
    public static final String SCHEDULE_NOT_ADDED = "date must be provided.";

    private final Index index;
    private final ScheduleDescriptor scheduleDescriptor;

    /**
     * @param index of the person in the filtered person list to add or edit schedule
     * @param scheduleDescriptor details of schedules
     */
    public ScheduleCommand(Index index, ScheduleDescriptor scheduleDescriptor) {
        requireAllNonNull(index, scheduleDescriptor);

        this.index = index;
        this.scheduleDescriptor = scheduleDescriptor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEditSchedule = lastShownList.get(index.getZeroBased());
        Person editedPerson = createScheduledPerson(personToEditSchedule, scheduleDescriptor);

        model.setPerson(personToEditSchedule, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(SCHEDULE_ADD_SUCCESS, Messages.format(editedPerson)));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEditSchedule}
     * edited with {@code schedulePersonDescriptor}.
     */
    private static Person createScheduledPerson(Person personToEditSchedule, ScheduleDescriptor scheduleDescriptor) {
        assert personToEditSchedule != null;

        Name name = personToEditSchedule.getName();
        Phone phone = personToEditSchedule.getPhone();
        Email email = personToEditSchedule.getEmail();
        Company company = personToEditSchedule.getCompany();
        Job job = personToEditSchedule.getJob();
        Set<Tag> tags = personToEditSchedule.getTags();
        LastModifiedDateTime updatedLastModifiedDateTime =
                scheduleDescriptor.getLastModifiedDateTime();
        // While semantically, it would make sense that this would always be changed,
        // We do it like this for consistency with other fields
        // And to move responsibility for updating this field to the parser,
        // Like the other fields.
        Optional<Schedule> updatedSchedule = Optional.ofNullable(scheduleDescriptor.getSchedule());
        Optional<ScheduleName> updatedScheduleName = Optional.ofNullable(scheduleDescriptor.getScheduleName());
        Person toReturn =  new Person(name, phone, email, company, job, tags,
                updatedSchedule, updatedScheduleName, updatedLastModifiedDateTime);
        if (personToEditSchedule.getValue().getMarkStatus()) { // if the person was marked
            toReturn.mark();
        } else { // if the person was unmarked
            toReturn.unMark();
        }
        return toReturn;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ScheduleCommand)) {
            return false;
        }

        ScheduleCommand otherScheduleCommand = (ScheduleCommand) other;
        return index.equals(otherScheduleCommand.index)
                && scheduleDescriptor.equals(otherScheduleCommand.scheduleDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("scheduleDescriptor", scheduleDescriptor)
                .toString();
    }

    /**
     * Stores the details of a scheduled meeting with the person.
     */
    public static class ScheduleDescriptor {
        private Schedule schedule;
        private ScheduleName scheduleName;
        private LastModifiedDateTime lastModifiedDateTime;

        /**
         * Stores the schedule details and schedule names.
         * @param schedule the meeting timing
         * @param scheduleName the meeting name or agenda
         * @param lastModifiedDateTime the current time of modification
         */
        public ScheduleDescriptor(Schedule schedule,
                                  ScheduleName scheduleName, LastModifiedDateTime lastModifiedDateTime) {
            this.schedule = schedule;
            this.scheduleName = scheduleName;
            this.lastModifiedDateTime = lastModifiedDateTime;
        }

        /**
         * Copy constructor.
         */
        public ScheduleDescriptor(ScheduleDescriptor toCopy) {
            setSchedule(toCopy.schedule);
            setScheduleName(toCopy.scheduleName);
            setLastModifiedDateTime(toCopy.lastModifiedDateTime);
        }

        public Schedule getSchedule() {
            return this.schedule;
        }

        public void setSchedule(Schedule schedule) {
            this.schedule = schedule;
        }

        public void setScheduleName(ScheduleName scheduleName) {
            this.scheduleName = scheduleName;
        }

        public ScheduleName getScheduleName() {
            return this.scheduleName;
        }

        public LastModifiedDateTime getLastModifiedDateTime() {
            return this.lastModifiedDateTime;
        }

        public void setLastModifiedDateTime(LastModifiedDateTime lastModifiedDateTime) {
            this.lastModifiedDateTime = lastModifiedDateTime;
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof ScheduleDescriptor)) {
                return false;
            }

            ScheduleDescriptor otherScheduleDescriptor = (ScheduleDescriptor) other;
            return Objects.equals(schedule, otherScheduleDescriptor.schedule)
                    && Objects.equals(scheduleName, otherScheduleDescriptor.scheduleName)
                    && Objects.equals(lastModifiedDateTime, otherScheduleDescriptor.lastModifiedDateTime);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("schedule", schedule)
                    .add("scheduleName", scheduleName)
                    .add("last_modified", lastModifiedDateTime)
                    .toString();
        }
    }

}
