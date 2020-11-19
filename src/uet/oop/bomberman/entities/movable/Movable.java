package uet.oop.bomberman.entities.movable;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;

public abstract class Movable extends Entity {
    protected int direction = -1;  //0 : up, 1 : right, 2 : down, 3 : left
    protected boolean alive = true;
    protected boolean moving = false;
    protected int frameToDisapear = 24;

    protected int MAX_STEP = 2;

    public Movable(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public abstract void update();

    protected abstract void calculateMove();

    protected abstract void move(int xm, int ym);

    public abstract void kill();

    protected abstract void afterKill();

    protected abstract boolean canMove();

    protected abstract boolean collide(Entity other);

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public boolean isAlive() {
        return alive;
    }

    public boolean isMoving() {
        return moving;
    }

    public int getDirection() {
        return direction;
    }
}
