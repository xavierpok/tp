package connexion.ui;

import connexion.commons.core.LogsCenter;
import connexion.model.person.Person;
import connexion.model.person.Schedule;
import connexion.model.tag.Tag;
import javafx.beans.WeakListener;
import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.WeakChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;

import java.util.Comparator;
import java.util.logging.Logger;

import static connexion.commons.util.CollectionUtil.requireAllNonNull;


/**
 * Panel containing the list of persons.
 */
public class PersonViewPanel extends UiPart<Region> {
    private static final String FXML = "PersonViewPanel.fxml";

    public final ObservableValue<Person> person;


    private final Logger logger = LogsCenter.getLogger(PersonViewPanel.class);

    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label company;
    @FXML
    private Label job;
    @FXML
    private Label email;
    @FXML
    private Label markStatus;
    @FXML
    private FlowPane tags;
    @FXML
    private Label lastModifiedDateTime;

    @FXML
    private Label noteHeader;
    @FXML
    private Label note;

    @FXML
    private Label schedule;
    @FXML
    private Label scheduleName;

    /**
     * Creates a {@code PersonViewPanel} with the given {@code Person} and index to display.
     */
    public PersonViewPanel(ObservableValue<Person> person) {
        super(FXML);
        this.person = person;
        WeakChangeListener<Person> listener = new WeakChangeListener<Person>(this::updateLabels);
        this.person.addListener(listener);
    }

    private void updateLabels(ObservableValue< ? extends Person> observable, Person oldPerson, Person newPerson) {
        requireAllNonNull(observable,newPerson);
        //oldPerson is irrelevant here

        name.setText(newPerson.getName().getDetailString());
        phone.setText(newPerson.getPhone().getDetailString());
        company.setText(newPerson.getCompany().getDetailString());
        job.setText(newPerson.getJob().getDetailString());
        email.setText(newPerson.getEmail().getDetailString());
        markStatus.setText(newPerson.getMarkStatus().toString());
        newPerson.getTags().stream()
                .sorted(Comparator.comparing(Tag::getValue))
                .forEach(tag -> tags.getChildren().add(new Label(tag.getListString())));
        lastModifiedDateTime.setText(
                String.format("Last modified : %s", newPerson.getLastModifiedDateTime().toString()));
        schedule.setText(newPerson.getSchedule()
                .map(Schedule::getDetailString).orElse(""));
        scheduleName.setText(newPerson.getScheduleName()
                .map(Object::toString).orElse("No scheduled meetings with this person yet"));
        note.setText(newPerson.getNote().getDetailString());
    }

    /**
     * Constructor for PersonViewPanel
     * */
    public PersonViewPanel() {
        super(FXML);
        this.person = null;
        this.noteHeader.setText("");
        this.name.setText("Welcome to Connexion!");
    }

}
