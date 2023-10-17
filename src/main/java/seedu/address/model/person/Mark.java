package seedu.address.model.person;

import seedu.address.logic.commands.DeleteCommand;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Recipe's favourite status in the recipe book.
 * Guarantees: mutable.
 */
public class Mark {
    public static final String MESSAGE_CONSTRAINTS =
            "Favourites can only be true or false!";

    private boolean markStatus;

    /**
     * Constructs a Fav Object.
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

    public boolean getMarkStatus() {
        return markStatus;
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
