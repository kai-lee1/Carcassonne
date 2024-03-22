package carcassonne;
import java.util.ArrayList;
import java.util.Collections;

public class Board {
	public Tile[][] board;
	//-x x -y y
	public int[] extended;
	public Player[] players;  //playercount is players.size
	public ArrayList<Tile> tiles;
	public int turnCount;
	public ArrayList<int[]> fieldMeeples;
	
	public Board(int playerCount) {
		this.turnCount = 0;
		this.board = new Tile[145][145]; //the game board
		this.extended = new int[]{0, 0, 0, 0};
		this.players = new Player[playerCount];
		this.generatePlayers(playerCount);
		this.generateBoard();
		this.tiles = new ArrayList<Tile>(); //draw pile
		this.fieldMeeples = new ArrayList<int[]>();
		this.generateTiles();		
	}
	
	public void gameTime(Board board) {
		while (this.turnCount < 66) {
			int turn = turnCount % players.length;
			System.out.print("Player " + turn + "'s turn. ");
			CarcassonneMain.currentTile = board.tiles.get(board.turnCount);
			CarcassonneMain.drawGUI(board);
			CarcassonneMain.waitButton();
			if (CarcassonneMain.end){
				break;
			}
			// System.out.println("");
			// System.out.println("Send x and y coordinates for your tile:");
			// int x = Integer.parseInt(scan.nextLine());
			// int y = Integer.parseInt(scan.nextLine());
			players[turn].placeTile(board);
			this.turnCount += 1;
		}
		endGame(board);
	}

	public void endGame(Board board){
		for (int i = 0; i < board.board.length; i++) {
			for (int j = 0; j < board.board[i].length; j++){
				Tile current = board.board[i][j];
				int[] meeplesPresent = new int[board.players.length];

				if (current.types[0] != -1 && current.meeple[1] == 0){ //if current is not empty and has meeple
					if(current.meeple[1] == 0){ //if meeple is on farm
						for (int k = 0; k < 4; k++){
							endGameField(i, j, board, meeplesPresent, k); //call endGameField on each side of the tile
							//TODO k may be wrong here
							//its supposed to be previousSide
						}
					}
					if(current.meeple[1] == 1){ //if meeple is on city
						for (int k = 0; k < 4; k++){
							endGameCity(i, j, board, meeplesPresent);
						}
					}
					if(current.meeple[1] == 2){ //if meeple is on farm
						// ... endGameRoad(i, j, board, meeplesPresent);
					}
				}
			}
		}
		System.out.println("game has been ended");
	}

	public ArrayList<Integer> sidescreator (int x, int y, Board board, int previousSide){
		ArrayList<Integer> sides = new ArrayList<Integer>();

		for (int i = 0; i < 3; i++) {  
			if (previousSide <= i) {
				if (board.board[x][y].connected[previousSide][i] && board.board[x][y].types[i + 1] == 0) {
					sides.add(i + 1);
					break;
				}
			} 
			
			else if (board.board[x][y].connected[previousSide][i] && board.board[x][y].types[i] == 0) {
				sides.add(i);
				break;
			}
		}

		return sides;
	}

	public ArrayList<Integer> meeplesidescreator (int x, int y, Board board, int previousMeepleSide) {
		ArrayList<Integer> meeplesides = new ArrayList<Integer>();
		
		for (int i = 0; i < 12; i++) {
			if (previousMeepleSide <= i) {
				if (board.board[x][y].meeple[i+2] == 1 && board.board[x][y].types[i + 1] == 0) {
					meeplesides.add(i + 1); //if previousSide connected to side i, and 
					break;
				}
			} 
			
			else if (board.board[x][y].meeple[i+2] == 1 && board.board[x][y].types[i] == 0) {
				meeplesides.add(i);   //if previousMeepleSide connected to side i, and i is farm, add side
				break;
			}
		}
		return meeplesides;
	}

	public void endGameField(int x, int y, Board board, int[] meeplesPresent, int previousSide) { 
		ArrayList<Integer> sides = new ArrayList<Integer>();
		ArrayList<Integer> meeplesides = new ArrayList<Integer>();
		ArrayList<Integer> cities = new ArrayList<Integer>();

		sides = sidescreator(x, y, board, previousSide);
		//sides contains all the sides on current that are connected to previousSide
		//uses connected[]

		
		int previousMeepleSide = board.board[x][y].meeple[14];
		meeplesides = meeplesidescreator(x, y, board, previousMeepleSide);
		//meeplesides contains all 12-point sides on current that are connected to meeple
		//uses meeple[]

		if (sides.size() == 0 || meeplesides.size() == 0){
			return; }

	
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
		

		for (int j = 0; j < meeplesides.size(); j++){
			int newside = oppositeSide(meeplesides.get(j));
			board.board[145][145] = board.board[xy[0]][xy[1]]; //using [145][145] as temp tile
			if(board.players[0].placeMeeple(newside, 145, 145, board)){ //if placing meeple worked
				
			}
			
			else{System.out.println("oops");}
			
		}
	}

		for (int i = 0; i < 4; i++){
			
			if (board.board[x][y].types[i] == 2 && board.board[x][y].completion[i] != 0){
				cities.add(board.board[x][y].completion[i]); 
				board.players[board.board[x][y].meeple[0]].score += 3; //give current player +3 pts
			}
		}

}

	public void endGameRoad(int x, int y, Board board){
		Scorer s = new Scorer(board, x, y);
		s.endRoadScore(x, y, board);
		return;
	}

	public void endGameCity(int x, int y, Board board, int[] meeplesPresent){
		return;
	}

	public static int oppositeSide(int x) {  //opposite side on 12-point system
		switch (x){
			case 0, 1, 2, 6, 7, 8:
				return 8 - x;
			case 3, 4, 5, 9, 10, 11:
				return 14 - x;
			default:
				return -69420;
		}
	}


	
	public void generatePlayers(int playerCount) {
		for (int i = 0; i < playerCount; i++) {
			this.players[i] = new Player();
		}
	}
	
	public void generateBoard() {
		for (int i = 0; i < 133; i++) {
			for (int j = 0; j < 133; j++) {
				this.board[i][j] = (Tile) new EmptyTile();
			}
		}
		this.board[66][66] = new Tile("edgecityroadstraight");
	}
	
	// if tile1's side matches that of tile2
	public int otherSide(int side){
		switch (side) {
			case 0:
				return 2;	
			case 1:
				return 3;
			case 2: 
				return 0;
			case 3:
				return 1;
		}
		return -1000000; //idk
	}

	public boolean validSide(Tile tile1, Tile tile2, int side) {
		if (tile2.types[otherSide(side)] == -1) {
			return true;
		}
		return (tile2.types[otherSide(side)] == tile1.types[side]);
	}
	
	public void generateTiles() {
		for (int i = 0; i < 1; i++) {
			tiles.add(new Tile("Lroad")); //done
		}
		for (int i = 0; i < 4; i++) {
			tiles.add(new Tile("Troad")); //done
		}
		for (int i = 0; i < 3; i++) {
			tiles.add(new Tile("bowtiefield")); //done
		}
		for (int i = 0; i < 8; i++) {
			tiles.add(new Tile("Lroad")); //done
		}
		for (int i = 0; i < 3; i++) {
			tiles.add(new Tile("edgecityroadstraight")); //done
		}
		for (int i = 0; i < 1; i++) {
			tiles.add(new Tile("allcity")); //done
		}
		for (int i = 0; i < 4; i++) {
			tiles.add(new Tile("1not"));  //done
		}
		for (int i = 0; i < 3; i++) {
			tiles.add(new Tile("1road")); //done
		}
		for (int i = 0; i < 5; i++) {
			tiles.add(new Tile("cornercity")); //done
		}
		for (int i = 0; i < 5; i++) {
			tiles.add(new Tile("cornercityroad")); //done
		}
		for (int i = 0; i < 3; i++) {
			tiles.add(new Tile("bowtiecity")); //done
		}
		for (int i = 0; i < 2; i++) {
			tiles.add(new Tile("2adjacentcity")); //done
		}
		for (int i = 0; i < 5; i++) {
			tiles.add(new Tile("1edgecity")); //done
		}
		for (int i = 0; i < 3; i++) {
			tiles.add(new Tile("edgecityroadleft")); //done 
		}
		for (int i = 0; i < 3; i++) {
			tiles.add(new Tile("edgecityroadright")); //done
		}
		for (int i = 0; i < 3; i++) {
			tiles.add(new Tile("edgecityroadt")); //done
		}
		for (int i = 0; i < 8; i++) {
			tiles.add(new Tile("straightroad")); //done
		}
		for (int i = 0; i < 1; i++) {
			tiles.add(new Tile("+road")); //done
		}
		
		
		Collections.shuffle(tiles);
	}
}