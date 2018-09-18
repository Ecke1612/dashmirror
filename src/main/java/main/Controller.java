package main;

import apis.clock.ClockController;
import apis.google_news.GoogleNewsController;
import apis.googlecalender.GoogleCalendarController;
import apis.weather.WeatherController;
import data_structure.FileHandler;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.File;
import java.io.IOException;

public class Controller {

    @FXML
    public AnchorPane anchorpane;

    public int weatherindex = 0;
    public int gcalendarindex = 0;
    public int gnewsindex = 0;
    public int clockindex = 0;


    public void initialize() throws IOException {
        if(!FileHandler.fileExist("data")) FileHandler.createDir("data");
        if(!FileHandler.fileExist("data/store")) FileHandler.createDir("data/store");
        try {
            loadStoredData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addWeather() {
        addWidget("weather", "/fxml/api/weather.fxml", false, "");
    }

    public void addGoogleCalendar() {
        addWidget("gcal", "/fxml/api/googleCalendar.fxml", false, "");
    }

    public void addClock() {
        addWidget("clock", "/fxml/api/clock.fxml", false, "");
    }

    public void addGoogleNachrichten() {
        addWidget("gnews", "/fxml/api/googleNews.fxml", false, "");
    }



    public void loadStoredData() throws IOException {
        File[] fileArray = FileHandler.getFileInDir("data/store");
        if(fileArray.length != 0) {
            for(File f : fileArray) {
                String[] name = f.getName().split("_");
                switch(name[0]) {
                    case "weather":
                        addWidget("weather", "/fxml/api/weather.fxml", true, f.getName());
                        break;
                    case "gcal":
                        addWidget("gcal", "/fxml/api/googleCalendar.fxml", true, f.getName());
                        break;
                    case "clock":
                        addWidget("clock", "/fxml/api/clock.fxml", true, f.getName());
                        break;
                    case "gnews":
                        addWidget("gnews", "/fxml/api/googleNews.fxml", true, f.getName());
                }
            }
        }
    }

    private void addWidget(String type, String path, boolean load, String name) {
        Parent root = null;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(path));
            root = fxmlLoader.load();
            anchorpane.getChildren().add(root);
            switch(type) {
                case "weather":
                    WeatherController weatherController = fxmlLoader.getController();
                    weatherController.setIndex(weatherindex);
                    weatherController.setController(this);
                    if(load) FileHandler.renameFile("data/store/" + name, "data/store/weather_" + weatherindex);
                    weatherController.loadWeatherObject();
                    weatherindex++;
                    break;
                case "gcal":
                    GoogleCalendarController googleCalendarController = fxmlLoader.getController();
                    googleCalendarController.setIndex(gcalendarindex);
                    googleCalendarController.setController(this);
                    if(load) FileHandler.renameFile("data/store/" + name, "data/store/gcal_" + gcalendarindex);
                    googleCalendarController.loadGCalendarObject();
                    gcalendarindex++;
                    break;
                case "clock":
                    ClockController clockController = fxmlLoader.getController();
                    clockController.setIndex(clockindex);
                    clockController.setController(this);
                    if(load) FileHandler.renameFile("data/store/" + name, "data/store/clock_" + clockindex);
                    clockController.loadClockObject();
                    clockindex++;
                    break;
                case "gnews":
                    GoogleNewsController gnewsctr = fxmlLoader.getController();
                    gnewsctr.setIndex(gnewsindex);
                    gnewsctr.setController(this);
                    if(load) FileHandler.renameFile("data/store/" + name, "data/store/gnews_" + gnewsindex);
                    gnewsctr.loadGNewsObject();
                    gnewsindex++;
                    break;
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void fullscreen() {
        if(DashmirrorMain.primaryStage.isFullScreen()) {
            DashmirrorMain.primaryStage.setFullScreen(false);
        }else{
            DashmirrorMain.primaryStage.setFullScreen(true);
        }
    }

    private Parent positioning(Parent root) {
        root.setLayoutX((anchorpane.getWidth() / 4) - (root.getLayoutBounds().getWidth()));
        root.setLayoutY(anchorpane.getHeight() / 4 - (root.getLayoutBounds().getHeight()));
        return root;
    }

}

