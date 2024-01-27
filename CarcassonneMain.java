package carcassonne;

import javax.swing.JFrame;

public class CarcassonneMain {
	// Frame initialization
	static JFrame f = new JFrame();

	public static void main(String[] args) {
		Board board = new Board(2);
		GUI.gui(board, f);

		board.gameTime(board); // TODO i think theres a better way to do this

	}

	public static void drawGUI (Board board){
		f.getContentPane().removeAll(); //or remove(JComponent)
		f.repaint();
		GUI.gui(board, f);
	}
}

// import java.awt.BorderLayout;
// import java.awt.Component;

// import javax.swing.ImageIcon;
// import javax.swing.JFrame;
// import javax.swing.JLabel;
// import javax.swing.JScrollPane;
// import javax.swing.JTable;
// import javax.swing.table.AbstractTableModel;
// import javax.swing.table.DefaultTableCellRenderer;

// public class CarcassonneMain {
// 	public static void main(String[] args) {
// 		JFrame frame = new JFrame();
// 		frame.getContentPane().setLayout(new BorderLayout());

// 		MyTableModel model = new MyTableModel();

// 		JTable table = new JTable(model);
// 		table.setRowHeight(80);
// 		table.getColumnModel().getColumn(0).setCellRenderer(new ImageRenderer());
// 		table.getColumnModel().getColumn(1).setCellRenderer(new ImageRenderer());
// 		JScrollPane pane = new JScrollPane(table);
// 		frame.getContentPane().add(BorderLayout.CENTER, pane);
// 		frame.setSize(500, 400);
// 		frame.setVisible(true);
// 	}

// 	public static void drawGUI (Board board){
// 		GUI.gui(board, new JFrame());
// 	}
// }

// class MyTableModel extends AbstractTableModel {
// 	public Object getValueAt(int row, int column) {
// 		return "" + (row * column);
// 	}

// 	public int getColumnCount() {
// 		return 4;
// 	}

// 	public int getRowCount() {
// 		return 5;
// 	}
// }

// class ImageRenderer extends DefaultTableCellRenderer {
// 	JLabel lbl = new JLabel();

// 	ImageIcon icon = new ImageIcon(getClass().getResource("edgecityroadstraight.png"));

// 	public Component getTableCellRendererComponent(JTable table, Object value,
// 			boolean isSelected,
// 			boolean hasFocus, int row, int column) {
// 		lbl.setText((String) value);
// 		lbl.setIcon(icon);
// 		return lbl;
// 	}
// }
