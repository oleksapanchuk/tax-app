package com.panchuk.taxapp.util;

import com.panchuk.taxapp.MainAppController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

public class FxmlLoader {
    private Pane view;

    public Pane getPage(String fileName) {

        try {
            URL fileUrl = MainAppController.class.getResource(fileName + ".fxml");

            if (fileUrl == null) {
                throw new FileNotFoundException("FXML file can't be found");
            }

            view = FXMLLoader.load(fileUrl);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return view;
    }
}
