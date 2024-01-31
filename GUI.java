package carcassonne;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

import carcassonne.RotatedIcon.Rotate;

import java.lang.Integer;

public class GUI {
    // Constructor
    public static void gui(Board board, JFrame f) {
        // Table
        JTable j;

        // Frame Title
        f.getContentPane().setLayout(new BorderLayout());
        f.setTitle("Carcassonne");

        // Data to be displayed in the JTable
        Tile[][] data = board.board;
        Tile[][] strdata = new Tile[133][133];
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
        JScrollPane sp = new JScrollPane(j);
        //f.add(sp);
        f.getContentPane().add(BorderLayout.CENTER, sp);
        // Frame Size
        
        // Frame Visible = true
        f.setVisible(true);
    }
}

class ImageRenderer extends DefaultTableCellRenderer {
    JLabel lbl = new JLabel();

    ImageIcon blank = new ImageIcon(getClass().getResource("emptytile.png"));
    ImageIcon plusroad = new ImageIcon(getClass().getResource("+road.png"));
    ImageIcon oneedgecity = new ImageIcon(getClass().getResource("1edgecity.png"));
    ImageIcon onenot = new ImageIcon(getClass().getResource("1not.png"));
    ImageIcon oneroad = new ImageIcon(getClass().getResource("1road.png"));
    ImageIcon twoadjcity = new ImageIcon(getClass().getResource("2adjacentcity.png"));
    ImageIcon allcity = new ImageIcon(getClass().getResource("allcity.png"));
    ImageIcon bowtiecity = new ImageIcon(getClass().getResource("bowtiecity.png"));
    ImageIcon bowtiefield = new ImageIcon(getClass().getResource("bowtiefield.png"));
    ImageIcon cornercity = new ImageIcon(getClass().getResource("cornercity.png"));
    ImageIcon cornercityroad = new ImageIcon(getClass().getResource("cornercityroad.png"));
    ImageIcon edgecityroadleft = new ImageIcon(getClass().getResource("edgecityroadleft.png"));
    ImageIcon edgecityroadright = new ImageIcon(getClass().getResource("edgecityroadright.png"));
    ImageIcon edgecityroadstraight = new ImageIcon(getClass().getResource("edgecityroadstraight.png"));
    ImageIcon edgecityroadt = new ImageIcon(getClass().getResource("edgecityroadt.png"));
    ImageIcon lroad = new ImageIcon(getClass().getResource("Lroad.png"));
    ImageIcon straightroad = new ImageIcon(getClass().getResource("straightroad.png"));
    ImageIcon troad = new ImageIcon(getClass().getResource("Troad.png"));

    public ImageRenderer() {
        super();
    }

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
        img = new RotatedIcon(img, Rotate.ABOUT_CENTER);
        ((RotatedIcon) img).setDegrees(((Tile) value).rotations * 90.0);
        lbl.setIcon(img);
        return lbl;
    }
}