package kmeans;

import common.Caller;

public class Test_Redux {
	public static void main(String... strings) {
		Test_Redux tr = new Test_Redux();
		tr.recurse();
		tr.getNumberOfK();
	}
	public void getNumberOfK() { 
		// getNumberOfK() is, pretty much, (int)Math.log(x) * 2 ) 
		boolean isOk = true;
		int[] observationsCount = new int[] { 2,20,200,2000,20000,200000 };
		ReduxShiftMeans rsm = new ReduxShiftMeans();
		int[] expected = new int[] {2,5,10,15,19,24};
		for ( int i = 0; i < observationsCount.length; i++ ) { 
			int k = rsm.getNumberOfK( observationsCount[i]); 
			if ( k != expected[i]) {
				isOk = false;
			}
		}
		Caller.log( isOk ); 
	}

	public void recurse() {
		// hard to UnitTest recursive stuff :'(
		boolean isOk = false;
		try {
			ReduxShiftMeans rsm = new ReduxShiftMeans();
			rsm.doRecursiveCalc(10);
			isOk = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		Caller.log(isOk);

	}
}
