package data_structure.storage;

import data_structure.Vec2;

import java.io.Serializable;
import java.util.ArrayList;

public class StorageObject implements Serializable {

    private ArrayList<StoreWeather> storeWeathers = new ArrayList<>();
    private ArrayList<StoreGCalendar> storeGCalendars = new ArrayList<>();
    private ArrayList<StoreClock> storeClocks = new ArrayList<>();

    public void addStoreWeathers(String city, int updateCircle, Vec2 pos) {
        storeWeathers.add(new StoreWeather(city, updateCircle, pos));
    }

    public void addStoreWeathersObject(StoreWeather s) {
        storeWeathers.add(s);
    }

    public void addStoreGCalendar(String name, int maxResults, int updateCircle, Vec2 pos) {
        storeGCalendars.add(new StoreGCalendar(name, maxResults, updateCircle, pos));
    }

    public void addStoreGCalendarObject(StoreGCalendar g) {
        storeGCalendars.add(g);
    }

    public void addStoreClock(Vec2 pos) {
        storeClocks.add(new StoreClock(pos));
    }

    public void addStoreClockObject(StoreClock c) {
        storeClocks.add(c);
    }

    public ArrayList<StoreClock> getStoreClocks() {
        return storeClocks;
    }

    public ArrayList<StoreGCalendar> getStoreGCalendars() {
        return storeGCalendars;
    }

    public ArrayList<StoreWeather> getStoreWeathers() {
        return storeWeathers;
    }

    public void clearStoragedData() {
        storeWeathers.clear();
        storeGCalendars.clear();
        storeClocks.clear();
    }
}
