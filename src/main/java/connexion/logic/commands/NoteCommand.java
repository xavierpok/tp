package connexion.logic.commands;

import static connexion.commons.util.CollectionUtil.requireAllNonNull;
import static connexion.logic.parser.CliSyntax.PREFIX_NOTE;
import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.HashSet;
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
    private final PersonToNoteDescriptor personToNoteDescriptor;


    /**
     * @param index of the person in the filtered person list to edit the note
     * @param personToNoteDescriptor contains the details of the noted person
     */
    public NoteCommand(Index index, PersonToNoteDescriptor personToNoteDescriptor) {
        requireAllNonNull(index, personToNoteDescriptor);

        this.index = index;
        this.personToNoteDescriptor = personToNoteDescriptor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToNote = lastShownList.get(index.getZeroBased());
        Person notedPerson = createPersonToNote(personToNote, personToNoteDescriptor);

        model.setPerson(personToNote, notedPerson);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(personToNote),
                personToNoteDescriptor.getNote().get()));
    }

    /**
     * To create a new Person using the personToNoteDescriptor.
     * This is because Person is immutable in AddressBook.
     *
     * @param personToNote the identified Person to be noted.
     * @param personToNoteDescriptor contains the details of the noted Person.
     * @return a new Person.
     */
    private static Person createPersonToNote(
            Person personToNote, NoteCommand.PersonToNoteDescriptor personToNoteDescriptor) {
        assert personToNote != null;

        Name updatedName = personToNoteDescriptor.getName().orElse(personToNote.getName());
        Phone updatedPhone = personToNoteDescriptor.getPhone().orElse(personToNote.getPhone());
        Email updatedEmail = personToNoteDescriptor.getEmail().orElse(personToNote.getEmail());
        Company updatedCompany = personToNoteDescriptor.getCompany().orElse(personToNote.getCompany());
        Job updatedJob = personToNoteDescriptor.getJob().orElse(personToNote.getJob());
        Mark updatedMark = personToNoteDescriptor.getMarkStatus().orElse(personToNote.getMarkStatus());
        Set<Tag> updatedTags = personToNoteDescriptor.getTags().orElse(personToNote.getTags());
        LastModifiedDateTime updatedLastModifiedDateTime =
                personToNoteDescriptor.getLastModifiedDateTime()
                        .orElse(personToNote.getLastModifiedDateTime());
        Note updatedNote = personToNoteDescriptor.getNote().orElse(personToNote.getNote());
        // While semantically, it would make sense that this would always be changed,
        // We do it like this for consistency with other fields
        // And to move responsibility for updating this field to the parser,
        // Like the other fields.
        return new Person(updatedName, updatedPhone,
                updatedEmail, updatedCompany, updatedJob, updatedMark,
                updatedTags, updatedLastModifiedDateTime, updatedNote);
    }

    /**
     * Stores the details to note the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class PersonToNoteDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Company company;
        private Job job;
        private Mark markStatus;
        private Set<Tag> tags;

        private LastModifiedDateTime lastModifiedDateTime;

        private Note note;

        public PersonToNoteDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public PersonToNoteDescriptor(NoteCommand.PersonToNoteDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setCompany(toCopy.company);
            setJob(toCopy.job);
            setMarkStatus(toCopy.markStatus);
            setTags(toCopy.tags);
            setLastModifiedDateTime(toCopy.lastModifiedDateTime);
            setNote(toCopy.note);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setCompany(Company company) {
            this.company = company;
        }

        public Optional<Company> getCompany() {
            return Optional.ofNullable(company);
        }

        public void setJob(Job job) {
            this.job = job;
        }

        public Optional<Job> getJob() {
            return Optional.ofNullable(job);
        }

        public void setMarkStatus(Mark markStatus) {
            this.markStatus = markStatus;
        }

        public Optional<Mark> getMarkStatus() {
            return Optional.ofNullable(markStatus);
        }

        public void setNote(Note note) {
            this.note = note;
        }

        public Optional<Note> getNote() {
            return Optional.ofNullable(note);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        /**
         * Setter for the last modified date & time as a @code LastModifiedDateTime object
         */
        public void setLastModifiedDateTime(LastModifiedDateTime lastModifiedDateTime) {
            this.lastModifiedDateTime = lastModifiedDateTime;
        }

        /**
         * Getter for last modified date & time as a @code LastModifiedDateTime object,
         * wrapped in an instance of @code Optional.
         */
        public Optional<LastModifiedDateTime> getLastModifiedDateTime() {
            return Optional.ofNullable(lastModifiedDateTime);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof NoteCommand.PersonToNoteDescriptor)) {
                return false;
            }

            NoteCommand.PersonToNoteDescriptor otherPersonToNoteDescriptor = (NoteCommand.PersonToNoteDescriptor) other;
            return Objects.equals(name, otherPersonToNoteDescriptor.name)
                    && Objects.equals(phone, otherPersonToNoteDescriptor.phone)
                    && Objects.equals(email, otherPersonToNoteDescriptor.email)
                    && Objects.equals(company, otherPersonToNoteDescriptor.company)
                    && Objects.equals(job, otherPersonToNoteDescriptor.job)
                    && Objects.equals(markStatus, otherPersonToNoteDescriptor.markStatus)
                    && Objects.equals(tags, otherPersonToNoteDescriptor.tags)
                    && Objects.equals(lastModifiedDateTime, otherPersonToNoteDescriptor.lastModifiedDateTime)
                    && Objects.equals(note, otherPersonToNoteDescriptor.note);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("phone", phone)
                    .add("email", email)
                    .add("company", company)
                    .add("job", job)
                    .add("mark", markStatus)
                    .add("tags", tags)
                    .add("last_modified", lastModifiedDateTime)
                    .add("note", note)
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
                && personToNoteDescriptor.equals(otherCommand.personToNoteDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("personToNoteDescriptor", personToNoteDescriptor)
                .toString();
    }

}
