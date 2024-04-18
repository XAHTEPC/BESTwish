package com.example.bestwish.View;

import com.example.bestwish.Data.Postgre;
import com.example.bestwish.Data.Toast;
import com.example.bestwish.Front;
import com.example.bestwish.Model.Client;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
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

public class FriendAddView {
    static Button friend;
    static Button wish;
    static Button exit;
    static Button find;
    static TextField findFriend;
    public static ScrollPane scrollPane;
    static TextField myName;
    public static Pane getFront(Set<String> setID) throws FileNotFoundException {
        Pane pane = new Pane();
        pane.setLayoutX(0);
        pane.setLayoutY(0);
        FileInputStream Url;
        Url = new FileInputStream("png/friendAdd.png");
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

        find = new Button();
        find.setBackground(null);
        find.setLayoutX(221);
        find.setLayoutY(195);
        find.setPrefSize(30,30);
        find.setOnAction(t->{
            try {
                Client.findFriend(findFriend.getText(), setID);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
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

        scrollPane = new ScrollPane();
        scrollPane.setLayoutX(87);
        scrollPane.setLayoutY(417);
        scrollPane.setMaxHeight(232);
        scrollPane.setMaxWidth(979);
        scrollPane.setMinHeight(232);
        scrollPane.setMinWidth(979);
        scrollPane.setStyle("-fx-background: rgb(213,211,221);-fx-background-color: rgb(213,211,221);");
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setFitToWidth(true);
        pane.getChildren().add(scrollPane);

        return pane;
    }
}
