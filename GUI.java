package carcassonne;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

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
        Object[][] strdata = new Object[133][134];
        for (int i = 0; i < 133; i++) {
            strdata[i][0] = Integer.toString(i);
        }
        for (int i = 1; i < 134; i++) {
            for (int k = 0; k < 133; k++) {
                strdata[k][i] = data[i - 1][k].toString();
                if (strdata[k][i].equals("2101"))
                    strdata[k][i] = "edgecityroadstraight.png";
                else
                    strdata[k][i] = "emptytile.png";
            }
        }


        // Column Names
        String[] columnNames = new String[133];
        columnNames[0] = "Row #";
        for (int i = 1; i < 133; i++) {
            columnNames[i] = Integer.toString(i - 1);
        }

        MyTableModel model = new MyTableModel(strdata);

        // Initializing the JTable
        j = new JTable(strdata, columnNames);
        j.setBounds(133, 133, 500, 133);
        j.setRowHeight(220);
        j.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (int i = 1; i < 133; i++) {
            j.getColumnModel().getColumn(i).setCellRenderer(new ImageRenderer());
            j.getColumnModel().getColumn(i).setPreferredWidth(220);;
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

    // public void guiupdate(Board board, JFrame f, JTable j){
    // Tile[][] data = board.board;
    // String[][] strdata = new String[133][134];
    // for (int i = 0; i < 133; i++) {
    // strdata[i][0] = Integer.toString(i);
    // }
    // for (int i = 1; i < 134; i++) {
    // for (int k = 0; k < 133; k++) {
    // strdata[k][i] = data[i - 1][k].toString();
    // // System.out.println(data[i][j].toString());
    // }
    // }

    // }
}

class MyTableModel extends AbstractTableModel {
    Object[][] strdata;
    public MyTableModel(Object[][] strdata) {
        super();
        this.strdata = strdata.clone();
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
        System.out.println("made");
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        lbl.setText("(" + Integer.toString(row) + ", " + Integer.toString(column) + ")");
        if (value.equals("emptytile.png"))
            lbl.setIcon(blank);
        else if (value.equals("edgecityroadstraight.png"))
            lbl.setIcon(starttile);
        return lbl;
    }
}