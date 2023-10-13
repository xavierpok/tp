package seedu.address.model.person;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.FormatStyle;
import java.time.format.ResolverStyle;
import java.time.temporal.TemporalAccessor;
import java.util.regex.Pattern;


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
    public static final LocalDateTime DEFAULT_LAST_MODIFIED = LocalDateTime.of(
            10, 10, 10, 10, 10);

    public static final DateTimeFormatter LASTMODIFIED_FORMATTER =
            DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).withResolverStyle(ResolverStyle.STRICT);
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

    /**
     * Returns if a string representation of a date & time is valid (matching the format of this class)
     */
    public static boolean isValidLastModifiedDateTime(String lastModified) {

        ParsePosition parsePosition = new ParsePosition(0);
        TemporalAccessor result = LASTMODIFIED_FORMATTER.parseUnresolved(lastModified,parsePosition);
        return (isNull(result));
        // parseUnresolved returns null if some error is occurred in parsing
        // If some error is run into, then this implies that it's not a valid LastModifiedDateTime.
    }

    @Override
    public String toString() {
        return lastModified.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM));
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

    @Override
    public int hashCode() {
        return lastModified.hashCode();
    }
}
