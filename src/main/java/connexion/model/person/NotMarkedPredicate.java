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

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof NotMarkedPredicate)) {
            return false;
        }
        return true;
    }
}
