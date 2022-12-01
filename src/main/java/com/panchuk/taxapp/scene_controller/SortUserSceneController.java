package com.panchuk.taxapp.scene_controller;

import com.panchuk.taxapp.DAOException;
import com.panchuk.taxapp.MainApplication;
import com.panchuk.taxapp.constant.ProjectConstant;
import com.panchuk.taxapp.dao.DAOFactory;
import com.panchuk.taxapp.model.User;
import com.panchuk.taxapp.util.LoggerController;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class SortUserSceneController implements Initializable {

    @FXML
    private TableView<User> usersTable;
    @FXML
    private TableColumn<User, String> idCol;
    @FXML
    private TableColumn<User, String> fnameCol;
    @FXML
    private TableColumn<User, String> lnameCol;
    @FXML
    private TableColumn<User, String> sexCol;
    @FXML
    private TableColumn<User, String> birthDCol;
    @FXML
    private TableColumn<User, String> emailCol;
    @FXML
    private TableColumn<User, String> taxCol;
    @FXML
    private TableColumn<User, String> editCol;

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
    public void actionSortFirst() {
        List<User> userList;
        try {
            userList = daoFactory.getTaxDAO().findUserByFilter(ProjectConstant.SORT_BY_FIRST_NAME);
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }

        usersTable.setItems(FXCollections.observableArrayList(userList));
    }

    @FXML
    public void actionSortLast() {
        List<User> userList;
        try {
            userList = daoFactory.getTaxDAO().findUserByFilter(ProjectConstant.SORT_BY_LAST_NAME);
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }

        usersTable.setItems(FXCollections.observableArrayList(userList));
    }

    @FXML
    public void actionSortAmount() {

        List<User> userList;
        try {
            userList = daoFactory.getTaxDAO().findUserByFilter(ProjectConstant.SORT_BY_AMOUNT);
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }

        usersTable.setItems(FXCollections.observableArrayList(userList));
    }

    public void loadData() {

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        fnameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lnameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        sexCol.setCellValueFactory(new PropertyValueFactory<>("sex"));
        birthDCol.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        taxCol.setCellValueFactory(new PropertyValueFactory<>("totalAmountOfTax"));
        birthDCol.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));

        Callback<TableColumn<User, String>, TableCell<User, String>> cellFactory = (TableColumn<User, String> param) -> {
            // make cell containing buttons
            return new TableCell<>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {

                        FontAwesomeIconView getIcon = new FontAwesomeIconView(FontAwesomeIcon.ARROW_CIRCLE_ALT_RIGHT);

                        getIcon.setStyle(
                                " -fx-cursor: hand ;"
                                        + "-glyph-size:28px;"
                                        + "-fx-fill:#094180;"
                        );

                        getIcon.setOnMouseClicked((MouseEvent event) -> {
                            User user = usersTable.getSelectionModel().getSelectedItem();
                            MainApplication.getMainAppController().setUser(user);
                        });


                        HBox manageBtn = new HBox(getIcon);
                        manageBtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(getIcon, new Insets(2, 2, 0, 3));

                        setGraphic(manageBtn);

                        setText(null);

                    }
                }

            };
        };

        editCol.setCellFactory(cellFactory);
    }
}
