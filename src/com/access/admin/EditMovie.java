package com.access.admin;

import javax.swing.*;

public class EditMovie extends JFrame {
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

    public EditMovie(Integer idMovie){
        setContentPane(mainEditJP);

        titleJTXT.setText(Movies.getTitle(idMovie));
//        starsJTXT.setText(Movies.ge);
    }
}
