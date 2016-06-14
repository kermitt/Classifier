package kmeans;

public class ReduxPoint {
	public double[] vector;
	
	public Integer closest = -1;
	public Integer nextClosest = -1;
	public double closestDistance = Double.MAX_VALUE;
	public ReduxPoint(double[] vector) {
		this.vector = vector;
	}
	public void maybeReassign(double distance, Integer attractorId ){
		if ( distance < closestDistance ) {
			closestDistance = distance;
			closest = attractorId;
		}
	}
	public String describe() {
		String out  = closest + ":\t"; 
		for ( int i = 0; i < vector.length; i++ ) { 
			out += vector[i] + "|";
		}
		out += " :: " + closestDistance;
		return out;
	}
}
