package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class JobContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        JobContainsKeywordsPredicate firstPredicate = new JobContainsKeywordsPredicate(firstPredicateKeywordList);
        JobContainsKeywordsPredicate secondPredicate = new JobContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        JobContainsKeywordsPredicate firstPredicateCopy = new JobContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different job -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_jobContainsKeywords_returnsTrue() {
        // One keyword
        JobContainsKeywordsPredicate predicate =
                new JobContainsKeywordsPredicate(Collections.singletonList("Engineer"));
        assertTrue(predicate.test(new PersonBuilder().withJob("Software Engineer").build()));

        // Multiple keywords
        predicate = new JobContainsKeywordsPredicate(Arrays.asList("Software", "Engineer"));
        assertTrue(predicate.test(new PersonBuilder().withJob("Software Engineer").build()));

        // Only one matching keyword
        predicate = new JobContainsKeywordsPredicate(Arrays.asList("Data", "Software"));
        assertTrue(predicate.test(new PersonBuilder().withJob("Data Engineer").build()));

        // Mixed-case keywords
        predicate = new JobContainsKeywordsPredicate(Arrays.asList("dATa", "EnginEER"));
        assertTrue(predicate.test(new PersonBuilder().withJob("Data Engineer").build()));
    }

    @Test
    public void test_jobDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        JobContainsKeywordsPredicate predicate = new JobContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withJob("Engineer").build()));

        // Non-matching keyword
        predicate = new JobContainsKeywordsPredicate(Arrays.asList("Analyst"));
        assertFalse(predicate.test(new PersonBuilder().withJob("Data Engineer").build()));

        // Keywords match name, phone, email and company, but does not match job
        predicate = new JobContainsKeywordsPredicate(Arrays
                .asList("Alice", "12345", "alice@email.com", "Street"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withCompany("Jane's Street").withJob("Investment Analyst").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        JobContainsKeywordsPredicate predicate = new JobContainsKeywordsPredicate(keywords);

        String expected = JobContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
