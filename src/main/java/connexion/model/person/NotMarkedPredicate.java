package connexion.model.person;

import java.util.function.Predicate;

/**
 * Tests that a {@code Person} is not marked.
 */
public class NotMarkedPredicate implements Predicate<Person> {
    @Override
    public boolean test(Person person) {
        return !person.getMarkStatus().getValue();
    }
}
