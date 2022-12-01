package com.panchuk.taxapp.scene_controller;

import com.panchuk.taxapp.AddTaxSceneController;
import com.panchuk.taxapp.DAOException;
import com.panchuk.taxapp.MainApplication;
import com.panchuk.taxapp.constant.ProjectConstant;
import com.panchuk.taxapp.dao.DAOFactory;
import com.panchuk.taxapp.model.TaxType;
import com.panchuk.taxapp.model.User;
import com.panchuk.taxapp.util.LoggerController;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class UserSceneController implements Initializable {

    private static User tempUser;

    @FXML
    private Label userFullName;
    @FXML
    private Label userSex;
    @FXML
    private Label userEmail;
    @FXML
    private Label userDateOfBirth;
    @FXML
    private Label userAmountOfTax;
    @FXML
    private TableView<TaxType> taxesTable;
    @FXML
    private TableColumn<User, String> idPaymentCol;
    @FXML
    private TableColumn<User, String> taxNameCol;
    @FXML
    private TableColumn<User, String> valueCol;
    @FXML
    private TableColumn<User, String> amountTaxCol;
    @FXML
    private TableColumn<User, String> dPaymentCol;


    private static DAOFactory daoFactory;

    static {
        try {
            DAOFactory.setDAOFactoryFQN(ProjectConstant.DAO_FACTORY_FQN);
            daoFactory = DAOFactory.getInstance();
        } catch (Exception e) {
            LoggerController.daoException(e, ShowUsersSceneController.class);
        }
    }

    @FXML
    private void addTax() throws Exception {

        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("AddTaxScene.fxml"));

        Scene scene = new Scene(loader.load());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Add Tax");
        stage.initStyle(StageStyle.DECORATED);
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/panchuk/taxapp/img/money-logo.png"))));
        stage.show();

        ((AddTaxSceneController) loader.getController()).init(stage, tempUser);

    }

    @FXML
    private void deleteUser() throws DAOException {

        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setAlertType(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.getDialogPane().setContentText("Are you sure to delete user?");
        alert.getDialogPane().setHeaderText("DELETE USER");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {

            daoFactory.getTaxDAO().deleteUser(tempUser);

            Alert alertDeleted = new Alert(Alert.AlertType.INFORMATION);
            alertDeleted.getDialogPane().setHeaderText("USER DELETED");
            alertDeleted.getDialogPane().setContentText("User was successfully deleted");

            MainApplication.getMainAppController().setStartScene();

            alertDeleted.showAndWait();

        }

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        userFullName.setText(tempUser.getFirstName() + " " + tempUser.getLastName());

        userSex.setText(tempUser.getSex().name().toLowerCase());

        userEmail.setText(tempUser.getEmail());

        userDateOfBirth.setText(tempUser.getDateOfBirth());

        userAmountOfTax.setText(String.format("%.4f", tempUser.calculateTotalAmountOfTax()));

        loadData();

        List<TaxType> taxList;
        try {
            taxList = daoFactory.getTaxDAO().getUserTaxes(tempUser.getId());
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }

        taxesTable.setItems(FXCollections.observableArrayList(taxList));

    }

    public static void setTempUser(User user) {
        tempUser = user;
    }

    public void loadData() {

        idPaymentCol.setCellValueFactory(new PropertyValueFactory<>("idNumber"));
        taxNameCol.setCellValueFactory(new PropertyValueFactory<>("nameTax"));
        valueCol.setCellValueFactory(new PropertyValueFactory<>("value"));
        amountTaxCol.setCellValueFactory(new PropertyValueFactory<>("amountOfTax"));
        dPaymentCol.setCellValueFactory(new PropertyValueFactory<>("datePayment"));

    }
}
