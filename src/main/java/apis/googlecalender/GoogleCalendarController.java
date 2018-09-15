package apis.googlecalender;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import data_structure.FileHandler;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static javafx.scene.paint.Color.WHITE;

public class GoogleCalendarController {

    @FXML
    public VBox vbox_main;
    @FXML
    public Label label_head;
    @FXML
    public Label label_head_date;
    @FXML
    public ScrollPane scrollpane;

    private GoogleCalendar gCal;
    private String name = "Eike";
    private int maxResult = 5;
    private Timeline timeline;
    private int updateCicle = 5;
    private Parent root;

    public GoogleCalendarController() {
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);

        KeyFrame frame = new KeyFrame(Duration.minutes(updateCicle), event -> {
            initialize();
        });
        timeline.getKeyFrames().add(frame);
        timeline.play();
    }

    public void initialize() {
        vbox_main.getChildren().clear();
        label_head.setText(name + "'s Google Calender");
        label_head_date.setText(getActualDate());
        gCal = new GoogleCalendar();
        try {
            List<Event> items = gCal.getCalendarData(maxResult);
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
                    System.out.printf("%s (%s)\n", event.getSummary(), start);
                    //System.out.printf(event.getSummary(), start);
                    createDateRow(event.getSummary(), start);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
    }

    private void createDateRow(String subject, DateTime start) {
        HBox hbox = new HBox(10);
        VBox vbox_date = new VBox();
        VBox vbox_subject = new VBox();

        LocalDateTime date = convertDateTime(start);

        getTimeRange(start);

        Label label_date = new Label(String.valueOf(date.getDayOfMonth()));
        Label label_weekday = new Label((date.getDayOfWeek().toString().substring(0,3)));
        Label label_subject = new Label(subject);

        LocalTime range = getTimeRange(start);
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

        styeLabel(label_date,true,15);
        styeLabel(label_weekday, false,12);
        styeLabel(label_subject,true,15);
        styeLabel(label_timeRange,false,12);

        vbox_date.getChildren().addAll(label_date,label_weekday);
        vbox_subject.getChildren().addAll(label_subject, label_timeRange);
        hbox.getChildren().addAll(vbox_date, vbox_subject);
        vbox_main.getChildren().add(hbox);
    }

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
        FileHandler.saveData();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMaxResult(int maxResult) {
        this.maxResult = maxResult;
    }

    public void setUpdateCicle(int updateCicle) {
        this.updateCicle = updateCicle;
    }

    public Parent getRoot() {
        return root;
    }

    public void setRoot(Parent root) {
        this.root = root;
    }

    public String getName() {
        return name;
    }

    public int getMaxResult() {
        return maxResult;
    }

    public int getUpdateCicle() {
        return updateCicle;
    }
}
