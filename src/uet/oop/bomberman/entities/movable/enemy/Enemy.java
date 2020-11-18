package uet.oop.bomberman.entities.movable.enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Grass;
import uet.oop.bomberman.entities.Wall;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.bomb.DirectionalExplosion;
import uet.oop.bomberman.entities.movable.Bomber;
import uet.oop.bomberman.entities.movable.Movable;
import uet.oop.bomberman.entities.movable.ai.AI;
import uet.oop.bomberman.entities.movable.ai.AILow;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;

public abstract class Enemy extends Movable {
    protected int points;

    protected int speed; //Speed should change on level transition
    protected AI ai;
    protected int step;
    protected final int MAX_STEP = 30;  // 10 lan update thi thay anh 1 lan * 3 anh

    protected Image deadImg;

    public Enemy(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    public Enemy(int x, int y, Image dead, int speed, int points) {
        super(x, y, dead);

        ai = new AILow();
        direction = ai.calculateDirection();
        this.points = points;
        this.speed = speed;

//        timeAfter = 20;
        deadImg = dead;
    }


    @Override
    public void update() {
        if(!alive) {
            afterKill();
            return;
        } else {
            calculateMove();
        }
    }

    public void render(Screen screen) {
        screen.renderEntity(x, y, this);
    }

    @Override
    public void calculateMove() {
        if (!alive) {
            return;
        } else {
            step++;
            switch (direction) {
                case 0: {
                    for (Entity entity : BombermanGame.entities) {
                        if (!(entity instanceof Bomber || entity instanceof Enemy)) {
                            if (entity.intersectDown(this)) {
                                direction = ai.calculateDirection();
                                return;
                            }
                        } else if (entity instanceof Bomber) {
                            if (entity.intersectDown(this)) {
                                ((Bomber) entity).kill();
                            }
                        }
                    }
                    for (Entity entity : BombermanGame.stillObjects) {
                        if (!(entity instanceof Grass)) {
                            if (entity.intersectDown(this)) {
                                direction = ai.calculateDirection();
                                return;
                            }
                        }
                    }

                    for (Bomb bomb : BombermanGame.bombs) {
                        if (bomb.intersectDown(this)) {
                            direction = ai.calculateDirection();
                            return;
                        }
                    }

                    y -= speed;
                    chooseSprite();
                    break;
                }
                case 1: {
                    for (Entity entity : BombermanGame.entities) {
                        if (!(entity instanceof Bomber || entity instanceof Enemy)) {
                            if (entity.intersectLeft(this)) {
                                direction = ai.calculateDirection();
                                return;
                            }
                        } else if (entity instanceof Bomber) {
                            if (entity.intersectLeft(this)) {
                                ((Bomber) entity).kill();
                            }
                        }
                    }
                    for (Entity entity : BombermanGame.stillObjects) {
                        if (!(entity instanceof Grass)) {
                            if (entity.intersectLeft(this)) {
                                direction = ai.calculateDirection();
                                return;
                            }
                        }
                    }
                    for (Bomb bomb : BombermanGame.bombs) {
                        if (bomb.intersectLeft(this)) {
                            direction = ai.calculateDirection();
                            return;
                        }
                    }

                    x += speed;
                    chooseSprite();
                    break;
                }
                case 2: {
                    for (Entity entity : BombermanGame.entities) {
                        if (!(entity instanceof Bomber || entity instanceof Enemy)) {
                            if (entity.intersectUp(this)) {
                                direction = ai.calculateDirection();
                                return;
                            }
                        } else if (entity instanceof Bomber) {
                            if (entity.intersectUp(this)) {
                                ((Bomber) entity).kill();
                            }
                        }
                    }
                    for (Entity entity : BombermanGame.stillObjects) {
                        if (!(entity instanceof Grass)) {
                            if (entity.intersectUp(this)) {
                                direction = ai.calculateDirection();
                                return;
                            }
                        }
                    }
                    for (Bomb bomb : BombermanGame.bombs) {
                        if (bomb.intersectUp(this)) {
                            direction = ai.calculateDirection();
                            return;
                        }
                    }

                    y += speed;
                    chooseSprite();
                    break;
                }
                case 3: {
                    for (Entity entity : BombermanGame.entities) {
                        if (!(entity instanceof Bomber || entity instanceof Enemy)) {
                            if (entity.intersectRight(this)) {
                                direction = ai.calculateDirection();
                                return;
                            }
                        } else if (entity instanceof Bomber) {
                            if (entity.intersectRight(this)) {
                                ((Bomber) entity).kill();
                            }
                        }
                    }
                    for (Entity entity : BombermanGame.stillObjects) {
                        if (!(entity instanceof Grass)) {
                            if (entity.intersectRight(this)) {
                                direction = ai.calculateDirection();
                                return;
                            }
                        }
                    }
                    for (Bomb bomb : BombermanGame.bombs) {
                        if (bomb.intersectRight(this)) {
                            direction = ai.calculateDirection();
                            return;
                        }
                    }

                    x -= speed;
                    chooseSprite();
                    break;
                }
            }
        }
    }

    @Override
    public void move(int xa, int ya) {
//        y += ya;
//        x += xa;
    }

    @Override
    public boolean canMove() {
        return true;
    }

    public boolean collide(Entity e) {
        if(e instanceof DirectionalExplosion) {
            kill();
            return false;
        }

        if(e instanceof Bomber) {
//            ((Bomber) e).kill();
            return false;
        }

        if (e instanceof Wall) {
            return true;
        }

        return false;
    }

    protected abstract void chooseSprite();
}
