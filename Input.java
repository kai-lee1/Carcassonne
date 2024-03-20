package carcassonne;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Input {
    public static JFrame inpf = new JFrame();
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
    private JLabel tile;
    public JLabel error;
    private JTable score;

    public Input() {
        inpf.getContentPane().removeAll();
        inpf.setSize(400, 500);
        inpf.setTitle("Input");
        score = new JTable(CarcassonneMain.playerCount, 2);
        score.setRowHeight(20);
        score.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
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
        tile = new JLabel(CarcassonneMain.currentTile.type);
        error = new JLabel("");
        error.setForeground(new Color(255, 0, 0));
        mainPanel.add(tile);
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
        mainPanel.add(score);
        // displayTile.add(new JLabel(new ImageIcon(getClass().getResource(CarcassonneMain.currentTile.type + ".png"))));
    }

    public void draw() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        tile = new JLabel("Current Tile: ");
        mainPanel.add(tile);
        if (!CarcassonneMain.currentTile.type.equals("")) {
            mainPanel.add(new JLabel(new ImageIcon(getClass().getResource("res/" + CarcassonneMain.currentTile.type + ".png"))));
        }
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
        mainPanel.repaint();
        inpf.getContentPane().removeAll();
        inpf.getContentPane().add((Component) mainPanel);
        System.out.println(CarcassonneMain.currentTile.type);
        inpf.pack();
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
