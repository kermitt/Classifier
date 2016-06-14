package kmeans;

public class ReduxAttractor {
	public int id;
	public double[] vector;
	public double count = 0;
	public ReduxAttractor( int id, double[] vector) {
		this.id = id;
		this.vector = vector;
		count = 1.0;
	}
	
	public void donate( double[] v ) {
		for ( int i = 0; i < v.length; i++ ) {
			vector[i] += v[i];
		}
		count++;
	}
	public void finalizer() {
		for ( int i = 0; i < vector.length; i++ ) {
			vector[i] /= count;
		}		
	}
	public String describe() {
		String out  = count + "   ATTRACTOR: " + id + ":\t"; 
		for ( int i = 0; i < vector.length; i++ ) { 
			out += vector[i] + "|";
		}
		out += "   " + vector.length;
		return out;
	}
}
