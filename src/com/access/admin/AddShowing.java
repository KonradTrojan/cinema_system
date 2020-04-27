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

    //                default value of year in class Timestamp is 1900
                    Timestamp start = new Timestamp(year- 1900,month-1,day,hour,minut,0,0);

                    if(minut + Movies.getLengthInt(idMovie)%60 > 60){
                        hour = hour + Movies.getLengthInt(idMovie)/60 + 1;
                        minut = (minut + Movies.getLengthInt(idMovie)%60)%60;
                    }else{
                        hour = hour  + Movies.getLengthInt(idMovie)/60;
                        minut = minut + Movies.getLengthInt(idMovie)%60;
                    }
                    Timestamp end = new Timestamp(year- 1900,month-1,day,hour,minut,0,0);
                    if(Showings.roomIsFree(idroom,start,end)) {
                        Showings.addShowing(idMovie, idroom, format, start, end);
                        JOptionPane.showMessageDialog(new JFrame(), "Wpis pomyślnie dodany do bazy danych.", "Komunikat",
                                JOptionPane.INFORMATION_MESSAGE);
                    }else {
                        JOptionPane.showMessageDialog(new JFrame(), "W sali nr "+idroom+" w tych godzinach jest grany film.", "Komunikat",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                }catch (Exception ex){
                    JOptionPane.showMessageDialog(new JFrame(), "Wpis nie został dodany do bazy danych.", "Błąd",
                            JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
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
    }




}
