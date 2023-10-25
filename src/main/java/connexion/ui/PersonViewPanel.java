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

    @FXML
    private Label noteHeader;
    @FXML
    private Label note;

    @FXML
    private Label scheduleHeader;

    @FXML
    private Label schedule;

    /**
     * Creates a {@code PersonViewPanel} with the given {@code Person} and index to display.
     */
    public PersonViewPanel(Person person) {
        super(FXML);
        this.person = person;
        name.setText(person.getName().getDetailString());
        phone.setText(person.getPhone().getDetailString());
        company.setText(person.getCompany().getDetailString());
        job.setText(person.getJob().getDetailString());
        email.setText(person.getEmail().getDetailString());
        markStatus.setText(person.getMarkStatus().toString());
        person.getTags().stream()
                .sorted(Comparator.comparing(Tag::getValue));
        //schedule.setText(person.getSchedule().getDetailString());
        schedule.setText("Upcoming Meeting: 10 Oct (in 1 year)");
        //note.setText(person.getNote.getDetailString());
        note.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam volutpat, quam ac vehicula tincidunt, justo quam dignissim justo, a feugiat ipsum nunc sit amet tellus. Vestibulum efficitur lectus non dui ullamcorper, a eleifend justo bibendum. Proin id orci a ipsum tincidunt lacinia. Sed at sem eu lectus luctus fringilla. Vivamus non libero et dolor bibendum dapibus. Morbi auctor turpis sit amet ante pellentesque, sed sodales purus fermentum. In hac habitasse platea dictumst. Suspendisse eget vestibulum erat. Phasellus in lectus vitae massa tincidunt sagittis Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam volutpat, quam ac vehicula tincidunt, justo quam dignissim justo, a feugiat ipsum nunc sit amet tellus. Vestibulum efficitur lectus non dui ullamcorper, a eleifend justo bibendum. Proin id orci a ipsum tincidunt lacinia. Sed at sem eu lectus luctus fringilla. Vivamus non libero et dolor bibendum dapibus. Morbi auctor turpis sit amet ante pellentesque, sed sodales purus fermentum. In hac habitasse platea dictumst. Suspendisse eget vestibulum erat. Phasellus in lectus vitae massa tincidunt sagittis Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam volutpat, quam ac vehicula tincidunt, justo quam dignissim justo, a feugiat ipsum nunc sit amet tellus. Vestibulum efficitur lectus non dui ullamcorper, a eleifend justo bibendum. Proin id orci a ipsum tincidunt lacinia. Sed at sem eu lectus luctus fringilla. Vivamus non libero et dolor bibendum dapibus. Morbi auctor turpis sit amet ante pellentesque, sed sodales purus fermentum. In hac habitasse platea dictumst. Suspendisse eget vestibulum erat. Phasellus in lectus vitae massa tincidunt sagittis Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam volutpat, quam ac vehicula tincidunt, justo quam dignissim justo, a feugiat ipsum nunc sit amet tellus. Vestibulum efficitur lectus non dui ullamcorper, a eleifend justo bibendum. Proin id orci a ipsum tincidunt lacinia. Sed at sem eu lectus luctus fringilla. Vivamus non libero et dolor bibendum dapibus. Morbi auctor turpis sit amet ante pellentesque, sed sodales purus fermentum. In hac habitasse platea dictumst. Suspendisse eget vestibulum erat. Phasellus in lectus vitae massa tincidunt sagittis Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam volutpat, quam ac vehicula tincidunt, justo quam dignissim justo, a feugiat ipsum nunc sit amet tellus. Vestibulum efficitur lectus non dui ullamcorper, a eleifend justo bibendum. Proin id orci a ipsum tincidunt lacinia. Sed at sem eu lectus luctus fringilla. Vivamus non libero et dolor bibendum dapibus. Morbi auctor turpis sit amet ante pellentesque, sed sodales purus fermentum. In hac habitasse platea dictumst. Suspendisse eget vestibulum erat. Phasellus in lectus vitae massa tincidunt sagittis Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam volutpat, quam ac vehicula tincidunt, justo quam dignissim justo, a feugiat ipsum nunc sit amet tellus. Vestibulum efficitur lectus non dui ullamcorper, a eleifend justo bibendum. Proin id orci a ipsum tincidunt lacinia. Sed at sem eu lectus luctus fringilla. Vivamus non libero et dolor bibendum dapibus. Morbi auctor turpis sit amet ante pellentesque, sed sodales purus fermentum. In hac habitasse platea dictumst. Suspendisse eget vestibulum erat. Phasellus in lectus vitae massa tincidunt sagittis");
    }
    public PersonViewPanel() {
        super(FXML);
        this.person = null;
        this.noteHeader.setText("");
        this.name.setText("Welcome to Connexion!");
    }

}
