package uet.oop.bomberman.entities.tile.powerup;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Screen;

public class Wallpass extends Powerup {

    public Wallpass(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void update() {

    }

    @Override
    public void setValues() {
        active = true;
        BombermanGame.player.setWallPass(true);
    }

    @Override
    public void render(Screen screen) {
        if(BombermanGame.player.intersect(this)) {
            BombermanGame.player.addPowerUp(this);
            remove();
            BombermanGame.stillObjects.remove(this);
            System.out.println("removed");
        }
        screen.getGraphicsContext().drawImage(img, x, y);
    }
}
