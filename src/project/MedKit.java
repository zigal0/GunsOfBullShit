package project;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MedKit extends ObjectOnMap {

    final ImageView medkit = new ImageView();

    public MedKit(double xn, double yn) {
        super(xn, yn, 20, 18, 1, 0, Type.medkit, 0, 0);
        medkit.setImage(new Image(getClass()
                .getResource("pics/medkit.png").toExternalForm()));
        this.getChildren().add(medkit);
    }
}
