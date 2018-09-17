package apis.clock;

import data_structure.Vec2;
import main.DashmirrorMain;

import java.io.Serializable;

public class ClockObject implements Serializable {

    private Vec2 pos = new Vec2((DashmirrorMain.WIDTH / 3), DashmirrorMain.HEIGHT / 3);

    public Vec2 getPos() {
        return pos;
    }

    public void setPos(Vec2 pos) {
        this.pos = pos;
    }
}
