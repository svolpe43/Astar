import java.util.*;

public class Tile{
		
		// huerustic data
		public int h = Integer.MAX_VALUE;
		public int g = Integer.MAX_VALUE;
		public int f = Integer.MAX_VALUE;
		
		Tile parent = null;
		
		// location on board
		int x;
		int y;
		
		boolean isTarget = false;
		boolean isWall = false;
		
		boolean canGoUp = true;
		boolean canGoDown = true;
		boolean canGoRight = true;
		boolean canGoLeft = true;
		
		Tile(int _x, int _y){
			x = _x;
			y = _y;
		}
		
		public void updateHueristicts(Tile start, Tile target){
			g = distance(start, this);
			h = distance(this, target);
			f = g + h;
			
		}
		
		// calculates the distance between two Tiles based on Manahattan Distance
		static int distance(Tile from, Tile to){
			return Math.abs(from.x - to.x) + Math.abs(from.y - to.y);
		}
		
		
	}