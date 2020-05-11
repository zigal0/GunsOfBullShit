package project;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import static project.Main.resolutionHeight;
import static project.Main.resolutionWidth;


public class NotAdded extends StackPane {

    final int edge = 12;
    final int width = 300;
    final int height = 150;

    public NotAdded() {

        VBox vBox = new VBox();

        Rectangle back = new Rectangle(resolutionWidth + edge, resolutionHeight + edge);
        back.setFill(Color.BLACK);
        back.setOpacity(0.4);

        Rectangle bg = new Rectangle(width, height);
        bg.setFill(Colors.MENU_BG);

        Rectangle lineTop = new Rectangle(width, 2);
        lineTop.setFill(Colors.MENU_BORDER);
        lineTop.setStroke(Color.BLACK);

        Rectangle lineBot = new Rectangle(width, 2);
        lineBot.setFill(Colors.MENU_BORDER);
        lineBot.setStroke(Color.BLACK);

        StackPane panel = new StackPane();
        panel.setPrefSize(width, height);

        Text text = new Text("Ha-ha, Lox, net takoi fuction");
        text.setFont(MyFont.font4Item);
        text.setFill(Colors.MENU_TEXT);
        text.setStroke(Color.BLACK);
        setMargin(text, new Insets(0, 0, 60, 0));

        MenuItem ok = new MenuItem("OK", 100);
        ok.setOnAction(() -> {
            getChildren().remove(back);
            getChildren().remove(vBox);
            toBack();
        });

        setMargin(ok, new Insets(60, 0, 0, 580));

        panel.getChildren().addAll(bg, text, ok);

        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(lineTop, panel, lineBot);

        getChildren().addAll(back, vBox);
    }
}