package carcassonne;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import javax.swing.*;
import javax.swing.table.*;
import java.lang.Integer;

public class GUI {
    static Tile[][] strdata;
    static Tile[][] data;
    static JScrollPane sp;
    // Constructor
    public static void gui(Board board, JFrame f, int x, int y) {
        // Table
        JTable j;

        // Frame Title
        f.getContentPane().setLayout(new BorderLayout());
        f.setTitle("Carcassonne");

        // Data to be displayed in the JTable
        data = board.board;
        strdata = new Tile[133][133];
        for (int i = 0; i < 133; i++) {
            for (int k = 0; k < 133; k++) {
                strdata[k][i] = data[i][k];
            }
        }


        // Column Names
        String[] columnNames = new String[133];
        columnNames[0] = "Row #";
        for (int i = 0; i < 133; i++) {
            columnNames[i] = Integer.toString(i);
        }

        // Initializing the JTable
        j = new JTable(strdata, columnNames);
        j.setBounds(133, 133, 500, 133);
        j.setRowHeight(222);
        j.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (int i = 0; i < 133; i++) {
            j.getColumnModel().getColumn(i).setCellRenderer(new ImageRenderer());
            j.getColumnModel().getColumn(i).setPreferredWidth(222);
        }

        // adding it to JScrollPane
        sp = new JScrollPane(j);
        
        //f.add(sp);
        f.getContentPane().add(sp);
        // Frame Size
        
        // Frame Visible = true
        f.setVisible(true);

        sp.getVerticalScrollBar().setValue(y);
        sp.getHorizontalScrollBar().setValue(x);
    }
}

class ImageRenderer extends DefaultTableCellRenderer {
    JLabel lbl = new JLabel();

    ImageIcon blank = new ImageIcon(getClass().getResource("res/emptytile.png"));
    ImageIcon plusroad = new ImageIcon(getClass().getResource("res/+road.png"));
    ImageIcon oneedgecity = new ImageIcon(getClass().getResource("res/1edgecity.png"));
    ImageIcon onenot = new ImageIcon(getClass().getResource("res/1not.png"));
    ImageIcon oneroad = new ImageIcon(getClass().getResource("res/1road.png"));
    ImageIcon twoadjcity = new ImageIcon(getClass().getResource("res/2adjacentcity.png"));
    ImageIcon allcity = new ImageIcon(getClass().getResource("res/allcity.png"));
    ImageIcon bowtiecity = new ImageIcon(getClass().getResource("res/bowtiecity.png"));
    ImageIcon bowtiefield = new ImageIcon(getClass().getResource("res/bowtiefield.png"));
    ImageIcon cornercity = new ImageIcon(getClass().getResource("res/cornercity.png"));
    ImageIcon cornercityroad = new ImageIcon(getClass().getResource("res/cornercityroad.png"));
    ImageIcon edgecityroadleft = new ImageIcon(getClass().getResource("res/edgecityroadleft.png"));
    ImageIcon edgecityroadright = new ImageIcon(getClass().getResource("res/edgecityroadright.png"));
    ImageIcon edgecityroadstraight = new ImageIcon(getClass().getResource("res/edgecityroadstraight.png"));
    ImageIcon edgecityroadt = new ImageIcon(getClass().getResource("res/edgecityroadt.png"));
    ImageIcon lroad = new ImageIcon(getClass().getResource("res/Lroad.png"));
    ImageIcon straightroad = new ImageIcon(getClass().getResource("res/straightroad.png"));
    ImageIcon troad = new ImageIcon(getClass().getResource("res/Troad.png"));
    ImageIcon meeple = new ImageIcon(getClass().getResource("res/meeple.png"));

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        // if (value.equals("emptytile.png"))
        //     lbl.setIcon(blank);
        Icon img = blank;
        if (((Tile) value).type.equals("")) {
            lbl.setIcon(blank);
            lbl.setText("(" + Integer.toString(column) + ", " + Integer.toString(row) + ")");
            return lbl;
        }
        if (((Tile) value).type.equals("edgecityroadstraight")) {
            img = new ImageIcon(edgecityroadstraight.getImage());
        }
        else if (((Tile) value).type.equals("allcity")) {
            img = new ImageIcon(allcity.getImage());
        }
        else if (((Tile) value).type.equals("1not")) {
            img = new ImageIcon(onenot.getImage());
        }
        else if (((Tile) value).type.equals("1road")) {
            img = new ImageIcon(oneroad.getImage());
        }
        else if (((Tile) value).type.equals("cornercity")) {
            img = new ImageIcon(cornercity.getImage());
        }
        else if (((Tile) value).type.equals("cornercityroad")) {
            img = new ImageIcon(cornercityroad.getImage());
        }
        else if (((Tile) value).type.equals("bowtiecity")) {
            img = new ImageIcon(bowtiecity.getImage());
        }
        else if (((Tile) value).type.equals("2adjacentcity")) {
            img = new ImageIcon(twoadjcity.getImage());
        }
        else if (((Tile) value).type.equals("bowtiefield")) {
            img = new ImageIcon(bowtiefield.getImage());
        }
        else if (((Tile) value).type.equals("1edgecity")) {
            img = new ImageIcon(oneedgecity.getImage());
        }
        else if (((Tile) value).type.equals("edgecityroadleft")) {
            img = new ImageIcon(edgecityroadleft.getImage());
        }
        else if (((Tile) value).type.equals("edgecityroadright")) {
            img = new ImageIcon(edgecityroadright.getImage());
        }
        else if (((Tile) value).type.equals("edgecityroadt")) {
            img = new ImageIcon(edgecityroadt.getImage());
        }
        else if (((Tile) value).type.equals("straightroad")) {
            img = new ImageIcon(straightroad.getImage());
        }
        else if (((Tile) value).type.equals("Lroad")) {
            img = new ImageIcon(lroad.getImage());
        }
        else if (((Tile) value).type.equals("Troad")) {
            img = new ImageIcon(troad.getImage());
        }
        else if (((Tile) value).type.equals("+road")) {
            img = new ImageIcon(plusroad.getImage());
        }
        lbl.setText("(" + Integer.toString(column) + ", " + Integer.toString(row) + ")");
        // img = new RotatedIcon(img, Rotate.ABOUT_CENTER);
        // ((RotatedIcon) img).setDegrees(((Tile) value).rotations * 90.0);
        BufferedImage combined = new BufferedImage(220, 220, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = combined.createGraphics();
        AffineTransform old = g.getTransform();
        g.rotate(((Tile) value).rotations * Math.PI / 2, 110, 110);
        g.drawImage(((ImageIcon) img).getImage(), 0, 0, null);
        g.setTransform(old);
        if (((Tile) value).meeple[14] != -1) {
            int[] pos = meeplePos(((Tile) value).meeple[14]);
            g.drawImage(meeple.getImage(), pos[0], pos[1], null);
        }
        g.dispose();
        lbl.setIcon(new ImageIcon(combined));
        return lbl;
    }

    int[] meeplePos(int pos) {
        int[] xy = new int[2];
        switch(pos) {
            case 0:
                xy = new int[] {42, 14};
                break;
            case 1:
                xy = new int[] {110, 14};
                break;
            case 2:
                xy = new int[] {178, 14};
                break;
            case 3:
                xy = new int[] {206, 42};
                break;
            case 4:
                xy = new int[] {206, 110};
                break;
            case 5:
                xy = new int[] {206, 178};
                break;
            case 6:
                xy = new int[] {178, 206};
                break;
            case 7:
                xy = new int[] {110, 206};
                break;
            case 8:
                xy = new int[] {42, 206};
                break;
            case 9:
                xy = new int[] {14, 178};
                break;
            case 10:
                xy = new int[] {14, 110};
                break;
            case 11:
                xy = new int[] {14, 42};
                break;
            default:
                xy = new int[] {0, 0};
                break;
        }
        xy[0] -= 14;
        xy[1] -= 14;
        return xy;
    }
}