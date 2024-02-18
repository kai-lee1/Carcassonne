package carcassonne;

import javax.swing.*;

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

    public Input() {
        inpf = new JFrame();
        inpf.setSize(400, 200);
        inpf.setTitle("Input");
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        coordx = new JTextField(300);
        coordy = new JTextField(300);
        rots = new JTextField(300);
        meeple = new JTextField(300);
        coordxloc = new JLabel("X Coordinate:");
        coordyloc = new JLabel("Y Coordinate:");
        rotsloc = new JLabel("Rotations:");
        meepleloc = new JLabel("Meeple:");
        mainPanel.add(coordxloc);
        mainPanel.add(coordx);
        mainPanel.add(coordyloc);
        mainPanel.add(coordy);
        mainPanel.add(rotsloc);
        mainPanel.add(rots);
        mainPanel.add(meepleloc);
        mainPanel.add(meeple);
    }

    public void draw() {
        inpf.getContentPane().removeAll();
        inpf.getContentPane().add(mainPanel);
        inpf.repaint();
        inpf.setVisible(true);
    }
}
