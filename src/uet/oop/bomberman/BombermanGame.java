package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.movable.Bomber;
import uet.oop.bomberman.entities.movable.enemy.Balloom;
import uet.oop.bomberman.entities.movable.enemy.Oneal;
import uet.oop.bomberman.entities.tile.destroyable.Brick;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.level.Coordinates;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class BombermanGame extends Application {
    
    public static int WIDTH;
    public static int HEIGHT;
    public static int LEVEL;
    public static Bomber player;
    public static char[][] currentMap;
    public static int bombRadius = 1;


    private Group root;
    public static Screen mainScreen;
    public static GraphicsContext gc;
    private Canvas canvas;
    public static List<Entity> entities = new ArrayList<>();
    public static List<Entity> stillObjects = new ArrayList<>();
    public static List<Entity> grasses = new ArrayList<>();
    public static List<Bomb> bombs = new ArrayList<>();

    private boolean running = false;
    private boolean paused = true;

    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) {
        running = true;
        createMap();
        mainScreen = new Screen(WIDTH, HEIGHT);
        // Tao Canvas
        canvas = mainScreen.getCanvas();
        gc = mainScreen.getGraphicsContext();

        // Tao root container
        root = new Group();
        root.getChildren().add(canvas);
        root.setManaged(false);

        // Tao scene
        Scene scene = new Scene(root);

        // Them scene vao stage
        stage.setScene(scene);
        stage.setTitle("Bomberman - JTeam");
        stage.setResizable(false);
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
        setKeyListener(scene);
    }

    public void setKeyListener(Scene scene) {
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
                    int xb = Coordinates.pixelToTile(player.getX() + Sprite.SCALED_SIZE / 2);
                    int yb = Coordinates.pixelToTile( player.getY() + Sprite.SCALED_SIZE / 2);
                    Bomb bomb = new Bomb(xb, yb, Sprite.bomb.getFxImage());
                    bombs.add(bomb);
                    break;
                }
            }
        });

        scene.setOnKeyReleased(key -> {
            player.setMove(false);
        });
    }

    public static int getRealWidth() {
        return WIDTH * Sprite.SCALED_SIZE;
    }

    public static int getRealHeight() {
        return HEIGHT * Sprite.SCALED_SIZE;
    }

    public void setMapSize(String size) {
        String[] arr = size.split("\\s+");

        LEVEL = Integer.parseInt(arr[0]);
        HEIGHT = Integer.parseInt(arr[1]);
        WIDTH = Integer.parseInt(arr[2]);
        mainScreen = new Screen(WIDTH, HEIGHT);
        currentMap = new char[HEIGHT][WIDTH];
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
                currentMap[j][i] = line.charAt(i);
                switch (line.charAt(i)) {
                    case '#' : {
                        object = new Wall(i, j, Sprite.wall.getFxImage());
                        stillObjects.add(object);
                        break;
                    }

                    case '*' : {
                        object = new Brick(i, j, Sprite.brick.getFxImage());
                        stillObjects.add(object);
                        break;
                    }

                    case 'x' : {
                        object = new Portal(i, j, Sprite.portal.getFxImage());
                        stillObjects.add(object);
                        break;
                    }

                    case '1' : {
                        object = new Balloom(i, j, Sprite.balloom_left1.getFxImage());
                        entities.add(object);
                        break;
                    }

                    case '2' : {
                        object = new Oneal(i, j, Sprite.oneal_left1.getFxImage());
                        entities.add(object);
                        break;
                    }

                    case 's' : {
                        object = new Speed(i, j, Sprite.powerup_speed.getFxImage());
                        stillObjects.add(object);
                        break;
                    }

                    case 'f' : {
                        object = new Flame(i, j, Sprite.powerup_flames.getFxImage());
                        stillObjects.add(object);
                        break;
                    }

                    default: {
                        object = new Grass(i, j, Sprite.grass.getFxImage());
                        stillObjects.add(object);
                        break;
                    }
                }
            }
        }
    }

    public static Entity getEntity(int x, int y) {
        for (Entity entity : entities) {
            if (entity.getX() == x && entity.getY() == y) {
                return entity;
            }
        }

        for (Entity entity : stillObjects) {
            if (entity.getX() == x && entity.getY() == y) {
                return entity;
            }
        }

        return new Grass();
    }

    public void update() {
        entities.forEach(Entity::update);
        bombs.forEach(Bomb::update);
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        grasses.forEach(g -> g.render(mainScreen));
        for (int i = 0; i < stillObjects.size(); ++i) {
            stillObjects.get(i).render(mainScreen);
        }
        for (int i = 0; i < entities.size(); ++i) {
            entities.get(i).render(mainScreen);
        }
        for (int i = 0; i < bombs.size(); ++i) {
            bombs.get(i).render(mainScreen);
        }
    }
}
