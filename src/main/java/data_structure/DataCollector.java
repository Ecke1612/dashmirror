package data_structure;

import apis.clock.ClockController;
import apis.googlecalender.GoogleCalendarController;
import apis.weather.WeatherController;
import java.util.ArrayList;

public class DataCollector {

    private ArrayList<WeatherController> weatherControllers = new ArrayList<>();
    private ArrayList<GoogleCalendarController> googleCalendarControllers = new ArrayList<>();
    private ArrayList<ClockController> clockControllers = new ArrayList<>();

    public void addWeatherController(WeatherController w) {
        weatherControllers.add(w);
    }

    public void addGoogleCalendarController(GoogleCalendarController g) {
        googleCalendarControllers.add(g);
    }

    public void addClockController(ClockController c) {
        clockControllers.add(c);
    }

    public ArrayList<ClockController> getClockControllers() {
        return clockControllers;
    }

    public ArrayList<WeatherController> getWeatherControllers() {
        return weatherControllers;
    }

    public ArrayList<GoogleCalendarController> getGoogleCalendarControllers() {
        return googleCalendarControllers;
    }
}
