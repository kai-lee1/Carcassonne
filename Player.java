package carcassonne;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;

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

		//score every time a new tile is placed
		if (x > 0 && y > 0 && (board.validSide(board.tiles.get(board.turnCount), board.board[x][y - 1], 0)) // if the
																											// tile is
																											// valid
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

				while (!(worked)) {
					System.out.println("invalid meeple position. do you want to place a meeple? (y/n)");
					answer = scan.nextLine();
					if (answer.equals("y")) {
						System.out.println("Where? (0-11)");
						position = Integer.parseInt(scan.nextLine());
						worked = placeMeeple(position, x, y, board);
					} else {
						break;
					}

				}
				if (worked) {
					System.out.println("meeple has been placed");
					meepleCount--;
				}
			}
			Scorer scorer = new Scorer(board, x, y);
			scorer.scoring(x, y, board);
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
					// for (int i = 1; i < 4; i++) {
					// if (tile.connected[position / 3][i - 1]) {
					// if (tile.types[(position / 3 + i) % 4] == 1) {
					// tile.meeple[(position + 3 * i) % 12 + 2] = 1;
					// }
					// }
					// }
					for (int j = 0; j < 3; j++) {
						if (position / 3 < j) {
							if (board.board[x][y].connected[position / 3][j] && board.board[x][y].types[j + 1] == 1) {
								tile.meeple[(j + 1) * 3 + 2] = 1;
								break;
							}
						} else if (board.board[x][y].connected[position / 3][j] && board.board[x][y].types[j] == 1) {
							tile.meeple[j * 3 + 2] = 1;
							break;
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
					return false;
				}
				if (tile.types[((position) / 3 + 1) % 3] == 1) {
					tile.meeple[position + 2] = 1;
					tile.meeple[(position + 1) % 12 + 2] = 1;
				} else {
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

class Scorer {
	public int score;
	public ArrayList<Tile> tiletracker;
	public Board board;
	public int x;
	public int y;

	public Scorer(Board board, int x, int y) {
		this.score = 0;
		this.tiletracker = new ArrayList<Tile>();
		this.board = board;
		this.x = x;
		this.y = y;
	}

	public void scoring(int x, int y, Board board) {
		// boolean[] checked = new boolean[] {false, false, false, false};
		int[] checkedSides = new int[] { 0, 0, 0, 0 };
		int[] meeplesPresent = new int[board.players.length];
		int[] xy;
		c: for (int i = 0; i < 4; i++) { // i is the side # of the just-placed tile
			int tx = x;
			int ty = y;
			int type = board.board[tx][ty].types[i];
			meeplesPresent = new int[board.players.length];

			if (checkedSides[i] == 1)
				continue c;

			switch (type) {

				case 1: // ------------------------------ROAD-----------------------------------
					switch (i) {
						case 0:
							xy = new int[] { tx, ty - 1 };
							break;
						case 1:
							xy = new int[] { tx + 1, ty };
							break;
						case 2:
							xy = new int[] { tx, ty + 1 };
							break;
						case 3:
							xy = new int[] { tx - 1, ty };
							break;
						default:
							xy = new int[] { 0, 0 };
							break;
					}
					if (board.board[tx][ty].meeple[i * 3 + 1 + 2] == 1) {
						// if there is a meeple on checkingTile's road. meeple[1] is tile type that
						// meeple is on
						meeplesPresent[board.board[tx][ty].meeple[0]] = 1;
					}
					if (!tiletracker.contains(board.board[x][y]))
						tiletracker.add(board.board[x][y]);

					roadScore(xy[0], xy[1], board, meeplesPresent, (i + 2) % 4);
					int s = -1; // i is the previousSide of just-placed tile, j is a counter for
					// board.board[x][y]
					for (int j = 0; j < 3; j++) {
						// if (board.board[x][y].connected[previousSide][j] &&
						// board.board[x][y].types[j] == 1) {
						if (i <= j) {
							if (board.board[x][y].connected[i][j] && board.board[x][y].types[j + 1] == 1) {
								s = j + 1;
								break;
							}
						} else if (board.board[x][y].connected[i][j] && board.board[x][y].types[j] == 1) {
							s = j;
							break;
						}

					}
					if (s == -1) {
						for (int k = 0; k < tiletracker.size(); k++) {
							if (tiletracker.get(k).types[0] == -1)
								continue c;
						}
						for (int k = 0; k < meeplesPresent.length; k++) { // score is actually changed
							if (meeplesPresent[k] == 1) {
								board.players[k].score += tiletracker.size();
							}
						}
						continue c;
					}
					switch (s) {
						case 0:
							xy = new int[] { tx, ty - 1 };
							break;
						case 1:
							xy = new int[] { tx + 1, ty };
							break;
						case 2:
							xy = new int[] { tx, ty + 1 };
							break;
						case 3:
							xy = new int[] { tx - 1, ty };
							break;
						default:
							xy = new int[] { 0, 0 };
							break;
					}
					roadScore(xy[0], xy[1], board, meeplesPresent, (s + 2) % 4);
					checkedSides[s] = 1;
					for (int k = 0; k < tiletracker.size(); k++) {
						if (tiletracker.get(k).types[0] == -1)
							continue c;
					}
					for (int k = 0; k < meeplesPresent.length; k++) { // score is actually
						if (meeplesPresent[k] == 1) {
							board.players[k].score += tiletracker.size();
						}
					}
					break;

				case 2: // ------------------------------CITY-----------------------------------
					int citySize = 0;

					// get checkingTile coordinates, depending on which side of the current
					// tile we are checking
					//store in xy[]
					switch (i) {
						case 0:
							xy = new int[] {tx, y - 1};
							break;
						case 1:
							xy = new int[] {tx + 1, y};
							break;
						case 2:
							xy = new int[] {tx, y + 1};
							break;
						case 3:
							xy = new int[] {tx - 1, y};
							break;
						default:
							xy = new int[] {0, 0};
					}

					if (board.board[x][y].meeple[i*3+2+1] == 1) { 
						meeplesPresent[board.board[x][y].meeple[0]] = 1;
					}

					if (board.board[xy[0]][xy[1]].types[0] == -1) { // if checkingTile is empty, stop the while loop.
						tiletracker.add(new EmptyTile());
						break;
					}

					tiletracker.add(board.board[x][y]);

					ArrayList<Integer> sides = new ArrayList<Integer>();

					for (int j = 0; j < 3; j++) {
						if (i > j) {
							if (board.board[x][y].connected[i][j] && board.board[x][y].types[j] == 2) {
								// check if any of the other 3 sides on checkingTile are cities, and connected to side i
								//if so, add that side number to sides
								sides.add(j);
							}
						} else {
							if (board.board[x][y].connected[i][j] && board.board[x][y].types[j + 1] == 2) {
								// check if any of the other 3 sides on checkingTile are cities1					1
								sides.add(j + 1);
							}
						}
					}

					// for (int k = 0; k < meeplesPresent.length; k++) {
					// 	if (meeplesPresent[k] == 1) {
					// 		board.players[k].score += (citySize * 2);
					// 	}
					// }
					break; // TODO why is this here?

				case 0: // ------------------------------FARM-----------------------------------
					break;
			}
		}
		// -----------------------end of method-----------------------------------
	}

	public void roadScore(int x, int y, Board board, int[] meeplesPresent, int previousSide) {
		int tx = x;
		int ty = y;
		int i = -1; //TODO whats i

		if (board.board[tx][ty].meeple[previousSide * 3 + 1 + 2] == 1) {
			// if there is a meeple on checkingTile's road. meeple[1] is tile type that
			// meeple is on
			meeplesPresent[board.board[tx][ty].meeple[0]] = 1;
		}

		for (int j = 0; j < 3; j++) {

			if (previousSide <= j) {
				if (board.board[x][y].connected[previousSide][j] && board.board[x][y].types[j + 1] == 1) {
					i = j + 1;
					break;
				}
			} else if (board.board[x][y].connected[previousSide][j] && board.board[x][y].types[j] == 1) {
				i = j;
				break;
			}
		}

		if (!tiletracker.contains(board.board[x][y]))
			tiletracker.add(board.board[x][y]);

		if (i == -1) { //if hitting empty tile, end roadScore
			return;
		}

		// get new checking tile coordinates, depending on which side of the current
		// tile (old checkingTile) we are checking
		Tile checkingTile;
		int[] xy;
		switch (i) {
			case 0:
				checkingTile = board.board[tx][y - 1];
				xy = new int[] { tx, ty - 1 };
				break;
			case 1:
				checkingTile = board.board[tx + 1][ty];
				xy = new int[] { tx + 1, ty };
				break;
			case 2:
				checkingTile = board.board[tx][ty + 1];
				xy = new int[] { tx, ty + 1 };
				break;
			case 3:
				checkingTile = board.board[tx - 1][ty];
				xy = new int[] { tx - 1, y };
				break;
			default:
				checkingTile = board.board[0][0];
				xy = new int[] { 0, 0 };
				break;
		}

		if (checkingTile.types[0] == -1) { // if checkingTile is empty, add empty tile to tiletracker
			tiletracker.add(new EmptyTile());
		}

		if (x == this.x && y == this.y) { // if hitting the original tile, double count must
			// have happened (circle road). divide by 2
			return;
		}

		roadScore(xy[0], xy[1], board, meeplesPresent, (i + 2) % 4);

	}

	public void cityScore(int x, int y, Board board, int[] meeplesPresent, int previousSide) {
		int tx = x;
		int ty = y;
		int i = -1; //TODO whats i

		if (board.board[tx][ty].meeple[previousSide * 3 + 1 + 2] == 1) {
			// if there is a meeple on checkingTile's road. meeple[1] is tile type that
			// meeple is on
			meeplesPresent[board.board[tx][ty].meeple[0]] = 1;
		}

		for (int j = 0; j < 3; j++) {

			if (previousSide <= j) {
				if (board.board[x][y].connected[previousSide][j] && board.board[x][y].types[j + 1] == 1) {
					i = j + 1;
					break;
				}
			} else if (board.board[x][y].connected[previousSide][j] && board.board[x][y].types[j] == 1) {
				i = j;
				break;
			}
		}

		if (!tiletracker.contains(board.board[x][y]))
			tiletracker.add(board.board[x][y]);

		if (i == -1) { //if hitting empty tile, end roadScore
			return;
		}

		// get new checking tile coordinates, depending on which side of the current
		// tile (old checkingTile) we are checking
		Tile checkingTile;
		int[] xy;
		switch (i) {
			case 0:
				checkingTile = board.board[tx][y - 1];
				xy = new int[] { tx, ty - 1 };
				break;
			case 1:
				checkingTile = board.board[tx + 1][ty];
				xy = new int[] { tx + 1, ty };
				break;
			case 2:
				checkingTile = board.board[tx][ty + 1];
				xy = new int[] { tx, ty + 1 };
				break;
			case 3:
				checkingTile = board.board[tx - 1][ty];
				xy = new int[] { tx - 1, y };
				break;
			default:
				checkingTile = board.board[0][0];
				xy = new int[] { 0, 0 };
				break;
		}

		if (checkingTile.types[0] == -1) { // if checkingTile is empty, add empty tile to tiletracker
			tiletracker.add(new EmptyTile());
		}

		if (x == this.x && y == this.y) { // if hitting the original tile, double count must
			// have happened (circle road). divide by 2
			return;
		}

		roadScore(xy[0], xy[1], board, meeplesPresent, (i + 2) % 4);

	}

}
