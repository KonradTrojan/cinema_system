package com.access.admin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

public class EditShowing extends JFrame{

    private static final int DEFAULT_WIDTH = 805;
    private static final int DEFAULT_HEIGHT = 500;

    Toolkit kit = Toolkit.getDefaultToolkit();
    Dimension screenSize = kit.getScreenSize();
    int screenWidth = screenSize.width;
    int screenHeight = screenSize.height;


    private JButton editButt;
    private JButton cancButt;
    private JComboBox selTitleCB;
    private JComboBox selRoomCB;
    private JComboBox selDayCB;
    private JComboBox selHourCB;
    private JPanel mainEditJP;
    private ArrayList<Timestamp> idShowings;


    public EditShowing() {
        setContentPane(mainEditJP);

        setLocation(screenWidth/2 - DEFAULT_WIDTH/2 ,screenHeight/2 - DEFAULT_HEIGHT/2);
        pack();
        setMinimumSize(new Dimension(DEFAULT_WIDTH,DEFAULT_HEIGHT));


        for (String movie: Movies.getTitles()){
            selTitleCB.addItem(movie);
        }

        for(Integer idRoom : Rooms.getAllRooms()){
            selRoomCB.addItem(idRoom);
        }

        refreshSelDayCB();

        cancButt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        selTitleCB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshSelDayCB();
            }
        });
        selRoomCB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshSelDayCB();
            }
        });
        editButt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        selDayCB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshSelDayCB();
            }
        });
    }

    private void refreshSelDayCB() {

        int selectedIndex = selTitleCB.getSelectedIndex();
        int idMovie = Movies.getId(selTitleCB.getItemAt(selectedIndex).toString());

        selectedIndex = selRoomCB.getSelectedIndex();
        int idRoom = (int) selRoomCB.getItemAt(selectedIndex);

        idShowings = new ArrayList<>();
        selDayCB.removeAllItems();
        for (Integer id : Showings.getShowings(idMovie, idRoom)) {
            String date = Showings.getDateString(id);
            if (!selDayCBContains(date)) {
                selDayCB.addItem(date);
//                idShowings.add(Showings.g);
            }
        }

    }
    private void refreshSelHourCB(){
        selHourCB.removeAllItems();
        int selectedIndex = selDayCB.getSelectedIndex();
//        int idShowing = idShowings.get(selectedIndex);


//        if(selDayCB.getItemCount()!=0){
//            selectedIndex = selDayCB.getSelectedIndex();
//            int idShowing = selectedIndex;
//            selHourCB.removeAllItems();
//            for(Calendar calendar: Showings.getDates(idMovie,idRoom,idShowing)) {
//                String time = calendar.get(Calendar.HOUR) + "." +
//                        calendar.get(Calendar.MINUTE);
//                if(!selHourCBContains(time))
//                    selHourCB.addItem(time);
//            }
//        }
    }

    private boolean selDayCBContains(String date){
        for(int i = 0; i < selDayCB.getItemCount(); i++){
            if(date.equals(selDayCB.getItemAt(i)))
                return true;
        }
        return false;
    }
    private boolean selHourCBContains(String time){
        for(int i = 0; i < selDayCB.getItemCount(); i++){
            if(time.equals(selHourCB.getItemAt(i)))
                return true;
        }
        return false;
    }
}
