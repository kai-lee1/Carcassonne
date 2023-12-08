package carcassonne;

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
            return true;
        }
        
        return false;

    }

    /*
     * for meeple in a corner - check if either of the adjacent edges is a city. if yes, meeple is part of city. then check what the city is connected to.
     * if neither of adjacent edges is city, check if they are roads.(run this check for both edges) if road, stop checking for that side.  
     * if field, then check next adjacent side and repeat.
     * 
     * for meeple on a side - 
     */

    public void placeMeeple(int position, int x, int y, Board board){ //position is one of the 8 sides of tile
        Tile tile = board.board[x][y];
        tile.meeple = new int[] {board.turnCount % board.players.length, };
    }

    
}
