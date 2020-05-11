package project;

import javafx.animation.FillTransition;
import javafx.geometry.Pos;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class MenuItem extends StackPane {

    private final Text text;
    private final Rectangle selection;
    private final DropShadow shadow;

    public MenuItem(String name, int width) {
        setAlignment(Pos.CENTER_LEFT);

        text = new Text(name);
        text.setTranslateX(5);
        text.setFont(MyFont.font4Item);
        text.setFill(Colors.MENU_TEXT);
        text.setStroke(Color.BLACK);

        shadow = new DropShadow(5, Color.BLACK);
        text.setEffect(shadow);

        selection = new Rectangle(width - 45, 30);
        selection.setFill(Colors.MENU_ITEM_SELECTION);
        selection.setStroke(Color.BLACK);
        selection.setVisible(false);

        GaussianBlur blur = new GaussianBlur(8);
        selection.setEffect(blur);

        getChildren().addAll(selection, text);

        setOnMouseEntered(e -> onSelect());

        setOnMouseExited(e -> onDeselect());

        setOnMousePressed(e -> text.setFill(Color.GREEN));
    }

    private void onSelect() {
        text.setFill(Color.YELLOW);
        selection.setVisible(true);

        shadow.setRadius(1);
    }

    private void onDeselect() {
        text.setFill(Colors.MENU_TEXT);
        selection.setVisible(false);

        shadow.setRadius(5);
    }

    public void setOnAction(Runnable action) {
        setOnMouseClicked(e -> {
            FillTransition ft = new FillTransition(Duration.seconds(0.45), selection,
                    Color.YELLOW, Colors.MENU_ITEM_SELECTION);
            ft.setOnFinished(e2 -> action.run());
            ft.play();
        });
    }
}
