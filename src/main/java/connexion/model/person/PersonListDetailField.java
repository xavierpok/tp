package connexion.model.person;


/**
 * API for fields in general that are associated with Person, with a defined method for retrieving the string
 * representation for view in both an at-a-glance and a more verbose context.
 * @param <T> the datatype this field wraps around
 */
public interface PersonListDetailField<T> extends PersonDetailField<T>, PersonListField<T> {
}
