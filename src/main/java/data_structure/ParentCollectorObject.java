package data_structure;

import apis.clock.ClockController;
import apis.googlecalender.GoogleCalendarController;
import apis.weather.WeatherController;
import data_structure.storage.StoreClock;
import data_structure.storage.StoreGCalendar;
import data_structure.storage.StoreWeather;
import javafx.scene.Parent;


public class ParentCollectorObject {

    private int index;
    private Parent parent;
    private WeatherController weatherController;
    private GoogleCalendarController googleCalendarController;
    private ClockController clockController;
    private String type;
    private boolean deleted = false;

    public ParentCollectorObject(int index, Parent parent, WeatherController weatherController) {
        this.index = index;
        this.parent = parent;
        this.weatherController = weatherController;
        weatherController.setIndex(index);
        type = "weather";
    }

    public ParentCollectorObject(int index, Parent parent, GoogleCalendarController googleCalendarController) {
        this.index = index;
        this.parent = parent;
        this.googleCalendarController = googleCalendarController;
        googleCalendarController.setIndex(index);
        type = "googleCalendar";
    }

    public ParentCollectorObject(int index, Parent parent, ClockController clockController) {
        this.index = index;
        this.parent = parent;
        this.clockController = clockController;
        clockController.setIndex(index);
        type = "clock";
    }

    public void restoreWeatherData(StoreWeather storeWeather) {
        weatherController.setCity(storeWeather.getCity());
        weatherController.setUpdateCircle(storeWeather.getUpdateCircle());
        parent.setLayoutX(storeWeather.getPos().getXd());
        parent.setLayoutY(storeWeather.getPos().getYd());
    }

    public void restoreGCalendarData(StoreGCalendar storeGCalendar) {
        googleCalendarController.setName(storeGCalendar.getName());
        googleCalendarController.setMaxResult(storeGCalendar.getMaxResult());
        googleCalendarController.setUpdateCicle(storeGCalendar.getUpdateCicle());
        parent.setLayoutX(storeGCalendar.getPos().getXd());
        parent.setLayoutY(storeGCalendar.getPos().getYd());
    }

    public void restoreClockData(StoreClock storeClock) {
        parent.setLayoutX(storeClock.getPos().getXd());
        parent.setLayoutY(storeClock.getPos().getYd());
    }

    public int getIndex() {
        return index;
    }

    public Parent getParent() {
        return parent;
    }

    public StoreWeather getStoreWeather() {
        return new StoreWeather(weatherController.getCity(), weatherController.getUpdateCircle(), new Vec2(parent.getLayoutX(), parent.getLayoutY()));
    }

    public StoreGCalendar getStoreGCalendar() {
        return new StoreGCalendar(googleCalendarController.getName(), googleCalendarController.getMaxResult(), googleCalendarController.getUpdateCicle(), new Vec2(parent.getLayoutX(), parent.getLayoutY()));
    }

    public StoreClock getStoreClock() {
        return new StoreClock(new Vec2(parent.getLayoutX(), parent.getLayoutY()));
    }

    public String getType() {
        return type;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
