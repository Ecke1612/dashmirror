package apis.google_news;

import apis.ParentController;
import data_structure.FileHandler;
import data_structure.OnDragDetect;
import data_structure.Vec2;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import main.Controller;
import main.DashmirrorMain;

import java.util.ArrayList;

public class GoogleNewsController extends ParentController {

    @FXML
    public VBox main_pane;
    @FXML
    public VBox vbox_content;

    private Controller controller;
    private int index;
    private GoogleNews googleNews = new GoogleNews();
    private ArrayList<NewsDataObject> newsDataObjects = new ArrayList<>();
    private GNewsObject gNewsObject = new GNewsObject();
    private String storePath = "data/store/gnews_";
    private int rowIndex = 0;

    public GoogleNewsController() {
        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        KeyFrame frame = new KeyFrame(javafx.util.Duration.minutes(gNewsObject.getUpdateCircle()), event -> {
            update();
        });
        timeline.getKeyFrames().add(frame);
        timeline.play();

        Timeline timlineSecondary = new Timeline();
        timlineSecondary.setCycleCount(Timeline.INDEFINITE);
        KeyFrame frame2 = new KeyFrame(javafx.util.Duration.seconds(15), event -> {
           updateReceivedData();
        });
        timlineSecondary.getKeyFrames().add(frame2);
        timlineSecondary.play();
    }

    public void initialize() {
        OnDragDetect onDragDetect = new OnDragDetect();
        onDragDetect.onDrag(main_pane, this);
    }

    public void update() {
        newsDataObjects = googleNews.getNews();
        vbox_content.getChildren().clear();
        for(int i = 0; i < gNewsObject.getMaxResults(); i++) {
            vbox_content.getChildren().add(createRow(newsDataObjects.get(i)));
        }
    }

    private void updateReceivedData() {
        boolean update = true;
        for(Node n : vbox_content.getChildren()) {
            TitledPane t = (TitledPane) n;
            if(t.isExpanded()) update = false;
        }

        if(update) {
            vbox_content.getChildren().clear();
            for (int i = 0; i < gNewsObject.getMaxResults(); i++) {
                vbox_content.getChildren().add(createRow(newsDataObjects.get(rowIndex)));
                if (rowIndex == newsDataObjects.size()-1) {
                    rowIndex = 0;
                } else {
                    rowIndex++;
                }
            }
        }
    }

    public void loadGNewsObject() {
        if(FileHandler.fileExist(storePath + index)) {
            System.out.println("load Object");
            gNewsObject = (GNewsObject) FileHandler.loadObjects(storePath + index);
            main_pane.setLayoutX(gNewsObject.getPos().getXd());
            main_pane.setLayoutY(gNewsObject.getPos().getYd());
        } else {
            main_pane.setLayoutX(gNewsObject.getPos().getXd());
            main_pane.setLayoutY(gNewsObject.getPos().getYd());
        }
        update();
        saveData();
    }

    private TitledPane createRow(NewsDataObject g) {
        TitledPane titledPane = new TitledPane();
        //titledPane.setMaxHeight(150);
        titledPane.setMaxSize(300,200);
        titledPane.setExpanded(false);
        titledPane.getStylesheets().add(getClass().getResource(DashmirrorMain.StyleClassMain).toExternalForm());

        TextArea tarea_title = new TextArea(g.getTitle());
        //tarea_title.getStylesheets().add(getClass().getResource(DashmirrorMain.StyleClassMain).toExternalForm());
        tarea_title.setWrapText(true);
        tarea_title.setFont(Font.font("System", FontWeight.BOLD, 14));
        tarea_title.setPrefRowCount(2);
        tarea_title.setPrefColumnCount(20);

        titledPane.setGraphic(tarea_title);

        TextArea tarea_content = new TextArea(g.getContent());
        //tarea_content.getStylesheets().add(getClass().getResource(DashmirrorMain.StyleClassMain).toExternalForm());
        tarea_content.setWrapText(true);
        tarea_content.setPrefRowCount(6);
        tarea_content.setPrefColumnCount(26);

        Button btn_toarticle = new Button();
        btn_toarticle.setGraphic(tarea_content);

        titledPane.setContent(btn_toarticle);

        btn_toarticle.setOnAction(event -> {

        });

        return  titledPane;
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
        System.out.println("delete");
        controller.anchorpane.getChildren().remove(main_pane);
        controller.gnewsindex--;
        FileHandler.deleteFile(storePath + index);
    }

    @Override
    public void saveData() {
        System.out.println("save gnews: " + index);
        gNewsObject.setPos(new Vec2(main_pane.getLayoutX(), main_pane.getLayoutY()));
        FileHandler.writeObject(gNewsObject, storePath + index);
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

}
