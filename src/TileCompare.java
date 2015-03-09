import java.util.Comparator;


public class TileCompare implements Comparator<Tile>{

	@Override
	// -1 if less than
	// 0 if equal
	// 1 if greater than
	public int compare(Tile o1, Tile o2) {
		if(o1.f == Integer.MAX_VALUE || o2.f == Integer.MAX_VALUE)
			System.out.println("We got some bad F values. They were never updated");
		
		return o1.f - o2.f;
	}
}