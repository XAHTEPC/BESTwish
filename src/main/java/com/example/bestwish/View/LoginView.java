package com.example.bestwish.View;

import com.example.bestwish.Data.Postgre;
import com.example.bestwish.Data.Toast;
import com.example.bestwish.Front;
import com.example.bestwish.Main;
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

public class LoginView {
    static Button enter;
    static Button registration;
    public static PasswordField password;
    public static TextField login;
    public static Pane getFront() throws FileNotFoundException {
        Pane pane = new Pane();
        pane.setLayoutX(0);
        pane.setLayoutY(0);
        FileInputStream Url;
        Url = new FileInputStream("png/login.png");
        Image url = new Image(Url);
        ImageView front = new ImageView(url);
        front.setX(0);
        front.setY(0);
        pane.getChildren().add(front);

        login = new TextField();
        login.setMinWidth(349);
        login.setMaxWidth(349);
        login.setMinHeight(40);
        login.setMaxHeight(40);
        login.setLayoutX(120);
        login.setLayoutY(380);
        login.setFont(Font.font("STXihei", 20));
        login.setBackground(null);
        pane.getChildren().add(login);

        password = new PasswordField();
        password.setPrefSize(349,40);
        password.setBackground(null);
        password.setLayoutX(120);
        password.setLayoutY(475);
        password.setFont(Font.font("STXihei", 20));
        pane.getChildren().add(password);

        enter = new Button();
        enter.setBackground(null);
        enter.setLayoutX(256);
        enter.setLayoutY(531);
        enter.setPrefSize(89,34);
        pane.getChildren().add(enter);
        enter.setOnAction(t->{
            try {
                String id = Postgre.getID(login.getText(),password.getText());
                if(!id.equals("error")){
                    Front.root.getChildren().remove(Front.pane);
                    Front.pane = MainView.getFront();
                    Front.root.getChildren().add(Front.pane);
                }
                else{
                    Toast.show("Неверный логин или пароль",pane,232,1000);
                }
            } catch (SQLException e) {
//                System.out.println("ERROR");
                password.setText("");
                Toast.show("Неверный логин или пароль",pane,232,1000);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        registration = new Button();
        registration.setBackground(null);
        registration.setLayoutX(207);
        registration.setLayoutY(571);
        registration.setPrefSize(220,36);
        registration.setOnAction(t->{
            Front.root.getChildren().remove(Front.pane);
            try {
                Front.pane = RegView.getFront();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            Front.root.getChildren().add(Front.pane);
        });
        pane.getChildren().add(registration);

        return pane;
    }
}
