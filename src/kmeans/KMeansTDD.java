package kmeans;

import java.util.ArrayList;

import java.util.List;
import common.Caller;

public class KMeansTDD {

	public static void main(String[] args) {

		KMeansTDD tdd = new KMeansTDD();
		tdd.toy_problem();
		tdd.card_problem();
		Caller.log("\nThe end.");
	}

	public void card_problem() {
		String[] suites = new String[] { "H", "C", "S", "D" };
		List<KMeansObservation> list = new ArrayList<KMeansObservation>();
		for (String suite : suites) {
			for (int i = 1; i < 14; i++) {
				String v = "" + i;
				if (i == 11) {
					v = "J";
				} else if (i == 12) {
					v = "Q";
				} else if (i == 13) {
					v = "K";
				} else if (i == 14 || i == 1) {
					v = "A";
				}
				String label = suite + "_" + v;
				double value = i;
				KMeansObservation o = new KMeansObservation(label, value);
				list.add(o);
			}
		}
		double[] centroids = new double[] { 1, 2, 9 };
		KMeans kmeans = new KMeans();
		kmeans.step1(centroids, list);
		kmeans.recurse();
		String results = kmeans._prettyprintCentroids();
		boolean isOk = results.contains("2.5, 6.5, 11.0");
		Caller.log(isOk);

	}

	public void toy_problem() {

		List<KMeansObservation> observations = new ArrayList<KMeansObservation>();

		observations.add(new KMeansObservation("A", 1));

		observations.add(new KMeansObservation("B", 2));

		observations.add(new KMeansObservation("C", 3));

		observations.add(new KMeansObservation("D", 4));

		observations.add(new KMeansObservation("E", 5));

		double[] centroids = new double[] { 0, 6 };

		KMeans kmeans = new KMeans();

		kmeans.step1(centroids, observations);

		kmeans.recurse();

		String results = kmeans._prettyprintCentroids();

		boolean isOk = results.contains("2.0, 4.5");

		Caller.log(isOk);

	}

}
