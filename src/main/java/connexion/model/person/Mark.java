package connexion.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents the mark status in the address book.
 * Guarantees: mutable.
 */
public class Mark implements PersonListDetailField<Boolean>{
    public static final String MESSAGE_CONSTRAINTS =
            "Mark Status can only be true or false!";

    private boolean markStatus;

    private static final String MARKED_STAR = "★";

    private static final String UNMARKED_STAR = "☆";

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
