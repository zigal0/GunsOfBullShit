package project;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main extends Application {

    // variables
    enum State {game, menu, gameOver, pause, wait}

    State state = State.menu;

    ScoreBoard scoreBoard = new ScoreBoard();
    String nickName = "Player";
    static int resolutionWidth = 1200;
    static int resolutionHeight = 800;
    static Character player;
    Pane root = new Pane();
    final int edge = 12; // size of boarder
    int points = 0; // score
    int t = 0; // time for shooting, need to fix
    boolean[] keysPressed = new boolean[4];
    boolean mousePressed = false;
    ArrayList<Bullet> bullets = new ArrayList<>();
    ArrayList<ObjectOnMap> objectOnMaps = new ArrayList<>();
    Sounds sounds = new Sounds();
    int quantityOfTurrets = 0;
    int quantityOfSoldiers = 0;
    int quantityOfMedkits = 0;
    Scene scene;

    AnimationTimer at;
    private final MyTimer myTimer = new MyTimer();

    Label sc;
    Label hp;
    StackPane scorePanel;
    StackPane hpPanel;

    BackToMenu backToMenu;

    private final MyHandlerForPressed myHandlerForPressed = new MyHandlerForPressed();
    private final MyHandlerForReleased myHandlerForReleased = new MyHandlerForReleased();
    private final PressEnter pressEnter = new PressEnter();

    private class MyTimer extends AnimationTimer {

        @Override
        public void handle(long now) {
            if (state == State.wait) {
                if (backToMenu.respond == BackToMenu.Respond.backToGame) {
                    at.start();
                    scene.setCursor(new ImageCursor(new Image(getClass()
                            .getResource("pics/aim.png").toExternalForm())));
                    state = State.game;
                } else if (backToMenu.respond == BackToMenu.Respond.restart) {
                    restart();
                    state = State.menu;
                }
            }
            if (state == State.pause) {
                backToMenu = new BackToMenu();
                root.getChildren().add(backToMenu);
                scene.setCursor(new ImageCursor(new Image(getClass()
                        .getResource("pics/cursor.png").toExternalForm())));
                state = State.wait;
            }
        }
    }

    private class PressEnter implements EventHandler<KeyEvent> {
        @Override
        public void handle(KeyEvent keyEvent) {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                state = State.menu;
                restart();
            }
        }
    }

    private class MyHandlerForReleased implements EventHandler<KeyEvent> {

        @Override
        public void handle(KeyEvent keyEvent) {
            switch (keyEvent.getCode()) {
                case W:
                    keysPressed[0] = false;
                    break;
                case S:
                    keysPressed[1] = false;
                    break;
                case A:
                    keysPressed[2] = false;
                    break;
                case D:
                    keysPressed[3] = false;
                    break;
                case DIGIT1:
                    sounds.wpnSelect();
                    player.takeAK();
                    break;
                case DIGIT2:
                    sounds.wpnSelect();
                    player.takeAWP();
                    break;
                case DIGIT3:
                    sounds.wpnSelect();
                    player.takeDEAGLE();
                    break;
                case ESCAPE:
                    if (state != State.pause && state != State.wait) {
                        state = State.pause;
                        at.stop();
                    }
                    break;
                case T:
                    createObjectOnMap(ObjectOnMap.Type.turret);
                    break;
                case Y:
                    createObjectOnMap(ObjectOnMap.Type.obstacle);
                    break;
                case G:
                    createObjectOnMap(ObjectOnMap.Type.soldier);
                    break;
                case H:
                    createObjectOnMap(ObjectOnMap.Type.medkit);
                    break;
            }
        }
    }

    private class MyHandlerForPressed implements EventHandler<KeyEvent> {

        @Override
        public void handle(KeyEvent keyEvent) {
            switch (keyEvent.getCode()) {
                case W:
                    keysPressed[0] = true;
                    break;
                case S:
                    keysPressed[1] = true;
                    break;
                case A:
                    keysPressed[2] = true;
                    break;
                case D:
                    keysPressed[3] = true;
                    break;
            }
        }
    }

    @Override
    public void start(Stage primaryStage) {

        scene = new Scene(root, resolutionWidth, resolutionHeight);
        myTimer.start();
        setNickName();

        // Icon of app
        Image icon = new Image(getClass().getResource("pics/icon.png").toExternalForm());
        primaryStage.getIcons().add(icon);

        primaryStage.setTitle("GunsOfBullShit");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void setNickName() {

        // Cursor in menu
        scene.setCursor(new ImageCursor(new Image(getClass()
                .getResource("pics/cursor.png").toExternalForm())));

        ImageView imageView = new ImageView(new Image(getClass()
                .getResource("pics/backGroundMenu.jpg").toExternalForm()));
        imageView.setFitWidth(resolutionWidth + edge);
        imageView.setFitHeight(resolutionHeight + edge);
        TextField nick = new TextField("Your nickname");
        nick.setMaxWidth(150);
        StackPane panelForNick = new StackPane();

        Rectangle back = new Rectangle(resolutionWidth + edge, resolutionHeight + edge);
        back.setFill(Color.BLACK);
        back.setOpacity(0.6);

        Rectangle bg = new Rectangle(300, 150);
        bg.setFill(Colors.MENU_BG);

        Rectangle lineTop = new Rectangle(300, 2);
        lineTop.setFill(Colors.MENU_BORDER);
        lineTop.setStroke(Color.BLACK);

        Rectangle lineBot = new Rectangle(300, 2);
        lineBot.setFill(Colors.MENU_BORDER);
        lineBot.setStroke(Color.BLACK);

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(lineTop, bg, lineBot);

        MenuItem ok = new MenuItem("OK", 100);
        ok.setOnAction(() -> {
            if (nick.getText().length() != 0) {
                nickName = nick.getText();
            }
            root.getChildren().clear();
            createMainMenu();
        });
        VBox vBox2 = new VBox();
        vBox2.setAlignment(Pos.CENTER);
        vBox2.getChildren().addAll(nick, ok);
        VBox.setMargin(nick, new Insets(0, 0, 40, 0));
        VBox.setMargin(ok, new Insets(0, 0, 0, 580));
        panelForNick.setAlignment(Pos.CENTER);
        panelForNick.getChildren().addAll(back, vBox, vBox2);
        root.getChildren().addAll(imageView, panelForNick);
    }

    private void createMainMenu() {

        // Cursor in menu
        scene.setCursor(new ImageCursor(new Image(getClass()
                .getResource("pics/cursor.png").toExternalForm())));

        Media MainMusic = new Media(getClass()
                .getResource("sounds/mainMenu.mp3").toExternalForm());
        MediaPlayer music = new MediaPlayer(MainMusic);
        MediaView mediaView = new MediaView(music);

        music.setCycleCount(MediaPlayer.INDEFINITE);

        ImageView imageView = new ImageView(new Image(getClass()
                .getResource("pics/backGroundMenu.jpg").toExternalForm()));

        imageView.setFitWidth(resolutionWidth + edge);
        imageView.setFitHeight(resolutionHeight + edge);

        MenuBox menuBoxMain = new MenuBox(250, 350);
        menuBoxMain.setTranslateX(50);
        menuBoxMain.setTranslateY(200);

        MenuBox menuBoxAdd = new MenuBox(400, 350);
        menuBoxAdd.setTranslateX(50 + 20 + 250);
        menuBoxAdd.setTranslateY(200);

        MenuItem itemNew = new MenuItem("NEW", 250);
        itemNew.setOnAction(() -> {
            menuBoxAdd.remove();
            MenuItem itemCampaign = new MenuItem("CAMPAIGN", 400);
            itemCampaign.setOnAction(() -> {
                root.getChildren().clear();
                music.stop();
                runGame();
            });
            MenuItem itemOnline = new MenuItem("ONLINE", 400);
            itemOnline.setOnAction(() -> root.getChildren().add(new NotAdded()));
            menuBoxAdd.addItems(itemCampaign, itemOnline);
        });
        menuBoxMain.addItem(itemNew);

        MenuItem itemSettings = new MenuItem("SETTINGS", 250);
        itemSettings.setOnAction(() -> {
            menuBoxAdd.remove();
            MenuItem itemGameplay = new MenuItem("GAMEPLAY", 400);
            itemGameplay.setOnAction(() -> root.getChildren().add(new NotAdded()));
            MenuItem itemControls = new MenuItem("CONTROLS", 400);
            itemControls.setOnAction(() -> root.getChildren().add(new NotAdded()));
            MenuItem itemDisplay = new MenuItem("DISPLAY", 400);
            itemDisplay.setOnAction(() -> root.getChildren().add(new NotAdded()));
            MenuItem itemAudio = new MenuItem("AUDIO", 400);
            itemAudio.setOnAction(() -> root.getChildren().add(new NotAdded()));
            menuBoxAdd.addItems(itemGameplay, itemControls, itemDisplay, itemAudio);
        });

        menuBoxMain.addItem(itemSettings);

        MenuItem itemRecords = new MenuItem("RECORDS", 250);
        itemRecords.setOnAction(() -> {
            StackPane gfx = scoreBoard.show();
            root.getChildren().add(gfx);
        });

        menuBoxMain.addItem(itemRecords);

        MenuItem itemExit = new MenuItem("EXIT", 250);
        itemExit.setOnAction(() -> root.getChildren().add(new Confirm()));
        menuBoxMain.addItem(itemExit);

        Text name = new Text(nickName);
        name.setFont(MyFont.font4Name);
        name.setFill(Color.YELLOW);
        name.setStroke(Color.BLACK);
        VBox vBox = new VBox(name);
        vBox.setAlignment(Pos.CENTER);
        vBox.setTranslateX(100);
        vBox.setTranslateY(150);

        final char dm = (char) 34;
        Text author = new Text("Created by team " + dm + "Single" + dm);
        author.setFont(MyFont.font4Team);
        author.setFill(Color.YELLOW);
        author.setStroke(Color.BLACK);
        author.setTranslateX(50);
        author.setTranslateY(780);

        root.getChildren().addAll(imageView, menuBoxMain, menuBoxAdd, mediaView, vBox, author);

        music.play();
    }

    private void runGame() {
        state = State.game;
        root.setPrefSize(resolutionWidth, resolutionHeight);
        player = createMapAndPlayer();
        sounds.prepare();

        // action with keyboard
        scene.addEventHandler(KeyEvent.KEY_PRESSED, myHandlerForPressed);
        scene.addEventHandler(KeyEvent.KEY_RELEASED, myHandlerForReleased);

        // AnimationTimer start
        at = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
            }
        };
        at.start();

        // crosshair
        Image aim = new Image(getClass()
                .getResource("pics/aim.png").toExternalForm());
        scene.setCursor(new ImageCursor(aim, aim.getWidth() / 2, aim.getWidth() / 2));
        EventHandler<MouseEvent> eventRotate = e -> player.rotateByMouse(e.getX(), e.getY());

        // movement and action with mouse
        scene.setOnMouseMoved(eventRotate);
        scene.setOnMouseDragged(eventRotate);
        scene.setOnMousePressed(event -> mousePressed = true);
        scene.setOnMouseReleased(event -> mousePressed = false);

        createBasicConfiguration();

        //Score board
        Rectangle backOfScore = new Rectangle(100, 50, Colors.BACK_OF_SCORE);
        backOfScore.setOpacity(0.5);
        scorePanel = new StackPane();
        sc = new Label(Integer.toString(points));
        sc.setTextFill(Colors.SCORE);
        sc.setFont(new Font("Arial", 30));
        scorePanel.getChildren().addAll(backOfScore, sc);
        root.getChildren().add(scorePanel);

        // health points of player
        Rectangle backOfHP = new Rectangle(100, 50, Colors.BACK_OF_HP);
        backOfHP.setOpacity(0.5);
        hpPanel = new StackPane();
        hp = new Label(Integer.toString(100));
        hp.setTextFill(Colors.HP);
        hp.setFont(new Font("Arial", 30));
        ImageView plus = new ImageView(new Image(getClass()
                .getResource("pics/plus.png").toExternalForm()));
        HBox hBox = new HBox(plus, hp);
        hBox.setAlignment(Pos.CENTER);
        hpPanel.setTranslateX(1100);
        hpPanel.setTranslateY(750);
        hpPanel.getChildren().addAll(backOfHP, hBox);
        root.getChildren().add(hpPanel);
    }

    private Character createMapAndPlayer() {
        createBack();
        Character player = new Character(Math.random() * (resolutionWidth - 52),
                Math.random() * (resolutionHeight - 52));
        objectOnMaps.add(player);
        root.getChildren().add(player);
        return player;
    }

    private void createBasicConfiguration() {
        for (int i = 0; i < 10; i++) {
            createObjectOnMap(ObjectOnMap.Type.obstacle);
        }
    }

    private void createBack() {
        ImageView background = new ImageView(new Image(getClass()
                .getResource("pics/background.jpg").toExternalForm()));
        background.setFitWidth(resolutionWidth + edge);
        background.setFitHeight(resolutionHeight + edge);

        ImageView boarderUp = new ImageView(new Image(getClass()
                .getResource("pics/boarderUpDown.jpg").toExternalForm()));
        boarderUp.setFitWidth(resolutionWidth + edge);

        ImageView boarderDown = new ImageView(new Image(getClass()
                .getResource("pics/boarderUpDown.jpg").toExternalForm()));
        boarderDown.setFitWidth(resolutionWidth + edge);
        boarderDown.setTranslateY(802);

        ImageView boarderRight = new ImageView(new Image(getClass()
                .getResource("pics/boarderRightLeft.jpg").toExternalForm()));
        boarderRight.setFitHeight(resolutionHeight + edge);
        boarderRight.setTranslateX(1202);

        ImageView boarderLeft = new ImageView(new Image(getClass()
                .getResource("pics/boarderRightLeft.jpg").toExternalForm()));
        boarderLeft.setFitHeight(resolutionHeight + edge);

        root.getChildren().addAll(background, boarderUp, boarderDown, boarderLeft, boarderRight);
    }

    private void update() {
        if (t == 400) {
            t = 0;
        }
        if (t % 200 == 0 && (quantityOfTurrets + quantityOfSoldiers) < 5) {
            if (Math.random() >= 0.5) {
                createObjectOnMap(ObjectOnMap.Type.turret);
                quantityOfTurrets++;
            } else {
                createObjectOnMap(ObjectOnMap.Type.soldier);
                quantityOfSoldiers++;
            }
        }
        if (t % 400 == 0 && quantityOfMedkits < 3) {
            createObjectOnMap(ObjectOnMap.Type.medkit);
            quantityOfMedkits++;
        }
        if (mousePressed) {
            if (t % player.delay == 1) {
                bullets.add(player.shoot(root));
                switch (player.weapon) {
                    case ak:
                        sounds.ak47();
                        break;
                    case awp:
                        sounds.awp();
                        break;
                    case deagle:
                        sounds.deagle();
                        break;
                }
            }
        }
        ArrayList<ObjectOnMap> deleteObjectOnMap = new ArrayList<>();
        boolean[] confirm = {keysPressed[0], keysPressed[1], keysPressed[2], keysPressed[3]};
        objectOnMaps.forEach(objectOnMap -> {
            if (objectOnMap.type != ObjectOnMap.Type.player && objectOnMap.type != ObjectOnMap.Type.medkit) {
                if (objectOnMap.type == ObjectOnMap.Type.turret) {
                    Turret turret = (Turret) objectOnMap;
                    turret.followPlayer(player.getTranslateX() + 32, player.getTranslateY() + 32);
                    if (t % turret.delay == 0 || t % (turret.delay - 5) == 0) {
                        bullets.add(turret.shoot(root));
                        sounds.turret();
                    }
                }
                if (objectOnMap.type == ObjectOnMap.Type.soldier) {
                    Soldier soldier = (Soldier) objectOnMap;
                    soldier.followPlayer(player.getTranslateX() + 32, player.getTranslateY() + 32);
                    soldier.move(objectOnMaps);
                    if (t % soldier.delay == 0) {
                        bullets.add(soldier.shoot(root));
                        sounds.m4a1();
                    }
                }
                boolean[] cur = {false, false, false, false};
                if (keysPressed[0]) {
                    cur[0] = ((player.y - player.speed >= objectOnMap.y + objectOnMap.height
                            || player.y + player.height <= objectOnMap.y)
                            || (player.x + player.width <= objectOnMap.x
                            || player.x >= objectOnMap.x + objectOnMap.width));
                }
                if (keysPressed[1]) {
                    cur[1] = ((player.y >= objectOnMap.y + objectOnMap.height
                            || player.y + player.height + player.speed <= objectOnMap.y)
                            || (player.x + player.width <= objectOnMap.x
                            || player.x >= objectOnMap.x + objectOnMap.width));
                }
                if (keysPressed[2]) {
                    cur[2] = ((player.x - player.speed >= objectOnMap.x + objectOnMap.width
                            || player.x + player.width <= objectOnMap.x)
                            || (player.y + player.height <= objectOnMap.y
                            || player.y >= objectOnMap.y + objectOnMap.height));
                }
                if (keysPressed[3]) {
                    cur[3] = ((player.x >= objectOnMap.x + objectOnMap.width
                            || player.x + player.width + player.speed <= objectOnMap.x)
                            || (player.y + player.height <= objectOnMap.y
                            || player.y >= objectOnMap.y + objectOnMap.height));
                }
                for (int i = 0; i < 4; i++) {
                    confirm[i] = confirm[i] && cur[i];
                }
            }
            if (objectOnMap.type == ObjectOnMap.Type.medkit) {
                if (check4Cross(player, objectOnMap)) {
                    sounds.wpnHudOn();
                    quantityOfMedkits--;
                    player.addHP();
                    root.getChildren().remove(objectOnMap);
                    deleteObjectOnMap.add(objectOnMap);
                }
            }
        });

        if (confirm[0]) player.moveUp();
        if (confirm[1]) player.moveDown();
        if (confirm[2]) player.moveLeft();
        if (confirm[3]) player.moveRight();

        ArrayList<Bullet> deleteBullets = new ArrayList<>();
        bullets.forEach(bullet -> {
            bullet.move();
            if (bullet.getTranslateX() >= resolutionWidth || bullet.getTranslateX() <= 0) {
                root.getChildren().remove(bullet);
                deleteBullets.add(bullet);
            }
            if (bullet.getTranslateY() >= resolutionHeight || bullet.getTranslateY() <= 0) {
                root.getChildren().remove(bullet);
                deleteBullets.add(bullet);
            }
            objectOnMaps.forEach(objectOnMap -> {
                if ((bullet.getTranslateX() >= objectOnMap.x &&
                        bullet.getTranslateX() <= objectOnMap.x + objectOnMap.width) &&
                        (bullet.getTranslateY() >= objectOnMap.y
                                && bullet.getTranslateY() <= objectOnMap.y + objectOnMap.height)) {
                    root.getChildren().remove(bullet);
                    deleteBullets.add(bullet);
                    switch (objectOnMap.type) {
                        case player:
                        case soldier:
                            sounds.hit();
                            break;
                        case turret:
                            sounds.ricmetal();
                            break;
                        case obstacle:
                            Obstacle obstacle = (Obstacle) objectOnMap;
                            if (obstacle.typeoFObstacle == Obstacle.TypeOfObstacle.greenCrate) {
                                sounds.ric();
                            } else {
                                sounds.ricmetal();
                            }
                    }
                    objectOnMap.loss(bullet.damage);
                    if (objectOnMap.check4life()) {
                        switch (objectOnMap.type) {
                            case soldier:
                                quantityOfSoldiers--;
                                sounds.die();
                                points += 2;
                                sc.setText(Integer.toString(points));
                                deleteObjectOnMap.add(objectOnMap);
                                root.getChildren().remove(objectOnMap);
                                break;
                            case player:
                                at.stop();
                                scoreBoard.writeResult(points, nickName);
                                sounds.die();
                                endGame();
                                sounds.unstoppable();
                                break;
                            case turret:
                                quantityOfTurrets--;
                                sounds.explosion();
                                Explosion exp = new Explosion(objectOnMap.x + objectOnMap.width / 2, objectOnMap.y + objectOnMap.height / 2);
                                exp.animation.play();
                                root.getChildren().add(exp.exp);
                                points++;
                                sc.setText(Integer.toString(points));
                                sounds.explode();
                                deleteObjectOnMap.add(objectOnMap);
                                root.getChildren().remove(objectOnMap);
                                break;
                        }
                    }
                }
            });
        });
        deleteBullets.forEach(bullet -> bullets.remove(bullet));
        deleteObjectOnMap.forEach(objectOnMap -> objectOnMaps.remove(objectOnMap));
        t++;
        hp.setText(Integer.toString((int) player.health));
    }

    private void createObjectOnMap(ObjectOnMap.Type type) {
        switch (type) {
            case medkit:
                findFreePlace(new MedKit(0, 0));
                break;
            case turret:
                findFreePlace(new Turret(0, 0, player.xc, player.yc));
                break;
            case obstacle:
                Obstacle.TypeOfObstacle typo;
                if (Math.random() >= 0.5) {
                    typo = Obstacle.TypeOfObstacle.greenCrate;
                } else {
                    if (Math.random() >= 0.5) {
                        typo = Obstacle.TypeOfObstacle.container1;
                    } else {
                        typo = Obstacle.TypeOfObstacle.container2;
                    }
                }
                findFreePlace(new Obstacle(0, 0, typo, Math.PI / 2));
                break;
            case soldier:
                findFreePlace(new Soldier(0, 0, player.xc, player.yc));
                break;
        }
    }

    private void findFreePlace(ObjectOnMap object) {
        AtomicBoolean res = new AtomicBoolean(true);
        while (res.get()) {
            res.set(false);
            object.x = edge + Math.random() * (resolutionWidth - edge - object.width);
            object.y = edge + Math.random() * (resolutionHeight - edge - object.height);
            res.set((object.x <= player.xc + 200) && (object.x >= player.xc - 200)
                    && (object.y <= player.yc + 200) && (object.y >= player.yc - 200));
            objectOnMaps.forEach(objectOnMap -> res.set(res.get() || check4Cross(object, objectOnMap)));
        }
        object.setTranslate();
        objectOnMaps.add(object);
        root.getChildren().add(object);
    }

    private boolean check4Cross(ObjectOnMap object1, ObjectOnMap object2) {
        return (object1.x >= object2.x - object1.width)
                && (object1.x <= object2.x + object2.width)
                && (object1.y >= object2.y - object1.height)
                && (object1.y <= object2.y + object2.height);
    }

    private void endGame() {
        state = State.gameOver;
        objectOnMaps.forEach(objectOnMap -> root.getChildren().remove(objectOnMap));
        bullets.forEach(bullet -> root.getChildren().remove(bullet));
        root.getChildren().remove(scorePanel);
        root.getChildren().remove(hpPanel);

        Label gameOver = new Label("GAME OVER");
        gameOver.setTextFill(Colors.SCORE);
        gameOver.setFont(MyFont.font4Score);
        Label urscore = new Label("SCORE: " + points);
        urscore.setTextFill(Colors.SCORE);
        urscore.setFont(MyFont.font4Score);
        Label enter = new Label("press enter key to continue");
        enter.setTextFill(Colors.SCORE);
        enter.setFont(MyFont.font4Item);
        VBox vBox = new VBox(gameOver, urscore, enter);
        vBox.setAlignment(Pos.CENTER);
        vBox.setTranslateX(430);
        vBox.setTranslateY(300);

        Rectangle masker = new Rectangle(resolutionWidth + edge, resolutionHeight + edge);
        masker.setOpacity(0.5);
        root.getChildren().addAll(masker, vBox);
        scene.addEventHandler(KeyEvent.KEY_RELEASED, pressEnter);
    }

    private void restart() {
        scene.removeEventHandler(KeyEvent.KEY_RELEASED, pressEnter);
        points = 0;
        quantityOfTurrets = 0;
        quantityOfSoldiers = 0;
        quantityOfMedkits = 0;
        t = 0;
        bullets.clear();
        objectOnMaps.clear();
        root.getChildren().clear();
        scene.removeEventHandler(KeyEvent.KEY_RELEASED, myHandlerForReleased);
        scene.removeEventHandler(KeyEvent.KEY_PRESSED, myHandlerForPressed);
        createMainMenu();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
