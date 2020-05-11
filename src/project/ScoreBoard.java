package project;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.Font;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import static project.Main.resolutionHeight;
import static project.Main.resolutionWidth;

public class ScoreBoard {

    final int width = 400;
    final int height = 470;
    final int limit = 10;
    final int edge = 12;
    final Path path = Paths.get("D:/Java/GunsOfBullShit/out/production/GunsOfBullShit/project/scoreBoard.txt");

    public void writeResult(int points, String nickName) {
        TreeMap<Integer, ArrayList<String>> board = readResult();
        writeIntoMap(board, points, nickName);
        StringBuilder results = new StringBuilder();
        int i = 0;
        for (int key : board.keySet()) {
            ArrayList<String> listOfPlayer = board.get(key);
            for (String note : listOfPlayer) {
                if (i < limit) {
                    results.append(key).append(' ').append(note).append('\n');
                    i++;
                }

            }
        }

        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
            writer.write(results.toString());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public TreeMap<Integer, ArrayList<String>> readResult() {
        TreeMap<Integer, ArrayList<String>> board = new TreeMap<>((o1, o2) -> o2 - o1);
        try {
            List<String> list = Files.readAllLines(path);
            list.forEach(oneScore -> {
                String[] split = oneScore.split(" ");
                writeIntoMap(board, Integer.parseInt(split[0]), split[1]);
            });
        } catch (IOException e) {
            // exception handling
        }
        return board;
    }

    private void writeIntoMap(TreeMap<Integer, ArrayList<String>> board, int gpoints, String nick) {
        if (board.containsKey(gpoints)) {
            ArrayList<String> listOfPlayer;
            listOfPlayer = board.get(gpoints);
            listOfPlayer.add(nick);
            board.put(gpoints, listOfPlayer);
        } else {
            ArrayList<String> listOfPlayer = new ArrayList<>();
            listOfPlayer.add(nick);
            board.put(gpoints, listOfPlayer);
        }
    }

    public StackPane show() {
        StackPane gfx = new StackPane();

        Rectangle back = new Rectangle(resolutionWidth + edge, resolutionHeight + edge);
        back.setFill(Color.BLACK);
        back.setOpacity(0.6);

        Rectangle bg = new Rectangle(width, height);
        bg.setFill(Colors.MENU_BG);

        Rectangle lineTop = new Rectangle(width, 2);
        lineTop.setFill(Colors.MENU_BORDER);
        lineTop.setStroke(Color.BLACK);

        Rectangle lineBot = new Rectangle(width, 2);
        lineBot.setFill(Colors.MENU_BORDER);
        lineBot.setStroke(Color.BLACK);

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(lineTop, bg, lineBot);

        GridPane panel = new GridPane();
        panel.setAlignment(Pos.CENTER);
        panel.setPrefSize(width, height);
        TreeMap<Integer, ArrayList<String>> board = readResult();
        int i = 0;
        for (int key : board.keySet()) {
            ArrayList<String> listOfPlayer = board.get(key);
            for (String note : listOfPlayer) {
                String noteChange = note;
                if (note.length() > 10) {
                    noteChange = note.substring(0, 9);
                    noteChange += "...";
                }
                Text text1 = new Text(noteChange);
                text1.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.ITALIC, 26));
                text1.setFill(Colors.MENU_TEXT);
                text1.setStroke(Color.BLACK);
                Text text2 = new Text(String.valueOf(key));
                text2.setFont(MyFont.font4Item);
                text2.setFill(Colors.MENU_TEXT);
                text2.setStroke(Color.BLACK);
                GridPane.setHalignment(text1, HPos.CENTER);
                GridPane.setHalignment(text2, HPos.CENTER);
                panel.add(text1, 0, i);
                panel.add(text2, 1, i);
                i++;
            }
        }
        ColumnConstraints columnConstraints = new ColumnConstraints(150);
        columnConstraints.setPrefWidth(150);
        panel.getColumnConstraints().add(columnConstraints);
        panel.getColumnConstraints().add(columnConstraints);
        panel.setVgap(5);

        MenuItem ok = new MenuItem("OK", 100);
        ok.setOnAction(() -> {
            gfx.getChildren().remove(back);
            gfx.getChildren().remove(vBox);
            gfx.toBack();
        });
        VBox vBox2 = new VBox();
        vBox2.setAlignment(Pos.CENTER);
        vBox2.getChildren().addAll(panel, ok);
        VBox.setMargin(panel, new Insets(-30, 0, 0, 0));
        VBox.setMargin(ok, new Insets(-30, 0, 0, 580));
        gfx.setAlignment(Pos.CENTER);
        gfx.getChildren().addAll(back, vBox, vBox2);
        return gfx;
    }
}
