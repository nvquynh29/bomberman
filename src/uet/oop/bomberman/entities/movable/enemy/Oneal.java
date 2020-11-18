package uet.oop.bomberman.entities.movable.enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.movable.ai.AIMedium;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;

public class Oneal extends Enemy {

    public Oneal(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img, 2, 200);
        ai = new AIMedium(BombermanGame.player, this);
    }


    @Override
    public void kill() {

    }

    @Override
    protected void afterKill() {

    }

    @Override
    protected void chooseSprite() {
        switch (step % MAX_STEP) {
            case 0: {
                if (direction == 0 || direction == 1) {
                    img = Sprite.oneal_right1.getFxImage();
                } else {
                    img = Sprite.oneal_left1.getFxImage();
                }
                break;
            }
            case 10: {
                if (direction == 0 || direction == 1) {
                    img = Sprite.oneal_right2.getFxImage();
                } else {
                    img = Sprite.oneal_left2.getFxImage();
                }
                break;
            }
            case 20: {
                if (direction == 0 || direction == 1) {
                    img = Sprite.oneal_right3.getFxImage();
                } else {
                    img = Sprite.oneal_left3.getFxImage();
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
                        img = Sprite.oneal_dead.getFxImage();
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
