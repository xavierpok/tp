package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents the mark status in the address book.
 * Guarantees: mutable.
 */
public class Mark {
    public static final String MESSAGE_CONSTRAINTS =
            "Mark Status can only be true or false!";

    private boolean markStatus;

    /**
     * Constructs Mark Object.
     * @param status true or false.
     */
    public Mark(boolean status) {
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
        if (markStatus == true) {
            return "\u2605";
        } else {
            return "\u2606";
        }
    }

    @Override
    public int hashCode() {
        return String.valueOf(markStatus).hashCode();
    }

}
