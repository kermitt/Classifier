package common.conceptspace;

import common.Caller;
import common.GeneralWriter;
import common.Library;
import common.PSVReader;
import common.Seen;

import java.util.*;

public class PopulateConcepts_Step1 extends PSVReader {

	public static void main(String... strings) {

		long t1 = System.currentTimeMillis();

		PopulateConcepts_Step1 pc = new PopulateConcepts_Step1();
		String fullpath = Library.DATA_PATH + Library.DATA_FOR_REAL;
		pc.read_psv(3000000, fullpath);
		pc.marshal(Library.DATA_PATH + Library.RIV_STORE);

		long t2 = System.currentTimeMillis();

		Caller.context_note("The end in " + (t2 - t1) + " milsec ");

	}

	public void marshal(String filepath) {
		String header = "seen|feature|payload|riv";

		GeneralWriter gw = new GeneralWriter(filepath);

		gw.step1_of_2(header);
		for (String feature : router.keySet()) {
			if (!feature.equals("person_id") && !feature.equals("ndc_code")) {
				Map<String, Seen> map = router.get(feature);
				for (String payload : map.keySet()) {
					Seen seen = map.get(payload);
					String riv = createRIV();
					String row = seen.seen + "|" + feature + "|" + payload + "|" + riv;
					gw.step2_of_2(row);
				}
			}
		}
	}

	@Override
	public void populate(String entry) {
		try {
			// route() via parent's parent ( i.e., CommonPSVFormat.java )
			String[] pieces = entry.split(",");
			if (pieces.length == 13) {
				route("person_id", pieces[0]);
				route("gender_code", pieces[1]);
				route("ccs_category_id", pieces[2]);
				route("ndc_code", pieces[3]);
				route("drug_label_name", pieces[4]);
				route("drug_group_description", pieces[5]);

				// squish pointless uniqueness
				String dsc = "" + roundToNearest10th(pieces[6]);
				route("days_supply_count", dsc);

				// Dates suck; convert to an int
				String days_since_epoch = "" + getDaysSinceEpoch(pieces[7]);
				route("filled_date", days_since_epoch);

				// squish pointless uniqueness
				String ppa = "" + roundToNearest10th(pieces[8]);
				route("patient_paid_amount", ppa);

				// squish pointless uniqueness
				String icpa = "" + roundToNearest10th(pieces[9]);
				route("ingredient_cost_paid_amount", icpa);

				route("after_cure", pieces[10]);
				route("during_treatment", pieces[11]);
				route("before_diagnosis", pieces[12]);
			} else {
				Caller.note("[PopulateConcepts] Skipping " + entry);
			}
		} catch (Exception e) {
			Caller.log("ACK! " + entry + " \t" + e.getMessage());
			System.exit(0);
		}
	}

	public String createRIV() {

		double[] riv = new double[50];
		for (int i = 0; i < 50; i++) {
			double v = 1;
			if (Math.random() > 0.5) {
				v *= -1;
			}
			riv[i] = v;
		}
		riv[0] = 1;
		String result = "";
		for (int i = 0; i < riv.length; i++) {
			if (i < riv.length - 1) {
				result += riv[i] + ",";
			} else {
				result += riv[i];
			}
		}
		return result;
	}
}