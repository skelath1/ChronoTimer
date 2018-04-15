package GUI;

import TimingSystem.ChronoTimer;
import TimingSystem.Simulation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    private JButton button8;
    private JButton a8Button;
    private JButton a9Button;
    private JButton a0Button;
    private JTextPane runningPane;
    private JTextPane queuePane;
    private JTextPane finishedPane;
    private JTextPane printerPane;
    private JPanel NumPadPanel;
    private JButton button12;
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
    private static ChronoTimer chronoTimer;

    private static enum Function {
        TIME("TIME"),
        EVENT("EVENT"),
        NEWRUN("NEWRUN"),
        ENDRUN("ENDRUN"),
        PRINT("PRINT"),
        EXPORT("EXPORT"),
        NUM("NUM"),
        CLR("CLR"),
        DNF("DNF"),
        START("START"),
        FINISH("FINISH");

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
    }

    private enum Event{
        IND("IND"),
        PARIND("PARIN"),
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

    private Function cur;
    private Event curE;


    public ChronoGUI() {

        //need to instantiate chronotimer here so that runnable can use it
        chronoTimer = new ChronoTimer();

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
            }catch(InterruptedException ex){
                System.out.println("done updating");
            }
        };
        Thread running = new Thread(updateRunning);
        running.start();


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
            }catch(InterruptedException ex){
                System.out.println("done updating");
            }
        };
        Thread queue = new Thread(updateQueue);
        queue.start();


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
            }catch(InterruptedException ex){
                System.out.println("done updating");
            }
        };
        Thread finishTime= new Thread(updateFinishedTime);
        finishTime.start();




        cur = Function.NEWRUN;
        curE = Event.IND;
        valuePane.setText("");

        powerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                chronoTimer.execute("POWER",null);
                commandPane.setText(cur.getValue());

            }
        });

        SWAPButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chronoTimer.execute("SWAP",null);
            }
        });
        trigButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chronoTimer.execute("TRIG","1");
            }
        });
        chanButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chronoTimer.execute("TOG","1");
            }
        });
        trigButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chronoTimer.execute("TRIG","2");
            }
        });
        chanButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chronoTimer.execute("TOG","2");
            }
        });
        trigButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chronoTimer.execute("TRIG","3");
            }
        });
        chanButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chronoTimer.execute("TOG","3");
            }
        });
        trigButton4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chronoTimer.execute("TRIG","4");
            }
        });
        chanButton4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chronoTimer.execute("TOG","4");
            }
        });
        trigButton5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chronoTimer.execute("TRIG","5");
            }
        });
        chanButton5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chronoTimer.execute("TOG","5");
            }
        });
        trigButton6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chronoTimer.execute("TRIG","6");
            }
        });
        chanButton6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chronoTimer.execute("TOG","6");
            }
        });
        trigButton7.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chronoTimer.execute("TRIG","7");
            }
        });
        chanButton7.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chronoTimer.execute("TOG","7");
            }
        });
        trigButton8.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chronoTimer.execute("TRIG","8");
            }
        });
        chanButton8.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chronoTimer.execute("TOG","8");
            }
        });
        Backside.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BackSide.show();
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
        button8.addActionListener(listener);
        button12.addActionListener(listener);
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //"" != null so the value needs to be set to null
                String value = valuePane.getText();
                if(value.equals(""))
                    value = null;
                chronoTimer.execute(commandPane.getText(), value);

                if (commandPane.getText().equalsIgnoreCase("PRINT")) {
                    printerPane.setText(chronoTimer.getResults());
                }

                commandPane.setText("");
                valuePane.setText("");
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("ChronoGoo");
        frame.setContentPane(new ChronoGUI().panel1);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(1260, 768));
        frame.setLocationRelativeTo(null);
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