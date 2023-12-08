package carcassonne;

public class EmptyTile extends Tile {

	//not useful
//	public EmptyTile(int[] types, int[][] connected) {
//		super(types, connected);
//		
//	}
	
	public EmptyTile() {
		super(new int[4], new boolean[4][3]);
		this.types = new int[]{-1, -1, -1, -1};
		this.connected = new boolean[][] {
			{false, false, false},
			{false, false, false},
			{false, false, false},
			{false, false, false}
		};
		
	}
	public EmptyTile(String type) {
		super("");
	}
}
