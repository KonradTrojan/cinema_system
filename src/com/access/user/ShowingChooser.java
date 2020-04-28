package com.access.user;

import com.access.admin.Showings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

public class ShowingChooser{
    private JFrame frame = new JFrame("Wybierz seans");
    private JComboBox showingsComboBox;
    private JButton toPlaceChooserButton;
    private JPanel mainPanel;
    private int movieId;

    private class CBItem {
        int id;
        Timestamp date;

        public CBItem(int id) {
            this.id = id;
            date = Showings.getDateStart(id);
        }

        public int getId() {
            return id;
        }

        @Override
        public String toString() {

            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            return cal.get(Calendar.DAY_OF_MONTH) + "." + cal.get(Calendar.MONTH) + "." + cal.get(Calendar.YEAR) + ", godz. " + cal.get(Calendar.HOUR) + ':' + cal.get(Calendar.MINUTE);
        }
    }

    public ShowingChooser(int movieId) {
        this.movieId = movieId;
        ArrayList<Integer> allShowings = Showings.getAllShowingsByMovie(movieId);
        for (int i = 0; i < allShowings.size(); i++) {
            showingsComboBox.addItem(new CBItem(allShowings.get(i)));
        }
        toPlaceChooserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CBItem currShowing = (CBItem) showingsComboBox.getSelectedItem();
                frame.setVisible(false);
                SeatChooser s = new SeatChooser(currShowing.getId());
            }
        });
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setBounds(screenSize.width / 2 - (frame.getWidth() / 2), (screenSize.height / 2) - 700 / 2, frame.getWidth(), frame.getHeight());
        frame.setVisible(true);
    }
}
