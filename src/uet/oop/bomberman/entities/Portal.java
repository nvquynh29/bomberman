package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.movable.Bomber;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;

public class Portal extends Entity {
    public Portal(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void update() {

    }

    @Override
    public void render(Screen screen) {
        if (this.intersect(BombermanGame.player)) {
            BombermanGame.nextGame(BombermanGame.currentLevel);
        }
        else {
            screen.getGraphicsContext().drawImage(img, x - screen.xOffset, y);
        }
    }

}
