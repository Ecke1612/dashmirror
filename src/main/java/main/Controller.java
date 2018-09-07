package main;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;

import java.io.IOException;

public class Controller {

    @FXML
    FlowPane flowpane;

    public void initialize() {

    }

    public void addWeather() {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/api/weather.fxml"));
            flowpane.getChildren().add(root);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
