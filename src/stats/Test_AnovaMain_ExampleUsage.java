package stats;

/*
 * Numbers for the tests are from: https://www.youtube.com/watch?v=-yQb_ZJnFXw
 * How To Calculate and Understand Analysis of Variance (ANOVA) F Test.
 * User: statisticsfun
 * 
 * The distribution tables are from: http://www.socr.ucla.edu/applets.dir/f_table.html
 * 
 * This is a simple 1 way ANOVA. 
 * If the calculated Fscore is larger than the critial number then reject the null hypothesis.
 * 
 */

public class Test_AnovaMain_ExampleUsage {

	public static void main(String... strings) {

		Anova anova = new Anova();

		/*
		 * double[][] observations = new double[][] { {2,3,7,2,6},
		 * {10,8,7,5,10}, {10,13,14,13,15}, };
		 */
		/*
		 * double[][] observations = new double[][] { {2,3,7,2,6},
		 * {10,8,7,5,10,10,8,7,5,10}, {2,3,7,2,6}, };
		 */

		double[][] observations = new double[][] {

//			{478,2109,18180,23829,18635,14579,13910,42803,21367,11958,2260,21077,4050,34870,8117,16371,16589,24026,26769,20532,19502,16917,10054,8208,3683,7791,14861,12875,14553,28035,14298,5723,13933,32581,13333,20010,5640,4987,11870,13886,6573,8678,13875,4694,2874,8817,6432,7073,6367,4906,3593,18441,10942,5340,2661,5329,9041,12578,4646,15833,5447,5238,4294,3915,8695,3911,14252,5346,3715,2008,3896,2831,3510,6129,2625,1231,3026,3241,4023,2217,1363,1331,2129,2102,4037,558,405,687,462,120,1,17,10,},
//			{8947,20173,27397,4859,21469,23863,1599,19834,9454,3715,6107,7412,13577,16213,13978,2365,4016,2003,2777,6401,14124,872,596,2575,4043,3428,825,408,2140,2500,1030,3561,192,2577,806,2695,247,94,366,269,84,1305,483,345,22,881,295,154,50,91,115,339,774,66,25,269,68,515,16,489,189,15,173,22,403,143,432,95,143,5,123,1,22,287,1,18,128,103,116,52,16,4,4,95,28,0,12,0,2,0,0,0,0,},

			{18180,23829,18635,14579,13910,42803,21367,11958,2260,21077,4050,34870,8117,16371,16589,24026,26769,20532,},
			{27397,4859,21469,23863,1599,19834,9454,3715,6107,7412,13577,16213,13978,2365,4016,2003,2777,6401,},

		};

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
		System.out.println("Groups degrees of freedom: " + anova.getNumenator());
		System.out.println("Observations degrees of freedom: " + anova.getDenomenator());
		System.out.println("SSW_sum_of_squares_within_groups: " + anova.SSW_sum_of_squares_within_groups);
		System.out.println("SSB_sum_of_squares_between_groups: " + anova.SSB_sum_of_squares_between_groups);
		System.out.println("allObservationsMean: " + anova.allObservationsMean);
		System.out.println("Critical number: " + criticalNumber);
		System.out.println("*** F Score: " + f_score);
		System.out.println(result);
	}
}
