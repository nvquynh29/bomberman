package uet.oop.bomberman.entities.movable.enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;

public class Balloom extends Enemy {

    public Balloom(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img, 1, 100);
//        timeAfter = 20;
    }

    @Override
    public void kill() {
        alive = false;
    }

    @Override
    protected void afterKill() {

    }

    //direction = 0, 1 -> balloom_right, else -> left
    //step = 0, 4, 8 -> image 1, 2, 3
    @Override
    protected void chooseSprite() {
        switch (step % MAX_STEP) {
            case 0: {
                if (direction == 0 || direction == 1) {
                    img = Sprite.balloom_right1.getFxImage();
                } else {
                    img = Sprite.balloom_left1.getFxImage();
                }
                break;
            }
            case 10: {
                if (direction == 0 || direction == 1) {
                    img = Sprite.balloom_right2.getFxImage();
                } else {
                    img = Sprite.balloom_left2.getFxImage();
                }
                break;
            }
            case 20: {
                if (direction == 0 || direction == 1) {
                    img = Sprite.balloom_right3.getFxImage();
                } else {
                    img = Sprite.balloom_left3.getFxImage();
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
                    case 55: {
                        img = Sprite.balloom_dead.getFxImage();
                        break;
                    }
                    case 40: {
                        img = Sprite.mob_dead1.getFxImage();
                        break;
                    }
                    case 20: {
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
