package main;

import data_structure.Vec2;
import javafx.animation.Timeline;
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
    private Timeline timeline;

    private ArrayList<Parent> parentlist = new ArrayList<>();

    public void initialize() {

    }

    public void addWeather() {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/api/weather.fxml"));
            root = positioning(root);
            onDrag(root);
            anchorpane.getChildren().add(root);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addGoogleCalendar() {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/api/googleCalendar.fxml"));
            root = positioning(root);
            onDrag(root);
            anchorpane.getChildren().add(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addClock() {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/api/clock.fxml"));
            root = positioning(root);
            onDrag(root);
            anchorpane.getChildren().add(root);
        }catch (IOException e) {
            e.printStackTrace();
        }
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

