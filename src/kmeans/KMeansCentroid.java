package kmeans;

import java.util.ArrayList;

import java.util.List;
import common.Caller;

public class KMeansCentroid {
    public double value = 0;
    public double observations = 0;
    public double total = 0;
    public String label = "";
    public List < String > keys = new ArrayList < String > (); 
    public List < String > values = new ArrayList < String > (); 

    public void addNode( KMeansObservation o ) {
        total += o.value;
        observations++;
        keys.add( o.label ); 
        values.add( o.value + ""); 
    }

    public void calculateNewLocation() {
        value = total / observations;
    }

    public double rebase() {
        //value = total / observations;
        observations = 0;
        total = 0;
        keys =  new ArrayList < String > (); 
        return value;
    }

    public KMeansCentroid( String label, Double initialValue ) {
        this.label = label; 
        this.value = initialValue;  
        Caller.log("KMeansCentroid " + label + " value = " + this.value );
    }
}
