package common;

import java.text.DecimalFormat;

public class Library {
//	public static String DATA_FOR_REAL = "female.csv"; // breast, melonoma,

	public static String DATA_FOR_REAL = "22_24_29.csv"; // breast, melonoma,
															// prostate
	public static String DATA_FOR_TEST = "head.csv"; // test data local
	public static String PIPE = "\\|";
	public static String DATA_PATH = "C://sites//healthpath//data//";
	public static String RIV_STORE = "22_24_29_RIV_STORE.psv";
	public static String ROLLUP_FILE = "22_24_29_rollup.psv";
	public static String CLUSTER_FILE = "22_24_29_clusters.psv";

	public static double vectorCosineSimilarity(double[] a, double[] b) {
		double dotProduct = 0.0;
		double magnitude1 = 0.0;
		double magnitude2 = 0.0;
		double cosineSimilarity = 0.0;

		double[] control = new double[a.length];
		double[] vector = new double[b.length];
		for (int i = 0; i < a.length; i++) {
			control[i] = (double) a[i];
			vector[i] = (double) b[i];
		}

		for (int i = 0; i < control.length; i++) {
			dotProduct += control[i] * vector[i]; // a.b
			magnitude1 += Math.pow(control[i], 2); // (a^2)
			magnitude2 += Math.pow(vector[i], 2); // (b^2)
		}

		magnitude1 = Math.sqrt(magnitude1);// sqrt(a^2)
		magnitude2 = Math.sqrt(magnitude2);// sqrt(b^2)

		if (magnitude1 != 0.0 | magnitude2 != 0.0) {
			cosineSimilarity = dotProduct / (magnitude1 * magnitude2);
		} else {
			return 0;
		}
		return cosineSimilarity;
	}

	public static String _join(double[] ary) {
		String out = "";
		for (int i = 0; i < ary.length; i++) {
			if (i < ary.length - 1) {
				out += ary[i] + ",";
			} else {
				out += ary[i] + "";
			}
		}
		return out;
	}
	public static double[] getBlankRiv() {
		double[] riv = new double[50];
		for (int i = 0; i < 50; i++) {
			riv[i] = 0;//Double.parseDouble(ary[i]);
		}
		riv[0] = 1;
		return riv;
	}
	public static double[] getRiv(String riv_as_string) {
		String[] ary = riv_as_string.split(",");
		double[] riv = new double[ary.length];
		for (int i = 0; i < ary.length; i++) {
			riv[i] = Double.parseDouble(ary[i]);
		}
		return riv;
	}

	public static double roundTwoDecimals(double d) {
		DecimalFormat twoDForm = new DecimalFormat("#.##");
		return Double.valueOf(twoDForm.format(d));
	}

	/*
	 * Use common.DescribeData.java to generate more of this sort of thing -
	 * This is here just as a temp helper during dev
	 */
	public static String most_before_diagnosis = "1304575|false|1.0,1.0,1.0,1.0,1.0,1.0,-1.0,-1.0,-1.0,-1.0,-1.0,-1.0,1.0,-1.0,-1.0,1.0,-1.0,-1.0,1.0,1.0,1.0,-1.0,-1.0,-1.0,-1.0,-1.0,-1.0,-1.0,-1.0,-1.0,1.0,1.0,1.0,1.0,1.0,-1.0,-1.0,-1.0,-1.0,-1.0,-1.0,-1.0,-1.0,-1.0,1.0,-1.0,-1.0,1.0,-1.0,-1.0";
	public static String most_gender_code = "1007935|m|1.0,-1.0,1.0,-1.0,-1.0,1.0,-1.0,-1.0,-1.0,1.0,-1.0,-1.0,1.0,-1.0,-1.0,-1.0,-1.0,1.0,-1.0,1.0,-1.0,-1.0,-1.0,1.0,1.0,1.0,1.0,-1.0,1.0,-1.0,1.0,-1.0,1.0,-1.0,1.0,-1.0,-1.0,1.0,1.0,1.0,1.0,1.0,-1.0,-1.0,-1.0,1.0,1.0,1.0,-1.0,1.0";
	public static String most_drug_label_name = "28950|hydrocodone/acetaminophen tablet|1.0,-1.0,1.0,-1.0,-1.0,1.0,-1.0,1.0,-1.0,1.0,-1.0,1.0,-1.0,-1.0,1.0,-1.0,1.0,-1.0,1.0,1.0,-1.0,1.0,1.0,1.0,-1.0,1.0,1.0,-1.0,1.0,-1.0,1.0,1.0,-1.0,-1.0,1.0,1.0,1.0,-1.0,-1.0,1.0,1.0,-1.0,1.0,-1.0,1.0,1.0,1.0,-1.0,-1.0,1.0";
	public static String most_during_treatment = "931782|true|1.0,1.0,-1.0,-1.0,1.0,-1.0,-1.0,-1.0,-1.0,-1.0,-1.0,-1.0,1.0,1.0,1.0,-1.0,-1.0,1.0,1.0,-1.0,-1.0,1.0,-1.0,-1.0,1.0,-1.0,1.0,1.0,-1.0,1.0,-1.0,1.0,-1.0,-1.0,-1.0,-1.0,1.0,1.0,-1.0,1.0,-1.0,1.0,-1.0,1.0,1.0,-1.0,1.0,-1.0,-1.0,-1.0";
	public static String most_drug_group_description = "132425|antihyperlipidemics|1.0,1.0,-1.0,-1.0,1.0,1.0,1.0,1.0,1.0,-1.0,1.0,-1.0,1.0,-1.0,-1.0,-1.0,-1.0,-1.0,-1.0,-1.0,-1.0,-1.0,1.0,1.0,-1.0,1.0,-1.0,1.0,1.0,-1.0,-1.0,1.0,1.0,1.0,-1.0,1.0,1.0,-1.0,1.0,-1.0,1.0,1.0,-1.0,1.0,1.0,1.0,-1.0,-1.0,1.0,1.0";
	public static String most_after_cure = "1070035|true|1.0,1.0,-1.0,1.0,1.0,1.0,1.0,-1.0,1.0,1.0,1.0,1.0,1.0,1.0,-1.0,1.0,-1.0,1.0,1.0,1.0,-1.0,1.0,-1.0,1.0,-1.0,-1.0,1.0,-1.0,-1.0,1.0,1.0,-1.0,1.0,1.0,-1.0,-1.0,-1.0,1.0,1.0,1.0,1.0,1.0,-1.0,-1.0,1.0,-1.0,-1.0,1.0,-1.0,1.0";

}