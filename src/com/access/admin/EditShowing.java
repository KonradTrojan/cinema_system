package com.access.admin;

import com.sun.xml.internal.ws.message.saaj.SAAJHeader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

public class EditShowing extends JFrame{

    private static final int DEFAULT_WIDTH = 400;
    private static final int DEFAULT_HEIGHT = 300;

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
    private ArrayList<Integer> idShowings = new ArrayList<>();
    private ArrayList<Integer> selectedIdShowings = new ArrayList<>();
    public EditShowing() {
        setContentPane(mainEditJP);
        setLocation(screenWidth / 2 - DEFAULT_WIDTH / 2, screenHeight / 2 - DEFAULT_HEIGHT / 2);
        pack();
        mainEditJP.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));



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
                refreshSelDayCB();
                refreshSelHourCB();           }
        });
    }

    private void refreshSelDayCB(){
        int selectedIndex = selTitleCB.getSelectedIndex();
        String title = (String) selTitleCB.getItemAt(selectedIndex);
        int idMovie = Movies.getId(title);
        selectedIndex = selRoomCB.getSelectedIndex();
        int idRoom = (int) selRoomCB.getItemAt(selectedIndex);

        selDayCB.removeAllItems();
        for(Integer id : Showings.getShowings(idMovie,idRoom)){
            if(dateDoesntExistInSelDayCB(id)) {
                Timestamp timestamp = Showings.getDate(id);
                String date = dateString(timestamp);
                selDayCB.addItem(date);
                idShowings.add(id);
            }
        }
    }
    private void refreshSelHourCB(){
        selHourCB.removeAllItems();
        for(Integer id : idShowings){
            if(timeDoesntExistInSelHourCB(id)){
                Timestamp timestamp = Showings.getDate(id);
                String time = timeString(timestamp);
                selHourCB.addItem(time);
                selectedIdShowings.add(id);
            }
        }
    }

    private boolean dateDoesntExistInSelDayCB(int idShow){
        Timestamp timestamp = Showings.getDate(idShow);
        String date = dateString(timestamp);
        for(int i=0;i<selDayCB.getItemCount();i++){
            if(date.equals(selDayCB.getItemAt(i)))
                return false;
        }
        return true;
    }
    private boolean timeDoesntExistInSelHourCB(int idShow){
        Timestamp timestamp = Showings.getDate(idShow);
        String time = timeString(timestamp);
        for(int i = 0;i<selHourCB.getItemCount();i++){
            if(time.equals(selHourCB.getItemAt(i))){
                return false;
            }
        }
        return true;
    }
    private String dateString(Timestamp timestamp){
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp.getTime());
        cal.add(Calendar.MONTH,1);
        String date = cal.get(Calendar.DAY_OF_MONTH)+"."+cal.get(Calendar.MONTH)+"."+cal.get(Calendar.YEAR);
        return date;
    }

    private String timeString(Timestamp timestamp){
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp.getTime());
        String time = cal.get(Calendar.HOUR)+"."+cal.get(Calendar.MINUTE);
        return time;
    }

}
