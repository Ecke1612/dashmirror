package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DashmirrorMain extends Application {

    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    private int build = 108;
    private double externalBuilt = (double)build / 100;
    private String appName = "Dashmirror";

    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Main.fxml"));
        primaryStage.setTitle(appName + " - build " + externalBuilt);
        primaryStage.setScene(new Scene(root, WIDTH, HEIGHT));
        primaryStage.show();

        App app = new App();
    }

    public int getBuild() {
        return build;
    }

    public String getAppName() {
        return appName;
    }

    public void terminate() {
        System.exit(0);
    }

    public static void main(String[] args) {
        launch(args);
    }

}
