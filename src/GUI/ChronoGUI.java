package GUI;

import javax.swing.*;
import java.awt.*;

public class ChronoGUI {
    private JPanel panel1;
    private JButton powerButton;
    private JButton printerPowerButton;
    private JPanel LeftPanel;
    private JPanel RightPanel;
    private JPanel CenterPanel;
    private JButton functionButton;
    private JButton SWAPButton;
    private JRadioButton a1RadioButton;
    private JRadioButton a3RadioButton;
    private JRadioButton a5RadioButton;
    private JRadioButton a7RadioButton;
    private JRadioButton a2RadioButton;
    private JRadioButton a4RadioButton;
    private JRadioButton a6RadioButton;
    private JRadioButton a8RadioButton;
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
    private JButton button12;

    public static void main(String[] args) {
        JFrame frame = new JFrame("ChronoGoo");
        frame.setContentPane(new ChronoGUI().panel1);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(1260, 768));
        frame.pack();
        frame.setVisible(true);
    }

}