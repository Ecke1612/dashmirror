package main;

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
    private int build = 119;
    private double externalBuilt = (double)build / 1000;
    private String appName = "Dashmirror";
    public static Stage primaryStage;
    public static String StyleClassMain = "/img/gui/css/main_css.css";

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Main.fxml"));
        primaryStage.setTitle(appName + " - build " + externalBuilt);
        primaryStage.setScene(new Scene(root, WIDTH, HEIGHT));
        //primaryStage.setFullScreen(true);
        DashmirrorMain.primaryStage = primaryStage;
        primaryStage.show();

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.out.println("Auf Wiedersehen");
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }

}
