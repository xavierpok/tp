package connexion.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import connexion.testutil.PersonBuilder;

public class NotMarkedPredicateTest {

    @Test
    public void equals() {
        NotMarkedPredicate firstPredicate = new NotMarkedPredicate();
        NotMarkedPredicate secondPredicate = new NotMarkedPredicate();

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same type -> returns true
        assertTrue(firstPredicate.equals(secondPredicate));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));
    }

    @Test
    public void test_personNotMarked_returnsTrue() {
        NotMarkedPredicate predicate = new NotMarkedPredicate();
        assertTrue(predicate.test(new PersonBuilder().withMark(false).build()));
    }

    @Test
    public void test_personIsMarked_returnsFalse() {
        NotMarkedPredicate predicate = new NotMarkedPredicate();
        assertFalse(predicate.test(new PersonBuilder().withMark(true).build()));
    }
}
