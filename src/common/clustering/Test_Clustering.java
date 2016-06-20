package common.clustering;

import common.Caller;
import common.Library;
import common.PSVReader;
import common.Seen;

import java.util.*;

public class Test_Clustering {
	private TimeSeriesClustering tsc = new TimeSeriesClustering();
	private void setup() { 
		tsc.setup();
		String fullpath = Library.DATA_PATH + Library.ROLLUP_FILE;
		tsc.read_psv(3000000, fullpath);
	}
	
	public static void main(String... strings) {
		Test_Clustering test = new Test_Clustering();
		test.setup();
		test.test_time_chunks();
		Caller.log("The end");
	}
	public void test_time_chunks() { 
		String[] keys = tsc.getSortedKeys_forTimeChunks();
		for ( String when : keys ) { 
			Caller.log("When: " +  when + "|" + tsc.time_chunks.get( when ).size() + " with total " + tsc.depth);
		}
	} 
}