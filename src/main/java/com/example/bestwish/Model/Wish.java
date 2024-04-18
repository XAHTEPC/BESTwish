package com.example.bestwish.Model;

import com.example.bestwish.Data.Postgre;
import com.example.bestwish.View.FriendWishListView;
import com.example.bestwish.View.MainView;
import com.example.bestwish.View.WishListAddView;
import com.example.bestwish.View.WishListEditView;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;

public class Wish {
    public String name;
    public String id;
    public String status;
    public String client;
    public static ArrayList<TextField> myWish;
    public static Pane myWishPane;
    static int height;
    static int kol;

    public Wish(String name, String id, String status) {
        this.name = name;
        this.id = id;
        this.status = status;
    }
    public Wish(String name, String id, String status, String client) {
        this.name = name;
        this.id = id;
        this.status = status;
        this.client = client;
    }

    public Wish(String name){
        this.name = name;
    }
    public static void start(){
        myWish = new ArrayList<>();
        myWishPane = new Pane();
        height = 0;
        kol = 0;


    }

    public static void addWish(int el) throws FileNotFoundException {
        FileInputStream Url = new FileInputStream("png/wishEl.png");
        Image url = new Image(Url);
        ImageView addWishEl = new ImageView(url);
        addWishEl.setLayoutX(0);
        addWishEl.setLayoutY(height);
        myWishPane.getChildren().add(addWishEl);

        TextField name = new TextField();
        name.setLayoutX(90);
        name.setLayoutY(25+height);
        name.setMaxWidth(533);
        name.setMinWidth(533);
        name.setFont(Font.font("STXihei", 20));
        name.setStyle("-fx-background: rgb(213,211,221);-fx-background-color: rgb(213,211,221);");
        myWish.add(name);
        myWishPane.getChildren().add(name);

        Button del = new Button();
        del.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        del.setLayoutX(704);
        del.setLayoutY(40+height);
        del.setPrefSize(95,36);
        myWishPane.getChildren().add(del);
        final int id = kol;
        del.setOnAction(t->{
            try {
                delWish(id,el);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        if(el == 0 )
            WishListAddView.scrollPane.setContent(myWishPane);
        else
            WishListEditView.scrollPane.setContent(myWishPane);
        height+=75;
        kol++;
    }
    static void delWish(int element, int el) throws FileNotFoundException {
        myWish.remove(element);
        myWishPane = new Pane();
        height = 0;
        kol = 0;
        for(int i=0;i<myWish.size();i++){
            FileInputStream Url = new FileInputStream("png/wishEl.png");
            Image url = new Image(Url);
            ImageView addWishEl = new ImageView(url);
            addWishEl.setLayoutX(0);
            addWishEl.setLayoutY(height);
            myWishPane.getChildren().add(addWishEl);

            TextField name = new TextField();
            name.setText(myWish.get(i).getText());
            name.setLayoutX(90);
            name.setLayoutY(25+height);
            name.setMaxWidth(533);
            name.setMinWidth(533);
            name.setFont(Font.font("STXihei", 20));
            name.setStyle("-fx-background: rgb(213,211,221);-fx-background-color: rgb(213,211,221);");
            myWishPane.getChildren().add(name);

            Button del = new Button();
            del.setLayoutX(704);
            del.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
            del.setLayoutY(40+height);
            del.setPrefSize(95,36);
            myWishPane.getChildren().add(del);
            final int id = kol;
            del.setOnAction(t->{
                try {
                    delWish(id,el);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            });
            height+=75;
            kol++;
        }
        if(el == 0)
            WishListAddView.scrollPane.setContent(myWishPane);
        else
            WishListEditView.scrollPane.setContent(myWishPane);
    }

    public static void save(String name) throws SQLException {
        ArrayList<Wish> wishMas = new ArrayList<>();
        for(int i=0;i<myWish.size();i++){
            String wishEl = myWish.get(i).getText();
            System.out.println(wishEl);
            Wish wish = new Wish(wishEl);
            wishMas.add(wish);
        }
        Postgre.addWishList(wishMas,name);
    }

    public static void getMyWishes(String id) throws SQLException, FileNotFoundException {
        ArrayList<Wish> wishes = new ArrayList<>();
        wishes = Postgre.getWishesById(id);
        height = 0;
        kol = 0;
        for(int i = 0;i<wishes.size();i++){
            FileInputStream Url = new FileInputStream("png/wishEl.png");
            Image url = new Image(Url);
            ImageView addWishEl = new ImageView(url);
            addWishEl.setLayoutX(0);
            addWishEl.setLayoutY(height);
            myWishPane.getChildren().add(addWishEl);

            TextField name = new TextField();
            name.setText(wishes.get(i).name);
            name.setLayoutX(90);
            name.setLayoutY(25+height);
            name.setMaxWidth(533);
            name.setMinWidth(533);
            name.setFont(Font.font("STXihei", 20));
            name.setStyle("-fx-background: rgb(213,211,221);-fx-background-color: rgb(213,211,221);");
            myWishPane.getChildren().add(name);

            Button del = new Button();
            del.setLayoutX(704);
            del.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
            del.setLayoutY(40+height);
            del.setPrefSize(95,36);
            myWishPane.getChildren().add(del);
            final int idWish = kol;
            del.setOnAction(t->{
                try {
                    delWish(idWish,1);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            });
            height+=75;
            kol++;
            myWish.add(name);
        }
        WishListEditView.scrollPane.setContent(myWishPane);
    }

    public static void updateMyWishes(String id, String nameWishList) throws SQLException {
        Postgre.deleteWishById(id);
        for(int i=0;i<myWish.size();i++){
            String name = myWish.get(i).getText();
            Postgre.addWish(name,id);
        }
        Postgre.updateWishListName(id,nameWishList);
    }

    public static void friendWishes(String id) throws SQLException, FileNotFoundException {
        ArrayList<Wish> wishes = Postgre.getFriendWishes(id);
        for(int i=0;i<wishes.size();i++){
            FileInputStream Url = new FileInputStream("png/friendWishListEl.png");
            Image url = new Image(Url);
            ImageView addWishEl = new ImageView(url);
            addWishEl.setLayoutX(0);
            addWishEl.setLayoutY(height);
            myWishPane.getChildren().add(addWishEl);

            TextField name = new TextField();
            name.setText(wishes.get(i).name);
            name.setLayoutX(44);
            name.setLayoutY(25+height);
            name.setMaxWidth(450);
            name.setEditable(false);
            name.setMinWidth(450);
            name.setFont(Font.font("STXihei", 30));
            name.setStyle("-fx-background: rgb(213,211,221);-fx-background-color: rgb(213,211,221);");
            myWishPane.getChildren().add(name);

            Button take = new Button();
            take.setLayoutX(510);
            take.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
            take.setLayoutY(33+height);
            take.setPrefSize(218,36);
            final String idWish = wishes.get(i).id;
            take.setOnAction(t->{
                try {
                    Postgre.updateFriendWish(idWish,true);
                    start();
                    friendWishes(id);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            });


            Button cancel = new Button();
            cancel.setLayoutX(786);
            cancel.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
            cancel.setLayoutY(33+height);
            cancel.setPrefSize(164,36);
            cancel.setOnAction(t->{
                try {
                    Postgre.updateFriendWish(idWish,false);
                    start();
                    friendWishes(id);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            });


            Rectangle rectangleBook = new Rectangle(218,36);
            rectangleBook.setFill(Color.rgb(213,211,221));
            rectangleBook.setY(33+height);
            rectangleBook.setX(510);



            Rectangle rectangleBook2 = new Rectangle(218,36);
            rectangleBook2.setFill(Color.rgb(213,211,221));
            rectangleBook2.setY(33+height);
            rectangleBook2.setX(786);
            System.out.println("if:"+wishes.get(i).client);
            if(wishes.get(i).client.equals(MainView.id)||wishes.get(i).client.equals("0")){
                System.out.println("1if");
                if(wishes.get(i).status.equals("0")){
                    System.out.println("2if");
                    myWishPane.getChildren().add(take);
                    myWishPane.getChildren().add(rectangleBook2);

                }else {
                    System.out.println("3if");
                    myWishPane.getChildren().add(cancel);
                    myWishPane.getChildren().add(rectangleBook);
                }
            }
            else{
                System.out.println("4if");
                myWishPane.getChildren().add(rectangleBook);
                myWishPane.getChildren().add(rectangleBook2);
            }
            height+=69;
        }
        FriendWishListView.scrollPane.setContent(myWishPane);
    }
}
