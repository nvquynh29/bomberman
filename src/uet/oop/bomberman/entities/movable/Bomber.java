package uet.oop.bomberman.entities.movable;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.audio.Audio;
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
    private int speed = 4;
    private int step = 0;
    private final int allowDistance = 8;
    private boolean canMove = false;
    private boolean stillAlive = false;
    private boolean wallPass = false;
    private boolean detonatorPower = false;
    public static List<Powerup> powerups = new ArrayList<Powerup>();
    private static final Bomber INSTANCE = new Bomber(1, 1, Sprite.player_right.getFxImage());
    private int check = 1;

    public ArrayList<Boolean> input;

    public static Bomber getInstance() {
        INSTANCE.x = Sprite.SCALED_SIZE;
        INSTANCE.y = Sprite.SCALED_SIZE;
        return INSTANCE;
    }

    private Bomber(int x, int y, Image img) {
        super(x, y, img);
        alive = true;
        step = 0;
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


    public void moveUp() {
        direction = 0;
        moving = true;
        if (canMoveUp()) {
            y -= speed;
        }
        step++;
        switch (step % 20) {
            case 0:
                setImage(Sprite.player_up_2.getFxImage());
                Audio.MakeSomeNoise(Audio.foot1Path);
                break;
            case 10:
                setImage(Sprite.player_up_1.getFxImage());
                Audio.MakeSomeNoise(Audio.foot2Path);
                break;
        }
        if (step > 20000000) step = 0;
    }

    public void moveDown() {
        direction = 2;
        moving = true;
        if (canMoveDown()) {
            y += speed;
        }
        step++;
        switch (step % 20) {
            case 0:
                setImage(Sprite.player_down_2.getFxImage());
                Audio.MakeSomeNoise(Audio.foot1Path);
                break;
            case 10:
                setImage(Sprite.player_down_1.getFxImage());
                Audio.MakeSomeNoise(Audio.foot2Path);
                break;
        }
        if (step > 20000000) step = 0;
    }

    public void moveLeft() {
        direction = 3;
        moving = true;
        if (canMoveLeft()) {
            x -= speed;
        }
        step++;
        switch (step % 20) {
            case 0:
                setImage(Sprite.player_left_2.getFxImage());
                Audio.MakeSomeNoise(Audio.foot1Path);
                break;
            case 10:
                setImage(Sprite.player_left_1.getFxImage());
                Audio.MakeSomeNoise(Audio.foot2Path);
                break;
        }
        if (step > 20000000) step = 0;
    }

    public void moveRight() {
        direction = 1;
        moving = true;
        if (canMoveRight()) {
            x += speed;
        }
        step++;
        switch (step % 20) {
            case 0:
                setImage(Sprite.player_right_2.getFxImage());
                Audio.MakeSomeNoise(Audio.foot1Path);
                break;
            case 10:
                setImage(Sprite.player_right_1.getFxImage());
                Audio.MakeSomeNoise(Audio.foot2Path);
                break;
        }
        if (step > 20000000) step = 0;
    }

    public boolean canMoveRight() {
        for (Entity entity : BombermanGame.stillObjects) {
            if (entity instanceof Wall || (entity instanceof Brick && !wallPass)) {
                if (entity.intersectLeft(this)) {
                    if (entity.getMaxY() - getY() <= allowDistance) {
                        y += entity.getMaxY() - getY();
                    }
                    if (getMaxY() - entity.getY() <= allowDistance) {
                        y -= getMaxY() - entity.getY();
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
                    if (entity.getMaxY() - getY() <= allowDistance) {
                        y += entity.getMaxY() - getY();
                    }
                    if (getMaxY() - entity.getY() <= allowDistance) {
                        y -= getMaxY() - entity.getY();
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
                    if (entity.getMaxX() - getX() <= allowDistance) {
                        x += entity.getMaxX() - getX();
                    }
                    if (getMaxX() - entity.getX() <= allowDistance + 5) {
                        x -= getMaxX() - entity.getX();
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
                    if (entity.getMaxX() - getX() <= allowDistance) {
                        x += entity.getMaxX() - getX();
                    }
                    if (getMaxX() - entity.getX() <= allowDistance + 5) {
                        x -= getMaxX() - entity.getX();
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
        Audio.MakeSomeNoise(Audio.deadPath);
        if (check == 1) {
            BombermanGame.lives -= 1;
            check++;
        }
    }

    @Override
    protected void afterKill() {
        check = 1;
        if (BombermanGame.lives != 0) {
            alive = true;
            BombermanGame.restartGame();
        } else {
            img = null;
            BombermanGame.drawEndGame();
        }
    }

    @Override
    public void update() {
        if (moving) {
            for (int i = 0; i < input.size(); i++) {
                if (input.get(i) == true) {
                    if (i == 0) moveUp();
                    if (i == 1) moveRight();
                    if (i == 2) moveDown();
                    if (i == 3) moveLeft();
                }
            }
        } else {
            switch (direction) {
                case 3:
                    setImage(Sprite.player_left.getFxImage());
                    break;
                case 1:
                    setImage(Sprite.player_right.getFxImage());
                    break;
                case 0:
                    setImage(Sprite.player_up.getFxImage());
                    break;
                case 2:
                    setImage(Sprite.player_down.getFxImage());
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected void calculateMove() {

    }

    @Override
    public void render(Screen screen) {
        if (!alive) {
            direction = -1;
            moving = false;
            if (frameToDisapear > 0) {
                switch (frameToDisapear) {
                    case 48: {
                        img = Sprite.player_dead1.getFxImage();
                        break;
                    }
                    case 32: {
                        img = Sprite.player_dead2.getFxImage();
                        break;
                    }
                    case 16: {
                        img = Sprite.player_dead3.getFxImage();
                        break;
                    }
                }
                frameToDisapear--;
            } else {
                img = Sprite.player_right.getFxImage();
                BombermanGame.entities.remove(this);
                afterKill();
                frameToDisapear = 48;
            }
        }
        screen.getGraphicsContext().drawImage(img, x, y);
    }

    public void addPowerUp(Powerup powerup) {
        if (powerup.isRemoved()) return;

        powerups.add(powerup);

        powerup.setValues();
    }

    private void setImage(Image image) {
        this.img = image;
    }

}
