package com.panchuk.taxapp.scene_controller;

import com.panchuk.taxapp.DAOException;
import com.panchuk.taxapp.MainApplication;
import com.panchuk.taxapp.constant.ProjectConstant;
import com.panchuk.taxapp.dao.DAOFactory;
import com.panchuk.taxapp.model.User;
import com.panchuk.taxapp.util.LoggerController;
import com.panchuk.taxapp.util.Validator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.apache.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

public class AddUserController implements Initializable {

    static final Logger logger = Logger.getLogger(AddUserController.class);

    @FXML
    private TextField tfFirstName;
    @FXML
    private TextField tfLastName;
    @FXML
    private TextField tfEmail;
    @FXML
    private RadioButton rbMale;
    private ToggleGroup sexGroup;
    @FXML
    private RadioButton rbFemale;
    @FXML
    private RadioButton rbOther;
    @FXML
    private DatePicker datePicker;

    private User user;

    private static DAOFactory daoFactory;

    static {
        try {
            DAOFactory.setDAOFactoryFQN(ProjectConstant.DAO_FACTORY_FQN);
            daoFactory = DAOFactory.getInstance();
        } catch (Exception e) {
            LoggerController.daoInstantiatingException(e, ShowUsersSceneController.class);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        sexGroup = new ToggleGroup();
        rbMale.setToggleGroup(sexGroup);
        rbFemale.setToggleGroup(sexGroup);
        rbOther.setToggleGroup(sexGroup);

        rbMale.setSelected(true);

        user = new User();

    }

    @FXML
    private void addAndShowButton() {

        logger.info("Press addAndShow Button");

        String firstName = tfFirstName.getText().trim();
        String lastName = tfLastName.getText().trim();
        String email = tfEmail.getText().trim();
        String birthDate = String.valueOf(datePicker.getValue());

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || birthDate.isEmpty()) {
            showAlert("Please fill ALL DATE");
            return;
        }

        if (!Validator.validationString(firstName, ProjectConstant.VALID_FIRST_NAME)) {
            showAlert("First Name is not valid");
            return;
        }

        if (!Validator.validationString(lastName, ProjectConstant.VALID_LAST_NAME)) {
            showAlert("Last Name is not valid");
            return;
        }

        if (!Validator.validationString(email, ProjectConstant.VALID_EMAIL)) {
            showAlert("Email is not valid");
            return;
        }

        if (!Validator.validationString(birthDate, ProjectConstant.VALID_DATE)) {
            showAlert("Birth date is not valid");
            return;
        }

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setSex(getUserSex());
        user.setDateOfBirth(birthDate);

        try {
            daoFactory.getTaxDAO().insertUser(user);
        } catch (DAOException e) {
            LoggerController.daoException(e, ShowUsersSceneController.class);
        }

        MainApplication.getMainAppController().setUser(user);

    }

    private User.Sex getUserSex() {
        String toggleName = ((RadioButton) sexGroup.getSelectedToggle()).getText();
        if (toggleName.equals("male")) return User.Sex.male;
        else if (toggleName.equals("female")) return User.Sex.female;
        else return User.Sex.other;
    }

    private void showAlert(String text) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
    }

}
