package com.panchuk.taxapp;

import com.panchuk.taxapp.model.User;
import com.panchuk.taxapp.scene_controller.UserSceneController;
import com.panchuk.taxapp.util.FxmlLoader;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

public class MainAppController {
    static final Logger logger = Logger.getLogger(MainAppController.class);
    @FXML
    private Pane titlePane;
    @FXML
    private ImageView btnMinimize, btnClose;

    @FXML
    private BorderPane borderPane;

    private double x, y;

    public void init(Stage stage) {

        logger.info("MainAppController init");

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

        logger.info("AddUser clicked");

        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("AddUserScene");
        borderPane.setCenter(view);
    }


    @FXML
    public void handleButton2Action() {

        logger.info("GetUserById clicked");

        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("GetUserByIdScene");
        borderPane.setCenter(view);
    }

    @FXML
    public void handleButton3Action() {

        logger.info("FindUser clicked");

        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("FindUserScene");
        borderPane.setCenter(view);
    }

    @FXML
    public void handleButton4Action() {

        logger.info("SortUser clicked");

        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("SortUserScene");
        borderPane.setCenter(view);
    }

    @FXML
    public void handleButton5Action() {

        logger.info("FindTax clicked");

        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("FindTaxScene");
        borderPane.setCenter(view);
    }

    @FXML
    public void handleButton6Action() {

        logger.info("ShowUsers clicked");

        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("ShowUsersScene");
        borderPane.setCenter(view);
    }

    @FXML
    public void setUser(User user) {

        logger.info("Set UserScene");

        UserSceneController.setTempUser(user);
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("UserScene");

        borderPane.setCenter(view);
    }

    @FXML
    public void setStartScene() {

        logger.info("Set UserScene");

        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("StartScene");
        borderPane.setCenter(view);
    }

}