package project;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Obstacle extends ObjectOnMap {
    enum TypeOfObstacle {container1, container2, greenCrate}

    final TypeOfObstacle typeoFObstacle;
    final ImageView obstacle = new ImageView();

    public Obstacle(double xn, double yn, TypeOfObstacle typen, double anglen) {
        super(xn, yn, 64, 64, 1, 0, Type.obstacle, anglen, 0);
        switch (typen) {
            case container1:
                width = 128;
                break;
            case container2:
                height = 128;
                break;
        }

        typeoFObstacle = typen;
        switch (typeoFObstacle) {
            case container1:
                obstacle.setImage(new Image(getClass()
                        .getResource("pics/container1.bmp").toExternalForm()));
                break;
            case container2:
                obstacle.setImage(new Image(getClass()
                        .getResource("pics/container2.bmp").toExternalForm()));
                break;
            case greenCrate:
                obstacle.setImage(new Image(getClass()
                        .getResource("pics/greenCrate.bmp").toExternalForm()));
                break;
        }
        this.getChildren().add(obstacle);
    }
}
