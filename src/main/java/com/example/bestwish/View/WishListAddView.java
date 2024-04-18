package com.example.bestwish.View;

import com.example.bestwish.Front;
import com.example.bestwish.Model.Wish;
import com.example.bestwish.Model.WishList;
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

public class WishListAddView {
    static Button save;
    static Button back;
    static TextField title;
    public static ScrollPane scrollPane;
    static Button addWish;
    public static Pane getFront() throws FileNotFoundException {
        Wish.start();
        Pane pane = new Pane();
        pane.setLayoutX(0);
        pane.setLayoutY(0);
        FileInputStream Url;
        Url = new FileInputStream("png/wishAdd.png");
        Image url = new Image(Url);
        ImageView front = new ImageView(url);
        front.setX(0);
        front.setY(0);
        pane.getChildren().add(front);

        title = new TextField();
        title.setLayoutX(313);
        title.setLayoutY(176);
        title.setFont(Font.font("STXihei", 20));
        title.setBackground(null);
        pane.getChildren().add(title);

        addWish = new Button();
        addWish.setBackground(null);
        addWish.setLayoutX(114);
        addWish.setLayoutY(248);
        addWish.setPrefSize(323,42);
        pane.getChildren().add(addWish);
        addWish.setOnAction(t->{
            try {
                Wish.addWish(0);
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
                Wish.save(title.getText());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
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
        scrollPane.setLayoutX(114);
        scrollPane.setLayoutY(312);
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
