package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.audio.Audio;
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
            Audio.MakeSomeNoise(Audio.portalPath);
            //get next level
            StringBuilder nextLevel = new StringBuilder(BombermanGame.currentLevel);
            char temp = nextLevel.charAt(nextLevel.length() - 1);
            temp++;
            nextLevel.deleteCharAt(nextLevel.length() -1);
            nextLevel.append(temp);
            BombermanGame.currentLevel = nextLevel.toString();
            if (BombermanGame.currentLevel.equals("Level6")) {
                BombermanGame.currentLevel = "Level5";
            }
            BombermanGame.nextGame(BombermanGame.currentLevel);
        }
        else {
            screen.getGraphicsContext().drawImage(img, x - screen.xOffset, y);
        }
    }

}
