package com.example.bestwish.View;

import com.example.bestwish.Data.Postgre;
import com.example.bestwish.Data.Toast;
import com.example.bestwish.Front;
import com.example.bestwish.Model.Client;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;

public class MainView {
    public static String id;
    public static String name;
    static Button friend;
    static Button wish;
    static Button exit;
    static TextField myName;
    public static Pane getFront() throws FileNotFoundException {
        Pane pane = new Pane();
        pane.setLayoutX(0);
        pane.setLayoutY(0);
        FileInputStream Url;
        Url = new FileInputStream("png/main.png");
        Image url = new Image(Url);
        ImageView front = new ImageView(url);
        front.setX(0);
        front.setY(0);
        pane.getChildren().add(front);

        myName = new TextField(getName());
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
        return pane;
    }

    public static String getName(){
        char[] mas = name.toCharArray();
        String newName ="";
        int kol = 0;
        for(int i=0;i<name.length();i++){
            if(kol==1){
                if(mas[i]!=' ') {
                    newName += mas[i];
                }
                else {
                    break;
                }
            }
            if(mas[i]==' '){
                kol++;
            }
        }
        return newName;
    }
}
