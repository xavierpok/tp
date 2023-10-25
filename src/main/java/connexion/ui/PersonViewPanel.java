package connexion.ui;

import connexion.commons.core.LogsCenter;
import connexion.model.person.Person;
import connexion.model.tag.Tag;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

import java.util.Comparator;
import java.util.logging.Logger;

/**
 * Panel containing the list of persons.
 */
public class PersonViewPanel extends UiPart<Region> {
    private static final String FXML = "PersonViewPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonViewPanel.class);

    public final Person person;

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

    /**
     * Creates a {@code PersonViewPanel} with the given {@code Person} and index to display.
     */
    public PersonViewPanel(Person person) {
        super(FXML);
        this.person = person;
        name.setText(person.getName().getListString());
        phone.setText(person.getPhone().toString());
        company.setText(person.getCompany().getListString());
        job.setText(person.getJob().getListString());
        email.setText(person.getEmail().toString());
        markStatus.setText(person.getMarkStatus().toString());
        person.getTags().stream()
                .sorted(Comparator.comparing(Tag::getValue))
                .forEach(tag -> tags.getChildren().add(new Label(tag.getListString())));
        lastModifiedDateTime.setText(
                String.format("Last modified : %s", person.getLastModifiedDateTime().toString()));

    }
    public PersonViewPanel() {
        super(FXML);
        this.person = null;
        this.name.setText("Welcome to Connexion!");
    }

}
