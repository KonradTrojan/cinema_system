package com.access.admin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditRoom extends JFrame{
    private JPanel mainEditRoomJP;
    private JButton confButt;
    private JButton anulujButton;
    private JTextField idRoomJTXT;
    private JTextField numRowsJTXT;
    private JTextField numSeatJTXT;
    private int idSelectedRoom;
    private static final int DEFAULT_WIDTH = 320;
    private static final int DEFAULT_HEIGHT = 180;

    public EditRoom(Integer idRoom, AdminInterface adminInterface){
        this.idSelectedRoom = idRoom;

        ToolsGUI.setSizeJFrame(EditRoom.this,mainEditRoomJP,DEFAULT_WIDTH,DEFAULT_HEIGHT,"Edycja sal");

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
                    Showings.editRoomId(idSelectedRoom,newIdRoom);
                    adminInterface.refresh();
                    JOptionPane.showMessageDialog(new JFrame(), "Zmiana została zapisana pomyślnie w bazie danych", "Komuniakt",
                            JOptionPane.INFORMATION_MESSAGE);

                }
            }
        });
    }

    public static void main(String[] args) {

            String subject[] = { "Math", " English", "SQL", "   java", "  c ", " c++ ",
                    " cobol ", "this is a test" };
            JFrame f = new JFrame();
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            JList<String> list = new JList<String>(subject);
            JScrollPane s = new JScrollPane(list);
            s.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            s.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

            f.getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);
            f.add(s);
            f.setSize(300, 300);
            f.setVisible(true);
    }
}
