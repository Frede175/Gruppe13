package ui;

import common.CaseState;
import common.IBusinessFacade;
import common.ICase;
import common.IController;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

/**
 * Show cases controller
 *
 * @author Andreas Mølgaard-Andersen
 * @author Lars Bjerregaard Jørgensen
 * @author Frederik Rosenberg
 * @author Mikkel Larsen
 * @author Sebastian Christensen
 * @author Kasper Schødts
 */
public class ShowCasesController implements Initializable, IController<MenuController> {

    @FXML
    private AnchorPane appBackground;
    @FXML
    private GridPane editCasesGridPane;
    @FXML
    private ListView<ICase> casesListView;
    @FXML
    private Label noCasesFound;
    @FXML
    private AnchorPane seeSpecificCase;
    @FXML
    private Label preview_Name;
    @FXML
    private Label preview_CPR;
    @FXML
    private Label preview_Adress;
    @FXML
    private Label preview_PhoneNumber;
    @FXML
    private Label preview_Email;
    @FXML
    private Label preview_CaseId;
    @FXML
    private Label preview_CaseStatus;
    @FXML
    private Label preview_CaseDate;
    @FXML
    private Label preview_CaseReason;
    @FXML
    private Label preview_CaseOffers;
    @FXML
    private Label preview_WorkerEmail;
    @FXML
    private Label preview_WorkerId;
    @FXML
    private Label preview_WorkerName;
    @FXML
    private AnchorPane closeCaseReview;
    @FXML
    private RadioButton goalAchieved_Yes;
    @FXML
    private TextArea citizenStillRequires;
    @FXML
    private TextArea caseReviewFinalComments;
    @FXML
    private Button closeCaseBtn;
    @FXML
    private Group ClosingDetails;
    @FXML
    private Label preview_Goal;
    @FXML
    private Label preview_FinalComments;
    @FXML
    private Label preview_Requires;
    @FXML
    private Label ClosingDetailsLabel;
    
    /**
     * An instance of the citizens case, for use in the case preview.
     */
    private ICase casepreview;

    /**
     * The business facade used to communicate with business layer
     */
    private IBusinessFacade business;

    /**
     * Ref to main controller
     */
    private MenuController menuController;
    
    

    /**
     * Initializes the controller
     * @param location
     * @param resources 
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        business = GUI.getInstance().getBusiness();

        casesListView.setCellFactory(value -> new ListCell<ICase>() {
            @Override
            protected void updateItem(ICase item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText("(" + item.getCitizen().getCpr() + ") \t\t " + item.getCitizen().getName() + " \t\t Status: " + item.getState());
                }
            }

        });

        casesListView.setItems(FXCollections.observableArrayList((List<ICase>) business.getActiveCases()));
        if (casesListView.getItems().isEmpty()) {
            noCasesFound.setVisible(true);
        } else {
            noCasesFound.setVisible(false);
        }
    }

    /**
     * Loads the main background FMXL.
     *
     * @param event mouse click
     * @throws IOException file not found / null pointer
     */
    @FXML
    private void closeEditCasesView(MouseEvent event) throws IOException {
        menuController.showBackground();
    }

    /**
     * Sets the clicked case from the list view to the specified object of the
     * ICase type.
     *
     * @param event Mouse click
     */
    @FXML
    public void selectCaseFromListView(MouseEvent event) {
        casepreview = casesListView.getSelectionModel().getSelectedItem();
        if (casepreview != null) {
            casepreview = business.findActiveCase(casepreview.getId());
            showCasePreview();
        }
    }

    /**
     * Sets the case's text from the case data.
     */
    private void showCasePreview() {
        editCasesGridPane.setVisible(false);
        seeSpecificCase.setVisible(true);
        makeCasePreview(casepreview);
    }

    /**
     * Makes the case into viewable labels for the gui to show.
     *
     * @param c the case to convert
     */
    private void makeCasePreview(ICase c) {
        if (c.getState() == CaseState.CLOSED) {
            closeCaseBtn.setDisable(true);
            closeCaseBtn.setVisible(false);
            ClosingDetails.setVisible(true);
            ClosingDetailsLabel.setVisible(true);
            preview_FinalComments.setText(c.getFinalComments());
            preview_Goal.setText(c.getGoalAchieved() ? "Ja" : "Nej");
            preview_Requires.setText(c.getCitizenRequires());
        } else {
            closeCaseBtn.setDisable(false);
            closeCaseBtn.setVisible(true);
            ClosingDetails.setVisible(false);
            ClosingDetailsLabel.setVisible(false);
        }
        
        
        preview_CaseId.setText(String.valueOf(c.getId()));
        preview_CaseStatus.setText(c.getState().toString());
        preview_CaseDate.setText(String.valueOf(c.getOpeningDate()));

        preview_CPR.setText(String.valueOf(c.getCitizen().getCpr()));
        preview_Name.setText(c.getCitizen().getName());
        preview_Adress.setText(c.getCitizen().getAddress());
        preview_PhoneNumber.setText(c.getCitizen().getPhoneNumber());
        preview_Email.setText(c.getCitizen().getEmail());

        preview_CaseReason.setText(c.getReason());
        preview_CaseOffers.setText(c.getAvailableOffers());

        preview_WorkerId.setText(c.getCaseWorker().getUserId());
        preview_WorkerName.setText(c.getCaseWorker().getName());
        preview_WorkerEmail.setText(c.getCaseWorker().getEmail());
    }

    /**
     * Cancels the preview of the given case.
     *
     * @param event the mouse click
     */
    @FXML
    private void cancelPreviewCase(ActionEvent event) {
        seeSpecificCase.setVisible(false);
        editCasesGridPane.setVisible(true);
    }

    /**
     * closes the specific case, as from the preview.
     *
     * @param event mouse click on button
     */
    @FXML
    private void closeCase(ActionEvent event) {
        closeCaseReview.setVisible(true);
    }

    /**
     * Hides the case closing review
     *
     * @param event on mouse click
     */
    @FXML
    private void cancelCloseCaseReview(ActionEvent event) {
        closeCaseReview.setVisible(false);
    }

    /**
     * Closes the case on the business facade. Loads the editExistingCases.fxml
     *
     * @param event on mouse click
     * @throws IOException file not found
     */
    @FXML
    private void submitCloseCaseReview(ActionEvent event) throws IOException {
        business.closeCase(casepreview.getId(), getFinalComments(), getCitizenRequires(), goalAchieved());
        menuController.showBackground();
    }

    /**
     * Gets the final comments left by the caseworker.
     *
     * @return final Comments getText.
     */
    private String getFinalComments() {
        return this.caseReviewFinalComments.getText();
    }

    /**
     * Gets the entered comment of what the citizen still requires.
     *
     * @return citizenStillRequires getText
     */
    private String getCitizenRequires() {
        return this.citizenStillRequires.getText();
    }

    /**
     * Returns whether or not the goal of the case was achieved.
     *
     * @return true if yes was selected
     */
    private boolean goalAchieved() {
        return this.goalAchieved_Yes.isSelected();
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
