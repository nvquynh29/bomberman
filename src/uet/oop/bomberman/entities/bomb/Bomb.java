package uet.oop.bomberman.entities.bomb;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Grass;
import uet.oop.bomberman.entities.movable.Bomber;
import uet.oop.bomberman.entities.movable.Movable;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;

public class Bomb extends Entity {
    private int timeToExplode = 120;
    private int frameToDisapear = 12;
    private boolean exploded = false;
    private DirectionalExplosion[] explosions = null;

    public Bomb(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        img = Sprite.bomb.getFxImage();
    }

    public void renderExplosions(Screen screen) {
        for (int i = 0; i < explosions.length; i++) {
            explosions[i].render(screen);
        }
    }

    public void updateExplosions() {
        for (int i = 0; i < explosions.length; i++) {
            explosions[i].update();
        }
    }

    public void initExplosion() {
        explosions = new DirectionalExplosion[4];

        for (int i = 0; i < explosions.length; i++) {
            explosions[i] = new DirectionalExplosion(x, y, i, BombermanGame.bombRadius);
        }
    }

    public void explosiveEffect() {
        for (int i = 0; i < explosions.length; ++i) {
            Explosion[] exp = explosions[i].getExplosions();
            collide(exp);
        }
    }

    public void collide(Explosion[] exp) {
        if (exp.length > 0) {
            Explosion begin = exp[0];
            Explosion end = exp[exp.length - 1];
            switch (end.getDirection()) {
                case 0: {
                    for (Entity entity : BombermanGame.entities) {
                        Movable e = (Movable) entity;
                        if (entity.getMaxY() > end.getY() && entity.getMaxY() < begin.getMaxY() + Sprite.SCALED_SIZE
                                && entity.getX() >= end.getX() && entity.getX() <= end.getMaxX()) {
                            if (e.isAlive()) {
                                if (e instanceof Bomber) {
                                    if (((Bomber) e).isStillAlive() == false) e.kill();
                                }
                                else e.kill();
                            }
                        }
                    }
                    break;
                }
                case 1: {
                    for (Entity entity : BombermanGame.entities) {
                        Movable e = (Movable) entity;
                        if (entity.getX() > begin.getX() - Sprite.SCALED_SIZE && entity.getX() < end.getMaxX()
                                && entity.getY() >= end.getY() && entity.getY() <= end.getMaxY()) {
                            if (e.isAlive()) {
                                if (e instanceof Bomber) {
                                    if (((Bomber) e).isStillAlive() == false) e.kill();
                                }
                                else e.kill();
                            }
                        }
                    }
                    break;
                }
                case 2: {
                    for (Entity entity : BombermanGame.entities) {
                        Movable e = (Movable) entity;
                        if (entity.getY() > begin.getY() - Sprite.SCALED_SIZE && entity.getY() < end.getMaxY()
                                && entity.getX() >= end.getX() && entity.getX() <= end.getMaxX()) {
                            if (e.isAlive()) {
                                if (e instanceof Bomber) {
                                    if (((Bomber) e).isStillAlive() == false) e.kill();
                                }
                                else e.kill();
                            }
                        }
                    }
                    break;
                }
                case 3: {
                    for (Entity entity : BombermanGame.entities) {
                        Movable e = (Movable) entity;
                        if (entity.getMaxX() > end.getX() && entity.getMaxX() < begin.getMaxX() + Sprite.SCALED_SIZE
                                && entity.getY() >= end.getY() && entity.getY() <= end.getMaxY()) {
                            if (e.isAlive()) {
                                if (e instanceof Bomber) {
                                    if (((Bomber) e).isStillAlive() == false) e.kill();
                                }
                                else e.kill();
                            }
                        }
                    }
                    break;
                }
            }
        }
    }

    public void explode() {
        exploded = true;
        initExplosion();
        explosiveEffect();
    }

    @Override
    public void update() {
        if (timeToExplode > 0) {
            timeToExplode--;
            switch (timeToExplode % 60) {
                case 0: {
                    img = Sprite.bomb.getFxImage();
                    break;
                }
                case 40: {
                    img = Sprite.bomb_1.getFxImage();
                    break;
                }
                case 20: {
                    img = Sprite.bomb_2.getFxImage();
                    break;
                }
            }
        } else {
            explode();
        }
    }

    @Override
    public void render(Screen screen) {
        if (exploded) {
            img = Sprite.bomb_exploded2.getFxImage();
            if (frameToDisapear > 0) {
                frameToDisapear--;
                renderExplosions(screen);
            } else {
                BombermanGame.bombs.remove(this);
            }
        }
        screen.getGraphicsContext().drawImage(img, x, y);
    }
}
