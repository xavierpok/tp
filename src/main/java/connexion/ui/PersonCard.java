package connexion.ui;

import java.util.Comparator;

import connexion.model.person.Person;
import connexion.model.tag.Tag;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Person person;

    @FXML
    private HBox cardPane;
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
    private Label schedule;
    @FXML
    private Label lastModifiedDateTime;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().getListString());
        company.setText(person.getCompany().getListString());
        job.setText(person.getJob().getListString());
        markStatus.setText(person.getMarkStatus().toString());
        person.getTags().stream()
                .sorted(Comparator.comparing(Tag::getValue))
                .forEach(tag -> tags.getChildren().add(new Label(tag.getListString())));
        //schedule.setText(person.getSchedule.getDetailString());
        schedule.setText("Upcoming Meeting: 10 Oct (in 1 year)");

    }
}
