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
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class GetUserByIdSceneController implements Initializable {

    @FXML
    private TextField tfGetUserById;

    private static DAOFactory daoFactory;

    static {
        try {
            DAOFactory.setDAOFactoryFQN(ProjectConstant.DAO_FACTORY_FQN);
            daoFactory = DAOFactory.getInstance();
        } catch (Exception e) {
            LoggerController.daoException(e, ShowUsersSceneController.class);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void handleButtonGetUserById() {
        String userIdForFindStr = tfGetUserById.getText();
        if (!Validator.isInteger(userIdForFindStr, "id payment")) return;
        int userIdForFind = Integer.parseInt(userIdForFindStr);

        if (!Validator.validationIdNumber(userIdForFind, ProjectConstant.VALID_ID_NUMBER)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("User with such id does not exist!");
            alert.showAndWait();
            return;
        }

        User user;
        try {
            user = daoFactory.getTaxDAO().getUser(userIdForFind);
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
        MainApplication.getMainAppController().setUser(user);
    }

}
