package carcassonne;

import java.util.Arrays;

public class Tile {
	//types: 0->field 1->road 2->city
	//Later: shields, cathedrals, lakes, churches
	public int[] types;
	public int rotations;
	public boolean[][] connected;
	public int[] meeple; //array of length 15. [0] is the player, 1 is for type, 2-13 is for if the meeple is connected to the respective side. 
						// [14] is for position
	                     //0 if not connected, 1 if connected. NOTE - 0 is top left (https://tinyurl.com/yeyzkd96)
						 //ex. meeple[4] is 1, means that meeple is connected to top middle ("2" on the 12 point system)
	public String type;
	public int[] completion;
	
	public Tile(int[] types, boolean[][] connected) {
		this.type = "";
		this.types = types;
		this.connected = connected;
		this.meeple = new int[] {-1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1};
		this.rotations = 0;
		this.completion = new int[] {0, 0, 0, 0};
	}
	
	public Tile(String type) {
		this.type = type;
		this.meeple = new int[] {-1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1};
		this.completion = new int[] {0, 0, 0, 0};
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
				break;
		}
	}

	public int[] connects(int pos1) {
		Tile newTile = this.copy();
		newTile.meeple = new int[] {-1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1};
		
		if (placeMeeple(pos1, newTile)) {
			return Arrays.copyOfRange(newTile.meeple, 2, 14);
		}
		
		if (pos1 % 3 == 0) {
			if (pos1 == 0) {
				placeMeeple(10, newTile);
				return Arrays.copyOfRange(newTile.meeple, 2, 14);
			}
			placeMeeple(pos1 - 2, newTile);
			return Arrays.copyOfRange(newTile.meeple, 2, 14);
		}

		if (pos1 == 11) {
			placeMeeple(1, newTile);
			return Arrays.copyOfRange(newTile.meeple, 2, 14);
		}
		placeMeeple(pos1 + 2, newTile);
		return Arrays.copyOfRange(newTile.meeple, 2, 14);
	}

	private boolean placeMeeple(int position, Tile tile) { // position is one of the 12 sides of tile
		// New switch, case 1 edge, case 0 & 2 different side corners
		switch (position % 3) {
			case 1:
				tile.meeple[1] = tile.types[position / 3];
				if ((tile.types[(position) / 3] == 2)) {
					// know it isn't 0 or 11
					tile.meeple[position + 2] = 1;
					tile.meeple[position + 1] = 1;
					tile.meeple[position + 3] = 1;
					for (int j = 0; j < 3; j++) {
						if (position / 3 < j) {
							if (tile.connected[position / 3][j] && tile.types[j + 1] == 2) {
								tile.meeple[(j + 1) * 3 + 2 + 1] = 1;
								tile.meeple[(j + 1) * 3 + 2] = 1;
								tile.meeple[(j + 1) * 3 + 2 + 2] = 1;
							}
						} else if (tile.connected[position / 3][j] && tile.types[j] == 2) {
							tile.meeple[j * 3 + 2 + 1] = 1;
							tile.meeple[j * 3 + 2] = 1;
							tile.meeple[j * 3 + 2 + 2] = 1;
						}
					}
				} else if ((tile.types[(position) / 3] == 1)) {
					tile.meeple[position + 2] = 1;
					for (int j = 0; j < 3; j++) {
						if (position / 3 < j) {
							if (tile.connected[position / 3][j] && tile.types[j + 1] == 1) {
								tile.meeple[(j + 1) * 3 + 2 + 1] = 1;
								break;
							}
						} else if (tile.connected[position / 3][j] && tile.types[j] == 1) {
							tile.meeple[j * 3 + 2 + 1] = 1;
							break;
						}
					}
				} else {
					// field
					tile.meeple[position + 2] = 1;
					tile.meeple[position + 1] = 1;
					tile.meeple[position + 3] = 1;
					for (int j = 0; j < 3; j++) {
						if (position / 3 < j) {
							if (tile.connected[position / 3][j] && tile.types[j + 1] == 0) {
								tile.meeple[(j + 1) * 3 + 2 + 1] = 1;
								tile.meeple[(j + 1) * 3 + 2] = 1;
								tile.meeple[(j + 1) * 3 + 2 + 2] = 1;
							}
						} else if (tile.connected[position / 3][j] && tile.types[j] == 0) {
							tile.meeple[j * 3 + 2 + 1] = 1;
							tile.meeple[j * 3 + 2] = 1;
							tile.meeple[j * 3 + 2 + 2] = 1;
						}
					}
					if (tile.types[(position / 3 + 1) % 4] == 0) {
						tile.meeple[(position + 2) % 12 + 2] = 1;
					}
					if (tile.types[(position / 3 + 3) % 4] == 0) {
						tile.meeple[(position - 2) % 12 + 2] = 1;
					}

					if (tile.type == "edgecityroadleft") {
						tile.meeple[(position + 7) % 12 + 2] = 1;
					} else if (tile.type == "edgecityroadright") {
						tile.meeple[(position + 5) % 12 + 2] = 1;
					}
				}
				break;
			case 0:
				// side must be road and meeple must be adjacent to non-field
				if (tile.types[(position) / 3] != 1 || tile.types[((position) / 3 - 1) % 3] == 0) {
					// destroy target player
					tile.meeple[0] = -1;
					tile.meeple[14] = -1;
					return false;
				}
				if (tile.types[((position) / 3 - 1) % 3] == 1) {
					tile.meeple[position + 2] = 1;
					tile.meeple[(position - 1) % 12 + 2] = 1;
				} else {
					tile.meeple[position + 2] = 1;
					if (tile.type == "cornercityroad") {
						tile.meeple[(position + 5) % 12 + 2] = 1;
					} else if ((tile.type == "edgecityroadt") || (tile.type == "edgecityroadstraight")) {
						tile.meeple[(position + 8) % 12 + 2] = 1;
					}
				}
				tile.meeple[1] = 0;
				break;
			case 2:
				if (tile.types[(position) / 3] != 1 || tile.types[((position) / 3 + 1) % 3] == 0) {
					tile.meeple[0] = -1;
					tile.meeple[14] = -1;
					return false;
				}
				if (tile.types[((position) / 3 + 1) % 3] == 1) {
					tile.meeple[position + 2] = 1;
					tile.meeple[(position + 1) % 12 + 2] = 1;
				} else {
					tile.meeple[position + 2] = 1;
					if (tile.type == "cornercityroad") {
						tile.meeple[(position + 7) % 12 + 2] = 1;
					} else if ((tile.type == "edgecityroadt") || (tile.type == "edgecityroadstraight")) {
						tile.meeple[(position + 4) % 12 + 2] = 1;
					}
				}
				tile.meeple[1] = 0;
				break;
			default:
				return false;
		}
		return true;
	}
	
	public Tile copy() {
		Tile ret = new Tile(this.types.clone(), this.connected.clone());
		ret.type = this.type;
		ret.meeple = this.meeple.clone();
		ret.rotations = this.rotations;
		ret.completion = this.completion.clone();
		return ret;
	}
	
	public String toString() {
		String ret = "";
		for (int j = 0; j < 4; j++)
			ret = ret.concat(Integer.toString(this.types[j]));
			for (int x : meeple){
				if (x > 0){
					ret+=x;
					break;
				}
			}
			// System.out.println(ret);
		return ret;
	}
}