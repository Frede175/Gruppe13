package ui;

import common.IBusinessFacade;
import common.ICaseLog;
import common.IController;
import common.ILog;
import common.ILoginAttemptLog;
import common.LogType;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * View the log controller
 * 
 * @author Andreas Mølgaard-Andersen
 * @author Lars Bjerregaard Jørgensen
 * @author Frederik Rosenberg
 * @author Mikkel Larsen
 * @author Sebastian Christensen
 * @author Kasper Schødts
 */
public class SeeLogController implements Initializable, IController<MenuController> {

    @FXML
    private AnchorPane appBackground;
    @FXML
    private AnchorPane logPane;
    @FXML
    private ListView<ILog> LogListView;
    @FXML
    private Label noLogFound;
    @FXML
    private MenuButton choice_logType;
    
    /**
     * Reference to the main controller
     */
    private MenuController menuController;

    /**
     * An instance of the LogType class, used for showing a specific type of
     * log.
     */
    private LogType logType;

    /**
     * The business facade used to communicate with business layer
     */
    private IBusinessFacade business;
    
    /**
     * Initializes the controller
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        business = GUI.getInstance().getBusiness();

        setCellFactory();
    }

    /**
     * Loads the edit existing case FMXL.
     *
     * @param event mouse click
     * @throws IOException file not found / null pointer
     */
    @FXML
    private void closeShowLog(MouseEvent event) throws IOException {
        menuController.showBackground();
    }

    /**
     * Sets the requested log type to see all logs. Also sets the text for the
     * dropdown, to the given type.
     *
     * @param event mouse click, on dropdown option
     */
    @FXML
    private void setLogType_All(ActionEvent event) {
        logType = null;
        choice_logType.setText("Alle");
        getAllLogs();
    }

    /**
     * Sets the requested log type to cases viewed. Also sets the text for the
     * dropdown, to the given type.
     *
     * @param event mouse click, on dropdown option
     */
    @FXML
    private void setLogType_ViewedCases(ActionEvent event) {
        logType = LogType.CASE_VIEWED;
        choice_logType.setText("Sete sager");
        getLogOfType();
    }

    /**
     * Sets the requested log type to cases opened. Also sets the text for the
     * dropdown, to the given type.
     *
     * @param event mouse click, on dropdown option
     */
    @FXML
    private void setLogType_CaseOpened(ActionEvent event) {
        logType = LogType.OPEN_CASE;
        choice_logType.setText("Åbnede sager");
        getLogOfType();

    }

    /**
     * Sets the requested log type to cases closed. Also sets the text for the
     * dropdown, to the given type.
     *
     * @param event mouse click, on dropdown option
     */
    @FXML
    private void setLogType_ClosedCases(ActionEvent event) {
        logType = LogType.CLOSE_CASE;
        choice_logType.setText("Lukkede sager");
        getLogOfType();

    }

    /**
     * Sets the requested log type . Also sets the text for the dropdown, to the
     * given type.
     *
     * @param event mouse click, on dropdown option
     */
    @FXML
    private void setLogType_ViewedLog(ActionEvent event) {
        logType = LogType.VIEW_LOG;
        choice_logType.setText("Set log");
        getLogOfType();

    }

    /**
     * Sets the requested log type to user logins. Also sets the text for the
     * dropdown, to the given type.
     *
     * @param event mouse click, on dropdown option
     */
    @FXML
    private void setLogType_LoggedIn(ActionEvent event) {
        logType = LogType.LOGIN;
        choice_logType.setText("Log ind");
        getLogOfType();

    }

    /**
     * Sets the requested log type to user logouts. Also sets the text for the
     * dropdown, to the given type.
     *
     * @param event mouse click, on dropdown option
     */
    @FXML
    private void setLogType_LoggedOut(ActionEvent event) {
        logType = LogType.LOGOUT;
        choice_logType.setText("Log ud");
        getLogOfType();

    }

    /**
     * Sets the requested log type to user who were timeouted Also sets the text
     * for the dropdown, to the given type.
     *
     * @param event mouse click, on dropdown option
     */
    @FXML
    private void setLogType_IdleLogOut(ActionEvent event) {
        logType = LogType.TIMEOUT;
        choice_logType.setText("Inaktivitets Log ud");
        getLogOfType();

    }

    /**
     * Sets the requested log type to login attempts. Also sets the text for the
     * dropdown, to the given type.
     *
     * @param event mouse click, on dropdown option
     */
    @FXML
    private void setLogType_AttemptedLogIn(ActionEvent event) {
        logType = LogType.ATTEMPT_LOGIN;
        choice_logType.setText("Log ind forsøg");
        getLogOfType();

    }

    /**
     * First the list view is formatted, then it Gets logs of the specified type
     * (logType). This is specified for each dropdown menu option click. Also
     * sets the listView's items.
     */
    private void getLogOfType() {
        List<ILog> logs = (List<ILog>) business.getLogsOfType(logType);
        if (logs != null) {
            LogListView.setItems(FXCollections.observableArrayList(logs));
            if (LogListView.getItems().isEmpty()) {
                noLogFound.setVisible(true);
            } else {
                noLogFound.setVisible(false);
            }
        } else {
            noLogFound.setVisible(true);
        }

    }

    /**
     * Formats the listview's cells, then gets all the log entries.
     */
    private void getAllLogs() {
        List<ILog> logs = (List<ILog>) business.getAllLogs();
        if (logs != null) {
            LogListView.setItems(FXCollections.observableArrayList(logs));
            if (LogListView.getItems().isEmpty()) {
                noLogFound.setVisible(true);
            } else {
                noLogFound.setVisible(false);
            }
        } else {
            noLogFound.setVisible(true);
        }

    }

    /**
     * Shows the log items as a standard log item.
     */
    public void setCellFactory() {
        LogListView.setCellFactory(value -> new ListCell<ILog>() {
            @Override
            protected void updateItem(ILog item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    switch (item.getLogType()) {
                        case CASE_VIEWED:
                            ICaseLog caseviewed = (ICaseLog) item;
                            setText("User: " + caseviewed.getUserId() + "\t " + "Så sagen: " + caseviewed.getCaseId() + "\t Dato: " + item.getDate());
                            break;
                        case OPEN_CASE:
                            ICaseLog opencase = (ICaseLog) item;
                            setText("User: " + opencase.getUserId() + "\t " + "Åbnede sagen: " + opencase.getCaseId() + "\t Dato: " + item.getDate());
                            break;
                        case CLOSE_CASE:
                            ICaseLog closecase = (ICaseLog) item;
                            setText("User: " + closecase.getUserId() + "\t " + "Lukket sagen: " + closecase.getCaseId() + "\t Dato: " + item.getDate());
                            break;
                        case LOGIN:
                            setText("User: " + item.getUserId() + "\t " + "Logget ind" + "\t Dato: " + item.getDate());
                            break;
                        case LOGOUT:
                            setText("User: " + item.getUserId() + "\t " + "Logget ud" + "\t Dato: " + item.getDate());
                            break;
                        case TIMEOUT:
                            setText("User: " + item.getUserId() + "\t " + "Logget ud, grundet inaktivitet" + "\t Dato: " + item.getDate());
                            break;
                        case ATTEMPT_LOGIN:
                            ILoginAttemptLog attempt = (ILoginAttemptLog) item;
                            setText("User: " + attempt.getUsername() + "\t " + "Forsøgt login" + "\t Dato: " + item.getDate());
                            break;
                        case VIEW_ALL_CASES:
                            setText("User: " + item.getUserId() + "\t " + "Set alle sager" + "\t Dato: " + item.getDate());
                            break;
                        case VIEW_CASEWORKERS_CASES:
                            setText("User: " + item.getUserId() + "\t " + "Set aktive sager" + "\t Dato: " + item.getDate());
                            break;
                        case VIEW_LOG:
                            setText("User: " + item.getUserId() + "\t " + "Set loggen" + "\t Dato: " + item.getDate());
                            break;
                        default:
                            setText("User: " + item.getUserId() + "\t " + item.getLogType().toString().toLowerCase() + "\t Dato: " + item.getDate());
                            break;
                    }
                }
            }
        });
    }
    
    /**
     * Sets the menu controller
     * @param parrentController the menu controller 
     */
    @Override
    public void setParrentController(MenuController parrentController) {
        menuController = parrentController;
    }
    
    /**
     * Allows the controller to stop threads and clean up
     */
    @Override
    public void unload() {
    }
}
