package uet.oop.bomberman.entities.movable;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Wall;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.tile.destroyable.Brick;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;

public class Bomber extends Movable {
    private int speed = 8;
    private int step = 0;

    private Image images[][] = new Image[][]{
            {Sprite.player_up.getFxImage(), Sprite.player_up_1.getFxImage(), Sprite.player_up_2.getFxImage()},
            {Sprite.player_right.getFxImage(), Sprite.player_right_1.getFxImage(), Sprite.player_right_2.getFxImage()},
            {Sprite.player_down.getFxImage(), Sprite.player_down_1.getFxImage(), Sprite.player_down_2.getFxImage()},
            {Sprite.player_left.getFxImage(), Sprite.player_left_1.getFxImage(), Sprite.player_left_2.getFxImage()}
    };

    public Bomber(int x, int y, Image img) {
        super(x, y, img);
        alive = true;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void setMove(boolean moving) {
        this.moving = moving;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public void changeStep() {
        if (step == 2) {
            step = 0;
        }
        step++;
    }

    public void moveUp() {
        direction = 0;
        moving = true;
        if (canMoveUp()) {
            y -= speed;
        }
        changeStep();
        chooseSprite();
    }

    public void moveDown() {
        direction = 2;
        moving = true;
        if (canMoveDown()) {
            y += speed;
        }
        changeStep();
        chooseSprite();
    }

    public void moveLeft() {
        direction = 3;
        moving = true;
        if (canMoveLeft()) {
            x -= speed;
        }
        changeStep();
        chooseSprite();
//        if (!checkCollision(x / Sprite.SCALED_SIZE - 1, y / Sprite.SCALED_SIZE)) {
//            x -= speed;
//            changeStep();
//            chooseSprite();
////            if (moveScreen(this.x)) {
////                BombermanGame.gc.translate(speed, 0);
////            }
//        }
    }

    public void moveRight() {
        direction = 1;
        moving = true;
        if (canMoveRight()) {
            x += speed;
        }
        changeStep();
        chooseSprite();
//        if (!checkCollision(x / Sprite.SCALED_SIZE + 1, y / Sprite.SCALED_SIZE)) {
//            x += speed;
//            changeStep();
//            chooseSprite();
////            if (moveScreen(this.x)) {
////                BombermanGame.gc.translate(-speed, 0);
////            }
//        }
    }

    public boolean canMoveRight() {
        for (Entity entity : BombermanGame.stillObjects) {
            if (entity instanceof Wall || entity instanceof Brick) {
                if (entity.intersectLeft(this)) {
                    return false;
                }
            }
        }

        for (Bomb bomb : BombermanGame.bombs) {
            if (bomb.intersectLeft(this)) {
                return false;
            }
        }
        return true;
    }

    public boolean canMoveLeft() {
        for (Entity entity : BombermanGame.stillObjects) {
            if (entity instanceof Wall || entity instanceof Brick) {
                if (entity.intersectRight(this)) {
                    return false;
                }
            }
        }

        for (Bomb bomb : BombermanGame.bombs) {
            if (bomb.intersectRight(this)) {
                return false;
            }
        }
        return true;
    }

    public boolean canMoveUp() {
        for (Entity entity : BombermanGame.stillObjects) {
            if (entity instanceof Wall || entity instanceof Brick) {
                if (entity.intersectDown(this)) {
                    return false;
                }
            }
        }

        for (Bomb bomb : BombermanGame.bombs) {
            if (bomb.intersectDown(this)) {
                return false;
            }
        }
        return true;
    }

    public boolean canMoveDown() {
        for (Entity entity : BombermanGame.stillObjects) {
            if (entity instanceof Wall || entity instanceof Brick) {
                if (entity.intersectUp(this)) {
                    return false;
                }
            }
        }

        for (Bomb bomb : BombermanGame.bombs) {
            if (bomb.intersectUp(this)) {
                return false;
            }
        }
        return true;
    }

    public void kill() {
        alive = false;
    }

    @Override
    protected void afterKill() {

    }

    @Override
    protected boolean canMove() {
        return true;
    }

    @Override
    public boolean collide(Entity other) {
        return false;
    }

    @Override
    public void update() {

    }

    @Override
    protected void calculateMove() {

    }

    @Override
    protected void move(int xm, int ym) {

    }

    @Override
    public void render(Screen screen) {
        if (alive)
            chooseSprite();
        else {
            if (frameToDisapear > 0) {
                switch (frameToDisapear) {
                    case 50: {
                        img = Sprite.player_dead1.getFxImage();
                        break;
                    }
                    case 25: {
                        img = Sprite.player_dead2.getFxImage();
                        break;
                    }
                    case 0: {
                        img = Sprite.player_dead3.getFxImage();
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

    public void chooseSprite() {
        if (direction >= 0) {
            if (!moving) {
                img = images[direction][0];
            } else {
                img = images[direction][getStep()];
            }
        }
    }
}
