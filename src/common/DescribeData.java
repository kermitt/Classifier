package common;

import java.util.HashMap;
import java.util.Map;

import common.Caller;
import common.Library;
import common.PSVReader;

public class DescribeData extends PSVReader {
	/*
before_diagnosis|1304575|false|1.0,1.0,1.0,1.0,1.0,1.0,-1.0,-1.0,-1.0,-1.0,-1.0,-1.0,1.0,-1.0,-1.0,1.0,-1.0,-1.0,1.0,1.0,1.0,-1.0,-1.0,-1.0,-1.0,-1.0,-1.0,-1.0,-1.0,-1.0,1.0,1.0,1.0,1.0,1.0,-1.0,-1.0,-1.0,-1.0,-1.0,-1.0,-1.0,-1.0,-1.0,1.0,-1.0,-1.0,1.0,-1.0,-1.0 |	 ln: 51 c: common.DescribeData m: display
gender_code|1007935|m|1.0,-1.0,1.0,-1.0,-1.0,1.0,-1.0,-1.0,-1.0,1.0,-1.0,-1.0,1.0,-1.0,-1.0,-1.0,-1.0,1.0,-1.0,1.0,-1.0,-1.0,-1.0,1.0,1.0,1.0,1.0,-1.0,1.0,-1.0,1.0,-1.0,1.0,-1.0,1.0,-1.0,-1.0,1.0,1.0,1.0,1.0,1.0,-1.0,-1.0,-1.0,1.0,1.0,1.0,-1.0,1.0 |	 ln: 51 c: common.DescribeData m: display
drug_label_name|28950|hydrocodone/acetaminophen tablet|1.0,-1.0,1.0,-1.0,-1.0,1.0,-1.0,1.0,-1.0,1.0,-1.0,1.0,-1.0,-1.0,1.0,-1.0,1.0,-1.0,1.0,1.0,-1.0,1.0,1.0,1.0,-1.0,1.0,1.0,-1.0,1.0,-1.0,1.0,1.0,-1.0,-1.0,1.0,1.0,1.0,-1.0,-1.0,1.0,1.0,-1.0,1.0,-1.0,1.0,1.0,1.0,-1.0,-1.0,1.0 |	 ln: 51 c: common.DescribeData m: display
during_treatment|931782|true|1.0,1.0,-1.0,-1.0,1.0,-1.0,-1.0,-1.0,-1.0,-1.0,-1.0,-1.0,1.0,1.0,1.0,-1.0,-1.0,1.0,1.0,-1.0,-1.0,1.0,-1.0,-1.0,1.0,-1.0,1.0,1.0,-1.0,1.0,-1.0,1.0,-1.0,-1.0,-1.0,-1.0,1.0,1.0,-1.0,1.0,-1.0,1.0,-1.0,1.0,1.0,-1.0,1.0,-1.0,-1.0,-1.0 |	 ln: 51 c: common.DescribeData m: display
drug_group_description|132425|antihyperlipidemics|1.0,1.0,-1.0,-1.0,1.0,1.0,1.0,1.0,1.0,-1.0,1.0,-1.0,1.0,-1.0,-1.0,-1.0,-1.0,-1.0,-1.0,-1.0,-1.0,-1.0,1.0,1.0,-1.0,1.0,-1.0,1.0,1.0,-1.0,-1.0,1.0,1.0,1.0,-1.0,1.0,1.0,-1.0,1.0,-1.0,1.0,1.0,-1.0,1.0,1.0,1.0,-1.0,-1.0,1.0,1.0 |	 ln: 51 c: common.DescribeData m: display
after_cure|1070035|true|1.0,1.0,-1.0,1.0,1.0,1.0,1.0,-1.0,1.0,1.0,1.0,1.0,1.0,1.0,-1.0,1.0,-1.0,1.0,1.0,1.0,-1.0,1.0,-1.0,1.0,-1.0,-1.0,1.0,-1.0,-1.0,1.0,1.0,-1.0,1.0,1.0,-1.0,-1.0,-1.0,1.0,1.0,1.0,1.0,1.0,-1.0,-1.0,1.0,-1.0,-1.0,1.0,-1.0,1.0 |	 ln: 51 c: common.DescribeData m: display

	 * 
	 * 
	 */
	public static void main(String... strings) {
		long t1 = System.currentTimeMillis();
		DescribeData rc = new DescribeData();
		rc.setup();
		
		String fullpath = Library.DATA_PATH + Library.RIV_STORE;
			
		rc.read_psv(3000000, fullpath);
		long t2 = System.currentTimeMillis();
		
		rc.display();
		
		Caller.context_note("The end in " + (t2 - t1) + " milsec ");
	}
	
	private Map < String, Tuple> most = new HashMap<>();
	private void setup( ) {

		most.put("gender_code", new Tuple()); 
		most.put("drug_label_name", new Tuple()); 
		most.put("drug_group_description", new Tuple()); 
		most.put("after_cure", new Tuple()); 
		most.put("during_treatment", new Tuple()); 
		most.put("before_diagnosis", new Tuple()); 
	}
	
	private void display() { 
		Caller.log("DEPTH: " + depth );
		for ( String feature : most.keySet()) { 
			String out = ""; 
			Tuple t = most.get( feature ) ; 
			
			out += feature + "|" + t.value + "|" + t.what + "|" + t.riv;  
			Caller.log( out );
		}
		
	}
	
	private int depth = 0 ; 
	@Override
	public void populate(String entry) {
		try {
			String[] pieces = entry.split(Library.PIPE);
			if (pieces.length == 4) {
				int observations = Integer.parseInt(pieces[0]);
				String feature = pieces[1];
				String payload = pieces[2];
				String riv = pieces[3];
				if ( most.containsKey(feature)) { 					
					if ( most.get(feature).value < observations ) { 
						most.get(feature).update(payload, observations,riv);
						Caller.log("! YAY! " + entry );
					} 
				}
			
				depth++; 
				
			} 
		} catch (Exception e) {
			Caller.log("ACK! " + entry + " \t" + e.getMessage());
			System.exit(0);
		}
	}
	
	public class Tuple {
		public String what;
		public int value = Integer.MIN_VALUE; 
		public String riv; 
		public Tuple() {  
		}
		public void update(String what, int value, String riv ) {
			this.what = what; 
			this.value = value; 
			this.riv = riv; 
		}
	}
}
