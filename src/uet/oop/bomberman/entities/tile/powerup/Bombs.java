package uet.oop.bomberman.entities.tile.powerup;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.audio.Audio;
import uet.oop.bomberman.graphics.Screen;

public class Bombs extends Powerup {

    public Bombs(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void setValues() {
        active = true;
        BombermanGame.bombRate += 1;
    }

    @Override
    public void update() {

    }

    @Override
    public void render(Screen screen) {
        if(BombermanGame.player.intersect(this)) {
            BombermanGame.player.addPowerUp(this);
            remove();
            Audio.MakeSomeNoise(Audio.buffSoundPath);
            BombermanGame.stillObjects.remove(this);
        }
        screen.getGraphicsContext().drawImage(img, x, y);
    }
}
