package apis.google_news;

import data_structure.FileHandler;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import main.Controller;

public class GoogleNewsController {

    private Controller controller;
    private int index;
    private boolean delete = false;
    private int updateCircle = 5;

    public GoogleNewsController() {
        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        KeyFrame frame = new KeyFrame(javafx.util.Duration.minutes(updateCircle), event -> {
            initialize();
        });
        timeline.getKeyFrames().add(frame);
        timeline.play();
    }

    public void initialize() {

    }

    public void loadGNewsObject() {

    }

    public void toarticle_1() {

    }

    public void toarticle_2() {

    }

    public void toarticle_3() {

    }

    public void settings() {

    }

    public void delete() {

    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }

    public void setUpdateCircle(int updateCircle) {
        this.updateCircle = updateCircle;
    }

    public Controller getController() {
        return controller;
    }

    public int getIndex() {
        return index;
    }

    public boolean isDelete() {
        return delete;
    }


}
