package project;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Rotate;

import static project.Main.resolutionHeight;
import static project.Main.resolutionWidth;

public class Character extends ObjectOnMap {

    enum Weapon {ak, awp, deagle}

    Weapon weapon = Weapon.ak;
    double pointerPrevX = 0;
    double pointerPrevY = 0;
    double xc;
    double yc;
    ImageView character;
    double damage = 10;
    double speedOfBullet = 15;

    public Character(double xn, double yn) {
        super(xn, yn, 64, 64, 0.5, 2, Type.player, Math.toRadians(90), 5);
        xc = xn + 32;
        yc = yn + 32;
        character = new ImageView(new Image(getClass()
                .getResource("pics/player.png").toExternalForm()));
        character.setViewport(new Rectangle2D(0, 0, width, height));
        this.getChildren().add(character);
        this.setTranslateX(xn);
        this.setTranslateY(yn);
    }

    public void addHP() {
        health += 50;
        if (health > 100) {
            health = 100;
        }
    }

    public void takeAK() {
        character.setViewport(new Rectangle2D(0, 0, width, height));
        weapon = Weapon.ak;
        delay = 7;
        damage = 20;
        speedOfBullet = 15;
    }

    public void takeAWP() {
        character.setViewport(new Rectangle2D(64, 0, width, height));
        weapon = Weapon.awp;
        delay = 80;
        damage = 100;
        speedOfBullet = 50;
    }

    public void takeDEAGLE() {
        character.setViewport(new Rectangle2D(128, 0, width, height));
        weapon = Weapon.deagle;
        delay = 20;
        damage = 50;
        speedOfBullet = 20;
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
        rotate();
        boolean down = yadd > 0;
        for (int i = 0; i < Math.abs(yadd); i++) {
            if (down) this.setTranslateY(this.getTranslateY() + 1);
            else this.setTranslateY(this.getTranslateY() - 1);
        }
    }

    private void moveX(int xadd) {
        xc += xadd;
        x += xadd;
        rotate();
        boolean right = xadd > 0;
        for (int i = 0; i < Math.abs(xadd); i++) {
            if (right) this.setTranslateX(this.getTranslateX() + 1);
            else this.setTranslateX(this.getTranslateX() - 1);
        }
    }

    public void rotate() {
        double objX = this.getTranslateX() + 32;
        double objY = this.getTranslateY() + 32;
        double radian = Math.atan2(pointerPrevY - objY, pointerPrevX - objX);
        this.getTransforms().add(new Rotate(Math.toDegrees(radian + angle), 32, 32));
        angle = -radian;
    }

    public void rotateByMouse(double xm, double ym) {
        pointerPrevX = xm;
        pointerPrevY = ym;
        rotate();
    }

    public Bullet shoot(Pane root) {

        Bullet bullet = new Bullet(xc + 40 * Math.cos(angle),
                yc - 40 * Math.sin(angle), angle, damage, speedOfBullet);
        root.getChildren().add(bullet);
        bullet.getTransforms().add(new Rotate(90 - Math.toDegrees(angle)));
        return bullet;
    }
}
