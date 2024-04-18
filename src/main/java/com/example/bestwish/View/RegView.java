package com.example.bestwish.View;

import com.example.bestwish.Data.Postgre;
import com.example.bestwish.Data.Toast;
import com.example.bestwish.Front;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javax.swing.text.MaskFormatter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.text.ParseException;

public class RegView {
    static Button back;
    static Button registration;
    public static PasswordField first_password;
    public static PasswordField sec_password;
    public static TextField login;
    public static TextField birthday;
    public static TextField name;
    public static Pane getFront() throws FileNotFoundException {
        Pane pane = new Pane();
        pane.setLayoutX(0);
        pane.setLayoutY(0);
        FileInputStream Url;
        Url = new FileInputStream("png/registration.png");
        Image url = new Image(Url);
        ImageView front = new ImageView(url);
        front.setX(0);
        front.setY(0);
        pane.getChildren().add(front);

        name = new TextField();
        name.setPrefSize(349,40);
        name.setLayoutX(120);
        name.setLayoutY(315);
        name.setFont(Font.font("STXihei", 20));
        name.setBackground(null);
        pane.getChildren().add(name);

        birthday = new TextField();

        birthday.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*-?\\d*-?\\d*")) {
                    birthday.setText(newValue.replaceAll("[^\\d-]", ""));
                } else if (newValue.length() == 2 || newValue.length() == 5) {
                    birthday.setText(newValue + "-");
                    birthday.positionCaret(birthday.getText().length());
                }
            }
        });

        birthday.setPrefSize(349,40);
        birthday.setLayoutX(120);
        birthday.setLayoutY(391);
        birthday.setFont(Font.font("STXihei", 20));
        birthday.setBackground(null);
        pane.getChildren().add(birthday);


        login = new TextField();
        login.setPrefSize(349,40);
        login.setLayoutX(120);
        login.setLayoutY(474);
        login.setFont(Font.font("STXihei", 20));
        login.setBackground(null);
        pane.getChildren().add(login);

        first_password = new PasswordField();
        first_password.setPrefSize(349,40);
        first_password.setBackground(null);
        first_password.setLayoutX(120);
        first_password.setLayoutY(550);
        first_password.setFont(Font.font("STXihei", 20));
        pane.getChildren().add(first_password);

        sec_password = new PasswordField();
        sec_password.setPrefSize(349,40);
        sec_password.setBackground(null);
        sec_password.setLayoutX(120);
        sec_password.setLayoutY(627);
        sec_password.setFont(Font.font("STXihei", 20));
        pane.getChildren().add(sec_password);

        back = new Button();
        back.setBackground(null);
        back.setLayoutX(254);
        back.setLayoutY(723);
        back.setPrefSize(65,36);
        back.setOnAction(t->{
            Front.root.getChildren().remove(Front.pane);
            try {
                Front.pane = LoginView.getFront();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            Front.root.getChildren().add(Front.pane);
        });
        pane.getChildren().add(back);

        registration = new Button();
        registration.setBackground(null);
        registration.setLayoutX(217);
        registration.setLayoutY(680);
        registration.setPrefSize(137,34);
        registration.setOnAction(t->{
            String pass1 = first_password.getText();
            String pass2 = sec_password.getText();
            String log = login.getText();
            if(pass2.equals(pass1) && pass2.length()!=0){
                try {
                    if(Postgre.checkLogin(log)){
                        if(name.getText().length()!=0){
                            Postgre.addClient(log,pass1,name.getText(),birthday.getText());
                            Front.root.getChildren().remove(Front.pane);
                            Front.pane = MainView.getFront();
                            Front.root.getChildren().add(Front.pane);
                        }
                        else {
                            Toast.show("Введите ФИО",pane,232,1000);
                        }
                    }
                    else{
                        Toast.show("Логин уже существует",pane,232,1000);
                        login.setText("");
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
            else{
                Toast.show("Пароли не совпадают",pane,232,1000);
                first_password.setText("");
                sec_password.setText("");
            }
        });
        pane.getChildren().add(registration);

        return pane;
    }
}
