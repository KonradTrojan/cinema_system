package com.access.admin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminInterface extends JFrame {



    private static final int DEFAULT_WIDTH = 600;
    private static final int DEFAULT_HEIGHT = 300;

    Toolkit kit = Toolkit.getDefaultToolkit();
    Dimension screenSize = kit.getScreenSize();
    int screenWidth = screenSize.width;
    int screenHeight = screenSize.height;
    private JPanel mainLeft;
    private JPanel mainRight;
    private JPanel mainJP;
    private JButton addMovieButt;
    private JButton removeMovieButt;
    private JButton editMovieButt;
    private JComboBox moviesComBox;
    private JButton pokażHarmonogramSeansówButton;
    private JButton usuńSeansButton1;
    private JButton edytujSeansButton;
    private JButton addShowButt;
    private JButton editRoomButt;
    private JButton addlRoomButt;
    private JButton deleteRoomButt;
    private JComboBox roomsComBox;
    private JButton showBookings;
    private JPanel moviesJP;
    private JPanel showJP;
    private JPanel roomJP;
    private JPanel bookJP;
    private JPanel cancJP;
    private JButton cancButt;

    public AdminInterface() {

        ToolsGUI.setSizeJFrame(AdminInterface.this,mainJP,DEFAULT_WIDTH,DEFAULT_HEIGHT,"Panel administratora");
        setInsideSize();
        refresh();

        addMovieButt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddMovie addMovie = new AddMovie(AdminInterface.this);
                setVisible(false);
                addMovie.setVisible(true);
            }
        });
        removeMovieButt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = moviesComBox.getSelectedIndex();
                String selectedTitle = (String) moviesComBox.getItemAt(selectedIndex);
                int i = okcancel("Czy na pewno chcesz usunąć film z bazy danych? ");
                if(i == 0)
                    Movies.deleteMovie(selectedTitle);
                refresh();
            }
        });
        editMovieButt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int selectedIndex = moviesComBox.getSelectedIndex();
                String selectedTitle = (String) moviesComBox.getItemAt(selectedIndex);
                int selectedIdMovie;

                for (Integer id : Movies.getAllMovies()){
                    if (selectedTitle.equals(Movies.getTitle(id))) {
                        selectedIdMovie = id;
                        EditMovie editMovie = new EditMovie(selectedIdMovie,AdminInterface.this);
                        editMovie.setVisible(true);
                    }
                }
            }
        });

        deleteRoomButt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int selectedIndexRoomCB = roomsComBox.getSelectedIndex();
                int selectedRoom = (int) roomsComBox.getItemAt(selectedIndexRoomCB);
                int i = okcancel("Czy na pewno chcesz usunąć salę nr "+ selectedRoom+ " z bazy danych? ");

                if (i == 0) {
                    Rooms.deleteRoom(selectedRoom);
                    refresh();
                }
            }
        });
        editRoomButt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndexRoomCB = roomsComBox.getSelectedIndex();
                int selectedRoom = (int) roomsComBox.getItemAt(selectedIndexRoomCB);

                EditRoom editRoom = new EditRoom(selectedRoom,AdminInterface.this);
                editRoom.setVisible(true);
            }
        });
        addlRoomButt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddRoom addRoom = new AddRoom();
                addRoom.setVisible(true);
            }
        });
        addShowButt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddShowing addShowing = new AddShowing();
                addShowing.setVisible(true);
            }
        });
        usuńSeansButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DeleteShowing deleteShowing = new DeleteShowing();
                deleteShowing.setVisible(true);
            }
        });
        showBookings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ShowBookings showBookings = new ShowBookings();
                showBookings.setVisible(true);
            }
        });
        cancButt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() ->
        {
            AdminInterface frame = new AdminInterface();
            frame.setContentPane(new AdminInterface().mainJP);
            frame.setVisible(true);

        });
    }

    public void refresh(){
        ToolsGUI.fillComboBoxTitle(moviesComBox,roomsComBox);
    }


    private void setInsideSize(){
        ToolsGUI.setJPanel(mainLeft,420,190);
        ToolsGUI.setJPanel(mainRight,100,190);
        ToolsGUI.setJPanel(moviesJP,400,80);
        ToolsGUI.setJPanel(showJP,400,80);
        ToolsGUI.setJPanel(roomJP,100,180);
        ToolsGUI.setJPanel(bookJP,400,30);
        ToolsGUI.setJPanel(cancJP,100,20);
        mainRight.setBackground(Color.white);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        refresh();
    }


    public static int okcancel(String theMessage) {
        int result = JOptionPane.showConfirmDialog((Component) null, theMessage,
                "alert", JOptionPane.OK_CANCEL_OPTION);
        return result;
    }



}
