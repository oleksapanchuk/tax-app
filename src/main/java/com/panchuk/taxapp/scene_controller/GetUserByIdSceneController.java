package com.panchuk.taxapp.scene_controller;

import com.panchuk.taxapp.DAOException;
import com.panchuk.taxapp.MainAppController;
import com.panchuk.taxapp.MainApplication;
import com.panchuk.taxapp.constant.ProjectConstant;
import com.panchuk.taxapp.dao.DAOFactory;
import com.panchuk.taxapp.model.User;
import com.panchuk.taxapp.util.LoggerController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
        int userIdForFind = Integer.parseInt(tfGetUserById.getText());
        User user;
        try {
            user = daoFactory.getTaxDAO().getUser(userIdForFind);
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
        MainApplication.getMainAppController().setUser(user);
    }

}
