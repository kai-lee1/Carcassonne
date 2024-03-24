package carcassonne;

public class Player {
	public int meepleCount;
	public int score;

	public Player() {
		this.meepleCount = 7;
		this.score = 0;
	}

	public void placeTile(Board board) {
		// System.out.println("how many times do you want to rotate tile? ");
		// int rotateans = Integer.parseInt(scan.nextLine());
		int x = Integer.parseInt(CarcassonneMain.inp.coordx.getText());
		int y = Integer.parseInt(CarcassonneMain.inp.coordy.getText());
		int rotateans = Integer.parseInt(CarcassonneMain.inp.rots.getText());
		rotateTile(board.turnCount, rotateans, board);
		System.out.println("new tile after rotating: ");
		for (int j = 0; j < 4; j++) {
			System.out.print(board.tiles.get(board.turnCount).types[j]);
		}
		System.out.println("");

		// score every time a new tile is placed
		if (x > 0 && y > 0 && (board.validSide(board.tiles.get(board.turnCount), board.board[x][y - 1], 0))
				&& (board.validSide(board.tiles.get(board.turnCount), board.board[x + 1][y], 1))
				&& (board.validSide(board.tiles.get(board.turnCount), board.board[x][y + 1], 2))
				&& (board.validSide(board.tiles.get(board.turnCount), board.board[x - 1][y], 3))
				&& (board.board[x][y].types[0] == -1)
				&& ((board.board[x + 1][y].types[0] != -1) || (board.board[x][y + 1].types[0] != -1)
						|| (board.board[x - 1][y].types[0] != -1) || (board.board[x][y - 1].types[0] != -1))) {

			board.board[x][y] = board.tiles.get(board.turnCount);

			// System.out.println("Place meeple? y/n");
			// String answer = scan.nextLine();

			boolean worked = false;
			int position;
			// System.out.println("Where? (0-11)");
			try {
				position = Integer.parseInt(CarcassonneMain.inp.meeple.getText());
			} catch (NumberFormatException e) {
				position = -1;
			}

			if (position != -1)
				worked = placeMeeple(position, x, y, board);
			
			else worked = true;
			
			while (!(worked)) {
				System.out.println("invalid meeple position. do you want to place a meeple? (y/n)");
				CarcassonneMain.inp.error.setText("invalid meeple position. Choose -1 to not place one.");
				CarcassonneMain.waitButton();
				if (Integer.parseInt(CarcassonneMain.inp.meeple.getText()) != -1) {
					System.out.println("Where? (0-11)");
					try {
						position = Integer.parseInt(CarcassonneMain.inp.meeple.getText());
					} catch (NumberFormatException e) {
						position = -1;
					}
					if (position != -1)
						worked = placeMeeple(position, x, y, board);
					
					else worked = true;
				} else {
					break;
				}

			}
			if (worked) {
				System.out.println("meeple has been placed");
				meepleCount--;
			}
			Scorer scorer = new Scorer(board, x, y);
			scorer.scoring(x, y, board);
			System.out.println("score of player 1: " + board.players[0].score);
			System.out.println("score of player 2: " + board.players[1].score);

		}

		else {
			System.out.println("invalid tile. try new tile coordinates");
			CarcassonneMain.inp.error.setText("Invalid tile. Try new coordinates");
			CarcassonneMain.waitButton();
			placeTile(board);

		}

	}

	public boolean placeMeeple(int position, int x, int y, Board board) { // position is one of the 12 sides of tile
		Tile tile = board.board[x][y];
		int turn = board.turnCount % board.players.length;
		tile.meeple[0] = turn;
		tile.meeple[14] = position;

		// New switch, case 1 edge, case 0 & 2 different side corners
		switch (position % 3) {
			case 1:
				tile.meeple[1] = board.board[x][y].types[position / 3];
				if ((tile.types[(position) / 3] == 2)) {
					// know it isn't 0 or 11
					tile.meeple[position + 2] = 1;
					tile.meeple[position + 1] = 1;
					tile.meeple[position + 3] = 1;
					for (int j = 0; j < 3; j++) {
						if (position / 3 < j) {
							if (board.board[x][y].connected[position / 3][j] && board.board[x][y].types[j + 1] == 2) {
								tile.meeple[(j + 1) * 3 + 2 + 1] = 1;
								tile.meeple[(j + 1) * 3 + 2] = 1;
								tile.meeple[(j + 1) * 3 + 2 + 2] = 1;
							}
						} else if (board.board[x][y].connected[position / 3][j] && board.board[x][y].types[j] == 2) {
							tile.meeple[j * 3 + 2 + 1] = 1;
							tile.meeple[j * 3 + 2] = 1;
							tile.meeple[j * 3 + 2 + 2] = 1;
						}
					}
				} else if ((tile.types[(position) / 3] == 1)) {
					tile.meeple[position + 2] = 1;
					for (int j = 0; j < 3; j++) {
						if (position / 3 < j) {
							if (board.board[x][y].connected[position / 3][j] && board.board[x][y].types[j + 1] == 1) {
								tile.meeple[(j + 1) * 3 + 2 + 1] = 1;
								break;
							}
						} else if (board.board[x][y].connected[position / 3][j] && board.board[x][y].types[j] == 1) {
							tile.meeple[j * 3 + 2 + 1] = 1;
							break;
						}
					}
				} else {
					// field
					board.fieldMeeples.add(new int[] {x, y});
					tile.meeple[position + 2] = 1;
					tile.meeple[position + 1] = 1;
					tile.meeple[position + 3] = 1;
					for (int j = 0; j < 3; j++) {
						if (position / 3 < j) {
							if (board.board[x][y].connected[position / 3][j] && board.board[x][y].types[j + 1] == 0) {
								tile.meeple[(j + 1) * 3 + 2 + 1] = 1;
								tile.meeple[(j + 1) * 3 + 2] = 1;
								tile.meeple[(j + 1) * 3 + 2 + 2] = 1;
							}
						} else if (board.board[x][y].connected[position / 3][j] && board.board[x][y].types[j] == 0) {
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
				board.fieldMeeples.add(new int[] {x, y});
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
				board.fieldMeeples.add(new int[] {x, y});
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

	// 0 1 2 3
	public void rotateTile(int turn, int rotate, Board board) { // rotating clockwise
		int[] old;
		boolean[] oldcon;
		boolean[][] oldcono;
		board.tiles.get(turn).rotations = board.tiles.get(turn).rotations + rotate;
		for (int i = 0; i < rotate; i++) {
			old = board.tiles.get(turn).types.clone();
			board.tiles.get(turn).types = new int[] { old[3], old[0], old[1], old[2] };

			for (int j = 0; j < 4; j++) {
				oldcon = board.tiles.get(turn).connected[j];
				if (j != 3)
					board.tiles.get(turn).connected[j] = new boolean[] { oldcon[2], oldcon[0], oldcon[1] };
			}

			oldcono = board.tiles.get(turn).connected;
			board.tiles.get(turn).connected = new boolean[][] { oldcono[3], oldcono[0], oldcono[1], oldcono[2] };
		}
	}

	/*
	 * if meeple is in a middle spot (1,4,7,10): check if that side is city (int
	 * divide by 3). if so, meeple is connected to its two adjacent spots. check the
	 * adjacent sides. if city, connect all three spots on that side and keep going
	 * around. if not, stop.
	 * 
	 * else if, check if the side is a road. if road, find the side that has the
	 * other end of the road, and connect the middle spot on that one. (unless
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
