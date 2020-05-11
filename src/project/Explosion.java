package project;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class Explosion extends Pane {

    final ImageView exp = new ImageView(new Image(getClass()
            .getResource("pics/explosion.png").toExternalForm()));
    final int rows = 25;
    final int columns = 5;
    final int width = 128;
    final int height = 128;
    final int offsetX = 0;
    final int offsetY = 0;
    SpriteAnimation animation;

    public Explosion(double x, double y) {
        exp.setTranslateX(x - (double) width / 2);
        exp.setTranslateY(y - (double) height / 2);
        animation = new SpriteAnimation(exp, Duration.seconds(2), rows, columns, offsetX, offsetY, width, height);
    }
}
