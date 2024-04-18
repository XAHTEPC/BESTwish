package com.example.bestwish.View;

import com.example.bestwish.Data.Postgre;
import com.example.bestwish.Data.Toast;
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

public class FriendWishListView {
    static Button save;
    static Button back;
    static TextField title;
    static TextField owner;

    public static ScrollPane scrollPane;

    public static Pane getFront(String id) throws FileNotFoundException, SQLException {
        Pane pane = new Pane();
        pane.setLayoutX(0);
        pane.setLayoutY(0);
        FileInputStream Url;
        Url = new FileInputStream("png/friendWishList.png");
        Image url = new Image(Url);
        ImageView front = new ImageView(url);
        front.setX(0);
        front.setY(0);
        pane.getChildren().add(front);


        title = new TextField();
        title.setLayoutX(420);
        title.setLayoutY(188);
        title.setMaxWidth(415);
        title.setMinWidth(415);
        title.setEditable(false);
        title.setFont(Font.font("STXihei", 20));
        title.setBackground(null);
        pane.getChildren().add(title);

        owner = new TextField();
        owner.setLayoutX(420);
        owner.setLayoutY(253);
        owner.setMaxWidth(415);
        owner.setEditable(false);
        owner.setMinWidth(415);
        owner.setFont(Font.font("STXihei", 20));
        owner.setBackground(null);
        pane.getChildren().add(owner);


        save = new Button();
        save.setBackground(null);
        save.setLayoutX(979);
        save.setLayoutY(277);
        save.setPrefSize(110,30);
        save.setOnAction(t->{

        });
        pane.getChildren().add(save);

        back = new Button();
        back.setBackground(null);
        back.setLayoutX(1026);
        back.setLayoutY(105);
        back.setPrefSize(70,36);
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
        scrollPane.setLayoutX(110);
        scrollPane.setLayoutY(350);
        scrollPane.setMaxHeight(347);
        scrollPane.setMaxWidth(979);
        scrollPane.setMinHeight(347);
        scrollPane.setMinWidth(979);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setFitToWidth(true);
        pane.getChildren().add(scrollPane);

        Wish.start();
        WishList wishList = Postgre.getActualFriendWishList(id);
        if(wishList != null){
            title.setText(wishList.name);
            owner.setText(wishList.owner);
            Wish.friendWishes(id);
        }
        else {
            TextField error = new TextField("У пользователя нет активного ВишЛиста");
            error.setLayoutX(10);
            error.setLayoutY(10);
            error.setEditable(false);
            error.setMinWidth(500);
            error.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
            error.setFont(Font.font("STXihei", 20));
            error.setBackground(null);
            Pane pane1 = new Pane();
            pane1.getChildren().add(error);
            scrollPane.setContent(pane1);
        }



        return pane;
    }
}
