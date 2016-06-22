package common.rollup;

import common.Caller;
import common.GeneralWriter;
import common.Library;
import common.PSVReader;
import common.Seen;

import java.util.*;

public class PersonRollup_Step3 extends PSVReader {
	public _ReadConceptspace conceptSpace;
	public Map<String, _Person> people = new HashMap<>();
	public List<String> entries = new ArrayList<>();

	// untested
	private void marshal() {
//		String header = "person_id|when|velocity|days_supply_count|patient_paid_amount|ingredient_cost_paid_amount|riv";
		String header = "person_id|when|velocity|days_supply_count|patient_paid_amount|ingredient_cost_paid_amount|riv|male|female|sex_other|ccs_category_id_22|ccs_category_id_24|ccs_category_id_29|ccs_cat_other";
		
		String filepath = Library.DATA_PATH + Library.ROLLUP_FILE;
		GeneralWriter gw = new GeneralWriter(filepath);
		gw.step1_of_2(header);
		int depth = 0;
		for (String person_id : people.keySet()) {
			_Person person = people.get(person_id);
			for (String when : person.series.keySet()) {
				String row = person_id + "|" + when + "|" + person.series.get(when).toPSV();
				gw.step2_of_2(row);
				depth++;
			}
		}
		Caller.context_note("Wrote " + depth + " lines");
	}

	public void merge() {
		for (String person_id : people.keySet()) {
			_Person person = people.get(person_id);

			for (String when : person.series.keySet()) {
				_TimeChunk tc = person.series.get(when);
				for (String feature : tc.router.keySet()) {
					if (!feature.equals("person_id") && !feature.equals("ndc_code")) {
						Map<String, Seen> payloads = tc.router.get(feature);
						for (String payload : payloads.keySet()) {
							try {
								Seen concept = conceptSpace.router.get(feature).get(payload);
								double[] riv = Library.getRiv(concept.riv);
								tc.addRIV(riv);
							} catch (java.lang.NullPointerException npe) {
								Caller.log(npe.getMessage() + "   " + feature + "|" + payload + "|" + npe.getMessage());
							}
						}
					}
				}
			}
		}
	}

	public void readConceptSpace() {
		conceptSpace = new _ReadConceptspace();
		String fullpath = Library.DATA_PATH + Library.RIV_STORE;
		conceptSpace.read_psv(3000000, fullpath);
	}

	public void readPeopleData() {
		String fullpath = Library.DATA_PATH + Library.DATA_FOR_REAL;
		read_psv(3000000, fullpath);
	}

	public static void main(String... strings) {
		long t1 = System.currentTimeMillis();

		PersonRollup_Step3 pc = new PersonRollup_Step3();
		pc.readConceptSpace();
		pc.readPeopleData();
		pc.populateTimeSeries();
		pc.merge();
		pc.marshal();

		long t2 = System.currentTimeMillis();
		Caller.context_note("The end in " + (t2 - t1) + " milsec ");

	}

	@Override
	public void populate(String entry) {
		/*
		 * Prep the global entry so we can next call populateTimeSeries()
		 */
		try {
			String[] pieces = entry.split(",");

			if (pieces.length == 13) {

				int isPositive = roundToNearest10th(pieces[6]);
				if (isPositive >= 0) {

					entries.add(entry);
					String person_id = pieces[0];
					int days_since_epoch = getDaysSinceEpoch(pieces[7]);

					if (!people.containsKey(person_id)) {
						_Person person = new _Person();
						people.put(person_id, person);
					}
					people.get(person_id).findZeroDay(days_since_epoch);
				}
			} else {
				Caller.note("[PopulateConcepts] Skipping " + entry);
			}

		} catch (Exception e) {
			Caller.log("ACK! " + entry + " \t" + e.getMessage());
			System.exit(0);
		}
	}

	public void populateTimeSeries() {
		for (String entry : entries) {
			String[] pieces = entry.split(",");
			try {
				if (pieces.length == 13) {
					String person_id = pieces[0];
					int days_since_epoch = getDaysSinceEpoch(pieces[7]);
					int isPositive = roundToNearest10th(pieces[6]);
					if (isPositive >= 0) {
						_Person person = people.get(person_id);
						days_since_epoch -= person.earliest_day;
						int period = days_since_epoch / Library.TIME_PERIOD;

						person.addRxEntry(period, entry);
					} else {
						Caller.note("Skipping 'take back' " + isPositive + " for " + entry);
					}
				} else {
					Caller.note("[PopulateConcepts] Skipping " + entry);
				}
			} catch (Exception e) {
				Caller.log("! FAILURE ON " + entry);
				e.printStackTrace();
			}
		}
	}
}