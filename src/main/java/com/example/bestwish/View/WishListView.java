package com.example.bestwish.View;

import com.example.bestwish.Front;
import com.example.bestwish.Model.Client;
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

public class WishListView {
    static Button friend;
    static Button wish;
    static Button exit;
    static TextField myName;
    public static ScrollPane scrollPane;
    static Button find;
    static TextField findFriend;
    static Button addWishList;
    public static Pane getFront() throws FileNotFoundException, SQLException {
        Pane pane = new Pane();
        pane.setLayoutX(0);
        pane.setLayoutY(0);
        FileInputStream Url;

        Url = new FileInputStream("png/wish.png");
        Image url = new Image(Url);
        ImageView front = new ImageView(url);
        front.setX(0);
        front.setY(0);
        pane.getChildren().add(front);

        myName = new TextField(MainView.getName());
        myName.setLayoutX(34);
        myName.setLayoutY(130);
        myName.setFont(Font.font("STXihei", 20));
        myName.setBackground(null);
        myName.setEditable(false);
        pane.getChildren().add(myName);

        friend = new Button();
        friend.setBackground(null);
        friend.setLayoutX(216);
        friend.setLayoutY(71);
        friend.setPrefSize(254,80);
        pane.getChildren().add(friend);
        friend.setOnAction(t->{
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

        wish = new Button();
        wish.setBackground(null);
        wish.setLayoutX(537);
        wish.setLayoutY(71);
        wish.setPrefSize(301,80);
        wish.setOnAction(t->{
            Front.root.getChildren().remove(Front.pane);
            try {
                Front.pane = WishListView.getFront();
            } catch (FileNotFoundException | SQLException e) {
                throw new RuntimeException(e);
            }
            Front.root.getChildren().add(Front.pane);
        });
        pane.getChildren().add(wish);

        exit = new Button();
        exit.setBackground(null);
        exit.setLayoutX(905);
        exit.setLayoutY(115);
        exit.setPrefSize(70,36);
        exit.setOnAction(t->{
            Front.root.getChildren().remove(Front.pane);
            try {
                Front.pane = LoginView.getFront();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            Front.root.getChildren().add(Front.pane);
        });
        pane.getChildren().add(exit);

        scrollPane = new ScrollPane();
        WishList.getMyWishLists();
        scrollPane.setLayoutX(54);
        scrollPane.setLayoutY(350);
        scrollPane.setMaxHeight(270);
        scrollPane.setMaxWidth(1028);
        scrollPane.setMinHeight(270);
        scrollPane.setMinWidth(1028);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setFitToWidth(true);
        pane.getChildren().add(scrollPane);

        find = new Button();
        find.setBackground(null);
        find.setLayoutX(221);
        find.setLayoutY(195);
        find.setPrefSize(30,30);
        find.setOnAction(t->{

        });
        pane.getChildren().add(find);

        findFriend = new TextField();
        findFriend.setLayoutX(250);
        findFriend.setLayoutY(190);
        findFriend.setPrefSize(580,40);
        findFriend.setPromptText("Логин или ФИО");
        findFriend.setFont(Font.font("STXihei", 20));
        findFriend.setBackground(null);
        pane.getChildren().add(findFriend);

        addWishList = new Button();
        addWishList.setBackground(null);
        addWishList.setLayoutX(857);
        addWishList.setLayoutY(715);
        addWishList.setPrefSize(170,36);
        addWishList.setOnAction(t->{
            Front.root.getChildren().remove(Front.pane);
            try {
                Front.pane = WishListAddView.getFront();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            Front.root.getChildren().add(Front.pane);
        });
        pane.getChildren().add(addWishList);

        return pane;
    }
}
