package GUI;

import javax.swing.*;
import java.awt.*;

public class BackSide {
    private JPanel main;
    private JRadioButton radioButton1;
    private JRadioButton radioButton3;
    private JRadioButton radioButton5;
    private JRadioButton radioButton7;
    private JRadioButton radioButton2;
    private JRadioButton radioButton4;
    private JRadioButton radioButton6;
    private JRadioButton radioButton8;
    private JButton usbButton;

    public static void show(){
        JFrame frame = new JFrame("Back Side");
        frame.setContentPane(new BackSide().main);
        frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        frame.setMinimumSize(new Dimension(500, 300));
//        frame.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
    }
}
