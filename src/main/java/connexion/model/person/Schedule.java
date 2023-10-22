package connexion.model.person;

import jdk.jshell.spi.ExecutionControl;

import java.time.LocalDateTime;

import static jdk.jshell.spi.ExecutionControl.NotImplementedException;

public abstract class Schedule implements PersonListDetailField<LocalDateTime>{

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
