package com.access.admin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

    private int idSelectedRoom;

    public EditRoom(Integer idRoom){
        this.idSelectedRoom = idRoom;
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
        anulujButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        confButt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                boolean helperAllIsInt;
                boolean helperThisRoomExist = true;
                try {
                    Double.parseDouble(idRoomJTXT.getText());
                    Double.parseDouble(numRowsJTXT.getText());
                    Double.parseDouble(numSeatJTXT.getText());
                    helperAllIsInt = true;
                } catch (NumberFormatException nfe) {
                    helperAllIsInt = false;
                }
                int newIdRoom = Integer.parseInt(idRoomJTXT.getText());

                for (int id : Rooms.getAllRooms()) {
                    if (id == newIdRoom && newIdRoom != idSelectedRoom)
                        helperThisRoomExist = false;
                }

                if (!helperAllIsInt) {
                    JOptionPane.showMessageDialog(new JFrame(), "Podane liczby muszą być całkowite", "Błąd",
                            JOptionPane.ERROR_MESSAGE);
                } else if (!helperThisRoomExist) {
                    JOptionPane.showMessageDialog(new JFrame(), "Istnieje sala o tym numerze.", "Błąd",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    Rooms.editRoom(idSelectedRoom, newIdRoom, Integer.parseInt(numRowsJTXT.getText()), Integer.parseInt(numSeatJTXT.getText()));
                    JOptionPane.showMessageDialog(new JFrame(), "Zmiana została zapisana pomyślnie w bazie danych", "Komuniakt",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
    }
}