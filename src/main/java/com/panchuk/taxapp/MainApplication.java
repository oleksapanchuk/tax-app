package com.panchuk.taxapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Objects;

public class MainApplication extends Application {
    static final Logger logger = Logger.getLogger(MainApplication.class);

    private static MainAppController mainAppController;

    @Override
    public void start(Stage stage) throws IOException {

        logger.info("Program started!");

        FXMLLoader loader = new FXMLLoader(
                MainApplication.class.getResource("MainWindowInterface.fxml"));

        Scene scene = new Scene(loader.load());
        scene.setFill(Color.TRANSPARENT);

        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setResizable(false);
        stage.setTitle("Tax Application");
        stage.getIcons().add(new Image(Objects.requireNonNull(
                getClass().getResourceAsStream("/com/panchuk/taxapp/img/money-logo.png"))));

        mainAppController = loader.getController();
        mainAppController.init(stage);

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public static MainAppController getMainAppController() {
        return mainAppController;
    }
}