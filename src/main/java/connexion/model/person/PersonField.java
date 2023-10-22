package connexion.model.person;

/**
 * API for fields in general that are associated with Person, but with no defined method to access user-facing
 * string representations
 * @param <T> the datatype this field wraps around
 */
public interface PersonField<T> {

    /**
     * Returns the value within this field.
     */
    public T getValue();
}
