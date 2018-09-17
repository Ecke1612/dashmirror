package apis.weather;

import data_structure.Vec2;
import main.DashmirrorMain;

import java.io.Serializable;

public class WeatherObject implements Serializable {

    private String city = "London";
    private int updateCircle = 15;
    private Vec2 pos = new Vec2((DashmirrorMain.WIDTH / 3), DashmirrorMain.HEIGHT / 3);

    public WeatherObject() {

    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getUpdateCircle() {
        return updateCircle;
    }

    public void setUpdateCircle(int updateCircle) {
        this.updateCircle = updateCircle;
    }

    public Vec2 getPos() {
        return pos;
    }

    public void setPos(Vec2 pos) {
        this.pos = pos;
    }
}
