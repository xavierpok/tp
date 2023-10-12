package seedu.address.model.person;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

public class LastModifiedDateTime {

    public static final String MESSAGE_CONSTRAINTS =
            "Last modified should consist of a DateTime Object, and creation not exposed to user.";

    private LocalDateTime lastModified;

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
        return DateTimeFormatter
                .ofLocalizedDateTime(FormatStyle.MEDIUM)
                .format(this.lastModified);
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

    @Override
    public int hashCode() {
        return Objects.hash(lastModified);
        // Java docs clarify that __identity__ hash code should be avoided
        // Regular hashing is A-ok
    }

    public LocalDateTime getValue() {
        return lastModified;
    }
}
