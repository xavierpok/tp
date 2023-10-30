package connexion.model.person;

import static connexion.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's note in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidNote(String)} and has valid length as declared in
 * {@link #hasValidLength(String)}.
 */
public class Note implements PersonDetailField<String> {

    public static final String MESSAGE_CONSTRAINTS =
            "Note should only contain alphanumeric characters, punctuation and spaces. Can be blank";

    public static final String MESSAGE_CONSTRAINTS_CHARACTER_LIMIT =
            "Character limit of note is 1000\n";

    public static final String VALIDATION_REGEX = "[\\p{Alnum}\\p{Punct} ]*";

    public static final int CHARACTER_LIMIT = 1000;

    private final String note;

    /**
     * Constructs a {@code Note}.
     *
     * @param note A valid note.
     */
    public Note(String note) {
        requireNonNull(note);
        checkArgument(isValidNote(note), MESSAGE_CONSTRAINTS);
        checkArgument(hasValidLength(note), MESSAGE_CONSTRAINTS_CHARACTER_LIMIT);
        this.note = note;
    }

    @Override
    public String toString() {
        return note;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Note)) {
            return false;
        }

        Note otherNote = (Note) other;
        return note.equals(otherNote.note);
    }

    @Override
    public int hashCode() {
        return note.hashCode();
    }

    /**
     * Returns true if a given string is a valid note.
     */
    public static boolean isValidNote(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns true if a given string has a valid length.
     */
    public static boolean hasValidLength(String test) {
        return test.length() <= CHARACTER_LIMIT;
    }

    @Override
    public String getDetailString() {
        return note;
    }

    @Override
    public String getValue() {
        return note;
    }
}
