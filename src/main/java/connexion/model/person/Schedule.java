package connexion.model.person;


import java.time.LocalDateTime;


/**
 * Class representing a schedule in Person. TO BE IMPLEMENTED
 */
public abstract class Schedule implements PersonListDetailField<LocalDateTime> {

    /**
     * Returns the user-facing string representation of this field in a detail view.
     */
    @Override
    public abstract String getDetailString();

    /**
     * Returns the value within this field.
     */
    @Override
    public abstract LocalDateTime getValue();

    /**
     * Returns the user-facing string representation of this field in an at-a-glance view.
     */
    @Override
    public abstract String getListString();
}
