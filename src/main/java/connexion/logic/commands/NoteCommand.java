package connexion.logic.commands;

import static connexion.commons.util.CollectionUtil.requireAllNonNull;
import static connexion.logic.parser.CliSyntax.PREFIX_NOTE;
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
import connexion.model.person.Mark;
import connexion.model.person.Name;
import connexion.model.person.Note;
import connexion.model.person.Person;
import connexion.model.person.Phone;
import connexion.model.person.Schedule;
import connexion.model.person.ScheduleName;
import connexion.model.tag.Tag;

/**
 * Adds a note for the person identified using its displayed index from the address book.
 */
public class NoteCommand extends Command {
    public static final String COMMAND_WORD = "note";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds a note for the person identified by the index number used in the displayed person list. \n"
            + "Existing note will be overwritten by the new note.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_NOTE + "[NOTE]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_NOTE + "Will be promoted to Senior Developer next month.";

    public static final String MESSAGE_SUCCESS = "Person: %1$s; Note: %2$s";

    private final Index index;
    private final NoteDescriptor noteDescriptor;


    /**
     * @param index of the person in the filtered person list to edit the note
     * @param noteDescriptor contains the details of the notes
     */
    public NoteCommand(Index index, NoteDescriptor noteDescriptor) {
        requireAllNonNull(index, noteDescriptor);

        this.index = index;
        this.noteDescriptor = noteDescriptor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToNote = lastShownList.get(index.getZeroBased());
        Person notedPerson = createPersonToNote(personToNote, noteDescriptor);

        model.setPerson(personToNote, notedPerson);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(personToNote),
                noteDescriptor.getNote()));
    }

    /**
     * To create a new Person using the personToNoteDescriptor.
     * This is because Person is immutable in AddressBook.
     *
     * @param personToNote the identified Person to be noted.
     * @param noteDescriptor contains the details of the note.
     * @return a new Person.
     */
    private static Person createPersonToNote(
            Person personToNote, NoteCommand.NoteDescriptor noteDescriptor) {
        assert personToNote != null;

        Name updatedName = personToNote.getName();
        Phone updatedPhone = personToNote.getPhone();
        Email updatedEmail = personToNote.getEmail();
        Company updatedCompany = personToNote.getCompany();
        Job updatedJob = personToNote.getJob();
        Mark updatedMark = personToNote.getMarkStatus();
        Set<Tag> updatedTags = personToNote.getTags();
        LastModifiedDateTime updatedLastModifiedDateTime =
                noteDescriptor.getLastModifiedDateTime();
        Note updatedNote = noteDescriptor.getNote();
        Optional<Schedule> updatedSchedule = personToNote.getSchedule();
        Optional<ScheduleName> updatedScheduleName = personToNote.getScheduleName();
        // While semantically, it would make sense that this would always be changed,
        // We do it like this for consistency with other fields
        // And to move responsibility for updating this field to the parser,
        // Like the other fields.
        return new Person(updatedName, updatedPhone,
                updatedEmail, updatedCompany, updatedJob, updatedMark,
                updatedTags, updatedSchedule, updatedScheduleName, updatedLastModifiedDateTime, updatedNote);
    }

    /**
     * Stores the details of the note.
     */
    public static class NoteDescriptor {
        private Note note;
        private LastModifiedDateTime lastModifiedDateTime;

        public NoteDescriptor() {}

        /**
         * Copy constructor.
         */
        public NoteDescriptor(NoteCommand.NoteDescriptor toCopy) {
            setNote(toCopy.note);
            setLastModifiedDateTime(toCopy.lastModifiedDateTime);
        }

        public void setNote(Note note) {
            this.note = note;
        }

        public Note getNote() {
            return note;
        }

        /**
         * Setter for the last modified date & time as a @code LastModifiedDateTime object
         */
        public void setLastModifiedDateTime(LastModifiedDateTime lastModifiedDateTime) {
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
            if (!(other instanceof NoteCommand.NoteDescriptor)) {
                return false;
            }

            NoteCommand.NoteDescriptor otherNoteDescriptor = (NoteCommand.NoteDescriptor) other;
            return Objects.equals(lastModifiedDateTime, otherNoteDescriptor.lastModifiedDateTime)
                    && Objects.equals(note, otherNoteDescriptor.note);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("note", note)
                    .add("last_modified", lastModifiedDateTime)
                    .toString();
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof NoteCommand)) {
            return false;
        }

        NoteCommand otherCommand = (NoteCommand) other;
        return index.equals(otherCommand.index)
                && noteDescriptor.equals(otherCommand.noteDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("personToNoteDescriptor", noteDescriptor)
                .toString();
    }

}
