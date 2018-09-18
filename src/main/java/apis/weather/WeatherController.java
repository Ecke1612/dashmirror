package apis.weather;

import apis.ParentController;
import data_structure.FileHandler;
import data_structure.OnDragDetect;
import data_structure.Vec2;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import main.Controller;

public class WeatherController extends ParentController {

    @FXML
    VBox main_pane;
    @FXML
    Label label_temp;
    @FXML
    Label label_city;
    @FXML
    Label label_wind;
    @FXML
    Label label_wind_inwords;
    @FXML
    ImageView icon;

    private GetWeatherData getWeatherDataAPI;
    private int index;
    private WeatherObject weatherObject = new WeatherObject();
    private Stage settingStage;
    private Controller controller;
    private String storePath = "data/store/weather_";


    public WeatherController() {
        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        KeyFrame frame = new KeyFrame(Duration.minutes(weatherObject.getUpdateCircle()), event -> {
            System.out.println("Weather Updatet - index: " + index);
            System.out.println();
            update();
        });
        timeline.getKeyFrames().add(frame);
        timeline.play();

    }

    public void loadWeatherObject() {
        if(FileHandler.fileExist(storePath + index)) {
            System.out.println("load Object");
            weatherObject = (WeatherObject) FileHandler.loadObjects(storePath + index);
            main_pane.setLayoutX(weatherObject.getPos().getXd());
            main_pane.setLayoutY(weatherObject.getPos().getYd());
            getWeatherDataAPI = new GetWeatherData(weatherObject.getCity());
        } else {
            getWeatherDataAPI = new GetWeatherData(weatherObject.getCity());
            main_pane.setLayoutX(weatherObject.getPos().getXd());
            main_pane.setLayoutY(weatherObject.getPos().getYd());
        }
        update();
        saveData();
    }

    public void initialize() {
        OnDragDetect onDragDetect = new OnDragDetect();
        onDragDetect.onDrag(main_pane, this);
    }

    public void update() {
        if(getWeatherDataAPI.checkCurrentWeather("weather")) {
            label_city.setText(weatherObject.getCity());
            label_temp.setText(getWeatherDataAPI.getCurrTemperature());
            label_wind.setText(getWeatherDataAPI.getCurrWind() + " kmH");
            label_wind_inwords.setText(getWeatherDataAPI.getWindInWords());
            Image image = new Image(this.getClass().getResourceAsStream("/img/weather/" + getWeatherDataAPI.getIcon() + ".png"));
            icon.setImage(image);
        }else {
            System.out.println("City not found");
        }
    }

    public void delete() {
        System.out.println("delete");
        controller.anchorpane.getChildren().remove(main_pane);
        controller.weatherindex--;
        FileHandler.deleteFile(storePath + index);
    }

    public void settings() {
        settingStage = new Stage();
        VBox vbox = new VBox(10);
        Scene scene = new Scene(vbox);
        settingStage.setScene(scene);

        Label name_label = new Label("Stadt Eingeben: ");
        final TextField city_textField = new TextField(weatherObject.getCity());
        Button btn_okay = new Button("Okay");
        Button btn_abort = new Button("Abbrechen");

        HBox hBox1 = new HBox(10);
        hBox1.getChildren().addAll(name_label, city_textField);

        HBox hBox2 = new HBox(10);
        hBox2.getChildren().addAll(btn_abort, btn_okay);

        vbox.getChildren().addAll(hBox1, hBox2);
        vbox.setPadding(new Insets(5));

        btn_abort.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                settingStage.close();
            }
        });

        btn_okay.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                weatherObject.setCity(city_textField.getText());
                getWeatherDataAPI = new GetWeatherData(weatherObject.getCity());
                saveData();
                update();
                settingStage.close();
            }
        });

        settingStage.showAndWait();
    }

    @Override
    public void saveData() {
        weatherObject.setPos(new Vec2(main_pane.getLayoutX(), main_pane.getLayoutY()));
        FileHandler.writeObject(weatherObject, storePath + index);
    }

    public void setController(Controller Controller) {
        this.controller = Controller;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
