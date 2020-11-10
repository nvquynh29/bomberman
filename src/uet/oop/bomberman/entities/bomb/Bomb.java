package uet.oop.bomberman.entities.bomb;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;

public class Bomb extends Entity {
    protected double timeToExplode = 3000;  //millisecond
    public int timeDisapear = 500;
    protected boolean exploded = false;

    public Bomb(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void update() {

    }
}
