package com.access.admin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminInterface extends JFrame {


    private static final int DEFAULT_WIDTH = 805;
    private static final int DEFAULT_HEIGHT = 500;

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

    public AdminInterface() {

        setLocation(screenWidth/2 - DEFAULT_WIDTH/2 ,
                    screenHeight/2 - DEFAULT_HEIGHT/2);

        setResizable(false);
        setTitle("Panel administratora");

        pack();
        setMinimumSize(new Dimension(DEFAULT_WIDTH,DEFAULT_HEIGHT));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        refresh();


        addMovieButt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddMovie addMovie = new AddMovie();
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
                        EditMovie editMovie = new EditMovie(selectedIdMovie);
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

                EditRoom editRoom = new EditRoom(selectedRoom);
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
    }

    public static void main(String[] args) {
        // poniższy kod wykonuje instrukcje w wątku dystrubucji zdarzeń
        EventQueue.invokeLater(() ->
        {
            AdminInterface frame = new AdminInterface();
            frame.setContentPane(new AdminInterface().mainJP);
            frame.setVisible(true);




        });
    }

    public void refresh(){
        fillComboBoxTitle();
    }

    public void fillComboBoxTitle() {
        moviesComBox.removeAllItems();
        for (String title : Movies.getTitles()){
            moviesComBox.addItem(title);
        }

        roomsComBox.removeAllItems();
        for (Integer idRoom : Rooms.getAllRooms()){
            roomsComBox.addItem(idRoom);
        }
    }



    public static int okcancel(String theMessage) {
        int result = JOptionPane.showConfirmDialog((Component) null, theMessage,
                "alert", JOptionPane.OK_CANCEL_OPTION);
        return result;
    }



}
