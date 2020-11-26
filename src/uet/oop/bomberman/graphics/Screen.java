package uet.oop.bomberman.graphics;

import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.movable.Bomber;


public class Screen {
    public static int WIDTH, HEIGHT;
    public int xOffset = 0;
    public int yOffset = 0;
    private static int[] pixels;  // All pixels of screen

    private static Canvas canvas;
    private static GraphicsContext gc;

    private static Screen INSTANCE;

    private Screen() {

    }

    public static Screen getInstance(int width, int height) {
        if (INSTANCE == null) {
            INSTANCE = new Screen();
            WIDTH = width;
            HEIGHT = height;
            pixels = new int[WIDTH * HEIGHT];
            canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
            gc = canvas.getGraphicsContext2D();
            return INSTANCE;
        }
        else return INSTANCE;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    public GraphicsContext getGraphicsContext() {
        return gc;
    }

    public void setOffset(int x, int y) {
        xOffset = x;
        yOffset = y;
    }

    //Di chuyen man hinh theo nhan vat
    public void calculateXOffset(Bomber player) {
        if (player == null) return;
        int temp = 0;

        int playerX = player.getX();  //   / Sprite.SCALED_SIZE
        double correction = 0.5;
        int firstScreen = BombermanGame.WIDTH / 4;
        int lastScreen = BombermanGame.WIDTH - firstScreen;

        if (playerX > firstScreen && playerX < lastScreen) {
            int halfScreen = BombermanGame.WIDTH / 4;  // Screen Width (show) = 1/2 * realWidth
            temp = playerX - halfScreen;
        }
        gc.translate(-temp, 0);
    }

    // Render theo mang pixels
    public void renderEntity(int xp, int yp, Entity entity) {
        gc.drawImage(entity.getImg(), entity.getX(), entity.getY());
    }
}
