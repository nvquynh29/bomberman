package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.graphics.Sprite;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BombermanGame extends Application {
    
    public static int WIDTH;
    public static int HEIGHT;
    public static int LEVEL;
    public static char[][] mainMap;

    private GraphicsContext gc;
    private Canvas canvas;
    private List<Entity> entities = new ArrayList<>();
    private List<Entity> stillObjects = new ArrayList<>();
    private List<Entity> grasses = new ArrayList<>();
    private Bomber player;

    private boolean running = false;
    private boolean paused = true;

    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) {
        running = true;
        createMap();
        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();

        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);

        // Tao scene
        Scene scene = new Scene(root);

        // Them scene vao stage
        stage.setScene(scene);
        stage.show();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                render();
                update();
            }
        };
        timer.start();

        player = new Bomber(1, 1, Sprite.player_right.getFxImage());
        entities.add(player);

        scene.setOnKeyPressed(key -> {
            switch (key.getCode()) {
                case UP : case W: {
                    player.moveUp();
                    break;
                }
                case DOWN: case S: {
                    player.moveDown();
                    break;
                }
                case LEFT: case A: {
                    player.moveLeft();
                    break;
                }
                case RIGHT: case D: {
                    player.moveRight();
                    break;
                }
                case SPACE: {
                    Bomb b = new Bomb(player.getX(), player.getY(), Sprite.bomb_2.getFxImage());
                    break;
                }
            }
        });
    }

    public void setMapSize(String size) {
        String[] arr = size.split("\\s+");

        LEVEL = Integer.parseInt(arr[0]);
        HEIGHT = Integer.parseInt(arr[1]);
        WIDTH = Integer.parseInt(arr[2]);
        mainMap = new char[HEIGHT][WIDTH];
    }

    public void createMap() {
        List<String> lines = null;
        try {
            lines = Files.readAllLines(Paths.get("res/levels/Level1.txt"));
            setMapSize(lines.get(0));
            lines.remove(0);
            loadMap(lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap(List<String> lines) {
        Entity object = null;
        for (int j = 0; j < HEIGHT; ++j) {
            String line = lines.get(j);
            for (int i = 0; i < WIDTH; ++i) {
                grasses.add(new Grass(i, j, Sprite.grass.getFxImage()));
                switch (line.charAt(i)) {
                    case '#' : {
                        object = new Wall(i, j, Sprite.wall.getFxImage());
                        break;
                    }
                    //Change
                    case '*' : {
                        object = new Brick(i, j, Sprite.brick.getFxImage());
                        break;
                    }

                    case 'x' : {
                        object = new Portal(i, j, Sprite.portal.getFxImage());
                        break;
                    }


                    case '1' : {
                        object = new Balloon(i, j, Sprite.balloom_left1.getFxImage());
                        break;
                    }

                    case '2' : {
                        object = new Oneal(i, j, Sprite.oneal_left1.getFxImage());
                        break;
                    }

                    case 's' : {
                        object = new Speed(i, j, Sprite.powerup_speed.getFxImage());
                        break;
                    }

                    case 'f' : {
                        object = new Flame(i, j, Sprite.powerup_flames.getFxImage());
                        break;
                    }

                    default: {
                        object = new Grass(i, j, Sprite.grass.getFxImage());
                        break;
                    }
                }

                entities.add(object);
            }
        }
    }

    public void update() {
        entities.forEach(Entity::update);
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        grasses.forEach(g -> g.render(gc));
        stillObjects.forEach(g -> g.render(gc));
        entities.forEach(g -> g.render(gc));
    }

    public void move(KeyEvent event) {
        switch (event.getCode()) {
            case UP: {
                player.moveUp();
                break;
            }
            case DOWN: {
                player.moveDown();
                break;
            }
        }
    }
}
