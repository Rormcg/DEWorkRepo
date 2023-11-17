/**
 * @author Rory McGuire
 */


public class Disk implements Comparable<Disk> {
	private int radius;
	
	public static final int LARGEST = 30;
	
	public Disk(int rad) {
		if(rad > LARGEST) throw new IllegalArgumentException("rad must be smaller than Disk.Largest");
		radius = rad;
	}
	
	@Override
	public int compareTo(Disk d) {
		return radius - d.radius;
	}
	
	@Override
	public String toString() {
		//return "Radius=" + radius;
		return ""+radius;
	}
	
	public int getRadius() {
		return radius;
	}
}
