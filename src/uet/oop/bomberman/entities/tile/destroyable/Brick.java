package uet.oop.bomberman.entities.tile.destroyable;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;

public class Brick extends Entity {
    private boolean destroyed = false;
    private int frameToDisapear = 24;

    public Brick(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }

    @Override
    public void update() {

    }

    @Override
    public void render(Screen screen) {
        if (destroyed) {
            if (frameToDisapear > 0) {
                switch (frameToDisapear) {
                    case 24: {
                        img = Sprite.brick_exploded.getFxImage();
                        break;
                    }
                    case 16: {
                        img = Sprite.brick_exploded1.getFxImage();
                        break;
                    }
                    case 8: {
                        img = Sprite.brick_exploded2.getFxImage();
                        break;
                    }
                }
                frameToDisapear--;
            } else {
                BombermanGame.stillObjects.remove(this);
            }
        }
        screen.getGraphicsContext().drawImage(img, x, y);
    }
}
