package uet.oop.bomberman.entities.movable;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Wall;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.tile.destroyable.Brick;
import uet.oop.bomberman.entities.tile.powerup.Powerup;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Bomber extends Movable {
    private int speed = 8;
    private int step = 0;
    private final int allowDistance = 8;
    private boolean canMove = false;
    private boolean stillAlive = false;
    private boolean wallPass = false;
    private boolean detonatorPower = false;
    public static List<Powerup> powerups = new ArrayList<Powerup>();
    private static final Bomber INSTANCE = new Bomber(1, 1, Sprite.player_right.getFxImage() );
    private int check = 1;

    public static Bomber getInstance() {
        INSTANCE.x = Sprite.SCALED_SIZE;
        INSTANCE.y = Sprite.SCALED_SIZE;
        return INSTANCE;
    }
    private Image images[][] = new Image[][]{
            {Sprite.player_up.getFxImage(), Sprite.player_up_1.getFxImage(), Sprite.player_up_2.getFxImage()},
            {Sprite.player_right.getFxImage(), Sprite.player_right_1.getFxImage(), Sprite.player_right_2.getFxImage()},
            {Sprite.player_down.getFxImage(), Sprite.player_down_1.getFxImage(), Sprite.player_down_2.getFxImage()},
            {Sprite.player_left.getFxImage(), Sprite.player_left_1.getFxImage(), Sprite.player_left_2.getFxImage()}
    };

    private Bomber(int x, int y, Image img) {
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

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public boolean isCanMove() {
        return canMove;
    }

    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }

    public boolean isStillAlive() {
        return stillAlive;
    }

    public void setStillAlive(boolean stillAlive) {
        this.stillAlive = stillAlive;
    }

    public boolean isWallPass() {
        return wallPass;
    }

    public void setWallPass(boolean wallPass) {
        this.wallPass = wallPass;
    }

    public boolean isDetonatorPower() {
        return detonatorPower;
    }

    public void setDetonatorPower(boolean detonatorPower) {
        this.detonatorPower = detonatorPower;
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
    }

    public void moveRight() {
        direction = 1;
        moving = true;
        if (canMoveRight()) {
            x += speed;
        }
        changeStep();
        chooseSprite();
    }

    public boolean canMoveRight() {
        for (Entity entity : BombermanGame.stillObjects) {
            if (entity instanceof Wall || (entity instanceof Brick && !wallPass)) {
                if (entity.intersectLeft(this)) {
                    if (y + allowDistance >= entity.getMaxY()) {
                        y += allowDistance;
                        break;
                    } else if (getMaxY() - allowDistance <= entity.getY()) {
                        y -= allowDistance;
                        break;
                    }
                    return false;
                }
            }
        }

        for (Bomb bomb : BombermanGame.bombs) {
            if (bomb.intersectLeft(this)) {
                if (canMove) return true;
                else return false;
            }
        }
        return true;
    }

    public boolean canMoveLeft() {
        for (Entity entity : BombermanGame.stillObjects) {
            if (entity instanceof Wall || (entity instanceof Brick && !wallPass)) {
                if (entity.intersectRight(this)) {
                    if (y + allowDistance >= entity.getMaxY()) {
                        y += allowDistance;
                        break;
                    } else if (getMaxY() - allowDistance <= entity.getY()) {
                        y -= allowDistance;
                        break;
                    }
                    return false;
                }
            }
        }

        for (Bomb bomb : BombermanGame.bombs) {
            if (bomb.intersectRight(this)) {
                if (canMove) return true;
                else return false;
            }
        }
        return true;
    }

    public boolean canMoveUp() {
        for (Entity entity : BombermanGame.stillObjects) {
            if (entity instanceof Wall || (entity instanceof Brick && !wallPass)) {
                if (entity.intersectDown(this)) {
                    if (getX() + allowDistance >= entity.getMaxX()) {
                        x += allowDistance;
                        break;
                    } else if (getMaxX() - allowDistance <= entity.getX()) {
                        x -= allowDistance;
                        break;
                    }
                    return false;
                }
            }
        }

        for (Bomb bomb : BombermanGame.bombs) {
            if (bomb.intersectDown(this)) {
                if (canMove) return true;
                else return false;
            }
        }
        return true;
    }

    public boolean canMoveDown() {
        for (Entity entity : BombermanGame.stillObjects) {
            if (entity instanceof Wall || (entity instanceof Brick && !wallPass)) {
                if (entity.intersectUp(this)) {
                    if (getX() + allowDistance >= entity.getMaxX()) {
                        x += allowDistance;
                        break;
                    } else if (getMaxX() - allowDistance <= entity.getX()) {
                        x -= allowDistance;
                        break;
                    }
                    return false;
                }
            }
        }

        for (Bomb bomb : BombermanGame.bombs) {
            if (bomb.intersectUp(this)) {
                if (canMove) return true;
                else return false;
            }
        }
        return true;
    }

    public void kill() {
        alive = false;
        if (check == 1) {
            BombermanGame.lives -= 1;
            System.out.println(BombermanGame.lives);
            check++;
        }
    }

    @Override
    protected void afterKill() {
        check = 1;
        if (BombermanGame.lives != 0) {
            alive = true;
            BombermanGame.restartGame();
        }
    }

    @Override
    public void update() {

    }

    @Override
    protected void calculateMove() {

    }

    @Override
    public void render(Screen screen) {
        if (alive) {
            chooseSprite();
        }
        else {
            if (frameToDisapear > 0) {
                switch (frameToDisapear) {
                    case 24: {
                        img = Sprite.player_dead1.getFxImage();
                        break;
                    }
                    case 16: {
                        img = Sprite.player_dead2.getFxImage();
                        break;
                    }
                    case 8: {
                        img = Sprite.player_dead3.getFxImage();
                        break;
                    }
                }
                frameToDisapear--;
            } else {
                BombermanGame.entities.remove(this);
                afterKill();
                frameToDisapear = 24;
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

    public void addPowerUp(Powerup powerup) {
        if(powerup.isRemoved()) return;

        powerups.add(powerup);

        powerup.setValues();
    }
}
