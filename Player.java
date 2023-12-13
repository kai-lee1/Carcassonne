package carcassonne;
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

                //testing
                System.out.println("tile has been placed. " + x + y);
                for(int i=0; i < 4; i++){
                    System.out.print(board.board[x][y].types[i]);
                }
                System.out.println("here is some stuff " + board.board[x][y].meeple[0]);
                //testing

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
        else{ 
            System.out.println("invalid tile");
            return false;
        }
        

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
        
        // New switch, case 1 edge, case 0 & 2 different side corners
        switch(position % 3) {
            case 1:
                if ((tile.types[(position)/3] == 2)) {
                    tile.meeple[position + 2] = 1;
                    tile.meeple[position + 1] = 1;
                    tile.meeple[position + 3] = 1;
                    for (int i = 0; i < 3; i++) {
                        boolean cons = tile.connected[position/3][i];
                        if (cons) {
                            if (tile.types[i] == 2) {
                                tile.meeple[(position + 3 * i) % 12 + 2] = 1;
                                tile.meeple[(position + 3 * i + 1) % 12 + 2] = 1;
                                tile.meeple[(position + 3 * i - 1) % 12 + 2] = 1;
                            }
                        }
                    }
                }
                else if ((tile.types[(position)/3] == 1))
        }
        
        switch(position) { //checking what sides meeple is connected to 
			case 0, 2, 3, 5, 6, 8, 9, 11:
				if((tile.types[(position+1)/3] == 2) || (tile.types[(position-1)/3] == 2)){
                    //do something idk will figure out later
                    if(position == 0){
                        tile.meeple[2] = 1;
                        tile.meeple[3] = 1;
                        tile.meeple[13] = 1;

                    }
                    else if(position == 11){
                        tile.meeple[2] = 1;
                        tile.meeple[12] = 1;
                        tile.meeple[13] = 1;

                    }
                    else {
                    tile.meeple[position+2] = 1;
                    tile.meeple[position+1] = 1;
                    tile.meeple[position+3] = 1;
                    }
                }
				break;
            case 1, 4, 7, 10:

    }

    /*
     * if meeple is in a middle spot (1,4,7,10): 
	    check if that side is city (int divide by 3). 
        if so, meeple is connected to its two adjacent spots. check the adjacent sides. 
            if city, connect all three spots on that side and keep going around. if not, stop. 
	
	    else if, check if the side is a road. 
            if road, find the side that has the other end of the road, and connect the middle spot on that one. 
            (todo: unless the current tile is one of the blocked road ones)
	
	    else, the entire side must be a farm. 
            connect the adjacent two spots. check the adjacent sides. 
                if city, stop. 
                else if road, connect the next spot with meeple and then stop.
                else if farm, keep going around.

    if meeple is on a corner spot (0, 2, 3, 5, 6, 8, 9, 11):
	    check if side is a city (int divide by 3).     
            if so, meeple is part of city. connect to all other spots on the same side. check again for both adjacent sides. 
        else if road, check the adjacent side (only the closest one). 
            if city or road, stop. 
            else if farm, connect with all three on that side. check again for the side after that.
        else, the entire side must be a farm. 
            connect to all other spots on the same side. check again for both adjacent sides. 
     * 
     */
    board.players[turn].meepleCount--;
    System.out.println("meeple has been placed. remaining meeples: " + board.players[turn].meepleCount);

}

}

    

