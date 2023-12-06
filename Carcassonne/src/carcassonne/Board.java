package carcassonne;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Board {
	public Tile[][] board;
	//-x x -y y
	public int[] extended;
	public Player[] players;
	public ArrayList<Tile> tiles;
	public int turnCount;
	
	public Board(int playerCount) {
		this.turnCount = 0;
		this.board = new Tile[145][145];
		this.extended = new int[]{0, 0, 0, 0};
		this.players = new Player[playerCount];
		this.generatePlayers(playerCount);
		this.generateBoard();
		this.tiles = new ArrayList<Tile>();
		this.generateTiles();
		
		
	}
	
	public void gameTime() {
		Scanner scan = new Scanner(System.in);
		while (this.turnCount < 66) {
			Tile current = tiles.get(turnCount);
			System.out.print
			int x = scan.nextInt();
			int y = scan.nextInt();
			System.out.println("Send x and y coordinates for your tile:");
			int turn = turnCount % players.length;
			
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
	public boolean validSide(Tile tile1, Tile tile2, int side) {
		if (tile2.types[side - 2] == -1) {
			return true;
		}
		return (tile2.types[side - 2] == tile1.types[side]);
	}
	
	public void generateTiles() {
		for (int i = 0; i < 1; i++) {
			tiles.add(new Tile("allcity"));
		}
		for (int i = 0; i < 4; i++) {
			tiles.add(new Tile("1not"));
		}
		for (int i = 0; i < 3; i++) {
			tiles.add(new Tile("1road"));
		}
		for (int i = 0; i < 5; i++) {
			tiles.add(new Tile("cornercity"));
		}
		for (int i = 0; i < 5; i++) {
			tiles.add(new Tile("cornercityroad"));
		}
		for (int i = 0; i < 3; i++) {
			tiles.add(new Tile("bowtiecity"));
		}
		for (int i = 0; i < 2; i++) {
			tiles.add(new Tile("2adjacentcity"));
		}
		for (int i = 0; i < 3; i++) {
			tiles.add(new Tile("bowtiefield"));
		}
		for (int i = 0; i < 5; i++) {
			tiles.add(new Tile("1edgecity"));
		}
		for (int i = 0; i < 3; i++) {
			tiles.add(new Tile("edgecityroadleft"));
		}
		for (int i = 0; i < 3; i++) {
			tiles.add(new Tile("edgecityroadright"));
		}
		for (int i = 0; i < 3; i++) {
			tiles.add(new Tile("edgecityroadt"));
		}
		for (int i = 0; i < 3; i++) {
			tiles.add(new Tile("edgecityroadstraight"));
		}
		for (int i = 0; i < 8; i++) {
			tiles.add(new Tile("straightroad"));
		}
		for (int i = 0; i < 9; i++) {
			tiles.add(new Tile("Lroad"));
		}
		for (int i = 0; i < 4; i++) {
			tiles.add(new Tile("Troad"));
		}
		for (int i = 0; i < 1; i++) {
			tiles.add(new Tile("+road"));
		}
		
		
		Collections.shuffle(tiles);
	}
}
