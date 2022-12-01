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

import java.util.Optional;

public class DeleteTaxSceneController {
    private Stage stage;
    private User user;
    @FXML
    private TextField tfDeleteTax;
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
    private void deleteTaxID() throws DAOException {

        String idPaymentStr = tfDeleteTax.getText().trim();
        if (!Validator.isInteger(idPaymentStr, "id tax")) return;
        int idPayment = Integer.parseInt(idPaymentStr);

        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setAlertType(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.getDialogPane().setContentText("Are you sure to delete tax?");
        alert.getDialogPane().setHeaderText("DELETE TAX");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {

            daoFactory.getTaxDAO().deleteUserTax(idPayment);

            MainApplication.getMainAppController().setUser(daoFactory.getTaxDAO().getUser(user.getId()));

            stage.close();

            Alert alertDeleted = new Alert(Alert.AlertType.INFORMATION);
            alertDeleted.getDialogPane().setHeaderText("TAX DELETED");
            alertDeleted.getDialogPane().setContentText("Tax was successfully deleted");

            alertDeleted.showAndWait();

        }
    }
}
