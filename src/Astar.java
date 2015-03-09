import java.util.*;

public class Astar {
	
	public static final int UP = 0;
	public static final int DOWN = 1;
	public static final int RIGHT = 2;
	public static final int LEFT = 3;
	
	int boardDIM = 16;
	
	Tile[][] board;
	ArrayList<Tile> openList;
	ArrayList<Tile> closedList;
	
	Tile start;
	Tile target;
	public static final int targetx = 6;
	public static final int targety = 9;
	
	public Astar(int _x, int _y){
		
		initBoard();
		
		openList = new ArrayList<Tile>();
		closedList = new ArrayList<Tile>();
		
		start = board[_x][_y];
	}
	
	public ArrayList getPath(){

		Tile current;
		
		openList.add(start);
		
		while(!openList.isEmpty()){
			Collections.sort(openList, new TileCompare());
			
			// get the tile with the best F score
			current = openList.get(0);
			
			// remove this from the open list and add it t the closed list
			openList.remove(current);
			closedList.add(current);
			
			// check if the current tile is the target tile
			if(current.x == target.x && current.y == target.y){
				System.out.println("Found target");
				break;
			}
			//System.out.println("x: " + current.x + " y: " + current.y);
			
			// traverse throught the 4 adjacent tiles
			for(int i = -1; i < 2; i++){
				for(int j = -1; j < 2; j++){
					// skip the current and corner tiles
					if((i == 0 && j == 0) || (i != 0 && j != 0))
						continue;
					
					if(outOfBounds(current.x + i, current.y + j))
						continue;
					
					Tile n = board[current.x + i][current.y + j];
					
					// skip if its in the closed list or we can't walk there
					if(closedList.contains(n) || n.isWall) //!walkable(current, n))
						continue;
					
					if(!openList.contains(n)){
						openList.add(n);
						n.parent = current;
						n.updateHueristicts(start, target);
					}else if(tentativeG(current, n) < n.g){
						n.parent = current;
						n.updateHueristicts(start, target);
					}
				}
			}
		}
		
		System.out.println("Done calculating path.");
		return makePath();
	}
	
	public int tentativeG(Tile current, Tile n){
		return current.g + Tile.distance(current,  n);
		
	}
	
	public boolean walkable(Tile from, Tile to){
		int direction = getDirection(from, to);
		
		// now decide if that direction is possible
		switch(direction){
			case LEFT: return from.canGoLeft;
			case RIGHT: return from.canGoRight;
			case DOWN: return from.canGoDown;
			case UP: return from.canGoDown;
		}
		
		// we had an error finding direction if we get here
		return false;
	}
	
	public boolean outOfBounds(int i, int j){
		 return (i >= boardDIM || j >= boardDIM) ||
				(i < 0 || j < 0);
	}
	
	public int getDirection(Tile from, Tile to){
		// figure out what direction were trying to go
		if(from.y > to.y){
			return LEFT;
		}else if(from.y < to.y){
			return RIGHT;
		}else if(from.x < to.x){
			return DOWN;
		}else if(from.x > to.x){
			return UP;
		}
		
		// if we get here the from and to tile are not one tile away - problem
		System.out.println("Direction not found.");
		return -1;
	}
	
	public ArrayList<Integer> makePath(){
		ArrayList<Integer> path = new ArrayList<Integer>();
		Tile tile = target;
		while(tile.parent != null){
			path.add(getDirection(tile.parent, tile));
			tile = tile.parent;
		}
		
		// reverse it so we start from the beginning
		Collections.reverse(path);
		return path;
	}
	
	
	// 32 tick tile board
	public void initBoard(){
		
		// make a new board
		board = new Tile[boardDIM][boardDIM];
		for(int i = 0; i < boardDIM; i++){
			for(int j = 0; j < boardDIM; j++){
				board[i][j] = new Tile(i, j);
			}
		}
		
		// add the target - and keep track of this tile for later
		board[targetx][targety].isTarget = true;
		target = board[targetx][targety];
		
		// add left wall restrictions
		for(int i = 0; i < 10; i++){
			board[3 + i][3].isWall = true;
			board[3 + i][4].isWall = true;
		}
		
		// add top wall restrictions
		for(int i = 0; i < 11; i++){
			board[3][3 + i].isWall = true;
			board[4][3 + i].isWall = true;
		}
		
		// add bottom wall restrictions
		for(int i = 0; i < 7; i++){
			board[9][7 + i].isWall = true;
			board[10][7 + i].isWall = true;
		}
	}
	
	// prints the contents of the board
	private void printBoard(){
		for(int i = 0; i < boardDIM; i++){
			for(int j = 0; j < boardDIM; j++){
				System.out.print(board[i][j].isWall + ", ");
			}
			System.out.println();
		}
	}
	
	// print the contents of an array list
	public static void printArrayList(ArrayList<Integer> list){
		for(Integer i : list){
			switch(i){
				case UP: System.out.println("UP"); break;
				case DOWN: System.out.println("DOWN"); break;
				case RIGHT: System.out.println("RIGHT"); break;
				case LEFT: System.out.println("LEFT"); break;
			}
		}
	}
	
	public static void main(String[] args) {
		Astar astar = new Astar(6, 1);
		
		ArrayList<Integer> path = astar.getPath();
		
		printArrayList(path);
	}

}
