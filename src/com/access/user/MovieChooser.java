package com.access.user;

import com.access.admin.Movies;
import org.jdesktop.swingx.JXLabel;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXTextArea;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class MovieChooser {
    private JFrame mainFrame = new JFrame("Wybór filmu");
    private JPanel f = new JPanel();
    private GridBagConstraints c = new GridBagConstraints();

    private void draw() {
        int numberofmovies = Movies.getNumberOfMovies();
        System.out.println(numberofmovies);
        ArrayList<Integer> movielist = Movies.getAllMovies();
        f.setLayout(new GridBagLayout());
        for (int i = 0; i < numberofmovies; i++) {
            drawSingleMovie(movielist.get(i), i);
        }
        JScrollPane scrollBar = new JScrollPane(f, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        f.setAlignmentX(JPanel.LEFT_ALIGNMENT);
        mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        ImageIcon img = new ImageIcon("favicon.jpg");
        mainFrame.setIconImage(img.getImage());
        //mainFrame.setPreferredSize(new Dimension(485, 500));
        mainFrame.add(scrollBar);
        mainFrame.pack();
        mainFrame.setResizable(false);
        mainFrame.setVisible(true);
    }

    private void drawSingleMovie(int movieid, int index) {
        int xshift = index % 2 + 1;
        //poster
        JLabel posterLabel = new JLabel(new ImageIcon(Movies.getPoster(movieid)));
        posterLabel.setBorder(BorderFactory.createTitledBorder("Plakat"));
        GridBagConstraints pC = new GridBagConstraints();
        pC.gridx = xshift;
        pC.gridy = index/2 * 3;
        pC.ipady = 0;
        pC.ipadx = 0;
        pC.gridwidth = 1;
        pC.gridheight = 3;
        pC.fill = GridBagConstraints.BOTH;
        pC.anchor = GridBagConstraints.NORTH;
        f.add(posterLabel, pC);
        //title
        JXLabel titleLabel = new JXLabel(Movies.getTitle(movieid));
        titleLabel.setBorder(BorderFactory.createTitledBorder("Tytuł"));
        //titleLabel.setPreferredSize(new Dimension(200, titleLabel.getPreferredSize().height));
        GridBagConstraints tC = new GridBagConstraints();
        tC.gridx = 1 + xshift;
        tC.gridy = index/2 * 3;
        tC.ipady = 0;
        tC.ipadx = 0;
        tC.gridwidth = 1;
        tC.gridheight = 1;
        tC.fill = GridBagConstraints.BOTH;
        f.add(titleLabel, tC);
        //director
        JXLabel directorLabel = new JXLabel(Movies.getDirector(movieid));
        directorLabel.setBorder(BorderFactory.createTitledBorder("Reżyseria"));
        //directorLabel.setPreferredSize(new Dimension(200, directorLabel.getPreferredSize().height));
        GridBagConstraints dC = new GridBagConstraints();
        dC.gridx = 1+xshift;
        dC.gridy = 1 + index/2 * 3;
        dC.ipady = 0;
        dC.ipadx = 0;
        dC.gridwidth = 1;
        dC.gridheight = 1;
        dC.fill = GridBagConstraints.BOTH;
        f.add(directorLabel, dC);
        //description
        String x = "It's there, but it's preferred height is 0. That means it's too small to be visible. Change line 14 into these:";
        JXLabel descriptionLabel = new JXLabel(Movies.getDescription(movieid) + x);
        descriptionLabel.setLineWrap(true);
        descriptionLabel.setMaxLineSpan(200);
        descriptionLabel.setBorder(BorderFactory.createTitledBorder("Opis"));
        descriptionLabel.setVerticalAlignment(SwingConstants.TOP);
        //descriptionLabel.setPreferredSize(new Dimension(200, 270));
        GridBagConstraints deC = new GridBagConstraints();
        deC.gridx = 1+xshift;
        deC.gridy = 2 + index/2 * 3;
        deC.ipady = 0;
        deC.ipadx = 0;
        deC.gridwidth = 1;
        deC.gridheight = 1;
        deC.weighty = 0.8;
        deC.weightx = 0.8;
        deC.anchor = GridBagConstraints.NORTH;
        deC.fill = GridBagConstraints.BOTH;
        f.add(descriptionLabel, deC);
        /*if (index == Movies.getNumberOfMovies() - 1) {
            //separator
            JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
            //separator.setPreferredSize(new Dimension(0, 10));
            GridBagConstraints sepC = new GridBagConstraints();
            sepC.gridx = 0;
            sepC.gridy = 3;
            sepC.ipady = 10;
            sepC.gridwidth = 2;
            //sepC.anchor = GridBagConstraints.CENTER;
            sepC.fill = GridBagConstraints.HORIZONTAL;
            f.add(separator, sepC);
        }*/
    }

    public static void main(String[] args) {
        MovieChooser x = new MovieChooser();
        x.draw();
    }
}
