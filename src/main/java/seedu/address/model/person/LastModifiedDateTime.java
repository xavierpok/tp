package seedu.address.model.person;

import java.time.LocalDateTime;

import static java.util.Objects.requireNonNull;

public class LastModifiedDateTime {

    public static final String MESSAGE_CONSTRAINTS =
            "Last modified should consist of a DateTime Object, and creation not exposed to user.";

    private LocalDateTime lastModified;

    public static LastModifiedDateTime DEFAULT_LAST_MODIFIED = new LastModifiedDateTime(LocalDateTime.of(
            10,10,10,10,10));
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
        LastModifiedDateTime otherLMDT = (LastModifiedDateTime) other;
        return lastModified.equals(otherLMDT.lastModified);
    }

    //TODO : Hashing. Hashing is not recommended for LocalDateTime instances.

}
