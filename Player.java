package carcassonne;

import java.util.Scanner;

public class Player {
	public int meepleCount;
	public int score;

	public Player() {
		this.meepleCount = 7;
		this.score = 0;
	}

	public void placeTile(int x, int y, Board board) {
		Scanner scan = new Scanner(System.in);
		System.out.println("how many times do you want to rotate tile? ");
		int rotateans = Integer.parseInt(scan.nextLine());
		rotateTile(board.turnCount, rotateans, board);
		System.out.println("new tile after rotating: ");
		for (int j = 0; j < 4; j++) {
			System.out.print(board.tiles.get(board.turnCount).types[j]);
		}
		System.out.println("");


		/*
		 * scoring:
		 * Score every time new tile is placed (every turn). 
		 */

		if (x > 0 && y > 0 && (board.validSide(board.tiles.get(board.turnCount), board.board[x][y - 1], 0))  //if the tile is valid
				&& (board.validSide(board.tiles.get(board.turnCount), board.board[x + 1][y], 1))
				&& (board.validSide(board.tiles.get(board.turnCount), board.board[x][y + 1], 2))
				&& (board.validSide(board.tiles.get(board.turnCount), board.board[x - 1][y], 3))
				&& (board.board[x][y].types[0] == -1)
				&& ((board.board[x + 1][y].types[0] != -1) || (board.board[x][y + 1].types[0] != -1)
						|| (board.board[x - 1][y].types[0] != -1) || (board.board[x][y - 1].types[0] != -1))) {

			board.board[x][y] = board.tiles.get(board.turnCount);
			
			System.out.println("Place meeple? y/n");
			String answer = scan.nextLine();

			boolean worked = false;
			int position;
			if (answer.equals("y")) {
				System.out.println("Where? (0-11)");
				position = Integer.parseInt(scan.nextLine());
				worked = placeMeeple(position, x, y, board);

				while (!(worked)){
					System.out.println("invalid meeple position. do you want to place a meeple? (y/n)");
					answer = scan.nextLine();
					if (answer.equals("y")) {
						System.out.println("Where? (0-11)");
						position = Integer.parseInt(scan.nextLine());
						worked = placeMeeple(position, x, y, board);
					}
					else {
						break;
					}

				}
				if (worked){ 
						System.out.println("meeple has been placed");
						meepleCount--;
					}
			}
			int[] meeplesPresent = new int[board.players.length]; //0 if no meeple from that player exists in the current search, 1 if yes
			Integer scoore = 0;
			scoring(x, y, board, scoore, meeplesPresent);
			System.out.println("scoore is: " + scoore);
			System.out.println("score of player 1: " + board.players[0].score);
			System.out.println("score of player 2: " + board.players[1].score);

		} 
		
		else {
			System.out.println("invalid tile. try new tile coordinates");
			int newansx = Integer.parseInt(scan.nextLine());
			int newansy = Integer.parseInt(scan.nextLine());
			placeTile(newansx, newansy, board);

		}

	}

	/*end of placeTile
	 * 
	 */


	
	public void scoring(int x, int y, Board board, int score, int[] meeplesPresent) {
			//boolean[] checked = new boolean[] {false, false, false, false};
			System.out.println("the scoring method has been called");
				c: for (int i = 0; i < 4; i++) { //each side of the tile we are on
					// if (checked[i]){
					// 	continue;
					// }
					int tx = x;
					int ty = y;
					int type = board.board[tx][ty].types[i];
					boolean continual = true;
					System.out.println("we are looping in scoring(). checking 4 tiles around!"); // around current tile
					switch (type) {
	
						case 1: //------------------------------ROAD-----------------------------------
							int roadLength = 1;
							while (continual) {
								
								//get the checking tile coordinates, depending on which side of the current tile we are checking
								Tile checkingTile;
								switch (i){
									case 0:
										checkingTile = board.board[tx][y-1];
										break;
									case 1:
										checkingTile = board.board[tx+1][ty];
										break;
									case 2:
										checkingTile = board.board[tx][ty+1];
										break;
									case 3: 
										checkingTile = board.board[tx-1][ty];
										break; 
									default:
										checkingTile = board.board[0][0];
								}

								System.out.println(board.board[tx][ty].meeple[1]);

								if (checkingTile.meeple[1] == 1 || board.board[tx][ty].meeple[1] == 1) { //if there is a meeple on checkingTile's road. meeple[1] is for the 
									System.out.println("the thing has happened!11!111!111!1");
									meeplesPresent[board.board[tx][ty].meeple[0]] = 1;
									break; 
								}

								if (checkingTile.types[0] == -1) { //if checkingTile is empty, stop the while loop. 
									roadLength = 0;
									break;
								}

								if (checkingTile.types[(i + 2) % 4] != 1 ){  //i+2 % 4 is the corresponding side on the checking tile.
									continue c; //if corresponding side exists but is not a road, check the next checkingTile
								}

								roadLength++;

								if (tx == x && ty == y && roadLength != 0){ //if hitting the original tile, double count must have happened (circle road). divide by 2
									roadLength = (roadLength + 1)/ 2;
									break;
								}

								for (int j = 0; j < 3; j++){ 
									if(checkingTile.connected[i][j] && checkingTile.types[(i + j) % 4] == 1){ //check if any of the other 3 sides on checkingTile are roads
										continual = true;
										break;
									}
									else { //if no other roads are connected, 
										continual = false;
										if(checkingTile.types[(i+j) % 4] == -1){
											roadLength = 0;
										}
									}
								}
							}

							for (int k = 0; k < meeplesPresent.length; k++){
								if (meeplesPresent[k] == 1){
									board.players[k].score += roadLength;
								System.out.println("here is the score of player " + k + ": " + score);
								}
							}
							System.out.println("about to break out of road case");
							break;
								
						case 2: //------------------------------CITY-----------------------------------
							int citySize = 0;
			
							while (continual) {
								// get the checking tile coordinates, depending on which side of the current tile we are checking
								Tile checkingTile;
								switch (i) {
									case 0:
										checkingTile = board.board[tx][y - 1];
										break;
									case 1:
										checkingTile = board.board[tx + 1][ty];
										break;
									case 2:
										checkingTile = board.board[tx][ty + 1];
										break;
									case 3:
										checkingTile = board.board[tx - 1][ty];
										break;
									default:
										checkingTile = board.board[0][0];
								}
			
								if (checkingTile.meeple[1] == 1) { // if there is a meeple on checkingTile's city
									meeplesPresent[checkingTile.meeple[0]] = 1;
									break;
								}
			
								if (checkingTile.types[0] == -1) { // if checkingTile is empty, stop the while loop.
									citySize = 0;
									break;
								}
			
								if (checkingTile.types[(i + 2) % 4] != 2) {  // i+2 % 4 is the corresponding side on the checking tile.
									continue c;
								}
			
								citySize++;
			
								if (tx == x && ty == y && citySize != 0) { // if hitting the original tile, double count must have happened (enclosed city). divide by 2
									citySize = (citySize + 1) / 2;
									break;
								}
			
								for (int j = 0; j < 3; j++) {
									if (checkingTile.connected[i][j] && checkingTile.types[(i + j) % 4] == 2) {
										// check if any of the other 3 sides on checkingTile are cities
										continual = true;
										break;
									} else {
										// if no other cities are connected,
										continual = false;
										if (checkingTile.types[(i + j) % 4] == -1) {
											citySize = 0;
										}
									}
								}
							}
			
							for (int k = 0; k < meeplesPresent.length; k++) {
								if (meeplesPresent[k] == 1) {
									board.players[k].score += (citySize * 2);
									System.out.println("here is the score of player " + k + ": " + board.players[k].score);
								}
							}
							break; //TODO why is this here?
						

						case 0: //------------------------------FARM-----------------------------------
							System.out.println("switch has encountered a farm");
							break;
					}
					System.out.println("the switch has been ended");

						
				}
				//-----------------------end of scoring-----------------------------------
	}




	// public boolean checkSide(Tile tile, int side) {          //seems to be useless
	// 	if (tile.types[side] == 2) {
	// 		checkSide(tile, side - 1);
	// 		checkSide(tile, side + 1);
	// 	} else if (tile.types[side] == 1) {
	// 		return false;
	// 	} else {
	// 		return false;
	// 	}
	// 	return true;
	// }

	public boolean placeMeeple(int position, int x, int y, Board board) { // position is one of the 12 sides of tile
		Tile tile = board.board[x][y];
		int turn = board.turnCount % board.players.length;
		tile.meeple[0] = turn;

		// New switch, case 1 edge, case 0 & 2 different side corners
		switch (position % 3) {
		case 1:
			tile.meeple[1] = board.board[x][y].types[position / 3];
			if ((tile.types[(position) / 3] == 2)) {
				//know it isn't 0 or 11
				tile.meeple[position + 2] = 1;
				tile.meeple[position + 1] = 1;
				tile.meeple[position + 3] = 1;
				for (int i = 1; i < 4; i++) {
					boolean cons = tile.connected[position / 3][i - 1];
					if (cons) {
						if (tile.types[(position / 3 + i) % 4] == 2) {
							tile.meeple[(position + 3 * i) % 12 + 2] = 1;
							tile.meeple[(position + 3 * i + 1) % 12 + 2] = 1;
							tile.meeple[(position + 3 * i - 1) % 12 + 2] = 1;
						}
					}
				}

			} else if ((tile.types[(position) / 3] == 1)) {
				tile.meeple[position + 2] = 1;
				for (int i = 1; i < 4; i++) {
					boolean cons = tile.connected[position / 3][i - 1];
					if (cons) {
						if (tile.types[(position / 3 + i) % 4] == 1) {
							tile.meeple[(position + 3 * i) % 12 + 2] = 1;
						}
					}
				}
			} else {
				// field
				tile.meeple[position + 2] = 1;
				tile.meeple[position + 1] = 1;
				tile.meeple[position + 3] = 1;
				for (int i = 1; i < 4; i++) {
					boolean cons = tile.connected[position / 3][i - 1];
					if (cons) {
						if (tile.types[(position / 3 + i) % 4] == 0) {
							tile.meeple[(position + 3 * i) % 12 + 2] = 1;
							tile.meeple[(position + 3 * i + 1) % 12 + 2] = 1;
							tile.meeple[(position + 3 * i - 1) % 12 + 2] = 1;
						}
					}
				}
				if (tile.types[(position / 3 + 1) % 4] == 1) {
					tile.meeple[(position + 2) % 12 + 2] = 1;
				}
				if (tile.types[(position / 3 + 3) % 4] == 1) {
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
				return false;
			}
			if (tile.types[((position) / 3 - 1) % 3] == 1) {
				tile.meeple[position + 2] = 1;
				tile.meeple[(position - 1) % 12 + 2] = 1;
			}
			else {
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
				return false;
			}
			if (tile.types[((position) / 3 + 1) % 3] == 1) {
				tile.meeple[position + 2] = 1;
				tile.meeple[(position + 1) % 12 + 2] = 1;
			}
			else {
				tile.meeple[position + 2] = 1;
				if (tile.type == "cornercityroad") {
					tile.meeple[(position - 5) % 12 + 2] = 1;
				} else if ((tile.type == "edgecityroadt") || (tile.type == "edgecityroadstraight")) {
					tile.meeple[(position - 8) % 12 + 2] = 1;
				}
			}
			tile.meeple[1] = 0;
			break;
		default:
			return false;
		}
		return true;
	}
	
	//0 1 2 3
	public void rotateTile(int turn, int rotate, Board board){  //rotating clockwise
		int[] old;
		boolean[] oldcon;
		boolean[][] oldcono;
		board.tiles.get(turn).rotations = board.tiles.get(turn).rotations + rotate;
		for (int i = 0; i < rotate; i++) {
			old = board.tiles.get(turn).types.clone();
			board.tiles.get(turn).types = new int[] {old[3], old[0], old[1], old[2]};
			
			for(int j = 0; j < 4; j++){
				oldcon = board.tiles.get(turn).connected[j];
				board.tiles.get(turn).connected[j] = new boolean[] {oldcon[2], oldcon[0], oldcon[1]};
			}

			oldcono = board.tiles.get(turn).connected;
			board.tiles.get(turn).connected = new boolean[][] {oldcono[3], oldcono[0], oldcono[1], oldcono[2]};
		}
	}

	//public void score (Board board, )
		
	/*
	 * if meeple is in a middle spot (1,4,7,10): check if that side is city (int
	 * divide by 3). if so, meeple is connected to its two adjacent spots. check the
	 * adjacent sides. if city, connect all three spots on that side and keep going
	 * around. if not, stop.
	 * 
	 * else if, check if the side is a road. if road, find the side that has the
	 * other end of the road, and connect the middle spot on that one. (todo: unless
	 * the current tile is one of the blocked road ones)
	 * 
	 * else, the entire side must be a farm. connect the adjacent two spots. check
	 * the adjacent sides. if city, stop. else if road, connect the next spot with
	 * meeple and then stop. else if farm, keep going around.
	 * 
	 * if meeple is on a corner spot (0, 2, 3, 5, 6, 8, 9, 11): check if side is a
	 * city (int divide by 3). if so, meeple is part of city. connect to all other
	 * spots on the same side. check again for both adjacent sides. else if road,
	 * check the adjacent side (only the closest one). if city or road, stop. else
	 * if farm, connect with all three on that side. check again for the side after
	 * that. else, the entire side must be a farm. connect to all other spots on the
	 * same side. check again for both adjacent sides.
	 */

}

