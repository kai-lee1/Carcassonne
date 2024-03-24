package carcassonne;

import java.util.ArrayList;
import java.util.HashMap;
import java.lang.Math;

public class Scorer {
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
		int[] meeplesPresent;
		int[] xy;
		int[] xyn;
		c: for (int i = 0; i < 4; i++) { // i is the side # of the just-placed tile
			int tx = x;
			int ty = y;
			int type = board.board[tx][ty].types[i];
			meeplesPresent = new int[board.players.length];
			this.tiletracker = new ArrayList<Tile>();

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
					switch (s) {
						case 0:
							xyn = new int[] { tx, ty - 1 };
							break;
						case 1:
							xyn = new int[] { tx + 1, ty };
							break;
						case 2:
							xyn = new int[] { tx, ty + 1 };
							break;
						case 3:
							xyn = new int[] { tx - 1, ty };
							break;
						case -1:
							xyn = new int[] { xy[0], xy[1] };
						default:
							xyn = new int[] { 0, 0 };
							break;
					}
					if (s != -1) {
						roadScore(xyn[0], xyn[1], board, meeplesPresent, (s + 2) % 4);
						checkedSides[s] = 1;
					}
					for (int k = 0; k < tiletracker.size(); k++) {
						if (tiletracker.get(k).types[0] == -1)
							continue c;
					}
					for (int k = 0; k < meeplesPresent.length; k++) { // score is actually
						if (meeplesPresent[k] == 1) {
							board.players[k].score += tiletracker.size();
						}
					}
					this.tiletracker = new ArrayList<Tile>();
					if (board.board[tx][ty].meeple[i * 3 + 1 + 2] == 1) {
						board.players[board.board[x][y].meeple[0]].meepleCount += 1;
						board.board[x][y].meeple = new int[] { -1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1 };
					}
					board.board[x][y].completion[i] = 1;
					sweepRoadMeeples(xy[0], xy[1], board, (i + 2) % 4);
					if (s != -1) {
						board.board[x][y].completion[s] = 1;
						sweepRoadMeeples(xyn[0], xyn[1], board, (s + 2) % 4);
					}
					break;

				case 2: // ------------------------------CITY-----------------------------------
					// get checkingTile coordinates, depending on which side of the current
					// tile we are checking
					// store in xy[]
					if (board.board[x][y].meeple[i * 3 + 2 + 1] == 1) {
						meeplesPresent[board.board[x][y].meeple[0]] = 1;
					}

					tiletracker.add(board.board[x][y]);

					ArrayList<Integer> sides = new ArrayList<Integer>();

					sides.add(i);

					for (int j = 0; j < 3; j++) {
						if (i > j) {
							if (board.board[x][y].connected[i][j] && board.board[x][y].types[j] == 2) {
								sides.add(j);
							}
						} else if (board.board[x][y].connected[i][j] && board.board[x][y].types[j + 1] == 2) {
							sides.add(j + 1);
						}
					}

					for (int j = 0; j < sides.size(); j++) {
						checkedSides[sides.get(j)] = 1;
						switch (sides.get(j)) {
							case 0:
								xy = new int[] { tx, y - 1 };
								break;
							case 1:
								xy = new int[] { tx + 1, y };
								break;
							case 2:
								xy = new int[] { tx, y + 1 };
								break;
							case 3:
								xy = new int[] { tx - 1, y };
								break;
							default:
								xy = new int[] { 0, 0 };
						}
						cityScore(xy[0], xy[1], board, meeplesPresent, (sides.get(j) + 2) % 4);
					}

					for (int k = 0; k < tiletracker.size(); k++) {
						if (tiletracker.get(k).types[0] == -1)
							continue c;
					}

					for (int k = 0; k < meeplesPresent.length; k++) {
						if (meeplesPresent[k] == 1) {
							board.players[k].score += tiletracker.size() * 2;
							// removing meeples after city finishes scoring
						}
					}
					this.tiletracker = new ArrayList<Tile>();
					if (board.board[tx][ty].meeple[i * 3 + 1 + 2] == 1) {
						board.players[board.board[x][y].meeple[0]].meepleCount += 1;
						board.board[x][y].meeple = new int[] { -1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1 };
					}
					board.board[x][y].completion[i] = CarcassonneMain.cityCount;
					for (int j = 0; j < sides.size(); j++) {
						switch (sides.get(j)) {
							case 0:
								xy = new int[] { tx, y - 1 };
								break;
							case 1:
								xy = new int[] { tx + 1, y };
								break;
							case 2:
								xy = new int[] { tx, y + 1 };
								break;
							case 3:
								xy = new int[] { tx - 1, y };
								break;
							default:
								xy = new int[] { 0, 0 };
						}
						board.board[x][y].completion[sides.get(j)] = CarcassonneMain.cityCount;
						sweepCityMeeples(xy[0], xy[1], board, (sides.get(j) + 2) % 4);
					}
					CarcassonneMain.cityCount++;
					break;

				case 0: // ------------------------------FARM-----------------------------------
					break;
				default:
					break;
			}
		}
		// -----------------------end of method-----------------------------------
	}

	public void sweepRoadMeeples(int x, int y, Board board, int previousSide) {
		int i = -1;
		board.board[x][y].completion[previousSide] = 1;

		if (board.board[x][y].meeple[previousSide * 3 + 1 + 2] == 1) {
			// if there is a meeple on checkingTile's road. meeple[1] is tile type that
			// meeple is on
			board.players[board.board[x][y].meeple[0]].meepleCount += 1;
			board.board[x][y].meeple = new int[] { -1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1 };
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

		if (i == -1) { // if hitting empty tile, end roadScore
			return;
		}

		board.board[x][y].completion[i] = 1;

		// get new checking tile coordinates, depending on which side of the current
		// tile (old checkingTile) we are checking
		int[] xy;
		switch (i) {
			case 0:
				xy = new int[] { x, y - 1 };
				break;
			case 1:
				xy = new int[] { x + 1, y };
				break;
			case 2:
				xy = new int[] { x, y + 1 };
				break;
			case 3:
				xy = new int[] { x - 1, y };
				break;
			default:
				xy = new int[] { 0, 0 };
				break;
		}

		if (board.board[xy[0]][xy[1]].types[0] == -1) { // if checkingTile is empty, add empty tile to tiletracker
			tiletracker.add(new EmptyTile());
		}

		if (x == this.x && y == this.y) { // if hitting the original tile, double count must
			// have happened (circle road). divide by 2
			return;
		}

		sweepRoadMeeples(xy[0], xy[1], board, (i + 2) % 4);
	}

	public void roadScore(int x, int y, Board board, int[] meeplesPresent, int previousSide) {
		int i = -1;

		if (board.board[x][y].meeple[previousSide * 3 + 1 + 2] == 1) {
			// if there is a meeple on checkingTile's road. meeple[1] is tile type that
			// meeple is on
			meeplesPresent[board.board[x][y].meeple[0]] = 1;
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

		if (i == -1) { // if hitting empty tile, end roadScore
			return;
		}

		// get new checking tile coordinates, depending on which side of the current
		// tile (old checkingTile) we are checking
		int[] xy;
		switch (i) {
			case 0:
				xy = new int[] { x, y - 1 };
				break;
			case 1:
				xy = new int[] { x + 1, y };
				break;
			case 2:
				xy = new int[] { x, y + 1 };
				break;
			case 3:
				xy = new int[] { x - 1, y };
				break;
			default:
				xy = new int[] { 0, 0 };
				break;
		}

		if (board.board[xy[0]][xy[1]].types[0] == -1) { // if checkingTile is empty, add empty tile to tiletracker
			tiletracker.add(new EmptyTile());
		}

		if (x == this.x && y == this.y) { // if hitting the original tile, double count must
			// have happened (circle road). divide by 2
			return;
		}

		roadScore(xy[0], xy[1], board, meeplesPresent, (i + 2) % 4);

	}

	public void sweepCityMeeples(int x, int y, Board board, int previousSide) {
		board.board[x][y].completion[previousSide] = CarcassonneMain.cityCount;

		if (board.board[x][y].meeple[previousSide * 3 + 1 + 2] == 1) {
			board.players[board.board[x][y].meeple[0]].meepleCount += 1;
			board.board[x][y].meeple = new int[] { -1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1 };
		}

		if (!tiletracker.contains(board.board[x][y]))
			tiletracker.add(board.board[x][y]);
		else
			return;

		ArrayList<Integer> sides = new ArrayList<Integer>();

		for (int j = 0; j < 3; j++) {

			if (previousSide <= j) {
				if (board.board[x][y].connected[previousSide][j] && board.board[x][y].types[j + 1] == 2) {
					sides.add(j + 1);
					break;
				}
			} else if (board.board[x][y].connected[previousSide][j] && board.board[x][y].types[j] == 2) {
				sides.add(j);
				break;
			}
		}

		if (sides.size() == 0)
			return;

		// get new checking tile coordinates, depending on which side of the current
		// tile (old checkingTile) we are checking
		int[] xy;
		for (int i = 0; i < sides.size(); i++) {
			int j = sides.get(i);
			switch (j) {
				case 0:
					xy = new int[] { x, y - 1 };
					break;
				case 1:
					xy = new int[] { x + 1, y };
					break;
				case 2:
					xy = new int[] { x, y + 1 };
					break;
				case 3:
					xy = new int[] { x - 1, y };
					break;
				default:
					xy = new int[] { 0, 0 };
					break;
			}
			if (board.board[xy[0]][xy[1]].types[0] == -1) { // if checkingTile is empty, add empty tile to tiletracker
				tiletracker.add(new EmptyTile());
				return;
			}
			board.board[x][y].completion[j] = CarcassonneMain.cityCount;
			sweepCityMeeples(xy[0], xy[1], board, (j + 2) % 4);
		}
	}

	public void cityScore(int x, int y, Board board, int[] meeplesPresent, int previousSide) {

		if (!tiletracker.contains(board.board[x][y]))
			tiletracker.add(board.board[x][y]);

		else
			return;

		if (board.board[x][y].meeple[previousSide * 3 + 1 + 2] == 1) {
			// if there is a meeple on checkingTile's road. meeple[1] is tile type that
			// meeple is on
			meeplesPresent[board.board[x][y].meeple[0]] = 1;
		}

		ArrayList<Integer> sides = new ArrayList<Integer>();

		for (int j = 0; j < 3; j++) {

			if (previousSide <= j) {
				if (board.board[x][y].connected[previousSide][j] && board.board[x][y].types[j + 1] == 2) {
					sides.add(j + 1);
					break;
				}
			} else if (board.board[x][y].connected[previousSide][j] && board.board[x][y].types[j] == 2) {
				sides.add(j);
				break;
			}
		}

		if (sides.size() == 0)
			return;

		// get new checking tile coordinates, depending on which side of the current
		// tile (old checkingTile) we are checking
		int[] xy;
		for (int i = 0; i < sides.size(); i++) {
			int j = sides.get(i);
			switch (j) {
				case 0:
					xy = new int[] { x, y - 1 };
					break;
				case 1:
					xy = new int[] { x + 1, y };
					break;
				case 2:
					xy = new int[] { x, y + 1 };
					break;
				case 3:
					xy = new int[] { x - 1, y };
					break;
				default:
					xy = new int[] { 0, 0 };
					break;
			}
			if (board.board[xy[0]][xy[1]].types[0] == -1) { // if checkingTile is empty, add empty tile to tiletracker
				tiletracker.add(new EmptyTile());
				return;
			}
			cityScore(xy[0], xy[1], board, meeplesPresent, (j + 2) % 4);
		}
	}

	public void endRoadScore(int x, int y, Board board) {
		int[] checkedSides = new int[] { 0, 0, 0, 0 };
		int[] xy;
		int[] xyn;
		c: for (int i = 0; i < 4; i++) { // i is the side # of the just-placed tile
			int tx = x;
			int ty = y;
			int type = board.board[tx][ty].types[i];
			this.tiletracker = new ArrayList<Tile>();

			if (checkedSides[i] == 1)
				continue c;

			if (type == 1 && board.board[x][y].meeple[i * 3 + 3] == 1) { // ------------------------------ROAD-----------------------------------
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
				if (!tiletracker.contains(board.board[x][y]))
					tiletracker.add(board.board[x][y]);

				roadScoreEnd(xy[0], xy[1], board, (i + 2) % 4);
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
				switch (s) {
					case 0:
						xyn = new int[] { tx, ty - 1 };
						break;
					case 1:
						xyn = new int[] { tx + 1, ty };
						break;
					case 2:
						xyn = new int[] { tx, ty + 1 };
						break;
					case 3:
						xyn = new int[] { tx - 1, ty };
						break;
					case -1:
						xyn = new int[] { xy[0], xy[1] };
					default:
						xyn = new int[] { 0, 0 };
						break;
				}
				if (s != -1) {
					roadScoreEnd(xyn[0], xyn[1], board, (s + 2) % 4);
					checkedSides[s] = 1;
				}
				for (int k = 0; k < tiletracker.size(); k++) {
					if (tiletracker.get(k).types[0] == -1) {
						tiletracker.remove(k);
						k--;
					}
				}
				board.players[board.board[x][y].meeple[0]].score += tiletracker.size();
				this.tiletracker = new ArrayList<Tile>();
				if (board.board[tx][ty].meeple[i * 3 + 1 + 2] == 1) {
					board.players[board.board[x][y].meeple[0]].meepleCount += 1;
					board.board[x][y].meeple = new int[] { -1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1 };
				}
			}
		}
	}

	private void roadScoreEnd(int x, int y, Board board, int previousSide) {
		int i = -1;

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

		if (i == -1) { // if hitting empty tile, end roadScore
			return;
		}

		// get new checking tile coordinates, depending on which side of the current
		// tile (old checkingTile) we are checking
		int[] xy;
		switch (i) {
			case 0:
				xy = new int[] { x, y - 1 };
				break;
			case 1:
				xy = new int[] { x + 1, y };
				break;
			case 2:
				xy = new int[] { x, y + 1 };
				break;
			case 3:
				xy = new int[] { x - 1, y };
				break;
			default:
				xy = new int[] { 0, 0 };
				break;
		}

		if (x == this.x && y == this.y) { // if hitting the original tile, double count must
			// have happened (circle road). divide by 2
			return;
		}

		roadScoreEnd(xy[0], xy[1], board, (i + 2) % 4);

	}

	public void endCityScore(int x, int y, Board board) {
		int[] checkedSides = new int[] { 0, 0, 0, 0 };
		int[] xy;
		c: for (int i = 0; i < 4; i++) { // i is the side # of the just-placed tile
			int tx = x;
			int ty = y;
			int type = board.board[tx][ty].types[i];
			this.tiletracker = new ArrayList<Tile>();

			if (checkedSides[i] == 1)
				continue c;

			if (type == 2 && board.board[x][y].meeple[i * 3 + 3] == 1) { // ------------------------------CITY-----------------------------------
				// get checkingTile coordinates, depending on which side of the current
				// tile we are checking
				// store in xy[]
				tiletracker.add(board.board[x][y]);

				ArrayList<Integer> sides = new ArrayList<Integer>();

				sides.add(i);

				for (int j = 0; j < 3; j++) {
					if (i > j) {
						if (board.board[x][y].connected[i][j] && board.board[x][y].types[j] == 2) {
							sides.add(j);
						}
					} else if (board.board[x][y].connected[i][j] && board.board[x][y].types[j + 1] == 2) {
						sides.add(j + 1);
					}
				}

				for (int j = 0; j < sides.size(); j++) {
					checkedSides[sides.get(j)] = 1;
					switch (sides.get(j)) {
						case 0:
							xy = new int[] { tx, y - 1 };
							break;
						case 1:
							xy = new int[] { tx + 1, y };
							break;
						case 2:
							xy = new int[] { tx, y + 1 };
							break;
						case 3:
							xy = new int[] { tx - 1, y };
							break;
						default:
							xy = new int[] { 0, 0 };
					}
					cityScoreEnd(xy[0], xy[1], board, (sides.get(j) + 2) % 4);
				}

				for (int k = 0; k < tiletracker.size(); k++) {
					if (tiletracker.get(k).types[0] == -1) {
						tiletracker.remove(k);
						k--;
					}
				}
				board.players[board.board[x][y].meeple[0]].score += tiletracker.size();
				this.tiletracker = new ArrayList<Tile>();
			}
		}
	}

	private void cityScoreEnd(int x, int y, Board board, int previousSide) {
		if (!tiletracker.contains(board.board[x][y]))
			tiletracker.add(board.board[x][y]);
		else
			return;

		ArrayList<Integer> sides = new ArrayList<Integer>();

		for (int j = 0; j < 3; j++) {

			if (previousSide <= j) {
				if (board.board[x][y].connected[previousSide][j] && board.board[x][y].types[j + 1] == 2) {
					sides.add(j + 1);
					break;
				}
			} else if (board.board[x][y].connected[previousSide][j] && board.board[x][y].types[j] == 2) {
				sides.add(j);
				break;
			}
		}

		if (sides.size() == 0)
			return;

		// get new checking tile coordinates, depending on which side of the current
		// tile (old checkingTile) we are checking
		int[] xy;
		for (int i = 0; i < sides.size(); i++) {
			switch (i) {
				case 0:
					xy = new int[] { x, y - 1 };
					break;
				case 1:
					xy = new int[] { x + 1, y };
					break;
				case 2:
					xy = new int[] { x, y + 1 };
					break;
				case 3:
					xy = new int[] { x - 1, y };
					break;
				default:
					xy = new int[] { 0, 0 };
					break;
			}
			cityScoreEnd(xy[0], xy[1], board, (i + 2) % 4);
		}
	}

	public void farmScore(int x, int y, Board board) {
		HashMap<Integer, int[]> visited = new HashMap<Integer, int[]>();
		int[] cities = new int[CarcassonneMain.cityCount];
		for (int i = 0; i < cities.length; i++) {
			cities[i] = 0;
		}
		for (int i = 0; i < board.board.length; i++) {
			for (int j = 0; j < board.board[i].length; j++) {
				visited.put(i * 1000 + j, new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
			}
		}

		// System.out.println(Arrays.toString(new int[] { x, y }));
		// System.out.println(Arrays.toString(board.board[x][y].connects(board.board[x][y].meeple[14])));

		farmScoreLoop(x, y, board, visited, board.board[x][y].meeple[14], cities);

		for (int i = 0; i < cities.length; i++) {
			if (cities[i] == 1) {
				board.players[board.board[x][y].meeple[0]].score += 3;
			}
		}
		// int[] connecteds = board.board[x][y].connects(board.board[x][y].meeple[14]);
		// for (int i = 0; i < connecteds.length; i++) {
		// 	if (connecteds[i] == 1) {
		// 		int newSide = oppositeSide(i);
				
		// 	}
		// }
	}

	private void farmScoreLoop(int x, int y, Board board, HashMap<Integer, int[]> visited, int pos, int[] cities) {
		if (x < 0 || y < 0 || x >= board.board.length || y >= board.board[0].length) {
			return;
		}

		if (board.board[x][y].types[0] == -1) {
			return;
		}

		if (visited.get(x * 1000 + y)[pos] == 1) {
			return;
		}

		visited.get(x * 1000 + y)[pos] = 1;

		for (int i = 0; i < 4; i++) {
			if (board.board[x][y].types[i] == 2 && fieldCityConnected(board.board[x][y], pos, i) && board.board[x][y].completion[i] != 0) {
				cities[board.board[x][y].completion[i]] = 1;
			}
		}

		int[] connecteds = board.board[x][y].connects(pos);
		for (int i = 0; i < connecteds.length; i++) {
			if (connecteds[i] == 1) {
				int newSide = oppositeSide(i);
				int[] xy;
				switch (i / 3) {
					case 0:
						xy = new int[] { x, y - 1 };
						break;
					case 1:
						xy = new int[] { x + 1, y };
						break;
					case 2:
						xy = new int[] { x, y + 1 };
						break;
					case 3:
						xy = new int[] { x - 1, y };
						break;
					default:
						xy = new int[] { 0, 0 };
						break;
				}
				farmScoreLoop(xy[0], xy[1], board, visited, newSide, cities);
			}
		}
	}

	private static boolean fieldCityConnected(Tile tile, int fieldPos, int citySide) {
		int[] connecteds = tile.connects(fieldPos);
		for (int i = 0; i < connecteds.length; i++) {
			if (connecteds[i] == 1 && (i + 1) % 3 == 0) {
				if (i / 3 < citySide) {
					if (tile.connected[i / 3][citySide - 1]) {
						return true;
					}
				}
			}
		}

		if (Math.abs((citySide * 3 + 1) - fieldPos) == 2) {
			return true;
		}

		if (fieldPos == 0 && citySide == 3) {
			return true;
		}
		
		return false;
	}

	private static int oppositeSide(int x) {  //opposite side on 12-point system
		switch (x){
			case 0, 1, 2, 6, 7, 8:
				return 8 - x;
			case 3, 4, 5, 9, 10, 11:
				return 14 - x;
			default:
				return -69420;
		}
	}
}
