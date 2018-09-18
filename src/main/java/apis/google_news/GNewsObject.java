package apis.google_news;

import data_structure.Vec2;
import main.DashmirrorMain;

import java.io.Serializable;

public class GNewsObject implements Serializable {

    private int updateCircle = 5;
    private int maxResults = 3;
    private Vec2 pos = new Vec2((DashmirrorMain.WIDTH / 3), DashmirrorMain.HEIGHT / 3);

    public int getUpdateCircle() {
        return updateCircle;
    }

    public void setUpdateCircle(int updateCircle) {
        this.updateCircle = updateCircle;
    }

    public int getMaxResults() {
        return maxResults;
    }

    public void setMaxResults(int maxResults) {
        this.maxResults = maxResults;
    }

    public Vec2 getPos() {
        return pos;
    }

    public void setPos(Vec2 pos) {
        this.pos = pos;
    }

}
