package com.example.bestwish.View;

import com.example.bestwish.Data.Postgre;
import com.example.bestwish.Front;
import com.example.bestwish.Model.Wish;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;

public class WishListEditView {
    static Button save;
    static Button back;
    static TextField title;
    public static ScrollPane scrollPane;
    static Button addWish;

    public static Pane getFront(String id, String name) throws FileNotFoundException, SQLException {
        Wish.start();

        Pane pane = new Pane();
        pane.setLayoutX(0);
        pane.setLayoutY(0);
        FileInputStream Url;
        Url = new FileInputStream("png/wishEdit.png");
        Image url = new Image(Url);
        ImageView front = new ImageView(url);
        front.setX(0);
        front.setY(0);
        pane.getChildren().add(front);

        title = new TextField();
        title.setText(name);
        title.setLayoutX(420);
        title.setLayoutY(188);
        title.setMaxWidth(415);
        title.setMinWidth(415);
        title.setFont(Font.font("STXihei", 20));
        title.setBackground(null);
        pane.getChildren().add(title);

        addWish = new Button();
        addWish.setBackground(null);
        addWish.setLayoutX(223);
        addWish.setLayoutY(259);
        addWish.setPrefSize(323,42);
        pane.getChildren().add(addWish);
        addWish.setOnAction(t->{
            try {
                Wish.addWish(1);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        save = new Button();
        save.setBackground(null);
        save.setLayoutX(473);
        save.setLayoutY(672);
        save.setPrefSize(157,47);
        save.setOnAction(t->{
            try {
                Wish.updateMyWishes(id,title.getText());
                Front.root.getChildren().remove(Front.pane);
                Front.pane = WishListView.getFront();
                Front.root.getChildren().add(Front.pane);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        pane.getChildren().add(save);

        back = new Button();
        back.setBackground(null);
        back.setLayoutX(699);
        back.setLayoutY(685);
        back.setPrefSize(65,36);
        back.setOnAction(t->{
            Front.root.getChildren().remove(Front.pane);
            try {
                Front.pane = WishListView.getFront();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            Front.root.getChildren().add(Front.pane);
        });
        pane.getChildren().add(back);

        scrollPane = new ScrollPane();
        Wish.getMyWishes(id);
        scrollPane.setLayoutX(123);
        scrollPane.setLayoutY(323);
        scrollPane.setMaxHeight(320);
        scrollPane.setMaxWidth(934);
        scrollPane.setMinHeight(320);
        scrollPane.setMinWidth(934);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setFitToWidth(true);
        pane.getChildren().add(scrollPane);

        return pane;
    }
}
