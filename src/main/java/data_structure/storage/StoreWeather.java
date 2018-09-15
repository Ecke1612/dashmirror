package data_structure.storage;

import data_structure.Vec2;

import java.io.Serializable;

public class StoreWeather implements Serializable {

    private String city;
    private int updateCircle;
    private Vec2 pos;

    public StoreWeather(String city, int updateCircle, Vec2 pos) {
        this.city = city;
        this.updateCircle = updateCircle;
        this.pos = pos;
    }

    public String getCity() {
        return city;
    }

    public int getUpdateCircle() {
        return updateCircle;
    }

    public Vec2 getPos() {
        return pos;
    }
}
