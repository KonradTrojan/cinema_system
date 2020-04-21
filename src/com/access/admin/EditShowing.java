package com.access.admin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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
    private ArrayList<String> showings = new ArrayList<>();

    public EditShowing() {
        setContentPane(mainEditJP);

        setLocation(screenWidth/2 - DEFAULT_WIDTH/2 ,
                screenHeight/2 - DEFAULT_HEIGHT/2);
        pack();
        setMinimumSize(new Dimension(DEFAULT_WIDTH,DEFAULT_HEIGHT));

        for (String movie: Movies.getTitles()){
            selTitleCB.addItem(movie);
        }

        for(Integer idRoom : Rooms.getAllRooms()){
            selRoomCB.addItem(idRoom);
        }


        cancButt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        selTitleCB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshComboBox();
            }
        });
        selRoomCB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshComboBox();
            }
        });
    }

    private void refreshComboBox(){

       int selectedIndex = selTitleCB.getSelectedIndex();
       int idMovie = Movies.getId(selTitleCB.getItemAt(selectedIndex).toString());

       selectedIndex = selRoomCB.getSelectedIndex();
       int idRoom = (int) selRoomCB.getItemAt(selectedIndex);

        for(Integer i: Showings.getShowings(idMovie,idRoom)){
            selDayCB.addItem(i);
        }
    }
}
