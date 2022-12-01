package com.panchuk.taxapp.scene_controller;

import com.panchuk.taxapp.DAOException;
import com.panchuk.taxapp.MainApplication;
import com.panchuk.taxapp.constant.ProjectConstant;
import com.panchuk.taxapp.dao.DAOFactory;
import com.panchuk.taxapp.model.TaxType;
import com.panchuk.taxapp.model.User;
import com.panchuk.taxapp.util.LoggerController;
import com.panchuk.taxapp.util.TaxController;
import com.panchuk.taxapp.util.Validator;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class AddTaxSceneController{

    static final Logger logger = Logger.getLogger(AddTaxSceneController.class);

    @FXML
    private TextField tfPayment;
    @FXML
    private TextField tfType;
    @FXML
    private TextField tfValue;
    @FXML
    private DatePicker datePayment;

    private Stage thisStage;
    private User thisUser;

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
        thisStage = stage;
        thisUser = user;
    }

    @FXML
    private void addTax() throws DAOException {

        logger.info("Press addAndShow Button");

        String idPaymentStr = tfPayment.getText().trim();
        String typeStr = tfType.getText().trim();
        String valueStr = tfValue.getText().trim();
        String dateStr = String.valueOf(datePayment.getValue());

        if (idPaymentStr.isEmpty() || typeStr.isEmpty() || valueStr.isEmpty() || dateStr.isEmpty()) {
            showAlert("Please fill ALL DATE");
            return;
        }

        if (!Validator.isInteger(idPaymentStr, "id payment")) return;
        int idPayment = Integer.parseInt(idPaymentStr);
        if (!Validator.validationIdNumberForTax(idPayment)) {
            showAlert("Payment id is not valid");
            return;
        }

        if (!Validator.isInteger(typeStr, "tax type")) return;
        int type = Integer.parseInt(typeStr);
        if (type < 1 || type > 7) {
            showAlert("Tax type is in range [1 - 7]");
            return;
        }

        if (!Validator.isDouble(valueStr, "value")) return;
        double value = Double.parseDouble(valueStr);

        if (!Validator.validationString(dateStr, ProjectConstant.VALID_DATE)) {
            showAlert("Payment date is not valid");
            return;
        }

        List<TaxType> taxes = new ArrayList<>();
        taxes.add(TaxController.createTax(idPayment, type, value, dateStr));

        daoFactory.getTaxDAO().addTaxForUser(thisUser, taxes);

        MainApplication.getMainAppController().setUser(daoFactory.getTaxDAO().getUser(thisUser.getId()));

        thisStage.close();

    }

    private void showAlert(String text) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
    }
}
