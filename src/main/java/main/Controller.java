package main;

import apis.clock.ClockController;
import apis.googlecalender.GoogleCalendarController;
import apis.weather.Weather;
import apis.weather.WeatherController;
import data_structure.DataCollector;
import data_structure.FileHandler;
import data_structure.storage.StorageObject;
import data_structure.storage.StoreClock;
import data_structure.storage.StoreGCalendar;
import data_structure.storage.StoreWeather;
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

    private ArrayList<Parent> parentlist = new ArrayList<>();
    public static DataCollector dataCollector = new DataCollector();
    public static StorageObject storageObject = new StorageObject();


    public void initialize() throws IOException {
        if(FileHandler.fileExist("data/storage")) {
            storageObject = (StorageObject) FileHandler.loadObjects("data/storage");
            loadStoredData();
        }
    }

    public void addWeather() {
        Parent root = null;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/api/weather.fxml"));
            root = fxmlLoader.load();
            WeatherController weatherController = fxmlLoader.getController();
            root = positioning(root);
            onDrag(root);
            anchorpane.getChildren().add(root);
            weatherController.setRoot(root);
            dataCollector.addWeatherController(weatherController);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addGoogleCalendar() {
        Parent root = null;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/api/googleCalendar.fxml"));
            root = fxmlLoader.load();
            GoogleCalendarController googleCalendarController = fxmlLoader.getController();
            root = positioning(root);
            onDrag(root);
            anchorpane.getChildren().add(root);
            googleCalendarController.setRoot(root);
            dataCollector.addGoogleCalendarController(googleCalendarController);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addClock() {
        Parent root = null;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/api/clock.fxml"));
            root = fxmlLoader.load();
            ClockController clockController = fxmlLoader.getController();
            root = positioning(root);
            onDrag(root);
            anchorpane.getChildren().add(root);
            clockController.setRoot(root);
            dataCollector.addClockController(clockController);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadStoredData() throws IOException {
        //------------WEATHER-------------------------------------------------------------------------------------------
        for(StoreWeather w : storageObject.getStoreWeathers()) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/api/weather.fxml"));
            Parent rootW = fxmlLoader.load();
            WeatherController wCTR = fxmlLoader.getController();
            wCTR.setCity(w.getCity());
            wCTR.setUpdateCircle(w.getUpdateCircle());
            rootW.setLayoutX(w.getPos().getXd());
            rootW.setLayoutY(w.getPos().getYd());
            wCTR.setRoot(rootW);
            wCTR.initialize();
            dataCollector.addWeatherController(wCTR);
            onDrag(rootW);
            anchorpane.getChildren().add(rootW);
        }
        //--------------------------------------------------------------------------------------------------------------
        //------------GoogleCalendar------------------------------------------------------------------------------------
        for(StoreGCalendar g : storageObject.getStoreGCalendars()) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/api/googleCalendar.fxml"));
            Parent rootG = fxmlLoader.load();
            GoogleCalendarController gCTR = fxmlLoader.getController();;
            gCTR.setName(g.getName());
            gCTR.setMaxResult(g.getMaxResult());
            gCTR.setUpdateCicle(g.getUpdateCicle());
            rootG.setLayoutX(g.getPos().getXd());
            rootG.setLayoutY(g.getPos().getYd());
            gCTR.setRoot(rootG);
            gCTR.initialize();
            dataCollector.addGoogleCalendarController(gCTR);
            onDrag(rootG);
            anchorpane.getChildren().add(rootG);
        }
        //--------------------------------------------------------------------------------------------------------------
        //------------Clock---------------------------------------------------------------------------------------------
        for(StoreClock c : storageObject.getStoreClocks()) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/api/clock.fxml"));
            Parent rootC = fxmlLoader.load();
            ClockController cCTR = fxmlLoader.getController();
            rootC.setLayoutX(c.getPos().getXd());
            rootC.setLayoutY(c.getPos().getYd());
            cCTR.setRoot(rootC);
            dataCollector.addClockController(cCTR);
            onDrag(rootC);
            anchorpane.getChildren().add(rootC);
        }
        //--------------------------------------------------------------------------------------------------------------
        //------------???????-------------------------------------------------------------------------------------------

        //--------------------------------------------------------------------------------------------------------------
    }

    private Parent positioning(Parent root) {
        root.setLayoutX((anchorpane.getWidth() / 2) - (root.getLayoutBounds().getWidth()));
        root.setLayoutY(anchorpane.getHeight() / 2 - (root.getLayoutBounds().getHeight()));
        parentlist.add(root);
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
            }
        });

    }
}

