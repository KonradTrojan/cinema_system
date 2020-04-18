package com.access.admin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Objects;

public class AdminInterface extends JFrame {


    private static final int DEFAULT_WIDTH = 805;
    private static final int DEFAULT_HEIGHT = 500;

    Toolkit kit = Toolkit.getDefaultToolkit();
    Dimension screenSize = kit.getScreenSize();
    int screenWidth = screenSize.width;
    int screenHeight = screenSize.height;
    private JPanel mainLeft;
    private JPanel mainRight;
    private JPanel mainJP;
    private JButton addMovieButt;
    private JButton removeMovieButt;
    private JButton editMovieButt;
    private JComboBox moviesComBox;
    private JButton pokażHarmonogramSeansówButton;
    private JButton usuńSeansButton1;
    private JButton edytujSeansButton;
    private JButton dodajSeansButton1;

    public AdminInterface() {

        setLocation(screenWidth/2 - DEFAULT_WIDTH/2 ,
                    screenHeight/2 - DEFAULT_HEIGHT/2);

        setResizable(false);
        setTitle("Panel administratora");

        pack();
        setMinimumSize(new Dimension(DEFAULT_WIDTH,DEFAULT_HEIGHT));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        refresh();


        addMovieButt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddMovie addMovie = new AddMovie();
                setVisible(false);
                addMovie.setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        // poniższy kod wykonuje instrukcje w wątku dystrubucji zdarzeń
        EventQueue.invokeLater(() ->
        {
            AdminInterface frame = new AdminInterface();
            frame.setContentPane(new AdminInterface().mainJP);
            frame.setVisible(true);

        });
    }

    public void refresh(){
        fillComboBox();
    }

    public void fillComboBox() {
        for (String title : Objects.requireNonNull(Movies.getTitles())){
            moviesComBox.addItem(title);
        }
    }

}
