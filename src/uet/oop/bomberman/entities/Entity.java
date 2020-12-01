package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.level.Coordinates;

public abstract class Entity {
    //Tọa độ X tính từ góc trái trên trong Canvas
    protected int x;

    //Tọa độ Y tính từ góc trái trên trong Canvas
    protected int y;

    protected Image img;

    protected boolean removed = false;

    public Entity() {

    }

    //Khởi tạo đối tượng, chuyển từ tọa độ đơn vị sang tọa độ trong canvas
    public Entity( int xUnit, int yUnit, Image img) {
        this.x = xUnit * Sprite.SCALED_SIZE;
        this.y = yUnit * Sprite.SCALED_SIZE;
        this.img = img;
    }

    public boolean isRemoved() {
        return removed;
    }

    public void remove() {
        removed = true;
    }

    public Image getImg() {
        return img;
    }

    public void setImg(Image img) {
        this.img = img;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getMaxX() {
        return x + Sprite.SCALED_SIZE;
    }

    public int getMaxY() {
        return y + Sprite.SCALED_SIZE;
    }



    public int getXTile() {
        return Coordinates.pixelToTile(x + Sprite.SCALED_SIZE / 2);
    }

    public int getYTile() {
        return Coordinates.pixelToTile(y + Sprite.SCALED_SIZE / 2);
    }

    public void render(Screen screen) {
        screen.getGraphicsContext().drawImage(img, x, y);
    }


    public abstract void update();

    public boolean intersectLeft(Entity other) {
        boolean impactX = (x == other.getMaxX());
        boolean intersectY = (other.getY() >= y && other.getY() < getMaxY())
                             || (other.getMaxY() > y && other.getMaxY() <= getMaxY());
        return impactX && intersectY;
    }

    public boolean intersectRight(Entity other) {
        boolean impactX = (getMaxX() == other.getX());
        boolean intersectY = (other.getY() >= y && other.getY() < getMaxY())
                             || (other.getMaxY() > y && other.getMaxY() <= getMaxY());
        return impactX && intersectY;
    }

    public boolean intersectUp(Entity other) {
        boolean impactY = (y == other.getMaxY());
        boolean intersectX = (other.getX() >= x && other.getX() < getMaxX())
                || (other.getMaxX() > x && other.getMaxX() <= getMaxX());
        return impactY && intersectX;
    }

    public boolean intersectDown(Entity other) {
        boolean impactY = (getMaxY() == other.getY());
        boolean intersectX = (other.getX() >= x && other.getX() < getMaxX())
                || (other.getMaxX() > x && other.getMaxX() <= getMaxX());
        return impactY && intersectX;
    }

    public boolean intersect(Entity other) {
        return (intersectLeft(other) || intersectRight(other)
                || intersectUp(other) || intersectDown(other));
    }

    public boolean isCollided(Entity e) {
        if ((getMaxX() <= e.getX() + 8) || (getMaxY() - 8 <= e.getY())
                || (e.getMaxX() - 8 <= x) || (e.getMaxY() - 8 <= y)) {
            return false;
        }
        return true;
    }
}
