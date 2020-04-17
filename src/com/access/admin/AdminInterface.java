package com.access.admin;

import javax.swing.*;
import java.awt.*;

public class AdminInterface extends JFrame {


    private static final int DEFAULT_WIDTH = 1500;
    private static final int DEFAULT_HEIGHT = 800;

    Toolkit kit = Toolkit.getDefaultToolkit();
    Dimension screenSize = kit.getScreenSize();
    int screenWidth = screenSize.width;
    int screenHeight = screenSize.height;

    public AdminInterface() {



        setLocation(screenWidth/2 - DEFAULT_WIDTH/2 ,
                screenHeight/2 - DEFAULT_HEIGHT/2);
        setSize(1500, 800 );
        setResizable(false);

    }

    public static void main(String[] args) {
        // poniższy kod wykonuje instrukcje w wątku dystrubucji zdarzeń
        EventQueue.invokeLater(() ->
        {
            AdminInterface frame = new AdminInterface();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }

}
