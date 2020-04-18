package com.access.user;

import com.access.admin.Movies;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class MovieChooser {
    private JFrame mainFrame = new JFrame("Wybór filmu");
    private JScrollPane f = new JScrollPane();
    GridBagConstraints c = new GridBagConstraints();

    private void draw() {
        int numberofmovies = Movies.getNumberOfMovies();
        ArrayList<Integer> movielist = Movies.getAllMovies();
        f.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        for (int i = 0; i < numberofmovies; i++) {
            drawSingleMovie(movielist.get(i), i);
        }
        mainFrame.add(f);
    }

    private void drawSingleMovie(int movieid, int index) {
        //poster
        BufferedImage poster = null; //TODO
        JLabel posterLabel = new JLabel(new ImageIcon(poster));
        c.gridx = 0;
        c.gridy = index * 3;
        c.ipady = 0;
        c.ipadx = 0;
        c.gridwidth = 1;
        c.gridheight = 3;
        f.add(posterLabel, c);
        //title
        JPanel titlePanel = new JPanel();
        Border titleBorder = BorderFactory.createTitledBorder("Tytuł");
        titlePanel.setBorder(titleBorder);
        JLabel titleLabel = new JLabel(Movies.getTitle(movieid));
        titlePanel.add(titleLabel);
        c.gridx = 1;
        c.gridy = index * 3;
        c.ipady = 0;
        c.ipadx = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        f.add(titlePanel, c);
        //director
        JPanel directorPanel = new JPanel();
        Border directorBorder = BorderFactory.createTitledBorder("Reżyseria");
        directorPanel.setBorder(directorBorder);
        JLabel directorLabel = new JLabel(Movies.getDirector(movieid));
        directorPanel.add(directorLabel);
        c.gridx = 1;
        c.gridy = 1 + index * 3;
        c.ipady = 0;
        c.ipadx = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        f.add(directorPanel, c);
        //description
        JPanel descriptionPanel = new JPanel();
        Border descriptionBorder = BorderFactory.createTitledBorder("Opis");
        descriptionPanel.setBorder(descriptionBorder);
        JTextArea descriptionArea = new JTextArea(Movies.getDescription(movieid));
        descriptionPanel.add(descriptionArea);
        c.gridx = 1;
        c.gridy = 2 + index * 3;
        c.ipady = 0;
        c.ipadx = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        f.add(descriptionPanel, c);
    }
}
