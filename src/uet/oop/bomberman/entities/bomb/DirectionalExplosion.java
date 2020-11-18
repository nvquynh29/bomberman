package uet.oop.bomberman.entities.bomb;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Wall;
import uet.oop.bomberman.entities.movable.Movable;
import uet.oop.bomberman.entities.tile.destroyable.Brick;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;

public class DirectionalExplosion extends Entity {
    protected int direction;
    private int radius;
    protected int xOrigin, yOrigin;
    protected Explosion[] explosions;

    public DirectionalExplosion(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    public Explosion[] getExplosions() {
        return explosions;
    }

    public DirectionalExplosion(int x, int y, int direction, int radius) {
        xOrigin = x;
        yOrigin = y;
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.radius = radius;
        explosions = new Explosion[calculatePermitedDistance()];
        createExplosions();
    }

    private void createExplosions() {
        boolean last = false;

        int xUnit = x / Sprite.SCALED_SIZE;
        int yUnit = y / Sprite.SCALED_SIZE;
        for (int i = 0; i < explosions.length; i++) {
            last = (i == explosions.length -1) ? true : false;

            switch (direction) {
                case 0: yUnit--; break;
                case 1: xUnit++; break;
                case 2: yUnit++; break;
                case 3: xUnit--; break;
            }
            explosions[i] = new Explosion(xUnit, yUnit, direction, last);
        }
    }

    private int calculatePermitedDistance() {
        int radius = 0;
        int xE = getX();
        int yE = getY();
        while(radius < this.radius) {
            if (direction == 0) {
                yE -= Sprite.SCALED_SIZE;
            } else if (direction == 1) {
                xE += Sprite.SCALED_SIZE;
            } else if (direction == 2) {
                yE += Sprite.SCALED_SIZE;
            } else if (direction == 3) {
                xE -= Sprite.SCALED_SIZE;
            }

            Entity entity = BombermanGame.getEntity(xE, yE);
            if (entity instanceof Wall) {
                break;
            } else if (entity instanceof Brick) {
                ++radius;
                ((Brick) entity).setDestroyed(true);
                break;
            }
            ++radius;
        }
        return radius;
    }

    public Explosion explosionAt(int x, int y) {
        for (int i = 0; i < explosions.length; i++) {
            if(explosions[i].getX() == x && explosions[i].getY() == y)
                return explosions[i];
        }
        return null;
    }

    @Override
    public void update() {}

    @Override
    public void render(Screen screen) {
        for (int i = 0; i < explosions.length; i++) {
            explosions[i].render(screen);
        }
    }

    public boolean collide(Entity e) {
        return true;
    }
}
