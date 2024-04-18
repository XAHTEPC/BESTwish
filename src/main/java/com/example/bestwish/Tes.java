package com.example.bestwish;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.text.ParseException;

public class Tes extends JFrame {
    private JFormattedTextField dateFormattedTextField;

    public Tes() {
        setTitle("Date Formatted TextField Example");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 100);

        try {
            // Создание форматтера с маской для даты
            MaskFormatter dateFormatter = new MaskFormatter("##-##-####");
            dateFormatter.setPlaceholderCharacter(' ');

            // Создание форматированного текстового поля с форматтером даты
            dateFormattedTextField = new JFormattedTextField(dateFormatter);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        getContentPane().setLayout(new FlowLayout());
        getContentPane().add(dateFormattedTextField);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Tes example = new Tes();
            example.setVisible(true);
        });
    }
}