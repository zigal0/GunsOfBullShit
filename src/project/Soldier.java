package project;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Rotate;

import java.util.ArrayList;

import static project.Main.resolutionHeight;
import static project.Main.resolutionWidth;

public class Soldier extends ObjectOnMap {

    double xc;
    double yc;
    ImageView soldier;
    int stepsLeft = 0;
    boolean[] accept = new boolean[4];
    final double damage = 10;

    public Soldier(double xn, double yn, double playerX, double playerY) {
        super(xn, yn, 64, 64, 0.1, 2, Type.soldier, Math.PI / 2, 50);
        soldier = new ImageView(new Image(getClass()
                .getResource("pics/soldier.png").toExternalForm()));
        this.getChildren().add(soldier);
        followPlayer(playerX, playerY);
    }

    public void followPlayer(double x, double y) {
        double radian = Math.atan2(y - yc, x - xc);
        soldier.getTransforms().add(new Rotate(Math.toDegrees(radian + angle), 32, 32));
        angle = -radian;
    }

    @Override
    public void setTranslate() {
        xc = x + 32;
        yc = y + 32;
        this.setTranslateX(x);
        this.setTranslateY(y);
    }

    public void move(ArrayList<ObjectOnMap> objectOnMaps) {
        if (stepsLeft < speed) {
            for (int i = 0; i < 4; i++) {
                accept[i] = false;
            }
            randomDirection();
        }
        stepsLeft -= speed;
        boolean[] confirm = {accept[0], accept[1], accept[2], accept[3]};
        objectOnMaps.forEach(objectOnMap -> {
            boolean[] cur = {false, false, false, false};
            if (objectOnMap != this && objectOnMap.type != Type.medkit) {
                if (accept[0]) {
                    cur[0] = ((y - speed >= objectOnMap.y + objectOnMap.height
                            || y + height <= objectOnMap.y)
                            || (x + width <= objectOnMap.x
                            || x >= objectOnMap.x + objectOnMap.width));
                }
                if (accept[1]) {
                    cur[1] = ((y >= objectOnMap.y + objectOnMap.height
                            || y + height + speed <= objectOnMap.y)
                            || (x + width <= objectOnMap.x
                            || x >= objectOnMap.x + objectOnMap.width));
                }
                if (accept[2]) {
                    cur[2] = ((x - speed >= objectOnMap.x + objectOnMap.width
                            || x + width <= objectOnMap.x)
                            || (y + height <= objectOnMap.y
                            || y >= objectOnMap.y + objectOnMap.height));
                }
                if (accept[3]) {
                    cur[3] = ((x >= objectOnMap.x + objectOnMap.width
                            || x + width + speed <= objectOnMap.x)
                            || (y + height <= objectOnMap.y
                            || y >= objectOnMap.y + objectOnMap.height));
                }
                for (int i = 0; i < 4; i++) {
                    confirm[i] = confirm[i] && cur[i];
                }
            }
        });

        if (confirm[0]) moveUp();
        if (confirm[1]) moveDown();
        if (confirm[2]) moveLeft();
        if (confirm[3]) moveRight();
    }

    public void randomDirection() {
        stepsLeft = (int) (200 * Math.random());
        for (int i = 0; i < 2; i++) {
            double rnd = 4 * Math.random();
            if (rnd <= 1) {
                accept[0] = true;
            } else if (rnd <= 2 && rnd > 1) {
                accept[1] = true;
            } else if (rnd <= 3 && rnd > 2) {
                accept[2] = true;
            } else if (rnd <= 4 && rnd > 3) {
                accept[3] = true;
            }
        }
    }

    public void moveLeft() {
        if (this.getTranslateX() >= 8 + speed) {
            this.moveX(-speed);
        }
    }

    public void moveRight() {
        if (this.getTranslateX() <= resolutionWidth - width + speed) {
            this.moveX(speed);
        }
    }

    public void moveUp() {
        if (this.getTranslateY() >= 8 + speed) {
            this.moveY(-speed);
        }
    }

    public void moveDown() {
        if (this.getTranslateY() <= resolutionHeight - height + speed) {
            this.moveY(speed);
        }
    }

    private void moveY(int yadd) {
        yc += yadd;
        y += yadd;
        boolean down = yadd > 0;
        for (int i = 0; i < Math.abs(yadd); i++) {
            if (down) this.setTranslateY(this.getTranslateY() + 1);
            else this.setTranslateY(this.getTranslateY() - 1);
        }
    }

    private void moveX(int xadd) {
        xc += xadd;
        x += xadd;
        boolean right = xadd > 0;
        for (int i = 0; i < Math.abs(xadd); i++) {
            if (right) this.setTranslateX(this.getTranslateX() + 1);
            else this.setTranslateX(this.getTranslateX() - 1);
        }
    }

    public Bullet shoot(Pane root) {
        Bullet bullet = new Bullet(xc + 35 * Math.cos(angle),
                yc - 35 * Math.sin(angle), angle, damage, 10);
        root.getChildren().add(bullet);
        bullet.getTransforms().add(new Rotate(90 - Math.toDegrees(angle)));
        return bullet;
    }
}
