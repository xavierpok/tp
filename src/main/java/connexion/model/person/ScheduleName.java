package connexion.model.person;

import static connexion.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's scheduleName in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidScheduleName(String)}
 */
public class ScheduleName implements PersonListDetailField<String> {

    public static final String MESSAGE_CONSTRAINTS =
            "ScheduleNames should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    private final String scheduleName;

    /**
     * Constructs a {@code ScheduleName}.
     *
     * @param scheduleName A valid scheduleName.
     */
    public ScheduleName(String scheduleName) {
        requireNonNull(scheduleName);
        checkArgument(isValidScheduleName(scheduleName), MESSAGE_CONSTRAINTS);
        this.scheduleName = scheduleName;
    }

    /**
     * Returns true if a given string is a valid scheduleName.
     */
    public static boolean isValidScheduleName(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return scheduleName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ScheduleName)) {
            return false;
        }

        ScheduleName otherScheduleName = (ScheduleName) other;
        return scheduleName.equals(otherScheduleName.scheduleName);
    }

    @Override
    public int hashCode() {
        return scheduleName.hashCode();
    }

    @Override
    public String getDetailString() {
        return scheduleName;
    }

    @Override
    public String getValue() {
        return scheduleName;
    }

    @Override
    public String getListString() {
        return scheduleName;
    }
}
