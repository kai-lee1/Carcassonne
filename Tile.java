package carcassonne;

public class Tile {
	//types: 0->field 1->road 2->city
	//Later: shields, cathedrals, lakes, churches
	public int[] types;
	public boolean[][] connected;
	public int[] meeple; //array of length 14. [0] is the player, 1 is for type, 2-13 is for if the meeple is connected to the respective side. 
	                     //0 if not connected, 1 if connected. NOTE - 1 is the top left corner of the tile, not the top middle
	public String type;
	
	public Tile(int[] types, boolean[][] connected) {
		this.types = types;
		this.connected = connected;
		this.meeple = new int[] {-1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
	}
	
	public Tile(String type) {
		this.type = type;
		switch(type) {
			case "allcity":
				this.types = new int[] {2, 2, 2, 2};
				this.connected = new boolean[][] {{true, true, true},
					{true, true, true},
					{true, true, true},
					{true, true, true}};
				break;
			case "1not":
				this.types = new int[] {2, 2, 0, 2};
				this.connected = new boolean[][] {{true, true, true},
					{true, true, true},
					{true, true, true},
					{true, true, true}};
				break;
			case "1road":
				this.types = new int[] {2, 2, 1, 2};
				this.connected = new boolean[][] {{true, true, true},
					{true, true, true},
					{true, true, true},
					{true, true, true}};
				break;
			case "cornercity":
				this.types = new int[] {2, 0, 0, 2};
				this.connected = new boolean[][] {{true, true, true},
					{true, true, true},
					{true, true, true},
					{true, true, true}};
				break;
			case "cornercityroad":
				this.types = new int[] {2, 1, 1, 2};
				this.connected = new boolean[][] {{false, false, true},
					{false, true, false},
					{false, true, false},
					{true, false, false}};
				break;				
			case "bowtiecity":
				this.types = new int[] {0, 2, 0, 2};
				this.connected = new boolean[][] {{true, false, true},
					{true, true, true},
					{false, true, true},
					{true, true, true}};
				break;
			case "2adjacentcity":
				this.types = new int[] {2, 0, 0, 2};
				this.connected = new boolean[][] {{true, true, false},
					{true, true, true},
					{true, true, true},
					{false, true, true}};
				break;
			case "bowtiefield":
				this.types = new int[] {2, 0, 2, 0};
				this.connected = new boolean[][] {{true, false, true},
					{true, true, true},
					{false, true, true},
					{true, true, true}};
				break;
			case "1edgecity":
				this.types = new int[] {2, 0, 0, 0};
				this.connected = new boolean[][] {{true, true, true},
					{true, true, true},
					{true, true, true},
					{true, true, true}};
				break;
			case "edgecityroadleft":
				this.types = new int[] {2, 0, 1, 1};
				this.connected = new boolean[][] {{true, false, false},
					{true, true, true},
					{false, true, true},
					{false, true, true}};
				break;
			case "edgecityroadright":
				this.types = new int[] {2, 1, 1, 0};
				this.connected = new boolean[][] {{false, false, true},
					{false, true, true},
					{false, true, true},
					{true, true, true}};
				break;
			case "edgecityroadt":
				this.types = new int[] {2, 1, 1, 1};
				this.connected = new boolean[][] {{false, false, false},
					{false, false, false},
					{false, false, false},
					{false, false, false}};
				break;
			case "edgecityroadstraight":
				this.types = new int[] {2, 1, 0, 1};
				this.connected = new boolean[][] {{false, false, false},
					{false, true, true},
					{false, true, true},
					{false, true, true}};
				break;
			case "straightroad":
				this.types = new int[] {0, 1, 0, 1};
				this.connected = new boolean[][] {{true, false, true},
					{true, true, true},
					{false, true, true},
					{true, true, true}};
				break;
			case "Lroad":
				this.types = new int[] {0, 0, 1, 1};
				this.connected = new boolean[][] {{true, true, true},
					{true, true, true},
					{true, true, true},
					{true, true, true}};
				break;
			case "Troad":
				this.types = new int[] {0, 1, 1, 1};
				this.connected = new boolean[][] {{true, false, true},
					{true, false, false},
					{false, false, false},
					{true, false, false}};
				break;
			case "+road":
				this.types = new int[] {1, 1, 1, 1};
				this.connected = new boolean[][] {{false, false, false},
					{false, false, false},
					{false, false, false},
					{false, false, false}};
				break;
			default:
				System.out.print("tf");
				break;
		}
	}
}
