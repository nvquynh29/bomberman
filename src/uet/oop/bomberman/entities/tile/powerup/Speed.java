package uet.oop.bomberman.entities.tile.powerup;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.audio.Audio;
import uet.oop.bomberman.graphics.Screen;

public class Speed extends Powerup {
//    public Speed(int xUnit, int yUnit, Image img, int level) {
//        super(xUnit, yUnit, img, level);
//    }

    public Speed(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void setValues() {
        active = true;
        BombermanGame.player.setSpeed(BombermanGame.player.getSpeed() + 4);
    }

    @Override
    public void update() {

    }

    @Override
    public void render(Screen screen) {
        if(BombermanGame.player.intersect(this)) {
            BombermanGame.player.addPowerUp(this);
            remove();
            BombermanGame.stillObjects.remove(this);
            Audio.MakeSomeNoise(Audio.buffSoundPath);
        }
        screen.getGraphicsContext().drawImage(img, x, y);
    }
}
