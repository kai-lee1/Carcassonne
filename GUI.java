package carcassonne;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumnModel;

import java.util.Arrays;

import java.lang.Integer;

public class GUI {
    // Constructor
    public static void gui(Board board) {
        // Frame initialization
        JFrame f;
        // Table
        JTable j;
        f = new JFrame();
 
        // Frame Title
        f.setTitle("JTable Example");
 
        // Data to be displayed in the JTable
        Tile[][] data = board.board;
        String[][] strdata = new String[133][134];
        for (int i = 0; i < 133; i++) {
            strdata[i][0] = Integer.toString(i);
        }
        for (int i = 1; i < 134; i++) {
            for (int k = 0; k < 133; k++) {
                strdata[k][i] = data[i - 1][k].toString();
                // System.out.println(data[i][j].toString());
            }
        }
        // System.out.println(Arrays.toString(strdata));

 
        // Column Names
        String[] columnNames = new String[133];
        columnNames[0] = "Row #";
        for (int i = 1; i < 133; i++) {
            columnNames[i] = Integer.toString(i - 1);
        }
 
        // Initializing the JTable
        j = new JTable(strdata, columnNames);
        j.setBounds(133, 133, 500, 133);
        j.setRowHeight(40);
        j.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
 
        // adding it to JScrollPane
        JScrollPane sp = new JScrollPane(j);
        f.add(sp);
        // Frame Size
        f.setSize(500, 133);
        // Frame Visible = true
        f.setVisible(true);
    }
}
