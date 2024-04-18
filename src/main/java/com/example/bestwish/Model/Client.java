package com.example.bestwish.Model;

import com.example.bestwish.Data.Postgre;
import com.example.bestwish.Front;
import com.example.bestwish.View.EntriesView;
import com.example.bestwish.View.FriendAddView;
import com.example.bestwish.View.FriendView;
import com.example.bestwish.View.FriendWishListView;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Client {
    String id;
    String login;
    String birthday;
    String name;
    String dateLeft;
    String idWishList;

    public Client(String id, String login, String birthday, String name) {
        this.id = id;
        this.login = login;
        this.birthday = birthday;
        this.name = name;
    }
    public Client(String id, String name) {
        this.id = id;
        this.name = name;
    }
    public Client(String id, String birthday, String name, String dateLeft, String idWishList) {
        this.id = id;
        this.birthday = birthday;
        this.name = name;
        this.dateLeft = dateLeft;
        this.idWishList = idWishList;
    }
    public static void findFriend(String findEl, Set<String> setIdMyFriend) throws SQLException, FileNotFoundException {
        ArrayList<Client> friendMas = Postgre.findFriend(findEl);
        Set<String> setIdToFriend = Postgre.getMyEntriesToFriend();
        Pane pane = new Pane();
        if(friendMas.size()==0){
            TextField message = new TextField();
            message.setLayoutX(30);
            message.setLayoutY(30);
            message.setMaxWidth(410);
            message.setMinWidth(410);
            message.setText("Не найдено такого человека....");
            message.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
            message.setEditable(false);
            message.setFont(Font.font("Tahoma", FontWeight.BOLD, 18));
            pane.getChildren().add(message);
        }
        else {
            for (int i = 0, y = 30; i < friendMas.size(); i++, y += 50) {
                if(setIdMyFriend != null && setIdMyFriend.contains(friendMas.get(i).id)){
                    continue;
                }
                TextField name = new TextField();
                name.setLayoutX(30);
                name.setLayoutY(y);
                name.setMaxWidth(410);
                name.setMinWidth(410);
                name.setText(friendMas.get(i).name);
                name.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
                name.setEditable(false);
                name.setFont(Font.font("Tahoma", FontWeight.BOLD, 18));
                pane.getChildren().add(name);

                TextField date = new TextField();
                date.setText(friendMas.get(i).birthday);
                date.setMaxWidth(130);
                date.setBackground(null);
                date.setEditable(false);
                date.setLayoutY(y);
                date.setLayoutX(460);
                date.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
                date.setFont(Font.font("Tahoma", FontWeight.BOLD, 18));
                pane.getChildren().add(date);

                FileInputStream Url = new FileInputStream("png/addFriend.png");
                Image url = new Image(Url);
                ImageView addFriend = new ImageView(url);

                Button add = new Button();
                add.setBackground(null);
                add.setGraphic(addFriend);
                add.setLayoutY(y);
                add.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
                add.setLayoutX(633);
                pane.getChildren().add(add);
                final String id = friendMas.get(i).id;
                add.setOnAction(t->{

                    try {
                        Postgre.addFriend(id);
                        findFriend(findEl,setIdMyFriend);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                });

                TextField application = new TextField("Заявка отправлена");
                application.setLayoutX(701);
                application.setBackground(null);
                application.setLayoutY(y);
                application.setMinWidth(230);
                application.setMaxWidth(230);
                application.setVisible(false);
                application.setEditable(false);
                application.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
                application.setStyle("-fx-text-fill: green; -fx-background: transparent; -fx-background-color: transparent;");
                pane.getChildren().add(application);
                if(setIdToFriend!=null && setIdToFriend.contains(friendMas.get(i).id)){
                    application.setVisible(true);
                    add.setVisible(false);
                }
            }
        }
        FriendAddView.scrollPane.setContent(pane);
    }
    public static Set<String> getMyFriends() throws SQLException, FileNotFoundException {
        ArrayList<Client> friendMas = Postgre.getMyFriends();
        Set<String> setId = new HashSet<String>();
        Pane pane = new Pane();
        if(friendMas.size()==0){
            TextField message = new TextField();
            message.setLayoutX(30);
            message.setLayoutY(30);
            message.setMaxWidth(410);
            message.setMinWidth(410);
            message.setText("Пока что друзей нет... Надо добавить!");
            message.setEditable(false);
            message.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
            message.setFont(Font.font("Tahoma", FontWeight.BOLD, 18));
            pane.getChildren().add(message);
        }
        else {
            Set<String> tmpSet = new HashSet<>();
            for (int i = 0, y = 0; i < friendMas.size(); i++, y += 50) {
                if(tmpSet!=null && tmpSet.contains(friendMas.get(i).id)){
                    y-=50;
                    continue;
                }
                tmpSet.add(friendMas.get(i).id);
                setId.add(friendMas.get(i).id);
                TextField name = new TextField();
                name.setLayoutX(30);
                name.setLayoutY(y);
                name.setMaxWidth(300);
                name.setMinWidth(300);
                name.setText(friendMas.get(i).name);
                //            name.setBackground(null);
                name.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
                name.setEditable(false);
                name.setFont(Font.font("Tahoma", FontWeight.BOLD, 18));
                pane.getChildren().add(name);

                TextField dateLeft = new TextField();
                dateLeft.setText(friendMas.get(i).dateLeft);
                dateLeft.setMaxWidth(130);
                dateLeft.setEditable(false);
                dateLeft.setLayoutY(y);
                dateLeft.setLayoutX(350);
                dateLeft.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
                dateLeft.setFont(Font.font("Tahoma", FontWeight.BOLD, 18));
                pane.getChildren().add(dateLeft);

                TextField date = new TextField();
                date.setText(friendMas.get(i).birthday);
                date.setMaxWidth(130);
                date.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
                date.setEditable(false);
                date.setLayoutY(y);
                date.setLayoutX(540);
                date.setFont(Font.font("Tahoma", FontWeight.BOLD, 18));
                pane.getChildren().add(date);

                FileInputStream Urlwish = new FileInputStream("png/wishList.png");
                Image urlwish = new Image(Urlwish);
                ImageView wishList = new ImageView(urlwish);

                Button wish = new Button();
                wish.setGraphic(wishList);
                wish.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
                wish.setLayoutY(y-10);
                wish.setLayoutX(700);
                pane.getChildren().add(wish);

                final String id = friendMas.get(i).id;
                wish.setOnAction(t->{
                        Front.root.getChildren().remove(Front.pane);
                    try {
                        Front.pane = FriendWishListView.getFront(id);
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    Front.root.getChildren().add(Front.pane);
                });

                Urlwish = new FileInputStream("png/delFriend.png");
                urlwish = new Image(Urlwish);
                ImageView delFriend = new ImageView(urlwish);

                Button del = new Button();
                del.setGraphic(delFriend);
                del.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
                del.setBackground(null);
                del.setLayoutY(y);
                del.setLayoutX(875);
                pane.getChildren().add(del);
                del.setOnAction(t->{
                    try {
                        System.out.println("id delete:"+id);
                        Postgre.deleteFriend(id);
                        getMyFriends();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                });

            }

        }
        FriendView.scrollPane.setContent(pane);
        return setId;
    }

    public static void getMyEntries() throws SQLException, FileNotFoundException {
        ArrayList<Client> friends = new ArrayList<>();
        friends = Postgre.getEntries();
        Pane pane = new Pane();
        if(friends.size()==0){
            TextField message = new TextField();
            message.setLayoutX(30);
            message.setLayoutY(30);
            message.setMaxWidth(410);
            message.setMinWidth(410);
            message.setText("Новых заявок нет!");
            message.setEditable(false);
            message.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
            message.setFont(Font.font("Tahoma", FontWeight.BOLD, 18));
            pane.getChildren().add(message);
        }
        else {
            for (int i = 0,y = 0; i < friends.size(); i++, y += 70) {
                FileInputStream Url = new FileInputStream("png/entryEl.png");
                Image url = new Image(Url);
                ImageView addWishEl = new ImageView(url);
                addWishEl.setLayoutX(0);
                addWishEl.setLayoutY(y);
                pane.getChildren().add(addWishEl);

                TextField name = new TextField();
                name.setLayoutX(60);
                name.setLayoutY(y+20);
                name.setMaxWidth(500);
                name.setMinWidth(500);
                name.setText(friends.get(i).name);
                name.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
                name.setEditable(false);
                name.setFont(Font.font("Tahoma", FontWeight.BOLD, 18));
                pane.getChildren().add(name);

                final String id = friends.get(i).id;
                Button add = new Button();
                add.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
                add.setLayoutY(y+23);
                add.setLayoutX(650);
                add.setPrefSize(50,50);
                pane.getChildren().add(add);
                add.setOnAction(t->{
                    try {
                        System.out.println("id add:"+id);
                        Postgre.updateFriendStatus(id);
                        Front.root.getChildren().remove(Front.pane);
                        Front.pane = FriendView.getFront();
                        Front.root.getChildren().add(Front.pane);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                });

                Button del = new Button();
                del.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
                del.setBackground(null);
                del.setLayoutY(y+23);
                del.setLayoutX(795);
                del.setPrefSize(50,50);
                pane.getChildren().add(del);

                del.setOnAction(t->{
                    try {
                        System.out.println("id delete:"+id);
                        Postgre.deleteFriend(id);
                        Front.root.getChildren().remove(Front.pane);
                        Front.pane = FriendView.getFront();
                        Front.root.getChildren().add(Front.pane);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                });

            }
            EntriesView.scrollPane.setContent(pane);
        }
    }
}
