package data_structure.storage;

import data_structure.Vec2;

import java.io.Serializable;

public class StoreGNews implements Serializable {

    private Vec2 pos;

    public StoreGNews(Vec2 pos) {

        this.pos = pos;
    }

    public Vec2 getPos() {
        return pos;
    }

}
