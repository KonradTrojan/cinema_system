package com.access.admin;

import com.sun.crypto.provider.JceKeyStore;

import javax.swing.*;
import java.awt.*;
import java.sql.Timestamp;
import java.util.ArrayList;

public abstract class ToolsGUI {
    public static void setJPanel(JPanel jp, int weight, int height){
        Dimension dim = new Dimension(weight,height);
        jp.setMaximumSize(dim);
        jp.setMinimumSize(dim);
        jp.setPreferredSize(dim);
        jp.setSize(dim);
        jp.revalidate();
    }
    public static void setSizeJFrame(JFrame jFrame, JPanel jPanel, int defaultWeight, int defaultHeight,String title){
        jFrame.setContentPane(jPanel);
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        jPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        jFrame.setLocation(screenWidth / 2 - defaultWeight / 2, screenHeight / 2 - defaultHeight / 2);
        jFrame.setMinimumSize(new Dimension(defaultWeight,defaultHeight));
        jFrame.setTitle(title);
        jFrame.setResizable(false);
        jFrame.pack();

    }

    public static ArrayList<Integer> refreshSelHourCB(JComboBox selDayCB, JComboBox selTitleCB, JComboBox selRoomCB, JComboBox selHourCB, ArrayList<Integer> selectedIdShowings){
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
                if (ToolsGUI.timeIsntInHourComBox(timestamp, selHourCB)) {
                    selHourCB.addItem(ToolsGUI.timeToString(timestamp));
                    selectedIdShowings.add(idShow);
                }
            }

        }
        return selectedIdShowings;
    }
    public static ArrayList<Integer> refreshSelDayCB(JComboBox selDayCB, JComboBox selTitleCB, JComboBox selRoomCB, ArrayList<Integer> idShowings){
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
            if(dateIsntInDayComBox(timestamp,selDayCB))
                selDayCB.addItem(dateToString(timestamp));
        }
        // here add method to sort date in selDayCB
        return idShowings;
    }
    private static boolean dateIsntInDayComBox(Timestamp timestamp, JComboBox selDayCB){
        String date = dateToString(timestamp);
        for (int i=0;i<selDayCB.getItemCount();i++){
            if(date.equals(selDayCB.getItemAt(i)))
                return false;
        }
        return true;
    }
    private static boolean timeIsntInHourComBox(Timestamp timestamp, JComboBox selHourCB){
        String date = timeToString(timestamp);
        for (int i=0;i<selHourCB.getItemCount();i++){
            if(date.equals(selHourCB.getItemAt(i)))
                return false;
        }
        return true;
    }

    public static void fillComboBoxTitle(JComboBox moviesComBox, JComboBox roomsComBox) {
        moviesComBox.removeAllItems();
        for (String title : Movies.getTitles()){
            moviesComBox.addItem(title);
        }
        roomsComBox.removeAllItems();
        for (Integer idRoom : Rooms.getAllRooms()){
            roomsComBox.addItem(idRoom);
        }
    }

    private static String dateToString(Timestamp timestamp){
        return timestamp.toString().substring(0,10);
    }
    private static String timeToString(Timestamp timestamp){
        return timestamp.toString().substring(11,16);
    }
}
