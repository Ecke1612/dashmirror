package main;

import data_structure.FileHandler;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


public class DashmirrorMain extends Application {

    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    private int build = 116;
    private double externalBuilt = (double)build / 100;
    private String appName = "Dashmirror";
    public static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Main.fxml"));
        primaryStage.setTitle(appName + " - build " + externalBuilt);
        primaryStage.setScene(new Scene(root, WIDTH, HEIGHT));
        primaryStage.setFullScreen(true);
        DashmirrorMain.primaryStage = primaryStage;
        primaryStage.show();

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.out.println("Auf Wiedersehen");
                FileHandler.saveData();
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }

}
