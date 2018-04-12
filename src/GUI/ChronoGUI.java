package GUI;

import TimingSystem.ChronoTimer;
import TimingSystem.Simulation;
import com.sun.org.apache.bcel.internal.generic.NEW;

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
    private JTextPane textPane1;
    private JTextPane queueScreen;
    private JTextPane textPane3;
    private JTextPane textPane4;
    private JPanel NumPadPanel;
    private JButton button12;
    private JButton trigButton3;
    private JButton trigButton5;
    private JButton button2;
    private JButton button4;
    private JButton button5;
    private JButton button6;
    private JButton trigButton1;
    private JButton trigButton2;
    private JButton trigButton4;
    private JButton trigButton6;
    private JButton trigButton7;
    private JButton trigButton8;
    private JTextPane textPane2;
    private JButton Backside;
    private JTextPane textPane5;
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
        cur = Function.NEWRUN;
        curE = Event.IND;
        textPane5.setText("");

        powerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                chronoTimer.execute("POWER",null);
                textPane2.setText(cur.getValue());
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
        button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!chronoTimer.getState().equalsIgnoreCase("OFF")) {
                    cur = cur.next();
                    if (cur == Function.EVENT) {
                        textPane5.setText(curE.getValue());
                        textPane2.setText(cur.getValue());

                    } else {
                        textPane2.setText(cur.getValue());
                        if(textPane5.getText() != null)
                            textPane5.setText("");
                    }
                }
            }
        });
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!chronoTimer.getState().equalsIgnoreCase("OFF")) {
                    cur = cur.prev();
                    if (cur == Function.EVENT) {
                        textPane5.setText(curE.getValue());
                        textPane2.setText(cur.getValue());

                    } else {
                        textPane2.setText(cur.getValue());
                        if(textPane5.getText() != null)
                            textPane5.setText("");
                    }
                }
            }
        });
        button5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(cur == Function.EVENT && !chronoTimer.getState().equalsIgnoreCase("OFF")) {
                    curE = curE.next();
                    textPane5.setText(curE.getValue());
                }
            }
        });
        button6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(cur == Function.EVENT && !chronoTimer.getState().equalsIgnoreCase("OFF")) {
                    curE = curE.prev();
                    textPane5.setText(curE.getValue());
                }
            }
        });
        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!chronoTimer.getState().equalsIgnoreCase("OFF") && validforNum()) {
                    JButton b = (JButton) e.getSource();
                        textPane5.setText(textPane5.getText() + b.getActionCommand());
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
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("ChronoGoo");
        frame.setContentPane(new ChronoGUI().panel1);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(1260, 768));
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);

        chronoTimer = new ChronoTimer();
        Simulation sim = new Simulation(chronoTimer);
        sim.doInput();
    }

    private boolean clearForInput(String function){
        return function.equalsIgnoreCase("Event");
    }

    private boolean validforNum(){
        return cur != Function.NEWRUN && cur != Function.ENDRUN && cur != Function.START && cur != Function.FINISH && cur != Function.DNF;
    }
}