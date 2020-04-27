package com.access.admin;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.util.ArrayList;

public class ShowBookings extends JFrame{
    private JPanel mainShow;
    private JComboBox selTitleCB;
    private JComboBox selRoomCB;
    private JButton showBookButt;
    private JButton cancButt;
    private JButton clearListButt;
    private JComboBox selDayCB;
    private JComboBox selHourCB;
    private JButton deleteButt;
    private JButton clearButt;
    private JPanel rightJP;
    private JPanel leftJP;
    private JList bookingJList = new JList();
    private JButton deleteSelectedButt;
    private ArrayList<Integer> idShowings = new ArrayList<>();
    private ArrayList<Integer> selectedIdShowings = new ArrayList<>();
    private DefaultListModel dlm = new DefaultListModel();

    private JScrollPane scrollPane;
    private JPanel buttonsJP;
    private JPanel comboxsJP;

    private static final int DEFAULT_WIDTH = 1150;
    private static final int DEFAULT_HEIGHT = 300;

    public ShowBookings() {
        ToolsGUI.setSizeJFrame(ShowBookings.this,mainShow,DEFAULT_WIDTH,DEFAULT_HEIGHT,"Rezerwacje");
        ToolsGUI.setJPanel(buttonsJP,245,90);
        ToolsGUI.setJPanel(comboxsJP,245,90);
        ToolsGUI.setJPanel(rightJP,745,160);
        ToolsGUI.fillComboBoxTitle(selTitleCB,selRoomCB);
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
                completeList();
            }
        });
        clearButt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearList();
            }
        });
        deleteButt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String record = bookingJList.getSelectedValue().toString();
                    Bookings.delete(getBookingId(record));
                    clearList();
                    completeList();
                }catch (Exception ex){
                    JOptionPane.showMessageDialog(new JFrame(), "Nie zaznaczono żadnej rezerwacji.", "Komunikat",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
    }
    private void refreshSelDayCB(){
        idShowings = ToolsGUI.refreshSelDayCB(selDayCB,selTitleCB,selRoomCB,idShowings);
    }
    private void refreshSelHourCB(){
        selectedIdShowings = ToolsGUI.refreshSelHourCB(selDayCB,selTitleCB,selRoomCB,selHourCB,selectedIdShowings);
    }

    private void completeList(){
        if (selDayCB.getItemCount()!=0 && selHourCB.getItemCount() != 0 &&
                selTitleCB.getItemCount() != 0 && selRoomCB.getItemCount()!=0) {

            int selectedIndex = selHourCB.getSelectedIndex();
            int idShow = selectedIdShowings.get(selectedIndex);


                ArrayList<String> bookings = Bookings.getBookingsToString(idShow);
                Timestamp timestamp = Showings.getDateStart(idShow);
                String date = ", Dzień: " + timestamp.toString().substring(0, 10) +
                        ", Godzina: " + timestamp.toString().substring(11, 16);
                for (String booking : bookings)
                    dlm.addElement(booking + date);

                bookingJList.setModel(dlm);
                bookingJList = new JList(dlm);

                scrollPane.setViewportView(bookingJList);
            }

    }
    private void clearList(){
        dlm = new DefaultListModel();
        dlm.clear();
        bookingJList.setModel(dlm);
    }
    public int getBookingId(String record){
        int i = 1;
        String bookingId;
        do{
            bookingId = record.substring(0,i);
            i++;
        }while (!record.substring(i-1,i).equals(","));
        return Integer.parseInt(bookingId);
    }


}
