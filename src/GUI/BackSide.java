package GUI;

import TimingSystem.ChronoTimer;
import TimingSystem.Simulation;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BackSide {
    private JPanel main;
    private JButton usbButton;
    private JComboBox comboBox1;
    private JComboBox comboBox3;
    private JComboBox comboBox5;
    private JComboBox comboBox77;
    private JComboBox comboBox6;
    private JComboBox comboBox4;
    private JComboBox comboBox2;
    private JComboBox comboBox8;
    private static ChronoTimer chronoTimer; //need to instantiate chronoTimer or should it be passed in from ChronoGUI?

    public BackSide() {

        //need to instantiate chronoTimer or should it be passed in from ChronoGUI?

        chronoTimer = new ChronoTimer();
        usbButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            chronoTimer.execute("export",null,null);
            }
        });
    }

    public static void show(){
        JFrame frame = new JFrame("Back Side Goo");
        frame.setContentPane(new BackSide().main);
        frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        frame.setMinimumSize(new Dimension(500, 300));
//        frame.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
    }
}
