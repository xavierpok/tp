package connexion.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import connexion.testutil.PersonBuilder;

public class IsMarkedPredicateTest {

    @Test
    public void equals() {
        IsMarkedPredicate firstPredicate = new IsMarkedPredicate();
        IsMarkedPredicate secondPredicate = new IsMarkedPredicate();

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
    public void test_personIsMarked_returnsTrue() {
        IsMarkedPredicate predicate = new IsMarkedPredicate();
        assertTrue(predicate.test(new PersonBuilder().buildMarked()));
    }

    @Test
    public void test_personNotMarked_returnsFalse() {
        IsMarkedPredicate predicate = new IsMarkedPredicate();
        assertFalse(predicate.test(new PersonBuilder().build()));
    }
}
