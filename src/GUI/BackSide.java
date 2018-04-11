package GUI;

import javax.swing.*;
import java.awt.*;

public class BackSide {
    private JPanel main;

    public static void show(){
        JFrame frame = new JFrame("Back Side");
        frame.setContentPane(new BackSide().main);
        frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        frame.setMinimumSize(new Dimension(500, 500));
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
    }
}
