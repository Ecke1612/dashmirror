package apis.googlecalender;

import apis.ParentController;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import data_structure.FileHandler;
import data_structure.OnDragDetect;
import data_structure.Processer;
import data_structure.Vec2;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;
import main.Controller;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;

import main.GCalDataObject;
import main.Google;
import main.Store;

import static javafx.scene.paint.Color.WHITE;

public class GoogleCalendarController extends ParentController {

    @FXML
    public VBox main_pane;
    @FXML
    public VBox vbox_main;
    @FXML
    public Label label_head;
    @FXML
    public Label label_head_date;
    @FXML
    public ScrollPane scrollpane;

    private Controller controller;
    private int index;
    private GCalendarObject gCalObj;
    private String storePath = "data/store/gcal_";


    public GoogleCalendarController() {
        gCalObj = new GCalendarObject();
        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);

        KeyFrame frame = new KeyFrame(Duration.minutes(gCalObj.getUpdateCircle()), event -> {
            System.out.println("Calendar updatet - index" + index);
            System.out.println();
            update();
        });
        timeline.getKeyFrames().add(frame);
        timeline.play();
    }

    public void initialize() {
        OnDragDetect onDragDetect = new OnDragDetect();
        onDragDetect.onDrag(main_pane, this);
        update();
    }

    private void update() {
        vbox_main.getChildren().clear();
        label_head.setText(gCalObj.getName() + "'s Google Calender");
        label_head_date.setText(getActualDate());
        /*GoogleCalendar gCal = new GoogleCalendar();
        try {
            List<Event> items = gCal.getCalendarData(gCalObj.getMaxResult());
            if(items != null) {
                System.out.println("size: " + items.size());
                if (items.isEmpty()) {
                    System.out.println("No upcoming events found.");
                } else {
                    System.out.println("Upcoming events");
                    for (Event event : items) {
                        DateTime start = event.getStart().getDateTime();
                        if (start == null) {
                            start = event.getStart().getDate();
                        }
                        //System.out.printf("%s (%s)\n", event.getSummary(), start);
                        createDateRow(event.getSummary(), start);
                    }
                }
            } else {
                System.out.println("no data arrived");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }*/
        Processer processer = new Processer();
        try {
            processer.startJar("src/main/java/apis/googlecalender/GCalendarapi_External.jar", "windwos");
            Store gCalStore = (Store) FileHandler.loadObjects("gCalData");
            for(GCalDataObject g : gCalStore.getgCalDataObjects()) {
                createDateRow(g);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void delete() {
        System.out.println("delete");
        controller.anchorpane.getChildren().remove(main_pane);
        controller.gcalendarindex--;
        FileHandler.deleteFile(storePath + index);
    }

    public void loadGCalendarObject() {
        if(FileHandler.fileExist(storePath + index)) {
            System.out.println("load Object");
            gCalObj = (GCalendarObject) FileHandler.loadObjects(storePath + index);
            main_pane.setLayoutX(gCalObj.getPos().getXd());
            main_pane.setLayoutY(gCalObj.getPos().getYd());
        } else {
            main_pane.setLayoutX(gCalObj.getPos().getXd());
            main_pane.setLayoutY(gCalObj.getPos().getYd());
        }
        saveData();
    }

    private void createDateRow(GCalDataObject g) {
        System.out.println("called");
        HBox hbox = new HBox(10);
        VBox vbox_date = new VBox();
        VBox vbox_subject = new VBox();

        LocalDateTime date = g.getDate();
        LocalTime range = g.getRange();

        Label label_date = new Label(String.valueOf(date.getDayOfMonth()));
        Label label_weekday = new Label((date.getDayOfWeek().toString().substring(0,3)));
        Label label_subject = new Label(g.getSummery());

        Label label_timeRange;
        if(range == null) {
            String optZeroM = "";
            String optZeroH = "";
            if(date.getMinute() < 10) optZeroM = "0";
            if(date.getHour() < 10) optZeroH = "0";
            label_timeRange = new Label(optZeroH + String.valueOf(date.getHour()) + ":" + optZeroM + String.valueOf(date.getMinute()));
        } else {
            int rangeInMinutes = (range.getHour() * 60) + range.getMinute();
            LocalTime timeRange = date.toLocalTime().plus(rangeInMinutes, ChronoUnit.MINUTES);

            String optZeroM = "";
            String optZeroH = "";
            if(date.getHour() < 10) optZeroH = "0";
            if (date.getMinute() < 10) optZeroM = "0";
            label_timeRange = new Label(optZeroH + String.valueOf(date.getHour()) + ":" + optZeroM + String.valueOf(date.getMinute())
                    + " - " + timeRange.getHour() + ":" + optZeroM + timeRange.getMinute());
        }

        styeLabel(label_date,true,18);
        styeLabel(label_weekday, false,14);
        styeLabel(label_subject,true,18);
        styeLabel(label_timeRange,false,14);

        vbox_date.getChildren().addAll(label_date,label_weekday);
        vbox_subject.getChildren().addAll(label_subject, label_timeRange);
        hbox.getChildren().addAll(vbox_date, vbox_subject);
        vbox_main.getChildren().add(hbox);
    }
/*
    private void createDateRow(String subject, DateTime start) {
        HBox hbox = new HBox(10);
        VBox vbox_date = new VBox();
        VBox vbox_subject = new VBox();

        LocalDateTime date = convertDateTime(start);
        LocalTime range = getTimeRange(start);

        Label label_date = new Label(String.valueOf(date.getDayOfMonth()));
        Label label_weekday = new Label((date.getDayOfWeek().toString().substring(0,3)));
        Label label_subject = new Label(subject);

        Label label_timeRange;
        if(range == null) {
            String optZeroM = "";
            String optZeroH = "";
            if(date.getMinute() < 10) optZeroM = "0";
            if(date.getHour() < 10) optZeroH = "0";
            label_timeRange = new Label(optZeroH + String.valueOf(date.getHour()) + ":" + optZeroM + String.valueOf(date.getMinute()));
        } else {
            int rangeInMinutes = (getTimeRange(start).getHour() * 60) + getTimeRange(start).getMinute();
            LocalTime timeRange = date.toLocalTime().plus(rangeInMinutes, ChronoUnit.MINUTES);

            String optZeroM = "";
            String optZeroH = "";
            if(date.getHour() < 10) optZeroH = "0";
            if (date.getMinute() < 10) optZeroM = "0";
            label_timeRange = new Label(optZeroH + String.valueOf(date.getHour()) + ":" + optZeroM + String.valueOf(date.getMinute())
                    + " - " + timeRange.getHour() + ":" + optZeroM + timeRange.getMinute());
        }

        styeLabel(label_date,true,18);
        styeLabel(label_weekday, false,14);
        styeLabel(label_subject,true,18);
        styeLabel(label_timeRange,false,14);

        vbox_date.getChildren().addAll(label_date,label_weekday);
        vbox_subject.getChildren().addAll(label_subject, label_timeRange);
        hbox.getChildren().addAll(vbox_date, vbox_subject);
        vbox_main.getChildren().add(hbox);
    }
*/
    private LocalDateTime convertDateTime(DateTime gDate) {
        Calendar cal = GregorianCalendar.getInstance();
        cal.setTimeInMillis(gDate.getValue());
        LocalDateTime date = toLocalDateTime(cal);
        return date;
    }

    private LocalTime getTimeRange(DateTime start) {
        try {
            String range = (start.toString()).substring(start.toString().lastIndexOf("+") + 1);
            LocalTime timeRange = LocalTime.parse(range);
            return timeRange;
        } catch (Exception e) {
            return null;
        }
    }

    public void saveData() {
        gCalObj.setPos(new Vec2(main_pane.getLayoutX(), main_pane.getLayoutY()));
        FileHandler.writeObject(gCalObj, storePath + index);
    }

    public static LocalDateTime toLocalDateTime(Calendar calendar) {
        if (calendar == null) {
            return null;
        }
        TimeZone tz = calendar.getTimeZone();
        ZoneId zid = tz == null ? ZoneId.systemDefault() : tz.toZoneId();
        return LocalDateTime.ofInstant(calendar.toInstant(), zid);
    }

    private void styeLabel(Label label, Boolean bold, int fontsize) {
        label.setTextFill(WHITE);
        if(bold) {
            label.setFont(Font.font("System", FontWeight.BOLD, fontsize));
        } else {
            label.setFont(Font.font("System", fontsize));
        }
    }

    private String getActualDate() {
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public void openSettings() {
        System.out.println("Einstellungen");
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void setIndex(int index) {
        this.index = index;
    }

}
