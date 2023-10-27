package connexion.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import connexion.testutil.PersonBuilder;

public class NotMarkedPredicateTest {
    @Test
    public void test_personNotMarked_returnsTrue() {
        NotMarkedPredicate predicate = new NotMarkedPredicate();
        assertTrue(predicate.test(new PersonBuilder().build()));
    }

    @Test
    public void test_personIsMarked_returnsFalse() {
        NotMarkedPredicate predicate = new NotMarkedPredicate();
        assertFalse(predicate.test(new PersonBuilder().buildMarked()));
    }
}
