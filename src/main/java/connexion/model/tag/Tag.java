package connexion.model.tag;

import static connexion.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

import connexion.model.person.PersonListDetailField;

/**
 * Represents a Tag in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidTagName(String)}
 */
public class Tag implements PersonListDetailField<String> {

    public static final String MESSAGE_CONSTRAINTS = "Tag name(s) should be alphanumeric" +
            " & consist of only one word not separated by whitespace";
    public static final String VALIDATION_REGEX = "\\p{Alnum}+";

    private final String tagName;

    /**
     * Constructs a {@code Tag}.
     *
     * @param tagName A valid tag name.
     */
    public Tag(String tagName) {
        requireNonNull(tagName);
        checkArgument(isValidTagName(tagName), MESSAGE_CONSTRAINTS);
        this.tagName = tagName;
    }

    /**
     * Returns true if a given string is a valid tag name.
     */
    public static boolean isValidTagName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Tag)) {
            return false;
        }

        Tag otherTag = (Tag) other;
        return tagName.equals(otherTag.tagName);
    }

    @Override
    public int hashCode() {
        return tagName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + tagName + ']';
    }

    @Override
    public String getDetailString() {
        return tagName;
    }

    @Override
    public String getValue() {
        return tagName;
    }

    @Override
    public String getListString() {
        return tagName;
    }
}
