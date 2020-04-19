package com.access.admin;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
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
import java.util.Objects;

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
    private JButton addPosterButt;
    private JFileChooser jFileChooser;
    private File selectedPoster = null;

    public AddMovie()  {

        setContentPane(mainAddMovJP);

        setLocation(screenWidth/2 - DEFAULT_WIDTH/2 ,
                screenHeight/2 - DEFAULT_HEIGHT/2);

        setResizable(false);
        setTitle("Dodawanie filmu");

        pack();
        setMinimumSize(new Dimension(DEFAULT_WIDTH,DEFAULT_HEIGHT));


        addMovieJButt.addActionListener(new ActionListener() {
            @Override

            public void actionPerformed(ActionEvent e) {
                if (titleJTXT.getText().equals("") || descJTXT.getText().equals("") || descJTXT.getText().equals("") ||
                        ageCatJTXT.getText().equals("") || writJTXT.getText().equals("") || starsJTXT.getText().equals("")) {

                    JOptionPane.showMessageDialog(new JFrame(), "Nie wszystkie pola zostały wypełnione", "Błąd",
                            JOptionPane.ERROR_MESSAGE);

                } else if(Objects.requireNonNull(Movies.getTitles()).contains(titleJTXT.getText())) {
                    JOptionPane.showMessageDialog(new JFrame(), "Istnieje film o takim tytule.", "Błąd",
                            JOptionPane.ERROR_MESSAGE);

                } else if (selectedPoster == null) {
                    JOptionPane.showMessageDialog(new JFrame(), "Plik plakatu nie został wczytany pomyślnie", "Błąd",
                            JOptionPane.ERROR_MESSAGE);

                }else {

                    boolean helperLengthIsInt;
                    try {
                        Double.parseDouble(lengJTXT.getText());
                        helperLengthIsInt = true;
                    } catch (NumberFormatException nfe) {
                        JOptionPane.showMessageDialog(new JFrame(), "Długość filmu musi być liczbą całkowitą", "Błąd",
                                JOptionPane.ERROR_MESSAGE);
                        helperLengthIsInt = false;
                    }
                    if(helperLengthIsInt) {
                        String title = titleJTXT.getText();
                        String description = descJTXT.getText();
                        String director = dirJTXT.getText();
                        String ageCategory = ageCatJTXT.getText();
                        String writer = writJTXT.getText();
                        String stars = starsJTXT.getText();
                        int length = Integer.parseInt(lengJTXT.getText());

                        File poster;
                        if (selectedPoster != null)
                            poster = selectedPoster;
                        else
                            poster = new File("src/com/access/admin/image/brak.png"); //default poster

                        Movies.addMovie(title, description, length, director, writer, stars, ageCategory, poster);

                        JOptionPane.showMessageDialog(new JFrame(), "Wpis pomyślnie dodany do bazy danych.", "Błąd",
                                JOptionPane.INFORMATION_MESSAGE);
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
        clearJButt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                titleJTXT.setText("");
                lengJTXT.setText("");
                dirJTXT.setText("");
                descJTXT.setText("");
                writJTXT.setText("");
                starsJTXT.setText("");
                ageCatJTXT.setText("");

                selectedPoster = null;
            }
        });
        cancJButt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
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
