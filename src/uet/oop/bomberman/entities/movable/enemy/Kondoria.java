package uet.oop.bomberman.entities.movable.enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.movable.Bomber;
import uet.oop.bomberman.entities.movable.ai.AIMedium;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;

public class Kondoria extends Enemy{
    public Kondoria(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img, 1, 1000);
        ai = new AIMedium(Bomber.getInstance(), this);
        direction = ai.calculateDirection();
        wallPass = true;
    }


    @Override
    public void kill() {
        alive = false;
    }

    @Override
    protected void afterKill() {

    }

    @Override
    protected void chooseSprite() {
        switch (step % MAX_STEP) {
            case 0: {
                if (direction == 0 || direction == 1) {
                    img = Sprite.kondoria_right1.getFxImage();
                } else {
                    img = Sprite.kondoria_left1.getFxImage();
                }
                break;
            }
            case 10: {
                if (direction == 0 || direction == 1) {
                    img = Sprite.kondoria_right2.getFxImage();
                } else {
                    img = Sprite.kondoria_left2.getFxImage();
                }
                break;
            }
            case 20: {
                if (direction == 0 || direction == 1) {
                    img = Sprite.kondoria_right3.getFxImage();
                } else {
                    img = Sprite.kondoria_left3.getFxImage();
                }
                break;
            }
        }
    }

    @Override
    public void render(Screen screen) {
        if (alive)
            chooseSprite();
        else {
            if (frameToDisapear > 0) {
                switch (frameToDisapear) {
                    case 18: {
                        img = Sprite.kondoria_dead.getFxImage();
                        break;
                    }
                    case 12: {
                        img = Sprite.mob_dead1.getFxImage();
                        break;
                    }
                    case 6: {
                        img = Sprite.mob_dead2.getFxImage();
                        break;
                    }
                    case 0: {
                        img = Sprite.mob_dead3.getFxImage();
                        break;
                    }
                }
                frameToDisapear--;
            } else {
                BombermanGame.entities.remove(this);
            }
        }
        screen.getGraphicsContext().drawImage(img, x, y);
    }
}
