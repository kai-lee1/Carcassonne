class CarcassonneMain {
  // Frame initialization
  static playerCount = 2;
  static f = new JFrame();
  static currentTile = new EmptyTile();
  static inp = new Input();
  static ready = false;
  static endWaitTime = Date.now() + 500 * 1000;
  static cityCount = 1;
  static end = false;
  static board;

  static main(args) {
    board = new Board(playerCount);
    f.setSize(500, 133);
    f.setExtendedState(JFrame.MAXIMIZED_BOTH); 
    f.getContentPane().setLayout(new BorderLayout());
    f.setTitle("Carcassonne");
    GUI.gui(board);
    GUI.sp.getVerticalScrollBar().setValue(64 * 222);
    GUI.sp.getHorizontalScrollBar().setValue(64 * 222);
    drawGUI(board);

    board.gameTime(board); // TODO i think theres a better way to do this
  }

  static drawGUI(board) {
    f.getContentPane().removeAll(); //or remove(JComponent)
    let pos = [GUI.sp.getHorizontalScrollBar().getValue(), GUI.sp.getVerticalScrollBar().getValue()];
    let table = GUI.gui(board);
    let input = inp.draw(); 
    let splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, table, input);
    f.getContentPane().add(splitPane);
    f.pack();
    f.setSize(screen.width, screen.height);
    //f.setExtendedState(JFrame.MAXIMIZED_BOTH); 
    GUI.sp.getVerticalScrollBar().setValue(pos[1]);
    GUI.sp.getHorizontalScrollBar().setValue(pos[0]);
    f.repaint();
    f.setVisible(true);
    splitPane.setDividerLocation(0.85);
  }

  static async waitButton() {
    CarcassonneMain.drawGUI(board);
    while (Date.now() < endWaitTime && !ready) {
      // isConditionMet = condition();
      if (ready || end) {
        break;
      } else {
        await new Promise(resolve => setTimeout(resolve, 100));
      }
    }
    ready = false;
  }
}

