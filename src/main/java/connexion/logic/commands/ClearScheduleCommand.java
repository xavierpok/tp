package connexion.logic.commands;

import static connexion.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static java.util.Objects.requireNonNull;

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
import connexion.model.person.Mark;
import connexion.model.person.Name;
import connexion.model.person.Note;
import connexion.model.person.Person;
import connexion.model.person.Phone;
import connexion.model.person.Schedule;
import connexion.model.person.ScheduleName;
import connexion.model.tag.Tag;

/**
 * Clears the schedule of a contact identified using it's displayed index from the address book.
 */
public class ClearScheduleCommand extends Command {


    public static final String COMMAND_WORD = "clearschedule";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes the schedule of the person identified by the index number used in the displayed person list\n"
            + "Parameters: INDEX (must be a positive integer and less than 2^31 - 1)";
    public static final String MESSAGE_CLEAR_SCHEDULE_SUCCESS = "Cleared Schedule : %1$s";

    public static final String MESSAGE_NO_SCHEDULE = "No schedule was found for this person.";

    public static final String MESSAGE_CLEAR_NAME_WITHOUT_SCHEDULE_SUCCESS =
            "There was a name for a schedule for this person, but no schedule. The name alone has been cleared : %1$s";


    private final Index index;

    private final ClearedScheduleDescriptor clearedScheduleDescriptor;

    /**
     * @param index of the person in the filtered person list to add or edit schedule.
     * @param clearedScheduleDescriptor descriptor describing the cleared schedule state.
     */
    public ClearScheduleCommand(Index index, ClearedScheduleDescriptor clearedScheduleDescriptor) {
        this.index = index;
        this.clearedScheduleDescriptor = clearedScheduleDescriptor;
    }

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToClear = lastShownList.get(index.getZeroBased());



        if (personToClear.getScheduleName().isEmpty() && personToClear.getSchedule().isEmpty()) {
            // If there is at least one present, attempt to remove.
            throw new CommandException(MESSAGE_NO_SCHEDULE);
        }



        Person clearedSchedulePerson = createClearedSchedulePerson(personToClear, clearedScheduleDescriptor);

        model.setPerson(personToClear, clearedSchedulePerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.setDetailedPerson(clearedSchedulePerson);


        if (personToClear.getScheduleName().isPresent() && personToClear.getSchedule().isEmpty()) {
            // Return slightly different CommandResult message for if only a name w/o a schedule is present.
            // A clause just-in-case for corner cases where there's no schedule but a name (for some reason).
            // Should not happen in normal operation.
            return new CommandResult(String.format(MESSAGE_CLEAR_NAME_WITHOUT_SCHEDULE_SUCCESS,
                    Messages.format(clearedSchedulePerson)));
        } else {
            return new CommandResult(String.format(MESSAGE_CLEAR_SCHEDULE_SUCCESS,
                    Messages.format(clearedSchedulePerson)));
        }

    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ClearScheduleCommand)) {
            return false;
        }

        ClearScheduleCommand otherCommand = (ClearScheduleCommand) other;
        return index.equals(otherCommand.index)
                && clearedScheduleDescriptor.equals(otherCommand.clearedScheduleDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("clearedScheduleDescriptor", clearedScheduleDescriptor)
                .toString();
    }

    /**
     * Creates a person from the clearedScheduleDescriptor that is a copy of personToClearSchedule,
     * except with an empty schedule & updated LastModifiedDateTime.
     */
    private static Person createClearedSchedulePerson(Person personToClearSchedule,
                                                      ClearedScheduleDescriptor clearedScheduleDescriptor) {
        assert personToClearSchedule != null;

        Name name = personToClearSchedule.getName();
        Phone phone = personToClearSchedule.getPhone();
        Email email = personToClearSchedule.getEmail();
        Company company = personToClearSchedule.getCompany();
        Job job = personToClearSchedule.getJob();
        Mark markStatus = personToClearSchedule.getMarkStatus();
        Set<Tag> tags = personToClearSchedule.getTags();
        LastModifiedDateTime updatedLastModifiedDateTime =
                clearedScheduleDescriptor.getLastModifiedDateTime();
        Note note = personToClearSchedule.getNote();
        // While semantically, it would make sense that this would always be changed,
        // We do it like this for consistency with other fields
        // And to move responsibility for updating this field to the parser,
        // Like the other fields.
        Optional<Schedule> updatedSchedule = Optional.empty();
        Optional<ScheduleName> updatedScheduleName = Optional.empty();

        return new Person(name, phone, email, company, job, markStatus, tags,
                updatedSchedule, updatedScheduleName, updatedLastModifiedDateTime, note);
    }

    /**
     * Stores the details of a scheduled meeting with the person.
     */
    public static class ClearedScheduleDescriptor {
        private final LastModifiedDateTime lastModifiedDateTime;
        public ClearedScheduleDescriptor(LastModifiedDateTime lastModifiedDateTime) {
            this.lastModifiedDateTime = lastModifiedDateTime;
        }

        public LastModifiedDateTime getLastModifiedDateTime() {
            return lastModifiedDateTime;
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof ClearScheduleCommand.ClearedScheduleDescriptor)) {
                return false;
            }

            ClearedScheduleDescriptor otherDescriptor = (ClearedScheduleDescriptor) other;
            return this.lastModifiedDateTime.equals(otherDescriptor.lastModifiedDateTime);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this).add("lastModified", lastModifiedDateTime).toString();
        }
    }

}
