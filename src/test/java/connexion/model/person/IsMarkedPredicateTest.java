package connexion.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import connexion.testutil.PersonBuilder;

public class IsMarkedPredicateTest {
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
