package project;

import javafx.scene.layout.StackPane;

public class ObjectOnMap extends StackPane {

    enum Type {player, turret, obstacle, soldier, medkit}

    final Type type;
    double health = 100;
    final double armor;
    double x;
    double y;
    double height;
    double width;
    final int speed;
    double angle;
    double delay;

    public ObjectOnMap(double xn, double yn, double widthn, double heightn,
                       double armorn, int speedn, Type typen, double anglen, double delayn) {
        delay = delayn;
        angle = anglen;
        speed = speedn;
        type = typen;
        x = xn;
        y = yn;
        height = heightn;
        width = widthn;
        this.setTranslateX(xn);
        this.setTranslateY(yn);
        armor = armorn;
    }

    public void loss(double damage) {
        health -= damage * (1 - armor);
    }

    public boolean check4life() {
        return health <= 0;
    }

    public void setTranslate() {
        this.setTranslateX(x);
        this.setTranslateY(y);
    }
}
