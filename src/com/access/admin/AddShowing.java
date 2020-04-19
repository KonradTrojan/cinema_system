package com.access.admin;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddShowing extends JFrame{
    private JPanel mainAddShow;
    private JButton addShowButt;
    private JButton CancButt;
    private JButton clearAllButt;
    private JComboBox selTimeCB;
    private JComboBox selFormCB;
    private JComboBox selDayCB;
    private JComboBox SelMovieCB;
    private JComboBox selRoomCB;

    public AddShowing(){
        setContentPane(mainAddShow);


        CancButt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
}
