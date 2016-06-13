package kmeans;

public class KMeansObservation {
    public String label; 
    public double value; 
    /*
     * PARAM label : the name of the thing
     * PARAM value : the double value of the thing 
     */

    public KMeansObservation( String label, double value ) { 
        this.label = label; 
        this.value = value; 
    }
}