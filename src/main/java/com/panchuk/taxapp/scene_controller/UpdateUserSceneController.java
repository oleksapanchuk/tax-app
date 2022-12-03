package com.panchuk.taxapp.scene_controller;

import com.panchuk.taxapp.DAOException;
import com.panchuk.taxapp.MainApplication;
import com.panchuk.taxapp.constant.ProjectConstant;
import com.panchuk.taxapp.dao.DAOFactory;
import com.panchuk.taxapp.model.User;
import com.panchuk.taxapp.util.LoggerController;
import com.panchuk.taxapp.util.Validator;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.util.Optional;

public class UpdateUserSceneController {
    static final Logger logger = Logger.getLogger(UpdateUserSceneController.class);
    private Stage stage;
    private User user;
    @FXML
    private TextField tfupfName;
    @FXML
    private TextField tfuplName;
    @FXML
    private TextField tfupEmail;

    private static DAOFactory daoFactory;

    static {
        try {
            DAOFactory.setDAOFactoryFQN(ProjectConstant.DAO_FACTORY_FQN);
            daoFactory = DAOFactory.getInstance();
        } catch (Exception e) {
            LoggerController.daoException(e, ShowUsersSceneController.class);
        }
    }

    public void init(Stage stage, User user) {
        this.stage = stage;
        this.user = user;
    }

    @FXML
    private void updateUserBtn() throws DAOException {

        logger.info("updateUser clicked");

        String fName = tfupfName.getText().trim();
        String lName = tfuplName.getText().trim();
        String email = tfupEmail.getText().trim();

        if (fName.isEmpty() || lName.isEmpty() || email.isEmpty()) {
            showAlert("Please fill ALL DATE");
            return;
        }
        logger.info("All field are filled");

        if (!Validator.validationString(fName, ProjectConstant.VALID_FIRST_NAME)) {
            showAlert("First Name is not valid");
            logger.info("First Name is not valid");
            return;
        }
        logger.info("First Name is correct");

        if (!Validator.validationString(lName, ProjectConstant.VALID_LAST_NAME)) {
            showAlert("Last Name is not valid");
            logger.info("Last Name is not valid");
            return;
        }
        logger.info("Last Name is correct");

        if (!Validator.validationString(email, ProjectConstant.VALID_EMAIL)) {
            showAlert("Email is not valid");
            logger.info("Email is not valid");
            return;
        }
        logger.info("Email is correct");

        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setAlertType(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.getDialogPane().setContentText("Are you sure to update user?");
        alert.getDialogPane().setHeaderText("UPDATE USER");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {

            user.setFirstName(fName);
            user.setLastName(lName);
            user.setEmail(email);

            daoFactory.getTaxDAO().updateUser(user);

            MainApplication.getMainAppController().setUser(daoFactory.getTaxDAO().getUser(user.getId()));

            stage.close();

            Alert alertDeleted = new Alert(Alert.AlertType.INFORMATION);
            alertDeleted.getDialogPane().setHeaderText("USER UPDATED");
            alertDeleted.getDialogPane().setContentText("User was successfully updated");

            alertDeleted.showAndWait();

        }
    }


    private void showAlert(String text) {
        logger.info("Show Alert");
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
    }
}
