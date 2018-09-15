package data_structure.storage;


import data_structure.Vec2;

import java.io.Serializable;

public class StoreGCalendar implements Serializable {

    private String name;
    private int maxResult;
    private int updateCicle;
    private Vec2 pos;

    public StoreGCalendar(String name, int maxResult, int updateCicle, Vec2 pos) {
        this.name = name;
        this.maxResult = maxResult;
        this.updateCicle = updateCicle;
        this.pos = pos;
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

    public int getUpdateCicle() {
        return updateCicle;
    }

    public void setUpdateCicle(int updateCicle) {
        this.updateCicle = updateCicle;
    }

    public Vec2 getPos() {
        return pos;
    }

    public void setPos(Vec2 pos) {
        this.pos = pos;
    }
}
