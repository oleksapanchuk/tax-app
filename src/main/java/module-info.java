module com.panchuk.taxapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires log4j;
    requires java.mail;
    requires commons.dbcp;
    requires java.sql;
    requires de.jensd.fx.glyphs.fontawesome;


    opens com.panchuk.taxapp to javafx.fxml;
    exports com.panchuk.taxapp;
    exports com.panchuk.taxapp.model;
    exports com.panchuk.taxapp.scene_controller;
    opens com.panchuk.taxapp.scene_controller to javafx.fxml;
    exports com.panchuk.taxapp.util;
    opens com.panchuk.taxapp.util to javafx.fxml;
    opens com.panchuk.taxapp.model to javafx.base;
}