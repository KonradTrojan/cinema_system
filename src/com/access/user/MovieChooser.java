package com.access.user;

//the main window of user interface.

import com.access.admin.Movies;
import org.jdesktop.swingx.JXLabel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

class MovieButton extends JButton {
    int movieId;

    public MovieButton(String text) {
        super(text);
    }
}

public class MovieChooser {
    private JFrame mainFrame = new JFrame("Wybór filmu");
    private JPanel f = new JPanel();
    private GridBagConstraints c = new GridBagConstraints();

    private void draw() {
        int numberofmovies = Movies.getNumberOfMovies();
        ArrayList<Integer> movielist = Movies.getAllMovies();
        f.setLayout(new GridBagLayout());
        for (int i = 0; i < numberofmovies; i++) {
            drawSingleMovie(movielist.get(i), i);
        }
        JScrollPane scrollBar = new JScrollPane(f, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollBar.getVerticalScrollBar().setUnitIncrement(16);
        f.setAlignmentX(JPanel.LEFT_ALIGNMENT);
        mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        ImageIcon img = new ImageIcon("favicon.jpg");
        mainFrame.setIconImage(img.getImage());
        //mainFrame.setPreferredSize(new Dimension(485, 500));
        mainFrame.add(scrollBar);
        mainFrame.pack();
        mainFrame.setResizable(false);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int windowWidth = mainFrame.getWidth();
        mainFrame.setBounds(screenSize.width / 2 - (windowWidth / 2), (screenSize.height / 2) - 700 / 2, windowWidth + 20, 700);
        mainFrame.setVisible(true);
    }

    private void drawSingleMovie(int movieid, int index) {
        int xshift; //0-left, 2-center, 4-right
        if (index % 3 == 0)
            xshift = 0;
        else if (index % 3 == 1)
            xshift = 2;
        else
            xshift = 4;
        //poster
        JLabel posterLabel = new JLabel(new ImageIcon(Movies.getPoster(movieid)));
        posterLabel.setBorder(BorderFactory.createTitledBorder("Plakat"));
        GridBagConstraints pC = new GridBagConstraints();
        pC.gridx = xshift;
        pC.gridy = (index / 3) * 4;
        pC.ipady = 0;
        pC.ipadx = 0;
        pC.gridwidth = 1;
        pC.gridheight = 4;
        pC.fill = GridBagConstraints.BOTH;
        pC.anchor = GridBagConstraints.NORTH;
        f.add(posterLabel, pC);
        //title
        JXLabel titleLabel = new JXLabel(Movies.getTitle(movieid));
        titleLabel.setBorder(BorderFactory.createTitledBorder("Tytuł"));
        GridBagConstraints tC = new GridBagConstraints();
        tC.gridx = 1 + xshift;
        tC.gridy = (index / 3) * 4;
        tC.ipady = 0;
        tC.ipadx = 0;
        tC.gridwidth = 1;
        tC.gridheight = 1;
        tC.fill = GridBagConstraints.BOTH;
        f.add(titleLabel, tC);
        //director
        JXLabel directorLabel = new JXLabel(Movies.getDirector(movieid));
        directorLabel.setBorder(BorderFactory.createTitledBorder("Reżyseria"));
        GridBagConstraints dC = new GridBagConstraints();
        dC.gridx = 1 + xshift;
        dC.gridy = 1 + (index / 3) * 4;
        dC.ipady = 0;
        dC.ipadx = 0;
        dC.gridwidth = 1;
        dC.gridheight = 1;
        dC.fill = GridBagConstraints.BOTH;
        f.add(directorLabel, dC);
        //description
        JXLabel descriptionLabel = new JXLabel(Movies.getDescription(movieid));
        descriptionLabel.setLineWrap(true);
        descriptionLabel.setMaxLineSpan(200);
        descriptionLabel.setBorder(BorderFactory.createTitledBorder("Opis"));
        descriptionLabel.setVerticalAlignment(SwingConstants.TOP);
        descriptionLabel.setPreferredSize(new Dimension(200, 270));
        GridBagConstraints deC = new GridBagConstraints();
        deC.gridx = 1 + xshift;
        deC.gridy = 2 + (index / 3) * 4;
        deC.ipady = 0;
        deC.ipadx = 0;
        deC.gridwidth = 1;
        deC.gridheight = 1;
        deC.weighty = 0.8;
        deC.weightx = 0.8;
        deC.anchor = GridBagConstraints.NORTH;
        deC.fill = GridBagConstraints.BOTH;
        f.add(descriptionLabel, deC);
        //button
        MovieButton chooseButton = new MovieButton("Wybierz");
        chooseButton.movieId=movieid;
        GridBagConstraints bC = new GridBagConstraints();
        bC.gridx = 1 + xshift;
        bC.gridy = 3 + (index / 3) * 4;
        bC.ipady = 0;
        bC.ipadx = 0;
        bC.gridwidth = 1;
        bC.gridheight = 1;
        bC.anchor = GridBagConstraints.NORTH;
        bC.fill = GridBagConstraints.BOTH;
        f.add(chooseButton, bC);
        /*if (xshift == 0 && index != Movies.getNumberOfMovies() - 1) {
            //separator
            JPanel separator = new JPanel();
            separator.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            //JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
            //separator.setPreferredSize(new Dimension(0, 10));
            GridBagConstraints sepC = new GridBagConstraints();
            sepC.gridx = 0;
            sepC.gridy = 3 + (index / 3) * 4;
            sepC.ipady = 0;
            sepC.gridwidth = 6;
            //sepC.anchor = GridBagConstraints.CENTER;
            sepC.fill = GridBagConstraints.HORIZONTAL;
            f.add(separator, sepC);
        }*/
        chooseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(chooseButton.movieId);
            }
        });
    }

    public static void main(String[] args) {
        MovieChooser x = new MovieChooser();
        x.draw();
    }
}
