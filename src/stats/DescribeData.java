package stats;

import common.Caller;
import common.GeneralWriter;
import common.Library;
import common.PSVReader;
import kmeans._ReduxAttractor;
import kmeans.ReduxShiftMeans;

import java.util.*;

public class DescribeData extends PSVReader {


	public static void main(String... strings) {
		long t1 = System.currentTimeMillis();
		DescribeData desc = new DescribeData();
		String fullpath = Library.DATA_PATH + Library.CLUSTER_FILE;
		desc.read_psv(3000000, fullpath);
//		desc.standardOut();
		
		desc.anova();
		
		Caller.log("The end");
	}
	
	private void anova() { 
		
		Anova anova = new Anova();

//		double[][] observations = new double[][] {

//			{478,2109,18180,23829,18635,14579,13910,42803,21367,11958,2260,21077,4050,34870,8117,16371,16589,24026,26769,20532,19502,16917,10054,8208,3683,7791,14861,12875,14553,28035,14298,5723,13933,32581,13333,20010,5640,4987,11870,13886,6573,8678,13875,4694,2874,8817,6432,7073,6367,4906,3593,18441,10942,5340,2661,5329,9041,12578,4646,15833,5447,5238,4294,3915,8695,3911,14252,5346,3715,2008,3896,2831,3510,6129,2625,1231,3026,3241,4023,2217,1363,1331,2129,2102,4037,558,405,687,462,120,1,17,10,},
//			{8947,20173,27397,4859,21469,23863,1599,19834,9454,3715,6107,7412,13577,16213,13978,2365,4016,2003,2777,6401,14124,872,596,2575,4043,3428,825,408,2140,2500,1030,3561,192,2577,806,2695,247,94,366,269,84,1305,483,345,22,881,295,154,50,91,115,339,774,66,25,269,68,515,16,489,189,15,173,22,403,143,432,95,143,5,123,1,22,287,1,18,128,103,116,52,16,4,4,95,28,0,12,0,2,0,0,0,0,},

	//		{18180,23829,18635,14579,13910,42803,21367,11958,2260,21077,4050,34870,8117,16371,16589,24026,26769,20532,},
		//	{27397,4859,21469,23863,1599,19834,9454,3715,6107,7412,13577,16213,13978,2365,4016,2003,2777,6401,},

	//	};

		double[][] observations = new double[2][females.size()];
		
		for ( int i = 0; i < males.size(); i++ ) { 
			observations[0][i] = males.get(i);
		}
		for ( int i = 0; i < females.size(); i++ ) { 
			observations[1][i] = females.get(i);
		}
		
		
		String type = _Constants.P_FIVE_PERCENT;

		anova.populate_step1(observations, type);
		anova.findWithinGroupMeans_step2();
		anova.setSumOfSquaresOfGroups_step3();
		anova.setTotalSumOfSquares_step4();
		anova.setTotalSumOfSquares_step5();
		anova.divide_by_degrees_of_freedom_step6();

		double f_score = anova.fScore_determineIt_step7();

		double criticalNumber = anova.getCriticalNumber();

		String result = "The null hypothesis is supported! There is no especial difference in these groups. ";

		if (f_score > criticalNumber) {
			result = "The null hypothesis is rejected! These groups are different.";
		}
		Caller.note("Groups degrees of freedom: " + anova.getNumenator());
		Caller.note("Observations degrees of freedom: " + anova.getDenomenator());
		Caller.note("SSW_sum_of_squares_within_groups: " + anova.SSW_sum_of_squares_within_groups);
		Caller.note("SSB_sum_of_squares_between_groups: " + anova.SSB_sum_of_squares_between_groups);
		Caller.note("allObservationsMean: " + anova.allObservationsMean);
		Caller.note("Critical number: " + criticalNumber);
		Caller.note("*** F Score: " + f_score);
		Caller.note(result);

		
		
	}

	private void standardOut() { 
		String male = "";
		String female = "";
		//for ( int i = 0; i < males.size(); i++ ) { 
		for ( int i = 0; i < 2; i++ ) { 
			male += males.get(i) + ",";
			female += females.get(i) + ",";
		}
		Caller.note("\n\n");
		Caller.note("{" + male + "},");
		Caller.note("{" + female + "},");
	}
	
	int depth = 0;
	List < Double > males = new ArrayList<>(); 
	List < Double > females = new ArrayList<>(); 
	List < String > times = new ArrayList<>(); 

	@Override
	public void populate(String entry) {
		String headers = "when|seen|X|Y|Z|velocity|days_supply_count|patient_paid_amount|ingredient_cost_paid_amount|male|female|sex_other|ccs_22|ccs_24|ccs_29|ccs_other";

		try {
			String[] pieces = entry.split(Library.PIPE);
			if (pieces.length == 16) {
				String when = "" + Integer.parseInt(pieces[0]);
				Double m = Double.parseDouble(pieces[9]);
				Double f = Double.parseDouble(pieces[10]);
				
				males.add(m);
				females.add(f);
				times.add(when);
				// wilcoxon
				Caller.log("when " + when +  " m " + m +  " f " + f + " |" + entry);
			} else {
				Caller.note(pieces.length + " [DescribeData] Skipping " + entry);
			}
		} catch (Exception e) {
			Caller.log("ACK! " + entry + " \t" + e.getMessage());
			System.exit(0);
		}
	}
}