package kmeans;
import java.util.*;
public class ReduxPoint {
	// Does not matter if DEFAULT_SIZE is too big, but 
	// it can't be too small	
	// I set it here to '3' to 'x' 'y' and 'z' but 
	// the value might as well be 2000
	public int DEFAULT_SIZE = 3; 
	public List < Double > vec = new ArrayList<>(); 
	public ReduxPoint(double[] points) {
		for ( int i = 0; i < points.length; i++) {
			vec.add(points[i]);
		}
	}
	public ReduxPoint() {

		for ( int i = 0; i < DEFAULT_SIZE; i++) {
			vec.add(0.0);
		}

		
	}
}
