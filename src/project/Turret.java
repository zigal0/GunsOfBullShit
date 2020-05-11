package project;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Rotate;

public class Turret extends ObjectOnMap {

    final ImageView base;
    final ImageView turret;
    double angleOfTurret = Math.PI / 2;
    final double damage = 20;

    public Turret(double xn, double yn, double playerX, double playerY) {
        super(xn, yn, 96, 96, 0.2, 0, Type.turret, Math.PI / 2, 100);
        base = new ImageView(new Image(getClass()
                .getResource("pics/base.png").toExternalForm()));
        turret = new ImageView(new Image(getClass()
                .getResource("pics/turret.png").toExternalForm()));
        turret.setTranslateY(-20);
        followPlayer(playerX, playerY);
        this.getChildren().addAll(base, turret);
    }

    public void followPlayer(double x, double y) {
        double radian = Math.atan2(y - this.y - 48, x - this.x - 48);
        turret.getTransforms().add(new Rotate(Math.toDegrees(radian + angleOfTurret), 32, 64));
        angleOfTurret = -radian;
    }

    public Bullet shoot(Pane root) {
        Bullet bullet = new Bullet(this.getTranslateX() + 48 + 65 * Math.cos(angleOfTurret),
                this.getTranslateY() + 48 - 65 * Math.sin(angleOfTurret), this.angleOfTurret, damage, 5);
        root.getChildren().add(bullet);
        bullet.getTransforms().add(new Rotate(90 - Math.toDegrees(angleOfTurret)));
        return bullet;
    }
}
