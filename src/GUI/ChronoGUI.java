package GUI;

import TimingSystem.ChronoTimer;
import TimingSystem.Simulation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ConcurrentModificationException;

public class ChronoGUI {
    private JPanel panel1;
    private JButton powerButton;
    private JButton printerPowerButton;
    private JPanel LeftPanel;
    private JPanel RightPanel;
    private JPanel CenterPanel;
    private JButton functionButton;
    private JButton SWAPButton;
    private JRadioButton chanButton1;
    private JRadioButton chanButton3;
    private JRadioButton chanButton5;
    private JRadioButton chanButton7;
    private JRadioButton chanButton2;
    private JRadioButton chanButton4;
    private JRadioButton chanButton6;
    private JRadioButton chanButton8;
    private JEditorPane editorPane1;
    private JEditorPane editorPane2;
    private JButton a2Button;
    private JButton a1Button;
    private JButton a3Button;
    private JButton a4Button;
    private JButton a5Button;
    private JButton a6Button;
    private JButton a7Button;
    private JButton starButton;
    private JButton a8Button;
    private JButton a9Button;
    private JButton a0Button;
    private JTextPane runningPane;
    private JTextPane queuePane;
    private JTextPane finishedPane;
    private JScrollPane printerScrollPane;
    private JPanel NumPadPanel;
    private JButton poundButton;
    private JButton trigButton3;
    private JButton trigButton5;
    private JButton commandButtonLeft;
    private JButton commandButtonRight;
    private JButton valueButtonUp;
    private JButton valueButtonDown;
    private JButton trigButton1;
    private JButton trigButton2;
    private JButton trigButton4;
    private JButton trigButton6;
    private JButton trigButton7;
    private JButton trigButton8;
    private JTextPane commandPane;
    private JButton Backside;
    private JTextPane valuePane;
    private JButton submitButton;
    private JTextPane printPane;
    private JScrollPane queueScrollPane;
    private JScrollPane runningScrollPane;
    private JScrollPane finishedScrollPane;
    private JTextPane prevCommandPane;
    private JTextPane prevValuePane;
    private JLabel commandLabel;
    private JLabel valueLabel;
    private JLabel prevCommandLabel;
    private JLabel prevValueLabel;
    private static ChronoTimer chronoTimer;
    private boolean printOn;
    private Color offColor;
    private Color onColor;
    private static BackSide bs;
    private Function cur;
    private Event curE;


    private enum Function {

        //TIME("TIME"),
        EVENT("EVENT"),
        NEWRUN("NEWRUN"),
        ENDRUN("ENDRUN"),
        PRINT("PRINT"),
        EXPORT("EXPORT"),
        NUM("NUM"),
        CLR("CLR"),
        DNF("DNF"),
        START("START"),
        FINISH("FINISH"),
        CANCEL("CANCEL");

        private String value;
        private static Function[] values = values();
        private static int curr = 2;

         Function(String value){
            this.value = value;
        }

        public String getValue(){
            return value;
        }

        public Function next(){
            ++curr;
            int nextInd = Math.abs((curr)%values.length);
            return values[nextInd];
        }

        public Function prev(){
            --curr;
            int nextInd = Math.abs((curr)%values.length);
            return values[nextInd];
        }

        public void reset(){
             curr = 2;
        }
    }

    private enum Event{
        IND("IND"),
        PARIND("PARIND"),
        GRP("GRP"),
        PARGRP("PARGRP");

        private String value;
        private static Event[] values = values();
        private static int curr = 0;

        private Event(String value){
            this.value = value;
        }

        public String getValue(){
            return value;
        }

        public Event next(){
            ++curr;
            int nextInd = Math.abs((curr)%values.length);
            return values[nextInd];
        }

        public Event prev(){
            --curr;
            int nextInd = Math.abs((curr)%values.length);
            return values[nextInd];
        }
    }

    public ChronoGUI() {
        offColor = Color.decode("#d63535");
        onColor = Color.decode("#3bed3e");

        //printer is off by default
        printOn = false;

        chronoTimer = new ChronoTimer();
        bs = new BackSide(chronoTimer);
        bs.setPane(bs);




        Runnable updateRunning =()->{
            try{
                //run forever till interrupted
                while(true) {
                    String data = chronoTimer.getElapsedTime();
                    if(data != null) {
                        runningPane.setText(data);
                    }
                    Thread.sleep(1);
                }
            }catch(InterruptedException  | ConcurrentModificationException cme){
                JOptionPane.showMessageDialog(null,"Slow Down","Slow Down",JOptionPane.ERROR_MESSAGE);
                Simulation.execute("ERROR", "Going to fast caused CME");

            }
        };
        Thread running = new Thread(updateRunning);

        Runnable updateQueue =()->{
            try{
                //run forever till interrupted
                while(true) {
                    String data = chronoTimer.getQueue();
                    if(data != null) {
                        queuePane.setText(data);
                    }
                    Thread.sleep(1);
                }
            }catch(InterruptedException  | ConcurrentModificationException cme){
                JOptionPane.showMessageDialog(null,"Slow Down","Slow Down",JOptionPane.ERROR_MESSAGE);
               Simulation.execute("ERROR", "Going to fast caused CME");
            }
        };
        Thread queue = new Thread(updateQueue);



        Runnable updateFinishedTime =()->{
            try{
                //run forever till interrupted
                while(true) {

                    String data = chronoTimer.getFinishedTime();
                    if(data != null) {
                        finishedPane.setText(data);
                    }
                    Thread.sleep(1);
                }
            }catch(InterruptedException | ConcurrentModificationException cme){
                JOptionPane.showMessageDialog(null,"Slow Down","Slow Down",JOptionPane.ERROR_MESSAGE);
                Simulation.execute("ERROR", "Going to fast caused CME");
            }
        };
        Thread finishTime= new Thread(updateFinishedTime);


        running.start();
        queue.start();
        finishTime.start();




        cur = Function.NEWRUN;
        curE = Event.IND;
        valuePane.setText("");
        queueScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        runningScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        finishedScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        printerScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        queueScrollPane.setPreferredSize(queuePane.getPreferredSize());
        runningScrollPane.setPreferredSize(runningPane.getPreferredSize());
        finishedScrollPane.setPreferredSize(finishedPane.getPreferredSize());
        printerScrollPane.setPreferredSize(printPane.getPreferredSize());

        powerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                chronoTimer.execute("POWER",null,null);
                if(chronoTimer.getState() == "ON") {
                    commandPane.setText(cur.getValue());
                    powerButton.setBackground(onColor);
                }
                else {
                    powerButton.setBackground(offColor);
                    commandPane.setText("");
                    valuePane.setText("");
                    finishedPane.setText("");
                    queuePane.setText("");
                    runningPane.setText("");
                    chanButton1.setSelected(false);
                    chanButton2.setSelected(false);
                    chanButton3.setSelected(false);
                    chanButton4.setSelected(false);
                    chanButton5.setSelected(false);
                    chanButton6.setSelected(false);
                    chanButton7.setSelected(false);
                    chanButton8.setSelected(false);
                    cur = Function.NEWRUN;

                }
            }
        });

        SWAPButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chronoTimer.execute("SWAP",null,null);
            }
        });
        trigButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chronoTimer.execute("TRIG","1",null);
            }
        });
        chanButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chronoTimer.execute("TOG","1",null);
            }
        });
        trigButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chronoTimer.execute("TRIG","2",null);
            }
        });
        chanButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chronoTimer.execute("TOG","2",null);
            }
        });
        trigButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chronoTimer.execute("TRIG","3",null);
            }
        });
        chanButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chronoTimer.execute("TOG","3",null);
            }
        });
        trigButton4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chronoTimer.execute("TRIG","4",null);
            }
        });
        chanButton4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chronoTimer.execute("TOG","4",null);
            }
        });
        trigButton5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chronoTimer.execute("TRIG","5",null);
            }
        });
        chanButton5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chronoTimer.execute("TOG","5",null);
            }
        });
        trigButton6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chronoTimer.execute("TRIG","6",null);
            }
        });
        chanButton6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chronoTimer.execute("TOG","6",null);
            }
        });
        trigButton7.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chronoTimer.execute("TRIG","7",null);
            }
        });
        chanButton7.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chronoTimer.execute("TOG","7",null);
            }
        });
        trigButton8.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chronoTimer.execute("TRIG","8",null);
            }
        });
        chanButton8.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chronoTimer.execute("TOG","8",null);
            }
        });
        Backside.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bs.show();
            }
        });
        commandButtonRight.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!chronoTimer.getState().equalsIgnoreCase("OFF")) {
                    cur = cur.next();
                    if (cur == Function.EVENT) {
                        valuePane.setText(curE.getValue());
                        commandPane.setText(cur.getValue());

                    } else {
                        commandPane.setText(cur.getValue());
                        if(valuePane.getText() != null)
                            valuePane.setText("");
                    }
                }
            }
        });
        commandButtonLeft.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!chronoTimer.getState().equalsIgnoreCase("OFF")) {
                    cur = cur.prev();
                    if (cur == Function.EVENT) {
                        valuePane.setText(curE.getValue());
                        commandPane.setText(cur.getValue());

                    } else {
                        commandPane.setText(cur.getValue());
                        if(valuePane.getText() != null)
                            valuePane.setText("");
                    }
                }
            }
        });
        valueButtonUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(cur == Function.EVENT && !chronoTimer.getState().equalsIgnoreCase("OFF")) {
                    curE = curE.next();
                    valuePane.setText(curE.getValue());
                }
            }
        });
        valueButtonDown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(cur == Function.EVENT && !chronoTimer.getState().equalsIgnoreCase("OFF")) {
                    curE = curE.prev();
                    valuePane.setText(curE.getValue());
                }
            }
        });
        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!chronoTimer.getState().equalsIgnoreCase("OFF") && validforNum()) {
                    JButton b = (JButton) e.getSource();
                        valuePane.setText(valuePane.getText() + b.getActionCommand());
                }
            }
        };
        a3Button.addActionListener(listener);
        a2Button.addActionListener(listener);
        a1Button.addActionListener(listener);
        a4Button.addActionListener(listener);
        a5Button.addActionListener(listener);
        a6Button.addActionListener(listener);
        a7Button.addActionListener(listener);
        a8Button.addActionListener(listener);
        a0Button.addActionListener(listener);
        a9Button.addActionListener(listener);
        starButton.addActionListener(listener);
        poundButton.addActionListener(listener);
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //"" != null so the value needs to be set to null
                String value = valuePane.getText();
                if(value.equals(""))
                    value = null;
                chronoTimer.execute(commandPane.getText(), value,null);

                //updating the printer pane
                if (commandPane.getText().equalsIgnoreCase("PRINT")) {
                    if (value == null) {
                        if (printOn) {
                            printPane.setText(printPane.getText() + "\n\n" + chronoTimer.getResults());
                        }
                    }
                    //need to check if there is a value associated with the command
                    else
                        printPane.setText(printPane.getText() + "\n\n" + chronoTimer.getResults(value));
                }


                //setting the prev command and value pane from the current ones
                prevCommandPane.setText(commandPane.getText());
                prevValuePane.setText(valuePane.getText());

                commandPane.setText(cur.getValue());
                if(cur == Function.NUM || cur == Function.CLR || cur == Function.CANCEL || cur == Function.DNF)
                    valuePane.setText("");
                else
                    valuePane.setText(value);


            }
        });
        printerPowerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(printOn) {
                    printOn = false;
                    printerPowerButton.setBackground(offColor);
                    printPane.setText("");
                }
                else {
                    printOn = true;
                    printerPowerButton.setBackground(onColor);
                }
            }
        });
    }
    public static void main(String[] args) {

        JFrame frame = new JFrame("ChronoTimer 1009");
        frame.setContentPane(new ChronoGUI().panel1);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(1260, 768));
        frame.setLocation(new Point(0,0));
        ImageIcon image = new ImageIcon("icons/logo.png");
        frame.setIconImage(image.getImage());

        frame.pack();
        frame.setVisible(true);

        Simulation sim = new Simulation(chronoTimer);
        sim.doInput();
    }


    private boolean clearForInput(String function){
        return function.equalsIgnoreCase("Event");
    }

    private boolean validforNum(){
        return cur != Function.EVENT && cur != Function.NEWRUN && cur != Function.ENDRUN && cur != Function.START && cur != Function.FINISH && cur != Function.DNF;
    }
}