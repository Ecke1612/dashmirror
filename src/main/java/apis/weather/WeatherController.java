package apis.weather;

import data_structure.Vec2;
import data_structure.storage.StoreWeather;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class WeatherController {

    @FXML
    VBox main_pane;
    @FXML
    Label label_temp;
    @FXML
    Label label_temp_min;
    @FXML
    Label label_temp_max;
    @FXML
    Label label_city;
    @FXML
    Label label_wind;
    @FXML
    Label label_wind_inwords;

    private Weather weatherAPI;
    private String city = "London";
    private Stage settingStage;
    private Timeline timeline;
    private int updateCircle = 10;
    private StoreWeather storeWeather;
    private Vec2 pos = new Vec2(0,0);
    private Parent root;

    public WeatherController() {
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        storeWeather = new StoreWeather(city, updateCircle, pos);
        KeyFrame frame = new KeyFrame(Duration.minutes(updateCircle), event -> {
            initialize();
        });
        timeline.getKeyFrames().add(frame);
        timeline.play();
    }

    public void initialize() {
        weatherAPI = new Weather(city);
        if(weatherAPI.checkCurrentWeather()) {
            label_city.setText(city);
            label_temp.setText(weatherAPI.getTemperature());
            label_temp_min.setText("min " + weatherAPI.getTempMin());
            label_temp_max.setText("max " + weatherAPI.getTempMax());
            label_wind.setText(weatherAPI.getWind() + " kmH");
            label_wind_inwords.setText(weatherAPI.getWindInWords());
        }else {
            System.out.println("City not found");
        }
    }

    public void settings() {
        settingStage = new Stage();
        VBox vbox = new VBox(10);
        Scene scene = new Scene(vbox);
        settingStage.setScene(scene);

        Label name_label = new Label("Stadt Eingeben: ");
        final TextField city_textField = new TextField(city);
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
              city = city_textField.getText();
              initialize();
              settingStage.close();
            }
        });

        settingStage.showAndWait();
    }

    public String getCity() {
        return city;
    }

    public int getUpdateCircle() {
        return updateCircle;
    }

    public void setPos(Vec2 pos) {
        this.pos = pos;
    }

    public Parent getRoot() {
        return root;
    }

    public void setRoot(Parent root) {
        this.root = root;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setUpdateCircle(int updateCircle) {
        this.updateCircle = updateCircle;
    }
}
