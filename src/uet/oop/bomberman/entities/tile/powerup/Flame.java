package uet.oop.bomberman.entities.tile.powerup;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.audio.Audio;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.graphics.Screen;

public class Flame extends Powerup {
    public Flame(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void setValues() {
        active = true;
        BombermanGame.bombRadius += 1;
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
