package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import uet.oop.bomberman.audio.Audio;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.movable.Bomber;
import uet.oop.bomberman.entities.movable.enemy.*;
import uet.oop.bomberman.entities.tile.destroyable.Brick;
import uet.oop.bomberman.entities.tile.powerup.*;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.level.Coordinates;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.DataTruncation;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BombermanGame extends Application {
    
    public static int WIDTH;
    public static int HEIGHT;
    public static int LEVEL;
    public static Bomber player;
    public static char[][] currentMap;
    public static int bombRadius = 1;
    public static int bombRate = 1;
    public static String currentLevel;
    public static int lives = 3;


    private Group root;
    public static Screen mainScreen;
    public static GraphicsContext gc;
    private static Canvas canvas;
    public static List<Entity> entities = new ArrayList<>();
    public static List<Entity> stillObjects = new ArrayList<>();
    public static List<Entity> grasses = new ArrayList<>();
    public static List<Bomb> bombs = new ArrayList<>();
    public static AnimationTimer timer;

    private static boolean running = true;
    private static boolean paused = false;

    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) {
        MenuBar menuBar = initMenu();
        currentLevel = "Level1";
        running = true;
        createMap(currentLevel);
        mainScreen = Screen.getInstance(WIDTH, HEIGHT);
        // Tao Canvas
        canvas = mainScreen.getCanvas();
        gc = mainScreen.getGraphicsContext();
        player = Bomber.getInstance();

        player.input = new ArrayList<>(0);
        for (int i=0; i<4; i++) player.input.add(false);

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
        timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                render();
                update();
            }
        };
        timer.start();

        entities.add(player);
        setKeyListener(scene);
    }

    public void setKeyListener(Scene scene) {
        scene.setOnKeyPressed(key -> {
            switch (key.getCode()) {
                case UP : case W: {
                    player.input.set(0, true);
                    player.moving = true;
                    player.direction = 0;
                    break;
                }
                case DOWN: case S: {
                    player.input.set(2, true);
                    player.moving = true;
                    player.direction = 2;
                    break;
                }
                case LEFT: case A: {
                    player.input.set(3, true);
                    player.moving = true;
                    player.direction = 3;
                    break;
                }
                case RIGHT: case D: {
                    player.input.set(1, true);
                    player.moving = true;
                    player.direction = 1;
                    break;
                }
                case B: {
                    if (player.isDetonatorPower()) {
                        for (Bomb bomb : bombs) {
                            bomb.explode();
                        }
                    }
                    break;
                }
                case SPACE: {
                    int xb = Coordinates.pixelToTile(player.getX() + Sprite.SCALED_SIZE / 2);
                    int yb = Coordinates.pixelToTile( player.getY() + Sprite.SCALED_SIZE / 2);
                    if (bombs.size() < bombRate) {
                        Bomb bomb = new Bomb(xb, yb, Sprite.bomb.getFxImage());
                        bombs.add(bomb);
                    }
                    break;
                }
                case P: {
                    Stage newStage = new Stage();
                    try {
                        URL url = new File("src/uet/oop/bomberman/otherScene/ChooseLevel.fxml").toURI().toURL();
                        Parent root = FXMLLoader.load(url);
                        Scene addScene = new Scene(root);
                        newStage.setScene(addScene);
                        newStage.setTitle("Entering Level");
                        newStage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        scene.setOnKeyReleased(key -> {
            player.setMove(false);
            switch (key.getCode()) {
                case UP : case W: {
                    player.input.set(0, false);
                    player.moving = false;
                    break;
                }
                case DOWN: case S: {
                    player.input.set(2, false);
                    player.moving = false;
                    break;
                }
                case LEFT: case A: {
                    player.input.set(3, false);
                    player.moving = false;
                    break;
                }
                case RIGHT: case D: {
                    player.input.set(1, false);
                    player.moving = false;
                    break;
                }
            }
        });
    }

    public static int getRealWidth() {
        return WIDTH * Sprite.SCALED_SIZE;
    }

    public static int getRealHeight() {
        return HEIGHT * Sprite.SCALED_SIZE;
    }

    public static void setMapSize(String size) {
        String[] arr = size.split("\\s+");

        LEVEL = Integer.parseInt(arr[0]);
        HEIGHT = Integer.parseInt(arr[1]);
        WIDTH = Integer.parseInt(arr[2]);
        mainScreen = Screen.getInstance(WIDTH, HEIGHT);
        currentMap = new char[HEIGHT][WIDTH];
    }

    public static void createMap(String level) {
        List<String> lines = null;
        try {
            lines = Files.readAllLines(Paths.get("res/levels/"+ level + ".txt"));
            setMapSize(lines.get(0));
            lines.remove(0);
            loadMap(lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadMap(List<String> lines) {
        Entity object = null;
        //store brick for randoming powerup + portal
        ArrayList<Brick> bricks = new ArrayList<>(0);
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
                        bricks.add((Brick)object);
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

                    case '3' : {
                        object = new Doll(i, j, Sprite.doll_left1.getFxImage());
                        entities.add(object);
                        break;
                    }

                    case '4' : {
                        object = new Minvo(i, j, Sprite.minvo_left1.getFxImage());
                        entities.add(object);
                        break;
                    }

                    case '5' : {
                        object = new Kondoria(i, j, Sprite.kondoria_left1.getFxImage());
                        entities.add(object);
                        break;
                    }

                }
            }
        }
        random(bricks);
    }
    public static void random(ArrayList<Brick> bricks) {
        Random rand = new Random();
        Entity object;
        ArrayList<Powerup> powerups = new ArrayList<>(0);
        powerups.add(new Bombpass(0, 0, Sprite.powerup_bombpass.getFxImage()));
        powerups.add(new Bombs(0, 0, Sprite.powerup_bombs.getFxImage()));
        powerups.add(new Detonator(0,0, Sprite.powerup_detonator.getFxImage()));
        powerups.add(new Flame(0, 0, Sprite.powerup_flames.getFxImage()));
        powerups.add(new Flamepass(0, 0, Sprite.powerup_flamepass.getFxImage()));
        powerups.add(new Speed(0, 0, Sprite.powerup_speed.getFxImage()));
        powerups.add(new Wallpass(0, 0, Sprite.powerup_wallpass.getFxImage()));
        //generate portal
        int temp = rand.nextInt(bricks.size());
        object = new Portal(bricks.get(temp).getX() / Sprite.SCALED_SIZE,

                            bricks.get(temp).getY() / Sprite.SCALED_SIZE, Sprite.portal.getFxImage());
        //portal o dau mang stillobjects de duoc render truoc
        stillObjects.add(0, object);
//        stillObjects.add(object);
        bricks.remove(temp);

        //generate item1;
        int temp1 = rand.nextInt(7);
        temp = rand.nextInt(bricks.size());
        powerups.get(temp1).setCorodinate(bricks.get(temp).getX() , bricks.get(temp).getY());
        //item 1 duoc dung thu 2 trong mang stillobjects
        stillObjects.add(1, powerups.get(temp1));
//        stillObjects.add(powerups.get(temp1));
        powerups.remove(temp1);
        bricks.remove(temp);

        //generate item2;
        int temp2 = rand.nextInt(6);
        temp = rand.nextInt(bricks.size());
        powerups.get(temp2).setCorodinate(bricks.get(temp).getX() , bricks.get(temp).getY());
        //item 2 duoc dung thu 3 trong mang stillobjects
        stillObjects.add(2, powerups.get(temp2));
    }
    public static Entity getEntity(int x, int y) {
        if (getBrick(x, y) != null) {
            return getBrick(x, y);
        } else {
            for (Entity entity : stillObjects) {
                if (entity.getX() == x && entity.getY() == y) {
                    return entity;
                }
            }

            for (Entity entity : entities) {
                if (entity.getX() == x && entity.getY() == y) {
                    return entity;
                }
            }

            return new Grass();
        }
    }

    public static Brick getBrick(int x, int y) {
        for (Entity entity : stillObjects) {
            if (entity instanceof Brick) {
                if (entity.getX() == x && entity.getY() == y) {
                    return (Brick) entity;
                }
            }
        }
        return null;
    }

    public static void update() {
        entities.forEach(Entity::update);
        bombs.forEach(Bomb::update);
    }

    public static void render() {
        mainScreen.getGraphicsContext().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
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
    public static void nextGame(String currentLevel) {
        //clear map
        player = Bomber.getInstance();
        entities.clear();
        stillObjects.clear();
        grasses.clear();
        bombs.clear();

        //createMap
        drawLevelGame();
        createMap(currentLevel);
        entities.add(player);

        resetItem(player);
    }

    public static void restartGame() {
        player = Bomber.getInstance();
        entities.clear();
        stillObjects.clear();
        grasses.clear();
        bombs.clear();

        createMap(currentLevel);
        entities.add(player);

        resetItem(player);
    }

    private MenuBar initMenu() {
        MenuBar menuBar = new MenuBar();

        Menu game = new Menu("Game");
        Menu option = new Menu("Options");
        Menu help = new Menu("Help");

        MenuItem exit = new MenuItem("Exit");
        exit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.exit(0);
            }
        });
        game.getItems().addAll(exit);

        MenuItem chooseLevel = new MenuItem("Choose Level");
        chooseLevel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    Stage newStage = new Stage();
                    Parent root = FXMLLoader.load(getClass().getResource("/otherScene/ChooseLevel.fxml"));
                    Scene addScene = new Scene(root);
                    newStage.setScene(addScene);
                    newStage.setTitle("Entering Level");
                    newStage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        option.getItems().add(chooseLevel);

        menuBar.getMenus().addAll(game, option, help);
        return menuBar;
    }

    public static void resetItem (Bomber bomber) {
        Bomber.powerups.clear();
        bomber.setSpeed(bomber.getSpeed());
        bomber.setWallPass(false);
        bomber.setStillAlive(false);
        bomber.setDetonatorPower(false);
        bombRate = 1;
        bombRadius = 1;
    }

    public static void drawEndGame () {
        mainScreen.getGraphicsContext().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setFill(Color.WHITE);
        gc.setTextAlign(TextAlignment.CENTER);
        Font font = new Font("Arial", 48);
        gc.setFont(font);
        gc.fillText("GAME OVER", getRealWidth() / 2, getRealHeight() / 2);
        timer.stop();
    }

    public static void drawLevelGame () { //TODO: screen Level
        mainScreen.getGraphicsContext().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setFill(Color.WHITE);
        gc.setTextAlign(TextAlignment.CENTER);
        Font font = new Font("Arial", 48);
        gc.setFont(font);
        gc.fillText(currentLevel, getRealWidth() / 2, getRealHeight() / 2);
    }

}
