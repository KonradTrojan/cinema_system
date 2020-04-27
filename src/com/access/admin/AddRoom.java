package com.access.admin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddRoom extends JFrame{
    private JPanel mainAddRoomJP;
    private JButton addRoomButt;
    private JButton cancButt;
    private JButton clearButt;
    private JTextField idRoomJTXT;
    private JTextField numRowsJTXT;
    private JTextField numSeatsJTXT;
    private static final int DEFAULT_WIDTH = 320;
    private static final int DEFAULT_HEIGHT = 180;

    public AddRoom() {
        ToolsGUI.setSizeJFrame(AddRoom.this,mainAddRoomJP,DEFAULT_WIDTH,DEFAULT_HEIGHT,"Dodawanie sal");

        cancButt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        addRoomButt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean helperAllIsInt;
                boolean helperThisRoomExist = true;
                try {
                    Double.parseDouble(idRoomJTXT.getText());
                    Double.parseDouble(numRowsJTXT.getText());
                    Double.parseDouble(numSeatsJTXT.getText());
                    helperAllIsInt = true;
                } catch (NumberFormatException nfe) {
                    helperAllIsInt = false;
                }
                int newIdRoom = Integer.parseInt(idRoomJTXT.getText());

                for (int id : Rooms.getAllRooms()) {
                    if (id == newIdRoom)
                        helperThisRoomExist = false;
                }

                if (!helperAllIsInt) {
                    JOptionPane.showMessageDialog(new JFrame(), "Podane liczby muszą być całkowite", "Błąd",
                            JOptionPane.ERROR_MESSAGE);
                } else if (!helperThisRoomExist) {
                    JOptionPane.showMessageDialog(new JFrame(), "Istnieje sala o tym numerze.", "Błąd",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    Rooms.addRoom(newIdRoom, Integer.parseInt(numRowsJTXT.getText()), Integer.parseInt(numSeatsJTXT.getText()));
                    JOptionPane.showMessageDialog(new JFrame(), "Sala została dodana pomyślnie do bazy danych", "Komuniakt",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        clearButt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                idRoomJTXT.setText("");
                numRowsJTXT.setText("");
                numSeatsJTXT.setText("");
            }
        });
    }
}
