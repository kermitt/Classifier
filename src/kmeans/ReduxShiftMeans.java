package kmeans;

import common.Caller;
import java.util.*;

public class ReduxShiftMeans {
	//Note: This collection will grow into the millions
	private Map < Integer, ReduxPoint > observations = new HashMap<>(); 
	private Map < Integer, ReduxPoint > attractors = new HashMap<>(); 

	
	private int NEXT_KEY = 0; 
	
	int maxRecurseDepth;
	///////////////////////////////////////
	
	public void doRecursiveCalc( int maxRecurseDepth ) {
		int number_of_diminsions = observations.get(0).vec.size();
//		observations.put(NEXT_KEY, new ReduxPoint(ary));
		
		
		this.maxRecurseDepth = maxRecurseDepth;
		calc(0);
	}


	
	public void calc(int depth) {
		if ( ++depth < maxRecurseDepth ) {
			calc( depth ); 
		} else { 
			Caller.note("Bottoming out @ depth " + depth ); 
		}
	}
	
	public void add( double[] ary ) {
		observations.put(NEXT_KEY, new ReduxPoint(ary));
		NEXT_KEY++; 
	}

	///////// Helper methods follow //////////////
	public int getNumberOfK(int observations) { 
		/* Set how many attractors will be needed */
		double count = Math.log(observations);
		count *= 2; 
		int result = (int)count;
		if ( result < 2 ) {
			result = 2; 
		}
		return result; 
	}

	public double getDistance(ReduxPoint p1, ReduxPoint p2) {		
		double value = 0.0;
		for ( int i = 0; i < p1.vec.size(); i++ ) {
			value += Math.pow((p2.vec.get(i) - p1.vec.get(i)), 2);
		}
		return Math.sqrt(value);
	}
}
