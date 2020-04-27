package com.access.admin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import static com.access.admin.AdminInterface.okcancel;

public class DeleteShowing extends JFrame {
    private JButton cancButt;
    private JComboBox selTitleCB;
    private JComboBox selRoomCB;
    private JComboBox selDayCB;
    private JComboBox selHourCB;

    private ArrayList<Integer> idShowings = new ArrayList<>();
    private ArrayList<Integer> selectedIdShowings = new ArrayList<>();
    private JPanel mainDeleteShowing;
    private JButton deleteButt;
    private Calendar cal = Calendar.getInstance();

    public DeleteShowing() {

        ToolsGUI.setSizeJFrame(DeleteShowing.this,mainDeleteShowing,400,300,"Usuwanie seansów");

        loadTitleAndRoomCB();
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
        deleteButt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i = okcancel("Czy na pewno chcesz usunąć seans z bazy danych? ");
                if(i == 0) {
                    int selectedIndex = selDayCB.getSelectedIndex();
                    int idShowToDelete = selectedIdShowings.get(selectedIndex);
                    refreshSelHourCB();
                    refreshSelDayCB();
                    try {
                        Showings.deleteShowing(idShowToDelete);
                        Bookings.deleteByIdShow(idShowToDelete);
                        JOptionPane.showMessageDialog(new JFrame(), "Seans został usunięty z bazy danych", "Komuniakt",
                                JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(new JFrame(), "Wystąpił błąd podaczas usuwania", "Komuniakt",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    }
    private void refreshSelDayCB(){
        ToolsGUI.refreshSelDayCB(selDayCB,selTitleCB,selRoomCB,idShowings);
    }
    private void refreshSelHourCB(){
        ToolsGUI.refreshSelHourCB(selDayCB,selTitleCB,selRoomCB,selHourCB,selectedIdShowings);
    }
    private void loadTitleAndRoomCB(){
        for (String movie : Movies.getTitles()) {
            selTitleCB.addItem(movie);
        }
        for (Integer idRoom : Rooms.getAllRooms()) {
            selRoomCB.addItem(idRoom);
        }
    }
}
