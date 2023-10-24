package connexion.logic.commands;

import static connexion.logic.parser.CliSyntax.PREFIX_SCHEDULE;
import static connexion.logic.parser.CliSyntax.PREFIX_SCHEDULE_NAME;

import static connexion.commons.util.CollectionUtil.requireAllNonNull;
import static connexion.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static java.util.Objects.requireNonNull;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
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
            + PREFIX_SCHEDULE + "2023-12-27-07-00 "
            + PREFIX_SCHEDULE_NAME + "Exam result release";

    public static final String SCHEDULE_ADD_SUCCESS = "Schedule added: %1$s";
    public static final String SCHEDULE_NOT_ADDED = "date must be provided.";

    private final Index index;
    private final Schedule schedule;
    private final ScheduleName scheduleName;

    /**
     * @param index of the person in the filtered person list to add or edit schedule
     * @param schedule the time of the meeting
     * @param scheduleName the name of the meeting
     */
    public ScheduleCommand(Index index, Schedule schedule, ScheduleName scheduleName) {
        requireAllNonNull(index, schedule, scheduleName);

        this.index = index;
        this.schedule = schedule;
        this.scheduleName = scheduleName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEditSchedule = lastShownList.get(index.getZeroBased());
        Person editedPerson = createScheduledPerson(personToEditSchedule, schedule, scheduleName);

        model.setPerson(personToEditSchedule, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(SCHEDULE_ADD_SUCCESS, Messages.format(editedPerson)));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEditSchedule}
     * edited with {@code schedulePersonDescriptor}.
     */
    private static Person createScheduledPerson(Person personToEditSchedule
            ,Schedule schedule, ScheduleName scheduleName) {
        assert personToEditSchedule != null;

        Name name = personToEditSchedule.getName();
        Phone phone = personToEditSchedule.getPhone();
        Email email = personToEditSchedule.getEmail();
        Company company = personToEditSchedule.getCompany();
        Job job = personToEditSchedule.getJob();
        Set<Tag> tags = personToEditSchedule.getTags();
        LastModifiedDateTime updatedLastModifiedDateTime = new LastModifiedDateTime(LocalDateTime
                .now(Clock.systemDefaultZone()));
        Optional<Schedule> updatedSchedule = Optional.ofNullable(schedule);
        Optional<ScheduleName> updatedScheduleName = Optional.ofNullable(scheduleName);
        // While semantically, it would make sense that this would always be changed,
        // We do it like this for consistency with other fields
        // And to move responsibility for updating this field to the parser,
        // Like the other fields.
        return new Person(name, phone, email, company, job, tags
                , updatedSchedule, updatedScheduleName, updatedLastModifiedDateTime);
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
                && schedule.equals(otherScheduleCommand.schedule)
                && scheduleName.equals(otherScheduleCommand.scheduleName);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("Schedule", schedule)
                .add("Schedule Name", scheduleName)
                .toString();
    }

}
