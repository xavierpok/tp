package connexion.model.tag;

import java.util.List;
import java.util.function.Predicate;

import connexion.commons.util.StringUtil;
import connexion.commons.util.ToStringBuilder;
import connexion.model.person.Person;

/**
 * Tests that a {@code Person}'s {@code Tag} matches any of the keywords given.
 */
public class TagContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public TagContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(
                        person.getTags()
                                .stream().reduce(
                                        "", (// I'm so sorry for this but checkstyle insists on this
                                                str, tag) -> str + " " + tag.getValue(), (
                                                        str1, str2) -> str1 + " " + str2),
                        keyword));
        //Above line concatenates all tags together with a space in between each one
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TagContainsKeywordsPredicate)) {
            return false;
        }

        TagContainsKeywordsPredicate otherTagContainsKeywordsPredicate =
                (TagContainsKeywordsPredicate) other;
        return keywords.equals(otherTagContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
