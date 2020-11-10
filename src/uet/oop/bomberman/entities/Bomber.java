package uet.oop.bomberman.entities;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import uet.oop.bomberman.graphics.Sprite;

public class Bomber extends Entity {
    private int speed = Sprite.SCALED_SIZE / 4;

    public Bomber(int x, int y, Image img) {
        super( x, y, img);
    }



    public void setImage(Image image) {
        this.img = image;
    }

    public void moveUp() {
        y -= speed;
        setImage(Sprite.player_up.getFxImage());
    }

    public void moveDown() {
        y += speed;
        setImage(Sprite.player_down.getFxImage());
    }

    public void moveLeft() {
        x -= speed;
        setImage(Sprite.player_left.getFxImage());
    }

    public void moveRight() {
        x += speed;
        setImage(Sprite.player_right.getFxImage());
    }


    @Override
    public void update() {

    }
}
