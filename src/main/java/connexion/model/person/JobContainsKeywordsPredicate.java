package connexion.model.person;

import java.util.List;
import java.util.function.Predicate;

import connexion.commons.util.StringUtil;
import connexion.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Job} matches any of the keywords given.
 */
public class JobContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public JobContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getJob().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof JobContainsKeywordsPredicate)) {
            return false;
        }

        JobContainsKeywordsPredicate otherJobContainsKeywordsPredicate =
                (JobContainsKeywordsPredicate) other;
        return keywords.equals(otherJobContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
