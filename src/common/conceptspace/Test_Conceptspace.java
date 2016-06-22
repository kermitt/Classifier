package common.conceptspace;

import common.Caller;
import common.Library;
import common.rollup._ReadConceptspace;

public class Test_Conceptspace {
	public static void main(String... strings) {
		populateCollection();
		round();
		getDaysSinceEpoch();
		createRIV();
		readRIV();
	}

	private static void readRIV() {
		boolean show = false;// true;//false;
		boolean isOk = true;
		_ReadConceptspace rc = new _ReadConceptspace();
		rc.read_psv(3000000, Library.DATA_PATH + Library.RIV_STORE);
		for (String key : rc.router.keySet()) {
			if (show)
				Caller.note(rc.router.get(key).size() + " for " + key);

			isOk &= rc.router.get(key).size() > 0;
		}
		isOk = rc.router.get("ccs_category_id").size() > 0;
		Caller.log(isOk);
	}
	
	private static void createRIV() {
		boolean show = false;
		boolean isOk = false;
		PopulateConcepts_Step1 pc = new PopulateConcepts_Step1();

		String riv1 = pc.createRIV();
		String riv2 = pc.createRIV();

		String[] pieces = riv1.split(",");
		isOk = pieces.length == 50;
		if (show) {
			Caller.note("riv1|" + riv1);
			Caller.note("riv2|" + riv2);
		}
		Caller.log(isOk);
	}

	private static void getDaysSinceEpoch() {
		boolean show = false;
		boolean isOk = false;
		PopulateConcepts_Step1 pc = new PopulateConcepts_Step1();
		String when = "06/03/2016";
		int days = pc.getDaysSinceEpoch(when);
		if (show)
			Caller.note(days + " for " + when);
		isOk = days == 16953;
		Caller.log(isOk);
	}

	private static void round() {
		boolean show = false;
		boolean isOk = true;
		try {
			String[] ary = { "1", "4", "5", "6", "8", "9", "10", "11", "14", "15", "16", "19", "21" };
			int[] expected = { 0, 0, 10, 10, 10, 10, 10, 10, 10, 20, 20, 20, 20 };
			PopulateConcepts_Step1 pc = new PopulateConcepts_Step1();
			for (int i = 0; i < ary.length; i++) {
				String s = ary[i];
				int result;
				result = pc.roundToNearest10th(s);
				if (show)
					Caller.note("" + result);
				isOk &= result == expected[i];
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		Caller.log(isOk);
	}

	private static void populateCollection() {
		boolean show = false;// true;//false;
		boolean isOk = true;
		// PopulateConcepts extends PSVReader which extends
		// CommonPSVFormat which
		// has the HoH... This method will test that that HoH
		// is properly populated
		// by PopulateConcepts.java
		PopulateConcepts_Step1 pc = new PopulateConcepts_Step1();
		pc.read_psv(100, Library.DATA_PATH + Library.DATA_FOR_TEST);
		for (String key : pc.router.keySet()) {
			if (show)
				Caller.note(pc.router.get(key).size() + " for " + key);

			isOk &= pc.router.get(key).size() > 0;

		}
		Caller.log(isOk);
	}

}
