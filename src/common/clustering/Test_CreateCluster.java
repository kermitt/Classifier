package common.clustering;

import common.Caller;
import common.Library;
import common.PSVReader;
import common.Seen;

import java.util.*;

public class Test_CreateCluster {
	
	private CreateClusters createClusters; 
	private void setup() { 
		createClusters = new CreateClusters();
		String fullpath = Library.DATA_PATH + "test_rollup.psv";
		createClusters.read_psv(3000000, fullpath);		
	}
	public static void main(String... strings) {

		Test_CreateCluster tcc = new Test_CreateCluster(); 
		tcc.setup(); 
		
		
		tcc.test_timeSeries(); 
	}

	public void test_timeSeries() { 
		for ( String when : createClusters.time_chunks.keySet()) { 
			
			List < String > history = createClusters.time_chunks.get(when); 
			Caller.note( when + " | " + history.size() ) ; 
			
		}
	}
}