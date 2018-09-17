package apis.googlecalender;

import data_structure.Vec2;
import main.DashmirrorMain;

import java.io.Serializable;

public class GCalendarObject implements Serializable {

    private String name = "Eike";
    private int maxResult = 5;
    private int updateCircle = 5;
    private Vec2 pos = new Vec2((DashmirrorMain.WIDTH / 3), DashmirrorMain.HEIGHT / 3);

    public GCalendarObject() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxResult() {
        return maxResult;
    }

    public void setMaxResult(int maxResult) {
        this.maxResult = maxResult;
    }

    public Vec2 getPos() {
        return pos;
    }

    public void setPos(Vec2 pos) {
        this.pos = pos;
    }

    public int getUpdateCircle() {
        return updateCircle;
    }

    public void setUpdateCircle(int updateCircle) {
        this.updateCircle = updateCircle;
    }
}
