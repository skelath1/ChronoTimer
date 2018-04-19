package GUI;

import TimingSystem.ChronoTimer;
import TimingSystem.Hardware.ElectricEye;
import TimingSystem.Simulation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class BackSide {
    private JPanel main;
    private JButton usbButton;
    private JComboBox comboBox1;
    private JComboBox comboBox3;
    private JComboBox comboBox5;
    private JComboBox comboBox7;
    private JComboBox comboBox6;
    private JComboBox comboBox4;
    private JComboBox comboBox2;
    private JComboBox comboBox8;
    private static ChronoTimer chronoTimer;

    public BackSide(ChronoTimer ch) {

        chronoTimer = ch;

        usbButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chronoTimer.execute("export", null, null);
            }
        });
        comboBox1.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    Object item = e.getItem();
                    String sensor = item.toString();
                    System.out.println("sensor name = " + sensor);
                    String channelNum = "1";
                    if(sensor.equals("Electric Eye")){
                        chronoTimer.execute("conn", "electriceye", channelNum);
                    }
                    if(sensor.equals("Gate")){
                        chronoTimer.execute("conn", "gate", channelNum);
                    }
                    if(sensor.equals("Pad")){
                        chronoTimer.execute("conn", "pad", channelNum);
                    }
                    if(sensor.equals("Push Button")){
                        chronoTimer.execute("conn", "pushbutton", channelNum);
                    }
                }
            }
        });
        comboBox2.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    Object item = e.getItem();
                    String sensor = item.toString();
                    String channelNum = "2";
                    if(sensor.equals("Electric Eye")){
                        chronoTimer.execute("conn", "electriceye", channelNum);
                    }
                    if(sensor.equals("Gate")){
                        chronoTimer.execute("conn", "gate", channelNum);
                    }
                    if(sensor.equals("Pad")){
                        chronoTimer.execute("conn", "pad", channelNum);
                    }
                    if(sensor.equals("Push Button")){
                        chronoTimer.execute("conn", "pushbutton", channelNum);
                    }
                }
            }
        });
        comboBox3.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    Object item = e.getItem();
                    String sensor = item.toString();
                    String channelNum = "3";
                    if(sensor.equals("Electric Eye")){
                        chronoTimer.execute("conn", "electriceye", channelNum);
                    }
                    if(sensor.equals("Gate")){
                        chronoTimer.execute("conn", "gate", channelNum);
                    }
                    if(sensor.equals("Pad")){
                        chronoTimer.execute("conn", "pad", channelNum);
                    }
                    if(sensor.equals("Push Button")){
                        chronoTimer.execute("conn", "pushbutton", channelNum);
                    }
                }
            }
        });
        comboBox4.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    Object item = e.getItem();
                    String sensor = item.toString();
                    String channelNum = "4";
                    if(sensor.equals("Electric Eye")){
                        chronoTimer.execute("conn", "electriceye", channelNum);
                    }
                    if(sensor.equals("Gate")){
                        chronoTimer.execute("conn", "gate", channelNum);
                    }
                    if(sensor.equals("Pad")){
                        chronoTimer.execute("conn", "pad", channelNum);
                    }
                    if(sensor.equals("Push Button")){
                        chronoTimer.execute("conn", "pushbutton", channelNum);
                    }
                }
            }
        });
        comboBox5.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    Object item = e.getItem();
                    String sensor = item.toString();
                    String channelNum = "5";
                    if(sensor.equals("Electric Eye")){
                        chronoTimer.execute("conn", "electriceye", channelNum);
                    }
                    if(sensor.equals("Gate")){
                        chronoTimer.execute("conn", "gate", channelNum);
                    }
                    if(sensor.equals("Pad")){
                        chronoTimer.execute("conn", "pad", channelNum);
                    }
                    if(sensor.equals("Push Button")){
                        chronoTimer.execute("conn", "pushbutton", channelNum);
                    }
                }
            }
        });
        comboBox6.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    Object item = e.getItem();
                    String sensor = item.toString();
                    String channelNum = "6";
                    if(sensor.equals("Electric Eye")){
                        chronoTimer.execute("conn", "electriceye", channelNum);
                    }
                    if(sensor.equals("Gate")){
                        chronoTimer.execute("conn", "gate", channelNum);
                    }
                    if(sensor.equals("Pad")){
                        chronoTimer.execute("conn", "pad", channelNum);
                    }
                    if(sensor.equals("Push Button")){
                        chronoTimer.execute("conn", "pushbutton", channelNum);
                    }
                }
            }
        });
        comboBox7.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    Object item = e.getItem();
                    String sensor = item.toString();
                    String channelNum = "7";
                    if(sensor.equals("Electric Eye")){
                        chronoTimer.execute("conn", "electriceye", channelNum);
                    }
                    if(sensor.equals("Gate")){
                        chronoTimer.execute("conn", "gate", channelNum);
                    }
                    if(sensor.equals("Pad")){
                        chronoTimer.execute("conn", "pad", channelNum);
                    }
                    if(sensor.equals("Push Button")){
                        chronoTimer.execute("conn", "pushbutton", channelNum);
                    }
                }
            }
        });
        comboBox8.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    Object item = e.getItem();
                    String sensor = item.toString();
                    String channelNum = "8";
                    if(sensor.equals("Electric Eye")){
                        chronoTimer.execute("conn", "electriceye", channelNum);
                    }
                    if(sensor.equals("Gate")){
                        chronoTimer.execute("conn", "gate", channelNum);
                    }
                    if(sensor.equals("Pad")){
                        chronoTimer.execute("conn", "pad", channelNum);
                    }
                    if(sensor.equals("Push Button")){
                        chronoTimer.execute("conn", "pushbutton", channelNum);
                    }
                }
            }
        });







    }
        public static void show() {
            JFrame frame = new JFrame("Back Side Goo");
            frame.setContentPane(new BackSide(chronoTimer).main);
            frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
            frame.setMinimumSize(new Dimension(500, 300));
//        frame.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
            frame.setLocationRelativeTo(null);
            frame.pack();
            frame.setVisible(true);
        }

        private void comboBoxSetup(ItemEvent e){
            if (e.getStateChange() == ItemEvent.SELECTED) {
                Object item = e.getItem();
                String sensor = item.toString();
                String channelNum = "8";
                if(sensor.equals("Electric Eye")){
                    chronoTimer.execute("conn", "electriceye", channelNum);
                }
                if(sensor.equals("Gate")){
                    chronoTimer.execute("conn", "gate", channelNum);
                }
                if(sensor.equals("Pad")){
                    chronoTimer.execute("conn", "pad", channelNum);
                }
                if(sensor.equals("Push Button")){
                    chronoTimer.execute("conn", "pushbutton", channelNum);
                }
            }
        }



}