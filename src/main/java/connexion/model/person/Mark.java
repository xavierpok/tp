package connexion.model.person;

import static connexion.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

/**
 * Represents the mark status in the address book.
 * Guarantees: mutable.
 */
public class Mark implements PersonListDetailField<Boolean> {
    public static final String MESSAGE_CONSTRAINTS =
            "Mark Status can only be ★ or ☆";
    private static final String UNMARKED_STAR = "☆";
    private static final String MARKED_STAR = "★";
    private static final String VALIDATION_REGEX = "[★|☆]";

    private boolean markStatus;

    /**
     * Constructs Mark Object.
     * @param status true or false.
     */
    public Mark(Boolean status) {
        requireNonNull(status);
        this.markStatus = status;
    }

    public void mark() {
        this.markStatus = true;
    }

    public void unMark() {
        this.markStatus = false;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Mark)) {
            return false;
        }
        return markStatus == ((Mark) other).markStatus;
    }
    public static boolean isValidMark(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Constructs a @code Mark from a @code String formatted as an accepted format,
     * The accepted formatter may be found as @code LastModifiedDateTime.LASTMODIFIED_FORMATTER.
     * @param mark is a valid string representation of Mark
     * @return the corresponding @code Mark object
     */
    public static Mark fromString(String mark) {
        requireNonNull(mark);
        checkArgument(isValidMark(mark), MESSAGE_CONSTRAINTS);
        if (mark.equals(MARKED_STAR)) {
            return new Mark(true);
        } else {
            return new Mark(false);
        }
    }
    @Override
    public String toString() {
        return (markStatus ? MARKED_STAR : UNMARKED_STAR);
    }

    @Override
    public int hashCode() {
        return String.valueOf(markStatus).hashCode();
    }

    @Override
    public String getDetailString() {
        return (markStatus ? MARKED_STAR : UNMARKED_STAR);
    }

    @Override
    public Boolean getValue() {
        return markStatus;
    }

    @Override
    public String getListString() {
        return (markStatus ? MARKED_STAR : UNMARKED_STAR);
    }
}
