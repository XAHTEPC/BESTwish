package com.example.bestwish.View;

import com.example.bestwish.Front;
import com.example.bestwish.Model.Client;
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
import java.util.Set;

public class FriendView {
    static Button friend;
    static Button wish;
    static Button exit;
    static Button addFriend;
    static Button entry;

    public static ScrollPane scrollPane;
    static TextField myName;
    public static Pane getFront() throws FileNotFoundException, SQLException {
        Pane pane = new Pane();
        Set<String> setMyFriendID = null;
        pane.setLayoutX(0);
        pane.setLayoutY(0);
        FileInputStream Url;
        Url = new FileInputStream("png/friend.png");
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
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
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
        setMyFriendID= Client.getMyFriends();
        scrollPane.setLayoutX(54);
        scrollPane.setLayoutY(393);
        scrollPane.setMaxHeight(300);
        scrollPane.setMaxWidth(1028);
        scrollPane.setMinHeight(300);
        scrollPane.setMinWidth(1028);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setFitToWidth(true);
        pane.getChildren().add(scrollPane);


        addFriend = new Button();
        addFriend.setBackground(null);
        addFriend.setLayoutX(866);
        addFriend.setLayoutY(714);
        addFriend.setPrefSize(180,36);
        Set<String> finalSetMyFriendID = setMyFriendID;
        addFriend.setOnAction(t->{
            Front.root.getChildren().remove(Front.pane);
            try {
                Front.pane = FriendAddView.getFront(finalSetMyFriendID);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            Front.root.getChildren().add(Front.pane);
        });
        pane.getChildren().add(addFriend);

        entry = new Button();
        entry.setBackground(null);
        entry.setLayoutX(888);
        entry.setLayoutY(201);
        entry.setPrefSize(194,36);
        entry.setOnAction(t->{
            Front.root.getChildren().remove(Front.pane);
            try {
                Front.pane = EntriesView.getFront();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            Front.root.getChildren().add(Front.pane);
        });
        pane.getChildren().add(entry);



        return pane;
    }
}
