package common.rollup;

import common.Caller;
import common.Library;
import common.PSVReader;

public class _TimeChunk extends PSVReader {
	public int days_supply_count = 0;
	public int patient_paid_amount = 0;
	public int ingredient_cost_paid_amount = 0;
	public int velocity = 0;
	public double[] riv = new double[50];
	public _TimeChunk() {

		router.remove("person_id");
		//router.remove("ndc_code");
		router.remove("days_supply_count");
		router.remove("filled_date");
		router.remove("patient_paid_amount");
		router.remove("ingredient_cost_paid_amount");

	}
	public String toPSV() {

		int dsc = days_supply_count / velocity;
		int ppa = patient_paid_amount / velocity;
		int icpa = ingredient_cost_paid_amount / velocity;
		String riv_as_string = Library._join(riv);
		
		String psv = velocity + "|" + dsc + "|" + ppa + "|" + icpa + "|" + riv_as_string;
		
		return psv;
	}
	
	public void addEntry(String when, String entry) {
		try {
			// route via parent's parent ( i.e., CommonPSVFormat.java )
			String[] pieces = entry.split(",");
			if (pieces.length == 13) {
				
					velocity++;

					route("gender_code", pieces[1]);
					route("ccs_category_id", pieces[2]);
					route("ndc_code", pieces[3]);
					route("drug_label_name", pieces[4]);
					route("drug_group_description", pieces[5]);

					days_supply_count += roundToNearest10th(pieces[6]);
					patient_paid_amount += roundToNearest10th(pieces[8]);
					ingredient_cost_paid_amount += roundToNearest10th(pieces[9]);

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
	
	public void addRIV(double[] riv_from_concept ) {
		for ( int i =0; i < riv.length; i++ ) { 
			riv[i] += riv_from_concept[i];
		}
	}
}