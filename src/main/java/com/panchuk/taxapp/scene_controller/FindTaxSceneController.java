package com.panchuk.taxapp.scene_controller;

import com.panchuk.taxapp.DAOException;
import com.panchuk.taxapp.constant.ProjectConstant;
import com.panchuk.taxapp.dao.DAOFactory;
import com.panchuk.taxapp.model.TaxType;
import com.panchuk.taxapp.model.User;
import com.panchuk.taxapp.util.LoggerController;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class FindTaxSceneController implements Initializable {

    @FXML
    private TableView<TaxType> taxesTable;
    @FXML
    private TextField tfStartRange;
    @FXML
    private TextField tfEndRange;
    @FXML
    private TextField tfFindType;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadData();
    }

    @FXML
    public void actionRange() {
        double startRange = Double.parseDouble(tfStartRange.getText());
        double endRange = Double.parseDouble(tfEndRange.getText());
        List<TaxType> taxList;
        try {
            taxList = daoFactory.getTaxDAO().findTaxByFilter(ProjectConstant.FIND_BY_RANGE + startRange + "-" + endRange);
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }

        taxesTable.setItems(FXCollections.observableArrayList(taxList));

    }

    @FXML
    public void actionType() {
        int type = Integer.parseInt(tfFindType.getText());
        List<TaxType> taxList;
        try {
            taxList = daoFactory.getTaxDAO().findTaxByFilter(ProjectConstant.FIND_BY_TYPE + type);
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }

        taxesTable.setItems(FXCollections.observableArrayList(taxList));

    }

    public void loadData() {

        idPaymentCol.setCellValueFactory(new PropertyValueFactory<>("idNumber"));
        taxNameCol.setCellValueFactory(new PropertyValueFactory<>("nameTax"));
        valueCol.setCellValueFactory(new PropertyValueFactory<>("value"));
        amountTaxCol.setCellValueFactory(new PropertyValueFactory<>("amountOfTax"));
        dPaymentCol.setCellValueFactory(new PropertyValueFactory<>("datePayment"));

    }

}
