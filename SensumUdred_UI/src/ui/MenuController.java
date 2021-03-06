package ui;

import common.IBusinessFacade;
import common.ICaseWorker;
import common.IController;
import common.IUser;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

/**
 * The menu controller
 * 
 * @author Andreas Mølgaard-Andersen
 * @author Lars Bjerregaard Jørgensen
 * @author Frederik Rosenberg
 * @author Mikkel Larsen
 * @author Sebastian Christensen
 * @author Kasper Schødts
 */
public class MenuController implements Initializable, IController<MainController> {

    @FXML
    private AnchorPane screen;
    @FXML
    private Label user_JobTitle;
    @FXML
    private Label user_Name;
    @FXML
    private Label user_Email;
    @FXML
    private GridPane ViewCasesGrid;
    @FXML
    private GridPane openCaseGrid;
    @FXML
    private GridPane ViewLogGrid;
    
    /**
     * The current controller loaded on the screen
     */
    private IController<MenuController> screenController;
    

    /**
     * The business facade used to communicate with business layer
     */
    private IBusinessFacade business;
    
    /**
     * Reference to main controller
     */
    private MainController mainController;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        business = GUI.getInstance().getBusiness();

        String name = "";
        String email = "";
        String job = "";
        IUser user = business.getUser();
        switch (user.getRole()) {
            case CASEWORKER:
                ICaseWorker caseWorker = business.getCaseWorker();
                name = caseWorker.getName();
                email = caseWorker.getEmail();
                job = "Sagsbehandler";
                ViewLogGrid.setDisable(true);
                ViewLogGrid.setVisible(false);
                break;
            case LOGRESPONSABLE:
                name = user.getName();
                email = user.getUserId();
                job = "Log ansvarlig";
                openCaseGrid.setDisable(true);
                openCaseGrid.setVisible(false);
                ViewCasesGrid.setDisable(true);
                ViewCasesGrid.setVisible(false);
                break;
            default:
                throw new AssertionError(business.getUser().getRole().name());
            
        }
        
        user_Name.setText(name);
        user_Email.setText(email);
        user_JobTitle.setText(job);
        
        loadController("fxml/Background.fxml");
    }

    /**
     * Shows the FXML doc for creating a new case.
     *
     * @param event mouse click
     * @throws java.io.IOException
     */
    @FXML
    public void OpenNewCase(MouseEvent event) throws IOException {
        loadController("fxml/OpenNewCase.fxml");

    }

    /**
     * Shows the FXML doc for editing the existing cases.
     *
     * @param event mouse click
     * @throws java.io.IOException
     */
    @FXML
    public void ShowCases(MouseEvent event) throws IOException {
        loadController("fxml/ShowCases.fxml");
    }

    /**
     * Shows the FXML doc for seeing the log.
     *
     * @param event mouse click
     */
    @FXML
    public void showLog(MouseEvent event) {
        loadController("fxml/SeeLog.fxml");
    }

    /**
     * Show the background/main screen in menu
     */
    public void showBackground() {
        loadController("fxml/Background.fxml");
    }

    /**
     * Sets the main controller
     * @param parrentController the main controller
     */
    @Override
    public void setParrentController(MainController parrentController) {
        mainController = parrentController;
    }
    
    /**
     * Called from parent controller, allowing controller to stop threads and clean up
     */
    @Override
    public void unload() {
        unloadController();
    }

    /**
     * Logout clicked on by user
     * @param event the mouse event
     */
    @FXML
    private void Logout(MouseEvent event) {
        mainController.logout(false);
    }
    
    /**
     * Unload the current screen controller
     */
    private void unloadController() {
        if (screenController != null) {
            screenController.unload();
        }
    }
    
    /**
     * Loads a new screen controller
     * @param url the URL for the .fxml document
     */
    private void loadController(String url) {
        try {
            unloadController();
            screen.getChildren().clear();
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(url));
            Node node = loader.load();
            screenController = loader.getController();
            screenController.setParrentController(this);
            
            AnchorPane.setTopAnchor(node, 0.0);
            AnchorPane.setRightAnchor(node, 0.0);
            AnchorPane.setLeftAnchor(node, 0.0);
            AnchorPane.setBottomAnchor(node, 0.0);
            
            screen.getChildren().add(node);
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
