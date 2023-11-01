package connexion.ui;

import java.util.logging.Logger;

import connexion.MainApp;
import connexion.commons.core.LogsCenter;
import connexion.commons.util.StringUtil;
import connexion.logic.Logic;
import connexion.model.person.Person;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * The manager of the UI component.
 */
public class UiManager implements Ui {

    public static final String ALERT_DIALOG_PANE_FIELD_ID = "alertDialogPane";
    private static Person personToDisplay = null;

    private static final Logger logger = LogsCenter.getLogger(UiManager.class);
    private static final String ICON_APPLICATION = "/images/address_book_32.png";

    private static MainWindow mainWindow;

    private Logic logic;

    /**
     * Creates a {@code UiManager} with the given {@code Logic}.
     */
    public UiManager(Logic logic) {
        this.logic = logic;
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting UI...");

        //Set the application icon.
        primaryStage.getIcons().add(getImage(ICON_APPLICATION));

        try {
            mainWindow = new MainWindow(primaryStage, logic);
            mainWindow.show(); //This should be called before creating other UI parts
            if (logic.getFilteredPersonList().size() > 0) {
                personToDisplay = logic.getFilteredPersonList().get(0);
            }
            mainWindow.fillInnerParts();
        } catch (Throwable e) {
            logger.severe(StringUtil.getDetails(e));
            showFatalErrorDialogAndShutdown("Fatal error during initializing", e);
        }
    }

    private Image getImage(String imagePath) {
        return new Image(MainApp.class.getResourceAsStream(imagePath));
    }

    /**
     * changes the displayed person
     * @param p the new person to be displayed.
     */
    public static void updatePersonView(Person p) {
/*        mainWindow.fillContactInfo(p);
        personToDisplay = p;*/
    }
    public static void updatePersonView() {
        mainWindow.fillInnerParts();
    }

    void showAlertDialogAndWait(AlertType type, String title, String headerText, String contentText) {
        showAlertDialogAndWait(mainWindow.getPrimaryStage(), type, title, headerText, contentText);
    }

    /**
     * Shows an alert dialog on {@code owner} with the given parameters.
     * This method only returns after the user has closed the alert dialog.
     */
    private static void showAlertDialogAndWait(Stage owner, AlertType type, String title, String headerText,
                                               String contentText) {
        final Alert alert = new Alert(type);
        alert.getDialogPane().getStylesheets().add("view/DarkTheme.css");
        alert.initOwner(owner);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.getDialogPane().setId(ALERT_DIALOG_PANE_FIELD_ID);
        alert.showAndWait();
    }

    /**
     * Shows an error alert dialog with {@code title} and error message, {@code e},
     * and exits the application after the user has closed the alert dialog.
     */
    private void showFatalErrorDialogAndShutdown(String title, Throwable e) {
        logger.severe(title + " " + e.getMessage() + StringUtil.getDetails(e));
        showAlertDialogAndWait(AlertType.ERROR, title, e.getMessage(), e.toString());
        Platform.exit();
        System.exit(1);
    }

}
