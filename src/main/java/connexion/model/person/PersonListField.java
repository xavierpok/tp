package connexion.model.person;

/**
 * API for fields in general that are associated with Person, with a defined method for retrieving the string
 * representation for view in an at-a-glance context.
 * @param <T> the datatype this field wraps around
 */
public interface PersonListField<T> extends PersonField<T>{

    /**
     * Returns the user-facing string representation of this field in an at-a-glance view.
     */
    public String getListString();
}
