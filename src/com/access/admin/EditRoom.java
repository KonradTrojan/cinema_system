package com.access.admin;

import javax.swing.*;
import java.awt.*;

public class EditRoom extends JFrame{

    private static final int DEFAULT_WIDTH = 805;
    private static final int DEFAULT_HEIGHT = 500;

    Toolkit kit = Toolkit.getDefaultToolkit();
    Dimension screenSize = kit.getScreenSize();
    int screenWidth = screenSize.width;
    int screenHeight = screenSize.height;

    private JPanel mainEditRoomJP;
    private JButton confButt;
    private JButton anulujButton;
    private JTextField idRoomJTXT;
    private JTextField numRowsJTXT;
    private JTextField numSeatJTXT;

    public EditRoom(Integer idRoom){
        setContentPane(mainEditRoomJP);

        setLocation(screenWidth/2 - DEFAULT_WIDTH/2 ,
                screenHeight/2 - DEFAULT_HEIGHT/2);

        setResizable(false);
        setTitle("Panel administratora");

        pack();
        setMinimumSize(new Dimension(DEFAULT_WIDTH,DEFAULT_HEIGHT));


        Integer numOfRows = Rooms.getNumOfRows(idRoom);
        Integer numOfSeats = Rooms.getNumOfSeats(idRoom);
        idRoomJTXT.setText(idRoom.toString());
        numRowsJTXT.setText(numOfRows.toString());
        numSeatJTXT.setText(numOfSeats.toString());
    }
}
