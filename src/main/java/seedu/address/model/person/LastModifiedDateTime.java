package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;



/**
 * Represents when a Person was last modified in the address book.
 * Guarantees: immutable; is valid as declared in LocalDateTime java API
 */
public class LastModifiedDateTime {

    public static final String MESSAGE_CONSTRAINTS =
            "Last modified should consist of a DateTime Object, and creation not exposed to user.";

    /**
     * Default LastModifiedDateTime when a more meaningful one cannot be found.
     */
    public static final LastModifiedDateTime DEFAULT_LASTMODIFIED =
            new LastModifiedDateTime(LocalDateTime.of(
            10, 10, 10, 10, 10));
    private LocalDateTime lastModified;

    /**
     * Constructs a @code LastModifiedDateTime
     * @param lastModified the @code LocalDateTime represented by this class
     */
    public LastModifiedDateTime(LocalDateTime lastModified) {
        requireNonNull(lastModified);
        this.lastModified = lastModified;
        // no other sanity checking should be required, as the
        // LocalDateTime object encapsulates already.
        // No sanity checking on the SEMANTICS of the provided LocalDateTime is provided.
        // Doing so goes beyond the responsibilities of this class.
    }

    @Override
    public String toString() {
        return lastModified.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof LastModifiedDateTime)) {
            return false;
        }
        // safe as we did type validation above
        LastModifiedDateTime otherLastModifiedDateTime = (LastModifiedDateTime) other;
        return lastModified.equals(otherLastModifiedDateTime.lastModified);
    }

    //TODO : Hashing. Hashing is not recommended for LocalDateTime instances.

}
