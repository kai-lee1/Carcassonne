package carcassonne;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Board {
	public Tile[][] board;
	//-x x -y y
	public int[] extended;
	public Player[] players;  //playercount is players.size
	public ArrayList<Tile> tiles;
	public int turnCount;
	
	public Board(int playerCount) {
		this.turnCount = 0;
		this.board = new Tile[145][145]; //the game board
		this.extended = new int[]{0, 0, 0, 0};
		this.players = new Player[playerCount];
		this.generatePlayers(playerCount);
		this.generateBoard();
		this.tiles = new ArrayList<Tile>(); //draw pile
		this.generateTiles();

		
		
	}
	
	public void gameTime(Board board) {
		Scanner scan = new Scanner(System.in);
		while (this.turnCount < 66) {
			Tile current = tiles.get(turnCount);
			int turn = turnCount % players.length;
			System.out.print("Player " + turn + "'s turn. ");
			System.out.println("here is the current tile to be placed: ");
			for (int j = 0; j < 4; j++)
				System.out.print(board.tiles.get(board.turnCount).types[j]);
			System.out.println("");
			System.out.println("starting tile is 2101");
			System.out.println("Send x and y coordinates for your tile:");
			int x = Integer.parseInt(scan.nextLine());
			int y = Integer.parseInt(scan.nextLine());
			players[turn].placeTile(x, y, board);
			this.turnCount += 1;
			CarcassonneMain.drawGUI(board);
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
		for (int i = 0; i < 3; i++) {
			tiles.add(new Tile("bowtiefield")); //done
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
		for (int i = 0; i < 9; i++) {
			tiles.add(new Tile("Lroad")); //done
		}
		for (int i = 0; i < 4; i++) {
			tiles.add(new Tile("Troad")); //done
		}
		for (int i = 0; i < 1; i++) {
			tiles.add(new Tile("+road")); //done
		}
		
		
		Collections.shuffle(tiles);
	}
}
