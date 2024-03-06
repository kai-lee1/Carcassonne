package carcassonne;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Input {
    public JFrame inpf;
    public JTextField coordx;
    public JTextField coordy;
    public JTextField rots;
    public JTextField meeple;
    private JLabel coordxloc;
    private JLabel coordyloc;
    private JLabel rotsloc;
    private JLabel meepleloc;
    private JPanel mainPanel;
    private JButton confirmbutton;
    private JButton endgamebutton;
    public JLabel error;

    public Input() {
        inpf = new JFrame();
        inpf.setSize(400, 350);
        inpf.setTitle("Input");
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        coordx = new JTextField(300);
        coordy = new JTextField(300);
        rots = new JTextField(300);
        meeple = new JTextField(300);
        coordxloc = new JLabel("X Coordinate:");
        coordyloc = new JLabel("Y Coordinate:");
        rotsloc = new JLabel("Rotations (Clockwise):");
        meepleloc = new JLabel("Meeple:");
        confirmbutton = new JButton("Confirm");
        confirmbutton.addActionListener(new ButtonPress());
        endgamebutton = new JButton("end the game");
        endgamebutton.addActionListener(new EndGamePress());
        error = new JLabel("");
        error.setForeground(new Color(255, 0, 0));
        mainPanel.add(coordxloc);
        mainPanel.add(coordx);
        mainPanel.add(coordyloc);
        mainPanel.add(coordy);
        mainPanel.add(rotsloc);
        mainPanel.add(rots);
        mainPanel.add(meepleloc);
        mainPanel.add(meeple);
        mainPanel.add(confirmbutton);
        mainPanel.add(endgamebutton);
        mainPanel.add(error);
    }

    public void draw() {
        inpf.getContentPane().removeAll();
        inpf.getContentPane().add((Component) mainPanel);
        inpf.repaint();
        inpf.setVisible(true);
    }
}

class ButtonPress implements ActionListener {
    public void actionPerformed(ActionEvent evt) {
        CarcassonneMain.ready = true;
    }
}

class EndGamePress implements ActionListener {
    public void actionPerformed(ActionEvent evt) {
        CarcassonneMain.end = true;
    }
}