package carcassonne;

public class Player {
    public Player() {
		
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

    
}
