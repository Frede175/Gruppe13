/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import common.IBusinessFacade;
import common.ILog;
import common.LogType;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

/**
 *
 * @author Sebas
 */
public class SeeLogController implements Initializable {

    @FXML
    private AnchorPane appBackground;
    @FXML
    private AnchorPane inappScreen;
    @FXML
    private Label user_JobTitle;
    @FXML
    private Label user_Name;
    @FXML
    private Label user_Email;
    @FXML
    private Label calendarMonth;
    @FXML
    private Label calendarDate;
    @FXML
    private GridPane openNewCaseGrid;
    @FXML
    private AnchorPane logPane;
    @FXML
    private ListView<ILog> LogListView;
    @FXML
    private Label noLogFound;
    @FXML
    private MenuButton choice_logType;

    private static MainBackgroundController mb;

    /**
     * An instace of the LogType class, used for showing a specific type of log.
     */
    private LogType logType;

    /**
     * An instance of the IdleChecker class, used to count idle time.
     */
    private IdleChecker checker;

    /**
     * The business facade used to communicate with business layer
     */
    private IBusinessFacade business;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        business = GUI.getInstance().getBusiness();

        mb = new MainBackgroundController();
        checker = new IdleChecker(1 * 60, mb);
        Thread idle = new Thread(checker);
        idle.setDaemon(true);
        idle.start();
        
        Calendar cal = Calendar.getInstance();
        Calendar calen = Calendar.getInstance();
        calen.add(Calendar.DATE, 0);
        Date dato = calen.getTime();

        SimpleDateFormat format2 = new SimpleDateFormat("MMM");
        calendarMonth.setText(String.valueOf(format2.format(dato)).substring(0, 1).toUpperCase() + String.valueOf(format2.format(dato)).substring(1));

        SimpleDateFormat format3 = new SimpleDateFormat("d");
        calendarDate.setText(String.valueOf(format3.format(dato)));
        
        user_Name.setText(business.getCaseWorker().getName());
        user_Email.setText(business.getCaseWorker().getEmail());
        user_JobTitle.setText("Sagsbehandler");

        checker.updateLastMove();
        checker.setLogin(true);
    }

    @FXML
    private void OpenNewCase(MouseEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/OpenNewCase.fxml"));
        appBackground.getChildren().setAll(pane);
    }

    @FXML
    private void EditExistingCases(MouseEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/EditExistingCases.fxml"));
        appBackground.getChildren().setAll(pane);
    }

    @FXML
    private void showLog(MouseEvent event) {
    }

    @FXML
    private void Logout(MouseEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/LoginScreen.fxml"));
        appBackground.getChildren().setAll(pane);
    }

    @FXML
    private void closeShowLog(MouseEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/MainBackground.fxml"));
        appBackground.getChildren().setAll(pane);
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
        showStandardLogs();

        LogListView.setItems(FXCollections.observableArrayList((List<ILog>) business.getLogsOfType(logType)));
        if (LogListView.getItems().isEmpty()) {
            noLogFound.setVisible(true);
        } else {
            noLogFound.setVisible(false);
        }
    }

    /**
     * Formats the listview's cells, then gets all the log entries.
     */
    private void getAllLogs() {
        showStandardLogs();
        LogListView.setItems(FXCollections.observableArrayList((List<ILog>) business.getAllLogs()));
        if (LogListView.getItems().isEmpty()) {
            noLogFound.setVisible(true);
        } else {
            noLogFound.setVisible(false);
        }
    }

    public void showStandardLogs() {
        LogListView.setCellFactory(value -> new ListCell<ILog>() {
            @Override
            protected void updateItem(ILog item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText("User: " + item.getUserId() + "\t " + item.getLogType().name() + "\t Date: " + item.getDate());
                }
            }
        });
    }

    @FXML
    private void resetIdle(MouseEvent event) {
        checker.updateLastMove();
    }

}
