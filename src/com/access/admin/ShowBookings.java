package com.access.admin;

import de.jaret.util.ui.timebars.model.TimeBarModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

public class ShowBookings extends JFrame{
    private JPanel mainShow;
    private JComboBox selTitleCB;
    private JComboBox selRoomCB;
    private JButton showBookButt;
    private JButton cancButt;
    private JButton clearListButt;
    private JComboBox selDayCB;
    private JComboBox selHourCB;
    private JList BookingJList;
    private ArrayList<Integer> idShowings = new ArrayList<>();
    private ArrayList<Integer> selectedIdShowings = new ArrayList<>();
    DefaultListModel dlm = new DefaultListModel();

    private static final int DEFAULT_WIDTH = 1005;
    private static final int DEFAULT_HEIGHT = 500;

    Toolkit kit = Toolkit.getDefaultToolkit();
    Dimension screenSize = kit.getScreenSize();
    int screenWidth = screenSize.width;
    int screenHeight = screenSize.height;

    public ShowBookings() {
        setContentPane(mainShow);
        setLocation(screenWidth / 2 - DEFAULT_WIDTH / 2, screenHeight / 2 - DEFAULT_HEIGHT / 2);
        pack();
        setMinimumSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));

        for (String movie : Movies.getTitles()) {
            selTitleCB.addItem(movie);
        }
        for (Integer idRoom : Rooms.getAllRooms()) {
            selRoomCB.addItem(idRoom);
        }
        refreshSelDayCB();
        refreshSelHourCB();

        selTitleCB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshSelDayCB();
                refreshSelHourCB();

            }
        });
        selRoomCB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshSelDayCB();
                refreshSelHourCB();

            }
        });
        selDayCB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshSelHourCB();
            }
        });
        cancButt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        showBookButt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selDayCB.getItemCount()!=0 && selHourCB.getItemCount() != 0 &&
                selTitleCB.getItemCount() != 0 && selRoomCB.getItemCount()!=0) {

                    int selectedIndex = selHourCB.getSelectedIndex();
                    int idShow = selectedIdShowings.get(selectedIndex);


                    ArrayList<String> bookings = Bookings.getBookingsToString(idShow);
                    Timestamp timestamp = Showings.getDate(idShow);
                    String date = ", Dzień: "+timestamp.toString().substring(0,10)+
                            ", Godzina: "+timestamp.toString().substring(11,16);
                    for(String booking : bookings)
                        dlm.addElement(booking+date);

                    BookingJList.setModel(dlm);
                }
            }
        });
        clearListButt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultListModel model = new DefaultListModel();
                model.clear();
                BookingJList.setModel(model);
            }
        });
    }
    private void refreshSelDayCB(){
        int selectedIndex = selTitleCB.getSelectedIndex();
        String title = (String) selTitleCB.getItemAt(selectedIndex);
        int idMovie = Movies.getId(title);
        selectedIndex = selRoomCB.getSelectedIndex();
        int idRoom = (int) selRoomCB.getItemAt(selectedIndex);

        Timestamp timestamp;
        idShowings = Showings.getShowings(idMovie,idRoom);
        selDayCB.removeAllItems();
        for (Integer idShow : idShowings){
            timestamp = Showings.getDate(idShow);
            if(dateIsntInDayComBox(timestamp))
                selDayCB.addItem(dateToString(timestamp));
        }
        // here add method to sort date in selDayCB

    }
    private void refreshSelHourCB(){
        if(selDayCB.getItemCount() != 0) {
            int selectedIndex = selTitleCB.getSelectedIndex();
            String title = (String) selTitleCB.getItemAt(selectedIndex);
            int idMovie = Movies.getId(title);
            selectedIndex = selRoomCB.getSelectedIndex();
            int idRoom = (int) selRoomCB.getItemAt(selectedIndex);
            selectedIndex = selDayCB.getSelectedIndex();
            String selectedDate = selDayCB.getItemAt(selectedIndex).toString();

            ArrayList<Integer> showings = Showings.getShowingsSameDay(idMovie, idRoom, selectedDate);
            Timestamp timestamp;
            selHourCB.removeAllItems();
            selectedIdShowings = new ArrayList<>();
            for (Integer idShow : showings) {
                timestamp = Showings.getDate(idShow);
                if (timeIsntInHourComBox(timestamp)) {
                    selHourCB.addItem(timeToString(timestamp));
                    selectedIdShowings.add(idShow);
                }
            }
        }
    }
    private boolean dateIsntInDayComBox(Timestamp timestamp){
        String date = dateToString(timestamp);
        for (int i=0;i<selDayCB.getItemCount();i++){
            if(date.equals(selDayCB.getItemAt(i)))
                return false;
        }
        return true;
    }
    private boolean timeIsntInHourComBox(Timestamp timestamp){
        String date = timeToString(timestamp);
        for (int i=0;i<selHourCB.getItemCount();i++){
            if(date.equals(selHourCB.getItemAt(i)))
                return false;
        }
        return true;
    }

    private String dateToString(Timestamp timestamp){
        return timestamp.toString().substring(0,10);
    }
    private String timeToString(Timestamp timestamp){
        return timestamp.toString().substring(11,16);
    }

    private void completeList(int idShow){

    }

}
