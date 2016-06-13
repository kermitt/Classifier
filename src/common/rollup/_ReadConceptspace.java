package common.rollup;

import common.Caller;
import common.Library;
import common.PSVReader;
import common.Seen;

public class _ReadConceptspace extends PSVReader {

	public static void main(String... strings) {
		long t1 = System.currentTimeMillis();
		_ReadConceptspace rc = new _ReadConceptspace();
		String fullpath = Library.DATA_PATH + Library.RIV_STORE;
		rc.read_psv(3000000, fullpath);
		long t2 = System.currentTimeMillis();
		Caller.context_note("The end in " + (t2 - t1) + " milsec ");
	//	rc.test_concepts_loaded();
	}

	public void test_concepts_loaded() {

		for (String feature : router.keySet()) {
			java.util.Map<String, Seen> seen = router.get(feature);
			Caller.note(feature + " size " + seen.size());

			for (String payload : seen.keySet()) {
				Seen s = seen.get(payload);
				Caller.log(payload + " |" + s.riv);
			}
		}
	}

	@Override
	public void populate(String entry) {
		try {
			String[] pieces = entry.split(Library.PIPE);
			if (pieces.length == 4) {
				int observations = Integer.parseInt(pieces[0]);
				String feature = pieces[1];
				String payload = pieces[2];
				String riv = pieces[3];
				Seen seen = new Seen(observations, riv);
				router.get(feature).put(payload, seen);
			} else {
				Caller.note("[ReadConceptspace]Skipping " + entry);
			}
		} catch (Exception e) {
			Caller.log("ACK! " + entry + " \t" + e.getMessage());
			System.exit(0);
		}
	}
	
}