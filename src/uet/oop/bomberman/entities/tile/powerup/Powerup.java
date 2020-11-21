package uet.oop.bomberman.entities.tile.powerup;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;

public abstract class Powerup extends Entity {
    protected boolean active = false;
//    protected int level;

    public Powerup(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
//        this.level = level;
    }

    public abstract void setValues();

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

//    public int getLevel() {
//        return level;
//    }
//
//    public void setLevel(int level) {
//        this.level = level;
//    }
}
