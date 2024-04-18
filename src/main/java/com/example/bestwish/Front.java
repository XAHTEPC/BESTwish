package com.example.bestwish;

import com.example.bestwish.Data.Postgre;
import com.example.bestwish.View.LoginView;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

public class Front  extends Application {
    public static String password = "";
    public static String login ="";
    public static String lvl ="";

    public static Pane pane;
    public static Group root;
    public static Scene scene;


    @Override
    public void start(Stage stage) throws FileNotFoundException {
        root = new Group();
        scene = new Scene(root, 1200, 800);
        stage.initStyle(StageStyle.DECORATED);
        stage.setWidth(1200);
        stage.setHeight(800);
        stage.setMaxWidth(1200);
        stage.setMaxHeight(800);
        stage.setMinWidth(1200);
        stage.setMinHeight(800);
        pane = LoginView.getFront();
        root.getChildren().add(pane);
        stage.setTitle("BESTwish");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException, ParseException {
        launch();
    }
}