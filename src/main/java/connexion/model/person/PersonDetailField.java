package connexion.model.person;


/**
 * API for fields in general that are associated with Person, with a defined method for retrieving the string
 * representation for view in a more verbose context.
 * For some fields, they may have only representations in a more verbose context as they are meant to only be seen
 * in a more verbose context.
 * @param <T> the datatype this field wraps around
 */
public interface PersonDetailField<T> extends PersonField<T>{

    /**
     * Returns the user-facing string representation of this field in a detail view.
     */
    public String getDetailString();
}
