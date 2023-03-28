package seedu.address.ui;

import java.io.IOException;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.Logic;

/**
 * The manager of the UI component.
 */
public class UiManager implements Ui {

    public static final String ALERT_DIALOG_PANE_FIELD_ID = "alertDialogPane";

    private static final Logger logger = LogsCenter.getLogger(UiManager.class);
    private static final String ICON_APPLICATION = "/images/address_book_32.png";

    private Logic logic;
    private MainWindow mainWindow;

    private ApplicationWindow applicationWindow;

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
            // this is where the window/scenes will be changed

            //this.showMainWindow(primaryStage);
            //mainWindow = new MainWindow(primaryStage, logic);
            this.showApplicationWindow(primaryStage);
            this.showWelcomePage(primaryStage);

        } catch (Throwable e) {
            logger.severe(StringUtil.getDetails(e));
            showFatalErrorDialogAndShutdown("Fatal error during initializing", e);
        }
    }

    /**
     * Shows the main window which is the main application
     * @param primaryStage Primary stage of the whole application
     */
    public void showMainWindow(Stage primaryStage) {
        mainWindow = new MainWindow(primaryStage, logic);
        mainWindow.show(); //This should be called before creating other UI parts
        mainWindow.fillInnerParts();
    }

    /**
     * Shows the application window which is the container for the whole app
     * @param primaryStage Primary stage of the whole application
     */
    public void showApplicationWindow(Stage primaryStage) {
        applicationWindow = new ApplicationWindow(primaryStage, logic);
        applicationWindow.show(); //This should be called before creating other UI parts
    }

    public void showWelcomePage(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/WelcomePage.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(1000);
        primaryStage.setMinHeight(600);
        primaryStage.show();
    }

    private Image getImage(String imagePath) {
        return new Image(MainApp.class.getResourceAsStream(imagePath));
    }

    void showAlertDialogAndWait(Alert.AlertType type, String title, String headerText, String contentText) {
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
        showAlertDialogAndWait(Alert.AlertType.ERROR, title, e.getMessage(), e.toString());
        Platform.exit();
        System.exit(1);
    }

}
