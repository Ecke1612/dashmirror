package apis.clock;

import apis.ParentController;
import data_structure.FileHandler;
import data_structure.OnDragDetect;
import data_structure.Vec2;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import main.Controller;

import java.time.LocalTime;

public class ClockController extends ParentController {

    @FXML
    VBox main_pane;
    @FXML
    Label label_hours;
    @FXML
    Label label_minutes;
    @FXML
    Label label_seconds;

    private Controller controller;
    private int index;
    private String storePath = "data/store/clock_";
    private ClockObject clockObject = new ClockObject();

    public ClockController() {
        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        KeyFrame frame = new KeyFrame(javafx.util.Duration.seconds(1), event -> {
            update();
        });
        timeline.getKeyFrames().add(frame);
        timeline.play();
    }

    public void initialize() {
        OnDragDetect onDragDetect = new OnDragDetect();
        onDragDetect.onDrag(main_pane, this);
    }

    public void update() {
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

    public void loadClockObject() {
        if(FileHandler.fileExist(storePath + index)) {
            System.out.println("load Object");
            clockObject = (ClockObject) FileHandler.loadObjects(storePath + index);
            main_pane.setLayoutX(clockObject.getPos().getXd());
            main_pane.setLayoutY(clockObject.getPos().getYd());
        } else {
            main_pane.setLayoutX(clockObject.getPos().getXd());
            main_pane.setLayoutY(clockObject.getPos().getYd());
        }
    }


    public void delete() {
        System.out.println("delete");
        controller.anchorpane.getChildren().remove(main_pane);
        controller.clockindex--;
        FileHandler.deleteFile(storePath + index);
    }

    @Override
    public void saveData() {
        System.out.println("save clock: " + index);
        clockObject.setPos(new Vec2(main_pane.getLayoutX(), main_pane.getLayoutY()));
        FileHandler.writeObject(clockObject, storePath + index);
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

    public void setController(Controller controller) {
        this.controller = controller;
    }
}
