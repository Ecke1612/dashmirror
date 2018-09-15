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

    public void addStoreGCalendar(String name, int maxResults, int updateCircle, Vec2 pos) {
        storeGCalendars.add(new StoreGCalendar(name, maxResults, updateCircle, pos));
    }

    public void addStoreClock(Vec2 pos) {
        storeClocks.add(new StoreClock(pos));
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
}
