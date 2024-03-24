package carcassonne;

import javax.swing.*;
import java.awt.*;

public class CarcassonneMain {
	// Frame initialization
	static int playerCount = 2;
	static JFrame f = new JFrame();
	static Tile currentTile = new EmptyTile();
	static Input inp = new Input();
	static boolean ready = false;
	static long endWaitTime = System.currentTimeMillis() + 500*1000;
	static int cityCount = 1;
	static boolean end = false;
	static Board board;

	public static void main(String[] args) {
		board = new Board(playerCount);
		f.setSize(500, 133);
		f.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        f.getContentPane().setLayout(new BorderLayout());
        f.setTitle("Carcassonne");
		GUI.gui(board);
		GUI.sp.getVerticalScrollBar().setValue(64*222);
        GUI.sp.getHorizontalScrollBar().setValue(64*222);
		drawGUI(board);

		board.gameTime(board); // TODO i think theres a better way to do this

	}

	public static void drawGUI (Board board){
		f.getContentPane().removeAll(); //or remove(JComponent)
		int[] pos = new int[] {GUI.sp.getHorizontalScrollBar().getValue(), GUI.sp.getVerticalScrollBar().getValue()};
		Component table = GUI.gui(board);
		Component input = inp.draw();
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, table, input);
		f.getContentPane().add(splitPane);
		f.pack();
		f.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		splitPane.setDividerLocation(splitPane.getSize().width - 250);
		GUI.sp.getVerticalScrollBar().setValue(pos[1]);
        GUI.sp.getHorizontalScrollBar().setValue(pos[0]);
		f.repaint();
		f.setVisible(true);
	}

	public static void waitButton() {
		while (System.currentTimeMillis() < endWaitTime && !ready) {
			// isConditionMet = condition();
			if (ready || end) {
				break;
			} else {
				try {
					Thread.sleep(1000);
				}
				catch (InterruptedException e) {}
			}
		}
		ready = false;
	}
}