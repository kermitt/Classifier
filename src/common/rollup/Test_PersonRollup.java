package common.rollup;

import java.util.Map;

import common.Caller;
import common.Library;
import common.Seen;

public class Test_PersonRollup {
	PersonRollup_Step3 rollup;

	public void setup() {
		rollup = new PersonRollup_Step3();
		// read the RIV concept space
		rollup.readConceptSpace();
		// read the Rx records
		rollup.readPeopleData();
		// spread people/Rx records into a time-series
		rollup.populateTimeSeries();
		// merge the concepts and the people/time-series
		rollup.merge();
	}
	
	public static void main(String... strings) {
		Test_PersonRollup prt = new Test_PersonRollup();

		prt.setup();
		prt.test_concepts_loaded();
		prt.test_people_loaded();
		prt.test_merge();

		Caller.context_note("Finished the tests");
	}
	public void test_merge() {
		boolean show = false;
		boolean isOk = true;
		Map<String, _Person> people = rollup.people;
		for (String person_id : people.keySet()) {
			_Person person = people.get(person_id);
			if (show)
				Caller.note("Earliestday: " + person.earliest_day + " | " + person_id + " | " + person.series.size());

			for (String when : person.series.keySet()) {
				_TimeChunk tc = person.series.get(when);
				if (show) {
					String riv_as_string = Library._join(tc.riv);
					Caller.note(when + " | velocity = " + tc.velocity + " | " + riv_as_string );
				}
				if ( tc.riv[0] == 0) {
					isOk = false;
				}
			}
		}
		Caller.log(isOk);
	}
	
	public void test_concepts_loaded() {

		boolean show = false;
		boolean isOk = true;

		Map<String, Map<String, Seen>> router = rollup.conceptSpace.router;
		for (String feature : router.keySet()) {
			Map<String, Seen> seen = router.get(feature);

			for (String payload : seen.keySet()) {
				Seen s = seen.get(payload);
				if (show)
					Caller.log(payload + " ..... |" + s.riv);

				if (!s.riv.contains("1")) {
					isOk = false;
				}
			}
		}
		isOk = router.size() == 13 && isOk;
		Caller.log(isOk);
	}

	public void test_people_loaded() {
		boolean show = false;
		boolean isOk = false;
		Map<String, _Person> people = rollup.people;
		for (String person_id : people.keySet()) {
			_Person person = people.get(person_id);
			if (show)
				Caller.note("Earliestday: " + person.earliest_day + " | " + person_id + " | " + person.series.size());

			for (String when : person.series.keySet()) {
				_TimeChunk tc = person.series.get(when);
				if (show)
					Caller.note(when + " | velocity = " + tc.velocity);
				isOk = true;
			}
		}
		Caller.log(isOk);
	}
}