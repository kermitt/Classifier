package common.clustering;

import common.Caller;
import common.Library;
import common.PSVReader;
import common.Seen;
import kmeans.ReduxPoint;
import kmeans.ReduxShiftMeans;
import kmeans.Redux_ExampleUsage;

import java.util.*;

public class CreateClusters extends PSVReader {
	public Map<String, List<String>> time_chunks = new HashMap<>();
	private double[] X, Y, Z;
	private double mostX, mostY, mostZ;
	private double leastX, leastY, leastZ;

	public void setup() {
		
		int shape = 3; // number of dimensions in the observers and the attractors
		int maxDepth = 10; // max number of recurses
		kShiftMeans = new ReduxShiftMeans( shape, maxDepth ); 

		
		X = Library.getRiv(Library.most_drug_label_name.split(Library.PIPE)[2]);
		Y = Library.getRiv(Library.most_drug_group_description.split(Library.PIPE)[2]);
		Z = Library.getRiv(Library.most_gender_code.split(Library.PIPE)[2]);

		mostX = Double.MIN_VALUE;
		mostY = Double.MIN_VALUE;
		mostZ = Double.MAX_VALUE;

		leastX = Double.MIN_VALUE;
		leastY = Double.MIN_VALUE;
		leastZ = Double.MIN_VALUE;

		// String most_before_diagnosis =
		// "1304575|false|1.0,1.0,1.0,1.0,1.0,1.0,-1.0,-1.0,-1.0,-1.0,-1.0,-1.0,1.0,-1.0,-1.0,1.0,-1.0,-1.0,1.0,1.0,1.0,-1.0,-1.0,-1.0,-1.0,-1.0,-1.0,-1.0,-1.0,-1.0,1.0,1.0,1.0,1.0,1.0,-1.0,-1.0,-1.0,-1.0,-1.0,-1.0,-1.0,-1.0,-1.0,1.0,-1.0,-1.0,1.0,-1.0,-1.0";
		// String most_gender_code =
		// "1007935|m|1.0,-1.0,1.0,-1.0,-1.0,1.0,-1.0,-1.0,-1.0,1.0,-1.0,-1.0,1.0,-1.0,-1.0,-1.0,-1.0,1.0,-1.0,1.0,-1.0,-1.0,-1.0,1.0,1.0,1.0,1.0,-1.0,1.0,-1.0,1.0,-1.0,1.0,-1.0,1.0,-1.0,-1.0,1.0,1.0,1.0,1.0,1.0,-1.0,-1.0,-1.0,1.0,1.0,1.0,-1.0,1.0";
		// String most_drug_label_name = "28950|hydrocodone/acetaminophen
		// tablet|1.0,-1.0,1.0,-1.0,-1.0,1.0,-1.0,1.0,-1.0,1.0,-1.0,1.0,-1.0,-1.0,1.0,-1.0,1.0,-1.0,1.0,1.0,-1.0,1.0,1.0,1.0,-1.0,1.0,1.0,-1.0,1.0,-1.0,1.0,1.0,-1.0,-1.0,1.0,1.0,1.0,-1.0,-1.0,1.0,1.0,-1.0,1.0,-1.0,1.0,1.0,1.0,-1.0,-1.0,1.0";
		// String most_during_treatment =
		// "931782|true|1.0,1.0,-1.0,-1.0,1.0,-1.0,-1.0,-1.0,-1.0,-1.0,-1.0,-1.0,1.0,1.0,1.0,-1.0,-1.0,1.0,1.0,-1.0,-1.0,1.0,-1.0,-1.0,1.0,-1.0,1.0,1.0,-1.0,1.0,-1.0,1.0,-1.0,-1.0,-1.0,-1.0,1.0,1.0,-1.0,1.0,-1.0,1.0,-1.0,1.0,1.0,-1.0,1.0,-1.0,-1.0,-1.0";
		// String most_drug_group_description =
		// "132425|antihyperlipidemics|1.0,1.0,-1.0,-1.0,1.0,1.0,1.0,1.0,1.0,-1.0,1.0,-1.0,1.0,-1.0,-1.0,-1.0,-1.0,-1.0,-1.0,-1.0,-1.0,-1.0,1.0,1.0,-1.0,1.0,-1.0,1.0,1.0,-1.0,-1.0,1.0,1.0,1.0,-1.0,1.0,1.0,-1.0,1.0,-1.0,1.0,1.0,-1.0,1.0,1.0,1.0,-1.0,-1.0,1.0,1.0";
		// String most_after_cure =
		// "1070035|true|1.0,1.0,-1.0,1.0,1.0,1.0,1.0,-1.0,1.0,1.0,1.0,1.0,1.0,1.0,-1.0,1.0,-1.0,1.0,1.0,1.0,-1.0,1.0,-1.0,1.0,-1.0,-1.0,1.0,-1.0,-1.0,1.0,1.0,-1.0,1.0,1.0,-1.0,-1.0,-1.0,1.0,1.0,1.0,1.0,1.0,-1.0,-1.0,1.0,-1.0,-1.0,1.0,-1.0,1.0";

	}
	public ReduxShiftMeans kShiftMeans;
	public static void main(String... strings) {

		long t1 = System.currentTimeMillis();

		CreateClusters pc = new CreateClusters();
		pc.setup();
		String fullpath = Library.DATA_PATH + Library.ROLLUP_FILE;

		pc.read_psv(3000000, fullpath);
		long t2 = System.currentTimeMillis();
		

		pc.kShiftMeans.createAttractors_step2();
		pc.kShiftMeans.recurse_prep_step3();
		pc.kShiftMeans.describe(0);
		
		
		pc.display();
		
		Caller.context_note("The end in " + (t2 - t1) + " milsec ");
	}

	public void display() {
		for (String when : time_chunks.keySet()) {
			List<String> events = time_chunks.get(when);
			Caller.note(events.size() + " for " + when);
		}
		Caller.log("MostX : " + mostX + " Y: " + mostY + " Z: " + mostZ);
		Caller.log("leastX : " + leastX + " Y: " + leastY + " Z: " + leastZ);

	}
/*
	public void bigTest() { 
		
		for ( int i = 0; i < 2000; i++ ) {
			double[] ary = new double[shape];
			ary[0] = Math.random() * 2 - 1;
			ary[1] = Math.random() * 2 - 1;
			ary[2] = Math.random() * 2 - 1;
			
			kShiftMeans.addPoints_step1(ary);
		}
		kShiftMeans.createAttractors_step2();
		kShiftMeans.recurse_prep_step3();
	//	rsm.describe();
	}
*/
	
	int depth = 0;
	@Override
	public void populate(String entry) {
		String headers = "person_id|when|velocity|days_supply_count|patient_paid_amount|ingredient_cost_paid_amount|riv";

		try {
			// route() via parent's parent ( i.e., CommonPSVFormat.java )
			String[] pieces = entry.split(Library.PIPE);
			if (pieces.length == 7) {

				String person_id = pieces[0];
				int when = Integer.parseInt(pieces[1]);
				int velocity = Integer.parseInt(pieces[2]);
				int days_supply_count = Integer.parseInt(pieces[3]);
				int patient_paid_amount = Integer.parseInt(pieces[4]);
				int ingredient_cost_paid_amount = Integer.parseInt(pieces[5]);
				String riv_as_string = pieces[6];

				double[] riv = Library.getRiv(riv_as_string);

				double XX = Library.vectorCosineSimilarity(X, riv);
				double YY = Library.vectorCosineSimilarity(Y, riv);
				double ZZ = Library.vectorCosineSimilarity(Z, riv);
				
	//			ReduxPoint rp = new ReduxPoint( new double[] { XX,YY,ZZ });
				kShiftMeans.addPoints_step1(new double[]{XX,YY,ZZ});
				if (depth < 20) {
					Caller.log(depth + " when: " + when + "   " + person_id + "  X: " + XX + "   Y: " + YY + "    Z: " + ZZ);
				}
				depth++;

			} else {
				Caller.note("[PopulateConcepts] Skipping " + entry);
			}
		} catch (Exception e) {
			Caller.log("ACK! " + entry + " \t" + e.getMessage());
			System.exit(0);
		}
	}
}