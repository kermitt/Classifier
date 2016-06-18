package kmeans;

import common.Caller;
import java.util.*;

public class ReduxShiftMeans {
	// Note: This collection will grow into the millions
	public Map<Integer, ReduxPoint> observations = new HashMap<>();
	public Map<Integer, ReduxAttractor> attractors = new HashMap<>();
	public int maxRecurseDepth;
	public int nextKey = 0;
	public int recursedDepthbser; // just for TDD record keeping
	public int shape; // how many dimensions the Observations and attractors get
	public int lastEpoch = 0;

	public ReduxShiftMeans(int shape, int maxRecurseDepth) {
		this.shape = shape;
		this.maxRecurseDepth = maxRecurseDepth;
		Caller.log("Shape: " + shape + " Depth: " + maxRecurseDepth);
	}
	
	// populate w/ observations
	public void addPoints_step1(double[] ary) {

		observations.put(nextKey, new ReduxPoint(ary));
		nextKey++;

	}

	// 1st time populate the attractors
	public void createAttractors_step2() {
		double count = Math.log(observations.size());
		count *= 3;
		int result = (int) count;
		if (result < 2) {
			result = 2;
		}
		Caller.log("COUNT: " + count );
		for (int i = 0; i < result; i++) {
			double[] d = new double[shape];
			for (int j = 0; j < shape; j++) {
				d[j] = Math.random() * 4 - 2;
			}
			attractors.put(i, new ReduxAttractor(i, d));
		}
		
	
	}


	public void recurse_prep_step3() {
		_recurse_epoch(0);
	}

	private void _recurse_epoch(int depth) {
		if (depth < maxRecurseDepth) {
			depth++;

			for (Integer oKey : observations.keySet()) {
				ReduxPoint o = observations.get(oKey);
				for (Integer aKey : attractors.keySet()) {
					ReduxAttractor a = attractors.get(aKey);
					double dist = _getDistance(a.vector, o.vector);
					o.maybeReassign(dist, aKey);
				}
			}

			for (Integer aKey : attractors.keySet()) {
				ReduxAttractor a = attractors.get(aKey);
			//	Caller.log(a.describe());
			}

			//Caller.note(" ... ");

			attractors = new HashMap<>();
			for (Integer oKey : observations.keySet()) {
				ReduxPoint o = observations.get(oKey);
				if (!attractors.containsKey(o.closest)) {
					attractors.put(o.closest, new ReduxAttractor(o.closest, o.vector));
				} else {
					attractors.get(o.closest).donate(o.vector);
				}
			}
			
			for ( Integer aKey : attractors.keySet()) {
				attractors.get(aKey).finalizer();
			}
			describe(depth);
			_recurse_epoch(depth);
		} else {
			lastEpoch = depth;
			Caller.note("Bottoming out @ depth " + depth);
		}
	}


	public void describe(int epoch) {
//		for (Integer oKey : observations.keySet()) {
//			ReduxPoint o = observations.get(oKey);
//			Caller.log(oKey + " -> " + o.closest + " with: " + o.closestDistance);
//		}
		Caller.log(" ......................... ");
		int i =0;
		for ( Integer aKey : attractors.keySet()) {
			ReduxAttractor a = attractors.get(aKey);
			Caller.log( epoch + "\t" + i + "   " +  a.id + " children " + a.count );
			i++; 
		}

	}

	public double _getDistance(double[] p1, double[] p2) {
		double value = 0.0;
		for (int i = 0; i < p1.length; i++) {
			value += Math.pow(p2[i] - p1[i], 2);
		}
		return Math.sqrt(value);
	}
}
