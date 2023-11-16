/**
 * @author Rory McGuire
 */


public class Disk implements Comparable<Disk> {
	private int radius;
	
	public Disk(int rad) {
		radius = rad;
	}
	
	@Override
	public int compareTo(Disk d) {
		return radius - d.radius;
	}
	
	@Override
	public String toString() {
		return "Radius=" + radius;
	}
}
