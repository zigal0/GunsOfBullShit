package project;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import static project.Main.resolutionHeight;
import static project.Main.resolutionWidth;


public class Confirm extends StackPane {

    final int edge = 12;

    public Confirm() {

        int width = 300;
        int height = 150;

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

        Text text = new Text("Are you sure?");
        text.setFont(MyFont.font4Item);
        text.setFill(Colors.MENU_TEXT);
        text.setStroke(Color.BLACK);
        setMargin(text, new Insets(0, 0, 60, 0));

        HBox hBox = new HBox(70);
        hBox.setAlignment(Pos.CENTER);
        MenuItem yes = new MenuItem("YES", 100);
        MenuItem no = new MenuItem("NO", 100);
        yes.setOnAction(() -> System.exit(0));
        no.setOnAction(() -> {
            getChildren().remove(back);
            getChildren().remove(vBox);
//           getChildren().clear();
            toBack();
        });
        hBox.getChildren().addAll(yes, no);
        hBox.setPrefSize(width, height);
        setMargin(hBox, new Insets(60, 0, 0, 0));

        panel.getChildren().addAll(bg, text, hBox);

        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(lineTop, panel, lineBot);

        getChildren().addAll(back, vBox);
    }

}
