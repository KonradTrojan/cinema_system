package com.access.admin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

public class AddShowing extends JFrame{

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
    private JComboBox selNumberOfReCB;
    private JComboBox selDayOrWeek;

    private static final long MILISECONDS_IN_DAY = 86400000;
    private static final long MILISECONDS_IN_WEEK = 604800000;

    private static final int DEFAULT_WIDTH = 400;
    private static final int DEFAULT_HEIGHT = 300;

    public AddShowing(){
        ToolsGUI.setSizeJFrame(AddShowing.this,mainAddShow,DEFAULT_WIDTH,DEFAULT_HEIGHT,"Dodawanie filmóe");
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
                try{
                    int selectedIndex = selTitleCB.getSelectedIndex();
                    String title = selTitleCB.getItemAt(selectedIndex).toString();
                    int idMovie = Movies.getId(title);

                    selectedIndex = selFormSoundCB.getSelectedIndex();
                    String formatSound = selFormSoundCB.getItemAt(selectedIndex).toString();

                    selectedIndex = selFormDimCB.getSelectedIndex();
                    String formatDim = selFormDimCB.getItemAt(selectedIndex).toString();

                    String format = formatDim + ", " + formatSound;

                    int idroom = ToolsGUI.getSelectedIndexOfCB(selRoomCB);
                    int day = ToolsGUI.getSelectedIndexOfCB(selDayCB);
                    int month = ToolsGUI.getSelectedIndexOfCB(selMonthCB);
                    int year = ToolsGUI.getSelectedIndexOfCB(selYearCB);
                    int hour = ToolsGUI.getSelectedIndexOfCB(selHourCB);
                    int minut = ToolsGUI.getSelectedIndexOfCB(selMinutCB);

                    //                default value of year in class Timestamp is 1900
                    Timestamp start = new Timestamp(year- 1900,month-1,day,hour,minut,0,0);

                    long lengthOfMovieInMili = Movies.getLengthInt(idMovie)*60*1000;
                    Timestamp end = new Timestamp(start.getTime());
                    end.setTime(end.getTime()+lengthOfMovieInMili);

                    selectedIndex = selNumberOfReCB.getSelectedIndex();
                    if(selectedIndex == 0)
                        Showings.addShowing(idMovie,idroom,format,start,end);
                    else
                        repeatedAdd(idMovie,idroom,format,start,end);

                }catch (Exception ex){
                    JOptionPane.showMessageDialog(new JFrame(), "Wpis nie został dodany do bazy danych.", "Błąd",
                            JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        });
        selDayOrWeek.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadNumberOfDayCB();
            }
        });
    }
    public void loadData(){
        completeCombobox();

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

    public void refresh(){
        selDayCB.removeAllItems();
        for (int day: ToolsGUI.numberOfDaysInComBox(selMonthCB))
            selDayCB.addItem(day);
    }
    private void completeCombobox(){
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

        selDayOrWeek.addItem("dni");
        selDayOrWeek.addItem("tygodni");
        loadNumberOfDayCB();

    }
    private void loadNumberOfDayCB(){
        int selectedIndex = selDayOrWeek.getSelectedIndex();
        String time = selDayOrWeek.getItemAt(selectedIndex).toString();
        selNumberOfReCB.removeAllItems();
        switch (time){
            case "dni":
                for(int i = 0;i<=7;i++)
                    selNumberOfReCB.addItem(i);
                break;
            case "tygodni":
                for(int i = 0;i<=4;i++)
                    selNumberOfReCB.addItem(i);
                break;
            default:
        }
    }

    public void repeatedAdd(int idMovie, int idRoom, String format,Timestamp start, Timestamp end) {
        int selectedIndex = selDayOrWeek.getSelectedIndex();
        String dayOrWeek = selDayOrWeek.getItemAt(selectedIndex).toString();

        selectedIndex = selNumberOfReCB.getSelectedIndex();
        int numberOfRepeats = (int) selNumberOfReCB.getItemAt(selectedIndex);

        long timeInterval;
        if (dayOrWeek.equals("dni"))
            timeInterval = MILISECONDS_IN_DAY;
        else
            timeInterval = MILISECONDS_IN_WEEK;

        for (int i = 0; i < numberOfRepeats; i++) {
            start.setTime(start.getTime() + i * timeInterval);
            end.setTime(end.getTime() + i * timeInterval);
            if (Showings.roomIsFree(idRoom, start, end)) {
                Showings.addShowing(idMovie, idRoom, format, start, end);
            } else {
                JOptionPane.showMessageDialog(new JFrame(), "W sali nr " + idRoom + " w dniu " +
                                start.toString().substring(0, 10) + " jest grany film.", "Komunikat",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
        JOptionPane.showMessageDialog(new JFrame(), "Wpisy pomyślnie dodane do bazy danych.", "Komunikat",
                            JOptionPane.INFORMATION_MESSAGE);
    }
}