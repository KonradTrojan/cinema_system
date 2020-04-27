package com.access.admin;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

public class EditMovie extends JFrame {

    private static final int DEFAULT_WIDTH = 400;
    private static final int DEFAULT_HEIGHT = 300;

    Toolkit kit = Toolkit.getDefaultToolkit();
    Dimension screenSize = kit.getScreenSize();
    int screenWidth = screenSize.width;
    int screenHeight = screenSize.height;

    private JPanel mainEditJP;
    private JButton confButt;
    private JButton cancButt;
    private JTextField titleJTXT;
    private JTextField starsJTXT;
    private JTextField descrJTXT;
    private JTextField ageCatJTXT;
    private JTextField wriJTXT;
    private JTextField lengJTXT;
    private JTextField dirJTXT;
    private JButton addPosterButt;
    private BufferedImage loadedPoster;
    private File selectedPoster;
    Integer idMovie;

    public EditMovie(Integer idMovie, AdminInterface adminInterface){
        setContentPane(mainEditJP);

        setLocation(screenWidth/2 - DEFAULT_WIDTH/2 ,
                screenHeight/2 - DEFAULT_HEIGHT/2);

        setResizable(false);
        setTitle("Edycja filmu");
        pack();

        mainEditJP.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        setMinimumSize(new Dimension(DEFAULT_WIDTH,DEFAULT_HEIGHT));

        pack();
        this.idMovie = idMovie;
        titleJTXT.setText(Movies.getTitle(idMovie));
        starsJTXT.setText(Movies.getStars(idMovie));
        descrJTXT.setText(Movies.getDescription(idMovie));
        ageCatJTXT.setText(Movies.getAgeCategory(idMovie));
        wriJTXT.setText(Movies.getWriter(idMovie));
        lengJTXT.setText(Movies.getLength(idMovie));
        dirJTXT.setText(Movies.getDirector(idMovie));
        loadedPoster = Movies.getPoster(idMovie);

        confButt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (titleJTXT.getText().equals("") || descrJTXT.getText().equals("") || dirJTXT.getText().equals("") ||
                        ageCatJTXT.getText().equals("") || wriJTXT.getText().equals("") || starsJTXT.getText().equals("")) {

                    JOptionPane.showMessageDialog(new JFrame(), "Nie wszystkie pola zostały wypełnione", "Błąd",
                            JOptionPane.ERROR_MESSAGE);

                } else if (loadedPoster == null) {
                    JOptionPane.showMessageDialog(new JFrame(), "Plik plakatu nie został wczytany pomyślnie", "Błąd",
                            JOptionPane.ERROR_MESSAGE);

                }else {
                    boolean helper;
                    try {
                        Double.parseDouble(lengJTXT.getText());
                        helper = true;
                    } catch (NumberFormatException nfe) {
                        JOptionPane.showMessageDialog(new JFrame(), "Długość filmu musi być liczbą całkowitą", "Błąd",
                                JOptionPane.ERROR_MESSAGE);
                        helper = false;

                    }
                    if (helper) {
                        String title = titleJTXT.getText();
                        String description = descrJTXT.getText();
                        String director = dirJTXT.getText();
                        String ageCategory = ageCatJTXT.getText();
                        String writer = wriJTXT.getText();
                        String stars = starsJTXT.getText();
                        int length = Integer.parseInt(lengJTXT.getText());


                        File poster;
                        if (selectedPoster != null) {
                            poster = selectedPoster;
                            Movies.editMovie(idMovie, title, description, length, director, writer, stars, ageCategory, poster);
                        } else
                            Movies.editMovie(idMovie, title, description, length, director, writer, stars, ageCategory);

                        JOptionPane.showMessageDialog(new JFrame(), "Wpis pomyślnie dodany do bazy danych.", "Błąd",
                                JOptionPane.INFORMATION_MESSAGE);

                        adminInterface.refresh();
                    }
                }
            }
        });
        addPosterButt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

                int returnValue = jfc.showOpenDialog(null);

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    selectedPoster = jfc.getSelectedFile();
                }
            }
        });
        cancButt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }


    public void refresh(){
        titleJTXT.setText(Movies.getTitle(idMovie));
        starsJTXT.setText(Movies.getStars(idMovie));
        descrJTXT.setText(Movies.getDescription(idMovie));
        ageCatJTXT.setText(Movies.getAgeCategory(idMovie));
        wriJTXT.setText(Movies.getWriter(idMovie));
        lengJTXT.setText(Movies.getLength(idMovie));
        dirJTXT.setText(Movies.getDirector(idMovie));
        loadedPoster = Movies.getPoster(idMovie);
    }
}
