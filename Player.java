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



		if ((board.validSide(board.tiles.get(board.turnCount), board.board[x][y - 1], 0))  //if the tile is valid
				&& (board.validSide(board.tiles.get(board.turnCount), board.board[x + 1][y], 1))
				&& (board.validSide(board.tiles.get(board.turnCount), board.board[x][y + 1], 2))
				&& (board.validSide(board.tiles.get(board.turnCount), board.board[x - 1][y], 3))
				&& (board.board[x][y].types[0] == -1)
				&& ((board.board[x + 1][y].types[0] != -1) || (board.board[x][y + 1].types[0] != -1)
						|| (board.board[x - 1][y].types[0] != -1) || (board.board[x][y - 1].types[0] != -1))) {

			board.board[x][y] = board.tiles.get(board.turnCount);

			// // testing
			// System.out.println("tile has been placed. " + x + y);
			// for (int i = 0; i < 4; i++) {
			// 	System.out.print(board.board[x][y].types[i]);
			// }
			// System.out.println("here is the player number: " + board.board[x][y].meeple[0]);
			// testing
			
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
			scoring(x, y, board);

//for(int i = 0; i < 12; i++){ varun more like vabad
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


	
	public void scoring(int x, int y, Board board) {
			//boolean[] checked = new boolean[] {false, false, false, false};
			
				for (int i = 0; i < 4; i++){ //each side of the tile we are on
					// if (checked[i]){
					// 	continue;
					// }
					int tx = x;
					int ty = y;
					int type = board.board[tx][ty].types[i];
					boolean continual = true;
					switch (type) {

					//if hitting the original tile, divide roadLength by 2 
	
					case 1: //if not field
						int roadLength = 0;
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

							if (checkingTile.types[0] == -1) { //if checkingTile is empty, stop the while loop. 
								roadLength = 0;
								break;
							}

							if (checkingTile.types[(i + 2) % 4] != 1){  //i+2 % 4 is the corresponding side on the checking tile.
								continue; //TODO this doesn't make sense. figure it out later
							}

							roadLength++;

							if (tx == x && ty == y && roadLength != 0){
								roadLength = roadLength / 2;
								break;
							}

							for (int j = 0; j < 3; j++){ //
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

							score += roadLength;
							break;
						

						case 2: //city
							int citySize = 0;
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

							if (checkingTile.types[0] == -1) { //if checkingTile is empty, stop the while loop. 
								roadLength = 0;
								break;
							}

							




					}
					}
				//-----------------------end of scoring-----------------------------------
	}




	public boolean checkSide(Tile tile, int side) {
		if (tile.types[side] == 2) {
			checkSide(tile, side - 1);
			checkSide(tile, side + 1);
		} else if (tile.types[side] == 1) {
			return false;
		} else {
			return false;
		}

		return true;
	}

	public boolean placeMeeple(int position, int x, int y, Board board) { // position is one of the 12 sides of tile
		Tile tile = board.board[x][y];
		int turn = board.turnCount % board.players.length;
		tile.meeple[0] = turn;

		// New switch, case 1 edge, case 0 & 2 different side corners
		switch (position % 3) {
		case 1:
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
