package data_structure.storage;

import data_structure.Vec2;

import java.io.Serializable;

public class StoreClock implements Serializable {

    private Vec2 pos;

    public StoreClock(Vec2 pos) {
        this.pos = pos;
    }

    public Vec2 getPos() {
        return pos;
    }
}
