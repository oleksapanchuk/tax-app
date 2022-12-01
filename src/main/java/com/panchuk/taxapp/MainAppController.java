package com.panchuk.taxapp;

import com.panchuk.taxapp.model.User;
import com.panchuk.taxapp.scene_controller.UserSceneController;
import com.panchuk.taxapp.util.FxmlLoader;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainAppController {
    @FXML
    private Pane titlePane;
    @FXML
    private ImageView btnMinimize, btnClose;

    @FXML
    private BorderPane borderPane;

    private double x, y;

    public void init(Stage stage) {
        titlePane.setOnMousePressed(mouseEvent -> {
            x = mouseEvent.getSceneX();
            y = mouseEvent.getSceneY();
        });

        titlePane.setOnMouseDragged(mouseEvent -> {
            stage.setX(mouseEvent.getScreenX() - x);
            stage.setY(mouseEvent.getScreenY() - y);

        });

        btnClose.setOnMouseClicked(mouseEvent -> stage.close());
        btnMinimize.setOnMouseClicked(mouseEvent -> stage.setIconified(true));

        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("StartScene");
        borderPane.setCenter(view);

    }

    @FXML
    private void handleButton1Action() {
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("AddUserScene");
        borderPane.setCenter(view);
    }


    @FXML
    public void handleButton2Action() {
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("GetUserByIdScene");
        borderPane.setCenter(view);
    }

    @FXML
    public void handleButton3Action() {
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("FindUserScene");
        borderPane.setCenter(view);
    }

    @FXML
    public void handleButton4Action() {
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("SortUserScene");
        borderPane.setCenter(view);
    }

    @FXML
    public void handleButton5Action() {
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("FindTaxScene");
        borderPane.setCenter(view);
    }

    @FXML
    public void handleButton6Action() {
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("ShowUsersScene");
        borderPane.setCenter(view);
    }

    @FXML
    public void setUser(User user) {
        UserSceneController.setTempUser(user);
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("UserScene");

        borderPane.setCenter(view);
    }

    @FXML
    public void setStartScene() {
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("StartScene");
        borderPane.setCenter(view);
    }

}