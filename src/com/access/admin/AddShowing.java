package com.access.admin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

public class AddShowing extends JFrame{

    private static final int DEFAULT_WIDTH = 805;
    private static final int DEFAULT_HEIGHT = 500;

    Toolkit kit = Toolkit.getDefaultToolkit();
    Dimension screenSize = kit.getScreenSize();
    int screenWidth = screenSize.width;
    int screenHeight = screenSize.height;

    int latestBooking = 14; // number of days from today, in which one can make booking
    private JPanel mainAddShow;
    private JButton addShowButt;
    private JButton CancButt;
    private JButton clearAllButt;
    private JComboBox selDayCB;
    private JComboBox selFormSoundCB;
    private JComboBox selTitleCB;
    private JComboBox selRoomCB;
    private JComboBox selHourCB;
    private JComboBox selMinutCB;
    private JComboBox selMonthCB;
    private JComboBox selYearCB;
    private JComboBox selFormDimCB;

    public AddShowing(){
        setContentPane(mainAddShow);

        setLocation(screenWidth/2 - DEFAULT_WIDTH/2 ,
                screenHeight/2 - DEFAULT_HEIGHT/2);

        setResizable(false);
        setTitle("Dodawanie seansu");

        pack();
        setMinimumSize(new Dimension(DEFAULT_WIDTH,DEFAULT_HEIGHT));

        loadData();

        CancButt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        selYearCB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refresh();
            }
        });
        selMonthCB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refresh();
            }
        });
        addShowButt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex;
                String title;
                int idroom;
                String formatSound;
                String formatDim;
                int day;
                int month;
                int year;
                int hour;
                int minut;

                selectedIndex = selTitleCB.getSelectedIndex();
                title = selTitleCB.getItemAt(selectedIndex).toString();
                int idMovie = Movies.getId(title);

                selectedIndex = selRoomCB.getSelectedIndex();
                idroom = (int)selRoomCB.getItemAt(selectedIndex);

                selectedIndex = selFormSoundCB.getSelectedIndex();
                formatSound = selFormSoundCB.getItemAt(selectedIndex).toString();

                selectedIndex = selFormDimCB.getSelectedIndex();
                formatDim = selFormDimCB.getItemAt(selectedIndex).toString();

                String format = formatDim + ", " + formatSound;

                selectedIndex = selDayCB.getSelectedIndex();
                day = (int) selDayCB.getItemAt(selectedIndex);

                selectedIndex = selMonthCB.getSelectedIndex();
                month = (int) selMonthCB.getItemAt(selectedIndex);

                selectedIndex = selYearCB.getSelectedIndex();
                year = (int) selYearCB.getItemAt(selectedIndex);

                selectedIndex = selHourCB.getSelectedIndex();
                hour = (int) selHourCB.getItemAt(selectedIndex);

                selectedIndex = selMinutCB.getSelectedIndex();
                minut = (int) selMinutCB.getItemAt(selectedIndex);


                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,day);

                calendar.set(Calendar.HOUR,hour);
                calendar.set(Calendar.MONTH,minut);

                Showings.addShowing(idMovie,idroom,format,calendar.get(Calendar.DAY_OF_WEEK),calendar);

            }
        });
    }

    public void loadData(){
        for (String title : Movies.getTitles())
            selTitleCB.addItem(title);

        for (Integer idRoom : Rooms.getAllRooms())
            selRoomCB.addItem(idRoom);

        for (int i = 0; i < 24; i++){
            selHourCB.addItem(i+1);
        }
        for (int i = 0; i < 12; i++){
            selMinutCB.addItem(i*5);
        }
        selFormSoundCB.addItem("Napisy");
        selFormSoundCB.addItem("Dubbing");
        selFormSoundCB.addItem("Dzwięk Oryginalny");

        selFormDimCB.addItem("2D");
        selFormDimCB.addItem("3D");

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Poland"));

        selYearCB.addItem(calendar.get(Calendar.YEAR));
        calendar.add(Calendar.DATE,+31);
        if ((int)selYearCB.getItemAt(0) != calendar.get(Calendar.YEAR))
            selYearCB.addItem(calendar.get(Calendar.YEAR));

        calendar = Calendar.getInstance(TimeZone.getTimeZone("Poland"));
        calendar.add(Calendar.DATE,+1);

        selMonthCB.addItem(calendar.get(Calendar.MONTH)+1);
        calendar.add(Calendar.DATE,+16);
        if(calendar.get(Calendar.MONTH) != (int)selMonthCB.getItemAt(0)){
            selMonthCB.addItem(calendar.get(Calendar.MONTH)+1);
            calendar.add(Calendar.DATE,+16);
            if(calendar.get(Calendar.MONTH) != (int)selMonthCB.getItemAt(1)){
                selMonthCB.addItem(calendar.get(Calendar.MONTH)+1);
            }
        }else{
            calendar.add(Calendar.DATE,+16);
            selMonthCB.addItem(calendar.get(Calendar.MONTH)+1);
        }
        refresh();

    }

    public ArrayList<Integer> numberOfDaysInComBox(){
        ArrayList<Integer> daysOfMonth = new ArrayList<>();
        int selectedIndex = selMonthCB.getSelectedIndex();
        if((int)selMonthCB.getItemAt(selectedIndex) == 1 || (int)selMonthCB.getItemAt(selectedIndex) == 3 ||
                (int)selMonthCB.getItemAt(selectedIndex) == 5 || (int)selMonthCB.getItemAt(selectedIndex) == 7 ||
                (int)selMonthCB.getItemAt(selectedIndex) == 8 || (int)selMonthCB.getItemAt(selectedIndex) == 10 ||
                (int)selMonthCB.getItemAt(selectedIndex) == 12){
            for (int i = 1; i < 32;i++){
                daysOfMonth.add(i);
            }
        }else if((int)selMonthCB.getItemAt(selectedIndex) == 2 ){
            for (int i = 1; i < 29;i++){
                daysOfMonth.add(i);
            }
        }else {
            for (int i = 1; i < 31;i++){
                daysOfMonth.add(i);
            }
        }
        return daysOfMonth;
    }

    public void refresh(){
        selDayCB.removeAllItems();
        for (int day: numberOfDaysInComBox())
            selDayCB.addItem(day);
    }

}
