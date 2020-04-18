package com.access.admin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class AddMovie extends JFrame{


    private static final int DEFAULT_WIDTH = 805;
    private static final int DEFAULT_HEIGHT = 500;

    Toolkit kit = Toolkit.getDefaultToolkit();
    Dimension screenSize = kit.getScreenSize();
    int screenWidth = screenSize.width;
    int screenHeight = screenSize.height;

    private JTextField titleJTXT;
    private JTextField descJTXT;
    private JButton addMovieJButt;
    private JButton cancJButt;
    private JButton clearJButt;
    private JTextField lengJTXT;
    private JTextField dirJTXT;
    private JTextField writJTXT;
    private JTextField starsJTXT;
    private JPanel mainAddMovJP;
    private JTextField ageCatJTXT;
    private JTextField textField1;

    public AddMovie()  {

        setContentPane(mainAddMovJP);

        setLocation(screenWidth/2 - DEFAULT_WIDTH/2 ,
                screenHeight/2 - DEFAULT_HEIGHT/2);

        setResizable(false);
        setTitle("Dodawanie filmu");

        pack();
        setMinimumSize(new Dimension(DEFAULT_WIDTH,DEFAULT_HEIGHT));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        addMovieJButt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = titleJTXT.getText();
                String description = descJTXT.getText();
                String director = dirJTXT.getText();
                String ageCategory = ageCatJTXT.getText();
                String writer = writJTXT.getText();
                String stars = starsJTXT.getText();
                int length = Integer.parseInt(lengJTXT.getText());

                File poster = new File("src/com/access/admin/image/brak.png"); //default poster

                Movies.addMovie(title,description,length,director,writer,stars,ageCategory,poster);

            }
        });


    }

    public static void main(String[] args) {
        FileInputStream inputStream = null;
        File image = new File("src/com/access/admin/image/brak.png");
        try {
            inputStream = new FileInputStream(image);

        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }

        System.out.println(inputStream);
    }
}
