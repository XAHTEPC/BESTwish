package com.example.bestwish.Data;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Toast {

    public static int TIME = 2000;
    public static Popup createPopup(final String message){
        final Popup popup = new Popup();
        popup.setAutoFix(true);
        Label label = new Label(message);
        label.setFont(Font.font("STXihei", 20));
        popup.getContent().add(label);
        return  popup;
    }

    public static void show(final String message, final Pane pane, int y, int x){
        Stage stage = (Stage) pane.getScene().getWindow();
        final Popup popup = createPopup(message);
        popup.setOnShown(e ->{
            popup.setX(x);
            popup.setY(y);
        });
        popup.show(stage);
        new Timeline(new KeyFrame(
                Duration.millis(TIME), actionEvent -> popup.hide())).play();
    }
}