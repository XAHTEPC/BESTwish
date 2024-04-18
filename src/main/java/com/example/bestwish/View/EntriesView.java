package com.example.bestwish.View;

import com.example.bestwish.Front;
import com.example.bestwish.Model.Client;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;

public class EntriesView {
    static Button back;
    public static ScrollPane scrollPane;

    public static Pane getFront() throws FileNotFoundException, SQLException {
        Pane pane = new Pane();
        pane.setLayoutX(0);
        pane.setLayoutY(0);
        FileInputStream Url;
        Url = new FileInputStream("png/entries.png");
        Image url = new Image(Url);
        ImageView front = new ImageView(url);
        front.setX(0);
        front.setY(0);
        pane.getChildren().add(front);


        back = new Button();
        back.setBackground(null);
        back.setLayoutX(992);
        back.setLayoutY(680);
        back.setPrefSize(65,34);
        back.setOnAction(t->{
            Front.root.getChildren().remove(Front.pane);
            try {
                Front.pane = FriendView.getFront();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            Front.root.getChildren().add(Front.pane);
        });
        pane.getChildren().add(back);

        scrollPane = new ScrollPane();
        Client.getMyEntries();
        scrollPane.setLayoutX(123);
        scrollPane.setLayoutY(270);
        scrollPane.setMaxHeight(390);
        scrollPane.setMaxWidth(934);
        scrollPane.setMinHeight(390);
        scrollPane.setMinWidth(934);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setFitToWidth(true);
        pane.getChildren().add(scrollPane);

        return pane;
    }
}
