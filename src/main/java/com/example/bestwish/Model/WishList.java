package com.example.bestwish.Model;

import com.example.bestwish.Data.Postgre;
import com.example.bestwish.Front;
import com.example.bestwish.View.FriendView;
import com.example.bestwish.View.WishListEditView;
import com.example.bestwish.View.WishListView;
import javafx.geometry.Pos;
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

public class WishList {
    public String id;
    public String name;
    public String actual;
    public String owner;

    public WishList(String id, String name, String actual) {
        this.id = id;
        this.name = name;
        this.actual = actual;
    }
    public WishList(String name, String owner) {
        this.name = name;
        this.owner = owner;
    }


    public static void getMyWishLists() throws SQLException, FileNotFoundException {
        ArrayList<WishList> wishLists = Postgre.getMyWishLists();
        Pane pane = new Pane();
        if(wishLists.size()==0){
            TextField message = new TextField();
            message.setLayoutX(30);
            message.setLayoutY(30);
            message.setMaxWidth(410);
            message.setMinWidth(410);
            message.setText("Пока что желаний нет... Надо добавить!");
//            name.setBackground(null);
            message.setEditable(false);
            message.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
            message.setFont(Font.font("Tahoma", FontWeight.BOLD, 18));
            pane.getChildren().add(message);
        }
        else {
            for (int i = 0, y = 0; i < wishLists.size(); i++, y += 60) {
                FileInputStream Url = new FileInputStream("png/wishListEl.png");
                Image url = new Image(Url);
                ImageView addWishListEl = new ImageView(url);
                addWishListEl.setLayoutX(0);
                addWishListEl.setLayoutY(y);
                pane.getChildren().add(addWishListEl);

                TextField name = new TextField();
                name.setLayoutX(69);
                name.setLayoutY(y+20);
                name.setMaxWidth(270);
                name.setMinWidth(270);
                name.setText(wishLists.get(i).name);
                name.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
                name.setEditable(false);
                name.setFont(Font.font("Tahoma", FontWeight.BOLD, 18));
                pane.getChildren().add(name);
                final String id = wishLists.get(i).id;

                Button edit = new Button();
                edit.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
                edit.setLayoutX(520);
                edit.setLayoutY(y+30);
                edit.setPrefSize(160,39);
                pane.getChildren().add(edit);
                final String idWishList = wishLists.get(i).id;
                final String nameWishList = wishLists.get(i).name;
                final String statusWishList = wishLists.get(i).actual;
                edit.setOnAction(t->{
                    Front.root.getChildren().remove(Front.pane);
                    try {
                        Front.pane = WishListEditView.getFront(idWishList,nameWishList);
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    Front.root.getChildren().add(Front.pane);
                });

                FileInputStream UrlActual = new FileInputStream("png/actual.png");
                Image urlActual = new Image(UrlActual);
                ImageView actual = new ImageView(urlActual);

                FileInputStream UrlNoActual = new FileInputStream("png/noactual.png");
                Image urlNoActual = new Image(UrlNoActual);
                ImageView noactual = new ImageView(urlNoActual);

                Button actuality = new Button();
                actuality.setBackground(null);
                if(statusWishList.equals("1")){
                    actuality.setGraphic(actual);
                } else {
                    actuality.setGraphic(noactual);
                }

                actuality.setLayoutY(y+20);
                actuality.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
                actuality.setLayoutX(750);
                pane.getChildren().add(actuality);
                actuality.setOnAction(t-> {
                    String newStatusWishList;
                    if(statusWishList.equals("1")){
                        newStatusWishList = "0";
                    } else {
                        newStatusWishList = "1";
                    }
                    try {
                        Postgre.updateWishListStatus(idWishList,newStatusWishList);
                        getMyWishLists();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }

                });
                Button del = new Button();
                del.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
                del.setLayoutX(899);
                del.setLayoutY(30+y);
                del.setPrefSize(95,36);
                pane.getChildren().add(del);
                del.setOnAction(t->{
                    try {
                        Postgre.deleteWishList(id);
                        getMyWishLists();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        }
        WishListView.scrollPane.setContent(pane);
    }

}
