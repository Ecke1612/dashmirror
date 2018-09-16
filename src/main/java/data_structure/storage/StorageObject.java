package data_structure.storage;

import java.io.Serializable;
import java.util.ArrayList;

public class StorageObject implements Serializable {

    private ArrayList<StoreWeather> storeWeathers = new ArrayList<>();
    private ArrayList<StoreGCalendar> storeGCalendars = new ArrayList<>();
    private ArrayList<StoreClock> storeClocks = new ArrayList<>();
    private ArrayList<StoreGNews> storeGNews = new ArrayList<>();

    public void addStoreWeathersObject(StoreWeather s) {
        storeWeathers.add(s);
    }

    public void addStoreGCalendarObject(StoreGCalendar g) {
        storeGCalendars.add(g);
    }

    public void addStoreClockObject(StoreClock c) {
        storeClocks.add(c);
    }

    public void addStoreGNews(StoreGNews g) {
        storeGNews.add(g);
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

    public ArrayList<StoreGNews> getStoreGNews() {
        return storeGNews;
    }

    public void clearStoragedData() {
        storeWeathers.clear();
        storeGCalendars.clear();
        storeClocks.clear();
        storeGNews.clear();
    }
}
