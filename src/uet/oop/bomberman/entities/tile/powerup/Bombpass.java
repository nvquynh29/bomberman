package uet.oop.bomberman.entities.tile.powerup;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Screen;

public class Bombpass extends Powerup {

    public Bombpass(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void setValues() {
        active = true;
        BombermanGame.player.setCanMove(true);
    }

    @Override
    public void update() {

    }

    @Override
    public void render(Screen screen) {
        if(BombermanGame.player.intersect(this)) {
            BombermanGame.player.addPowerUp(this);
            remove();
            System.out.println("removed");
            BombermanGame.stillObjects.remove(this);
        }
        screen.getGraphicsContext().drawImage(img, x, y);
    }
}
