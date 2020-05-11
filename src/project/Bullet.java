package project;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Bullet extends ImageView {
    double angle;
    double damage;
    double speed;

    public Bullet(double x, double y, double a, double damagen, double speedn) {
        this.setImage(new Image(getClass()
                .getResource("pics/bullet.png").toExternalForm()));
        setTranslateX(x);
        setTranslateY(y);
        angle = a;
        damage = damagen;
        speed = speedn;
    }

    void move() {
        setTranslateX(getTranslateX() + speed * Math.cos(angle));
        setTranslateY(getTranslateY() - speed * Math.sin(angle));
    }
}
