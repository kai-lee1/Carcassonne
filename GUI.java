package carcassonne;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

import carcassonne.RotatedIcon.Rotate;

import java.awt.geom.*;
import java.awt.image.*;
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

        MyTableModel model = new MyTableModel(strdata);

        // Initializing the JTable
        j = new JTable(strdata, columnNames);
        j.setBounds(133, 133, 500, 133);
        j.setRowHeight(220);
        j.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (int i = 0; i < 133; i++) {
            j.getColumnModel().getColumn(i).setCellRenderer(new ImageRenderer());
            j.getColumnModel().getColumn(i).setPreferredWidth(220);
        }

        // adding it to JScrollPane
        JScrollPane sp = new JScrollPane(j);
        //f.add(sp);
        f.getContentPane().add(BorderLayout.CENTER, sp);
        // Frame Size
        f.setSize(500, 133);
        // Frame Visible = true
        f.setVisible(true);
    }
}

class MyTableModel extends AbstractTableModel {
    Object[][] strdata;
    public MyTableModel(Object[][] strdata) {
        super();
        this.strdata = strdata;
    }

    public Object getValueAt(int row, int column) {
       // System.out.println(strdata[row][column]);
        return strdata[column][row];
    }

    public int getColumnCount() {
        return strdata.length;
    }

    public int getRowCount() {
        return strdata[0].length;
    }
}

class ImageRenderer extends DefaultTableCellRenderer {
    JLabel lbl = new JLabel();

    ImageIcon starttile = new ImageIcon(getClass().getResource("edgecityroadstraight.png"));
    ImageIcon blank = new ImageIcon(getClass().getResource("emptytile.png"));

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
            img = new ImageIcon(starttile.getImage());
        }
        img = new RotatedIcon(img, Rotate.ABOUT_CENTER);
        ((RotatedIcon) img).setDegrees(((Tile) value).rotations * 90.0);
        lbl.setIcon(img);
        return lbl;
    }
}