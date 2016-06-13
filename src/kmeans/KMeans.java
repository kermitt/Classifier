package kmeans; 

import java.util.ArrayList;

import java.util.List;
import common.Caller;
 

public class KMeans {

    double RECURSE_LIMIT = 10;
    double BIG_NUMBER = 999999999;
    List<KMeansObservation> observations;
    List<KMeansCentroid> centroids;
    String[] centroidLabels = new String[] { "A", "B", "C", "D", "E", "F", "G",
            "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
           "U", "V", "X", "Y", "Z" };

    public void step1(double[] hyperplaneLocations,
            List<KMeansObservation> observations) {
        this.observations = observations;
        this.centroids = new ArrayList<KMeansCentroid>();
        for (int i = 0; i < hyperplaneLocations.length; i++) {
            KMeansCentroid c = new KMeansCentroid(centroidLabels[i],
                    hyperplaneLocations[i]);
            this.centroids.add(c);
        }
    }

    public void recurse() {
        long t1 = System.currentTimeMillis(); 
        distributeObservationsUntoCentroids( 0, t1 );
    }

    public void distributeObservationsUntoCentroids( int depth, long t1 ) {
        if (depth >= RECURSE_LIMIT) {
            Caller.context_note("***Bottomed out with the result\n"
                    + _prettyprintCentroids());
            return;
        } else {
            String before = centroidValues_toString();
            rebase();
            for (KMeansObservation o : observations) {
                int chosen = -1;
                double threshold = BIG_NUMBER;
                for (int i = 0; i < centroids.size(); i++) {
                    double distance = Math
                            .abs(o.value - centroids.get(i).value);
                    if (threshold > distance) {
                        threshold = distance;
                        chosen = i;
                    }
                }
                centroids.get(chosen).addNode(o);
            }

            for (int i = 0; i < centroids.size(); i++) {
                centroids.get(i).calculateNewLocation();
            }

            String after = centroidValues_toString();

            depth += 1; 
            long t2 = System.currentTimeMillis();
            Caller.context_note("Depth: " + depth + " Milsec: " + ( t2 - t1 ) + "  Before " + before  + " After " + after );

            if ( ! before.equals(after) ) {
                distributeObservationsUntoCentroids( depth, t2 ); 
            } else {
                Caller.context_note("***The centroids have stopped moving on iteration " + depth + "\n" + _prettyprintCentroids());
                return;
            }
        }
    }

    private boolean areCentroidLocationsMoving( double[] a, double[] b ) { 
        boolean isDifferent = true;
        for ( int i = 0; i < a.length; i++ ) { 
            if ( a[i] != b[i] ) {
                isDifferent = false;
            }
        }

        return isDifferent; 
    }

    // zero stuff out in prep from the next centroid location 
    // re-caluculations
    private void rebase() {
        for (int i = 0; i < centroids.size(); i++) {
            centroids.get(i).rebase();
        }
    }

    protected String centroidValues_toString() {

        String numbers = "";
        for (int i = 0; i < centroids.size(); i++) {
            numbers += centroids.get(i).value;
            if (i < centroids.size() - 1) {
                numbers += ", ";
            }
        }

        return numbers;
    }

    protected String _prettyprintCentroids() {
        String numbers = "Values: ";
        String letters = "Labels: ";
        String totals = "Totals: ";

        for (int i = 0; i < centroids.size(); i++) {
            numbers += centroids.get(i).value;
            letters += centroids.get(i).label;
            totals += centroids.get(i).total;

            if (i < centroids.size() - 1) {
                numbers += ", ";
                letters += ", ";
                totals += ",";
            }
        }

        String out = "---------------------\n";
        out += numbers + "\n" + totals + "\n" + letters;
        out += "\n---------------------";

        return out;
    }
}