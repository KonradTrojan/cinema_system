package com.access.user;

import com.access.admin.Showings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

public class ShowingChooser {
    private JComboBox showingsComboBox;
    private JButton toPlaceChooserButton;
    private int movieId;

    private class CBItem {
        int id;

        public CBItem(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        @Override
        public String toString() {
            Timestamp date = Showings.getDateStart(id);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            return cal.get(Calendar.DAY_OF_MONTH) + '.' + cal.get(Calendar.MONTH) + '.' + cal.get(Calendar.YEAR) + ", godz. " + cal.get(Calendar.HOUR) + ':' + cal.get(Calendar.MINUTE);
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
                SeatChooser s = new SeatChooser(currShowing.getId());
            }
        });
    }

    private void refresh() {

    }
}
