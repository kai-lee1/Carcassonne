package carcassonne;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.*;

public class Input {
    public static JFrame inpf = CarcassonneMain.f;
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
    private String[] score;
    private JLabel scoreloc;

    public Input() {
        inpf.getContentPane().removeAll();
        inpf.setSize(400, 500);
        inpf.setTitle("Input");
        score = new String[CarcassonneMain.playerCount];
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
        scoreloc = new JLabel("Player Scores: ");
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
        mainPanel.add(scoreloc);
        for (int i = 0; i < CarcassonneMain.playerCount; i++) {
            score[i] = "";
        }
        for (String s : score) {
            mainPanel.add(new JLabel(s));
        }
        // displayTile.add(new JLabel(new ImageIcon(getClass().getResource(CarcassonneMain.currentTile.type + ".png"))));
    }

    public Component draw() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
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
        mainPanel.add(scoreloc);
        for (int i = 0; i < CarcassonneMain.playerCount; i++) {
            score[i] = "Player " + i + ": " + CarcassonneMain.board.players[i].score;
        }
        for (String s : score) {
            mainPanel.add(new JLabel(s));
        }
        tile = new JLabel("Current Tile: ");
        mainPanel.add(tile);
        if (!CarcassonneMain.currentTile.type.equals("")) {
            ImageIcon img = new ImageIcon(getClass().getResource("res/" + CarcassonneMain.currentTile.type + ".png"));
            BufferedImage combined = new BufferedImage(220, 220, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = combined.createGraphics();
            AffineTransform old = g.getTransform();
            g.rotate(CarcassonneMain.currentTile.rotations * Math.PI / 2, 110, 110);
            g.drawImage(img.getImage(), 0, 0, null);
            g.setTransform(old);
            g.dispose();
            mainPanel.add(new JLabel(new ImageIcon(combined)));
        }
        mainPanel.repaint();
        return mainPanel;
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
