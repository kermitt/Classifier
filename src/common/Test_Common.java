package common;

public class Test_Common {
	public static void main(String... strings) {
		unitVectorAngle();
		Caller.context_note("End of tests");
	}

	static void unitVectorAngle() {

		boolean show = false; // show output

		boolean isOk = true;
		// A
		double[] a = { 1, 2, 3 };
		double[] b = { 1, 2, 3 };
		double delta = Library.vectorCosineSimilarity(a, b);
		if (show)
			Caller.context_note("A " + delta);
		isOk &= delta > -1.000000001 & delta < 1.000000001;

		// B, like A but different
		double[] a2 = { 1, 2, 3 };
		double[] b2 = { 1, 2, 6 };
		double delta2 = Library.vectorCosineSimilarity(a2, b2);
		if (show)
			Caller.context_note("Like A " + delta2);
		isOk &= delta2 > -1.000000001 & delta2 < 1.000000001;

		// C, like A but magnitude is big
		double[] a3 = { 1000, 2000, 3000 };
		double[] b3 = { 1, 2, 3 };
		double delta3 = Library.vectorCosineSimilarity(a3, b3);
		if (show)
			Caller.context_note("Large magnitude " + delta3);
		isOk &= delta3 > -1.000000001 & delta3 < 1.000000001;

		// D, like A but with negatives
		double[] a4 = { 1, 2, -3 };
		double[] b4 = { 1, 2, 3 };
		double delta4 = Library.vectorCosineSimilarity(a4, b4);
		if (show)
			Caller.context_note("w/ neg " + delta4);
		isOk &= delta4 > -1.000000001 & delta4 < 1.000000001;

		// E, mirror of A
		double[] a5 = { -1, -2, -3 };
		double[] b5 = { 1, 2, 3 };
		double delta5 = Library.vectorCosineSimilarity(a5, b5);
		if (show)
			Caller.context_note("mirror " + delta5);
		isOk &= delta5 > -1.000000001 & delta5 < 1.000000001;

		// F, nearly a mirror of A
		double[] a6 = { -2, -2, -3 };
		double[] b6 = { 1, 2, 3 };
		double delta6 = Library.vectorCosineSimilarity(a6, b6);
		if (show)
			Caller.context_note("nearly mirror " + delta6);
		isOk &= delta6 > -1.000000001 & delta6 < 1.000000001;

		Caller.log(isOk);
	}
}