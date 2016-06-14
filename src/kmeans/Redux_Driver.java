package kmeans;

public class Redux_Driver {

	public static void main(String...strings) {
		Redux_Driver test = new Redux_Driver(); 
		test.bigTest();
	}
	
	
	public void bigTest() { 
		int shape = 3; // number of dimensions in the observers and the attractors
		int maxDepth = 10; // max number of recurses
		ReduxShiftMeans rsm = new ReduxShiftMeans( shape, maxDepth ); 
		
		for ( int i = 0; i < 2000; i++ ) {
			double[] ary = new double[shape];
			ary[0] = Math.random() * 2 - 1;
			ary[1] = Math.random() * 2 - 1;
			ary[2] = Math.random() * 2 - 1;
			
			rsm.addPoints_step1(ary);
		}
		rsm.createAttractors_step2();
		rsm.recurse_prep_step3();
	//	rsm.describe();
	} 
	
}
