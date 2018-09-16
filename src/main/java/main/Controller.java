package main;

import apis.clock.ClockController;
import apis.google_news.GoogleNews;
import apis.google_news.GoogleNewsController;
import apis.googlecalender.GoogleCalendarController;
import apis.weather.WeatherController;
import data_structure.FileHandler;
import data_structure.ParentCollectorObject;
import data_structure.storage.*;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.ArrayList;

public class Controller {

    @FXML
    public AnchorPane anchorpane;

    private double x = 0;
    private double y = 0;
    // mouse position
    private double mousex = 0;
    private double mousey = 0;
    //private Node view;
    private boolean dragging = false;

    public static StorageObject storageObject = new StorageObject();
    public static ArrayList<ParentCollectorObject> parentCollectorObjects = new ArrayList<>();


    public void initialize() throws IOException {
        if(FileHandler.fileExist("data/storage")) {
            try {
                storageObject = (StorageObject) FileHandler.loadObjects("data/storage");
                loadStoredData();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void addWeather() {
        addWidet("weather", "/fxml/api/weather.fxml");
    }

    public void addGoogleCalendar() {
        addWidet("gCalendar", "/fxml/api/googleCalendar.fxml");
    }

    public void addClock() {
        addWidet("clock", "/fxml/api/clock.fxml");
    }

    public void addGoogleNachrichten() {
        addWidet("gnews", "/fxml/api/googleNews.fxml");
    }

    public void addWidet(String type, String path) {
        Parent root = null;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(path));
            root = fxmlLoader.load();
            root = positioning(root);
            onDrag(root);
            anchorpane.getChildren().add(root);
            switch(type) {
                case "weather":
                    WeatherController weatherController = fxmlLoader.getController();
                    weatherController.setMainController(this);
                    parentCollectorObjects.add(new ParentCollectorObject(parentCollectorObjects.size(), root, weatherController));
                    break;
                case "gCalendar":
                    GoogleCalendarController googleCalendarController = fxmlLoader.getController();
                    googleCalendarController.setController(this);
                    parentCollectorObjects.add(new ParentCollectorObject(parentCollectorObjects.size(), root, googleCalendarController));
                    break;
                case "clock":
                    ClockController clockController = fxmlLoader.getController();
                    clockController.setController(this);
                    parentCollectorObjects.add(new ParentCollectorObject(parentCollectorObjects.size(), root, clockController));
                    break;
                case "gnews":
                    GoogleNewsController googleNewsController = fxmlLoader.getController();
                    googleNewsController.setController(this);
                    parentCollectorObjects.add(new ParentCollectorObject(parentCollectorObjects.size(), root, googleNewsController));
                    break;
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void loadStoredData() throws IOException {
        for(StoreWeather w : storageObject.getStoreWeathers()) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/api/weather.fxml"));
            Parent rootW = fxmlLoader.load();
            WeatherController wCTR = fxmlLoader.getController();
            wCTR.setMainController(this);
            parentCollectorObjects.add(new ParentCollectorObject(parentCollectorObjects.size(), rootW, wCTR));
            parentCollectorObjects.get(parentCollectorObjects.size() - 1).restoreWeatherData(w);
            wCTR.initialize();
            onDrag(rootW);
            anchorpane.getChildren().add(rootW);

        }
        //--------------------------------------------------------------------------------------------------------------
        //------------GoogleCalendar------------------------------------------------------------------------------------
        for(StoreGCalendar g : storageObject.getStoreGCalendars()) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/api/googleCalendar.fxml"));
            Parent rootG = fxmlLoader.load();
            GoogleCalendarController gCTR = fxmlLoader.getController();
            gCTR.setController(this);
            parentCollectorObjects.add(new ParentCollectorObject(parentCollectorObjects.size(), rootG, gCTR));
            parentCollectorObjects.get(parentCollectorObjects.size() - 1).restoreGCalendarData(g);
            onDrag(rootG);
            anchorpane.getChildren().add(rootG);
        }
        //--------------------------------------------------------------------------------------------------------------
        //------------Clock---------------------------------------------------------------------------------------------
        for(StoreClock c : storageObject.getStoreClocks()) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/api/clock.fxml"));
            Parent rootC = fxmlLoader.load();
            ClockController cCTR = fxmlLoader.getController();
            cCTR.setController(this);
            parentCollectorObjects.add(new ParentCollectorObject(parentCollectorObjects.size(), rootC, cCTR));
            parentCollectorObjects.get(parentCollectorObjects.size() - 1).restoreClockData(c);
            onDrag(rootC);
            anchorpane.getChildren().add(rootC);
        }
        //--------------------------------------------------------------------------------------------------------------
        //------------GNews---------------------------------------------------------------------------------------------
        for(StoreGNews gn : storageObject.getStoreGNews()) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/api/googleNews.fxml"));
            Parent rootGN = fxmlLoader.load();
            GoogleNewsController gnCTR = fxmlLoader.getController();
            gnCTR.setController(this);
            parentCollectorObjects.add(new ParentCollectorObject(parentCollectorObjects.size(), rootGN, gnCTR));
            parentCollectorObjects.get(parentCollectorObjects.size() - 1).restoreGNewsData(gn);
            onDrag(rootGN);
            anchorpane.getChildren().add(rootGN);
        }
        //--------------------------------------------------------------------------------------------------------------

        //--------------------------------------------------------------------------------------------------------------
        //------------???????-------------------------------------------------------------------------------------------

        //--------------------------------------------------------------------------------------------------------------
    }

    public void fullscreen() {
        DashmirrorMain.primaryStage.setFullScreen(true);
    }

    private Parent positioning(Parent root) {
        root.setLayoutX((anchorpane.getWidth() / 4) - (root.getLayoutBounds().getWidth()));
        root.setLayoutY(anchorpane.getHeight() / 4 - (root.getLayoutBounds().getHeight()));
        return root;
    }


    private void onDrag(Parent root) {
        root.onMousePressedProperty().set(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                // record the current mouse X and Y position on Node
                mousex = event.getSceneX();
                mousey = event.getSceneY();
                x = root.getLayoutX();
                y = root.getLayoutY();
            }
        });

        //Event Listener for MouseDragged
        root.onMouseDraggedProperty().set(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                // Get the exact moved X and Y

                double offsetX = event.getSceneX() - mousex;
                double offsetY = event.getSceneY() - mousey;

                x += offsetX;
                y += offsetY;

                double scaledX = x;
                double scaledY = y;

                root.setLayoutX(scaledX);
                root.setLayoutY(scaledY);

                dragging = true;

                // again set current Mouse x AND y position
                mousex = event.getSceneX();
                mousey = event.getSceneY();

                event.consume();
            }
        });

        root.onMouseClickedProperty().set(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                dragging = false;
                FileHandler.saveData();
            }
        });
    }
}

