package apis.clock;

import data_structure.FileHandler;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import main.Controller;

import java.time.LocalTime;

public class ClockController {

    @FXML
    Label label_hours;
    @FXML
    Label label_minutes;
    @FXML
    Label label_seconds;

    private Controller controller;
    private int index;
    private boolean delete = false;

    public ClockController() {
        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        KeyFrame frame = new KeyFrame(javafx.util.Duration.seconds(1), event -> {
            initialize();
        });
        timeline.getKeyFrames().add(frame);
        timeline.play();
    }

    public void initialize() {
        LocalTime t = LocalTime.now();
        String optZeroH = "";
        String optZeroM = "";
        String optZeroS = "";
        if(t.getHour() < 10) optZeroH = "0";
        if(t.getMinute() < 10) optZeroM = "0";
        if(t.getSecond() < 10) optZeroS = "0";

        label_hours.setText(optZeroH + String.valueOf(t.getHour()));
        label_minutes.setText(optZeroM + String.valueOf(t.getMinute()));
        label_seconds.setText(optZeroS + String.valueOf(t.getSecond()));
    }

    public void delete() {
        controller.anchorpane.getChildren().remove(Controller.parentCollectorObjects.get(index).getParent());
        Controller.parentCollectorObjects.get(index).setDeleted(true);
        FileHandler.saveData();
    }

    public void settings() {
        System.out.println("settings");
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }
}
