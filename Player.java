import java.util.Scanner;

public class Player {
    public int meepleCount;


    public Player() {
		this.meepleCount = 7;
    }

    public boolean placeTile(int x, int y, Board board) {
        if ((board.validSide(board.tiles.get(board.turnCount), board.board[x][y-1], 0)) && 
            (board.validSide(board.tiles.get(board.turnCount), board.board[x+1][y], 1)) &&
            (board.validSide(board.tiles.get(board.turnCount), board.board[x][y+1], 2)) &&
            (board.validSide(board.tiles.get(board.turnCount), board.board[x-1][y], 3)) &&
            (board.board[x][y].types[0] == -1) &&
            ((board.board[x+1][y].types[0] != -1) || (board.board[x][y+1].types[0] != -1) || (board.board[x-1][y].types[0] != -1) || (board.board[x][y-1].types[0] != -1)) ) {
            
                board.board[x][y] = board.tiles.get(board.turnCount);
                System.out.println("tile has been placed. " + x + y + " the turn is " + board.tiles.get(board.turnCount));
                Scanner scan = new Scanner(System.in);
                System.out.println("Place meeple? y/n");
                String answer = scan.nextLine();
                if (answer.equals("y")) {
                    System.out.println("Where? (0-11)");
                    int position = scan.nextInt();
                    placeMeeple(position, x, y, board);
                }

            return true;
        }
        
        return false;

    }

    /*
     * 
     */
    public boolean checkSide(Tile tile, int side){
        if (tile.types[side] == 2) { 
            checkSide(tile, side-1);
            checkSide(tile, side+1);
        }
        else if (tile.types[side] == 1){
            return false;
        }
        else {return false; }

        return true;
    }


    public void placeMeeple(int position, int x, int y, Board board){ //position is one of the 12 sides of tile
        Tile tile = board.board[x][y];
        int turn = board.turnCount % board.players.length;
        tile.meeple[0] = turn; 
        
        
        switch(position) { //checking what sides meeple is connected to 
			case 1:
				if(tile.types[0] == 2 || tile.types[1] == 2){

                }
                else if (tile.types[0] == 1)
				break;
    }
    board.players[turn].meepleCount--;
    System.out.println("meeple has been placed");

}

}

    

