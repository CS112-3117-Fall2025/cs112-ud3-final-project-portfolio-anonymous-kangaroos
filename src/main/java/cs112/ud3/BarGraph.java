package cs112.ud3;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a Bar Graph.
 * will be modified later to print the data appropriately in graph form
 * @author Isabella Watson
 * @version 1.0.0
 */
/* UML CLASS DIAGRAM:
-----------------------------------------
BarGraph
-----------------------------------------
- data: double[]
- dataLabels: String[]
- legend: Legend
-----------------------------------------
//default constructor method:
+ BarGraph()

//full constructor method
+ BarGraph(data : double[], dataLabels : String[], legend : Legend)

//Copy constructor method
+ BarGraph(original : BarGraph)

+ setData(data : double[]) : void

+ setDataLabels(dataLabels : String[]) : void

+ setLegend(legend : Legend) : void

+ getData() : double[]

+ getDataLabels() : String[]

+ getLegend() : Legend

+ toString() : String

+ equals(other : BarGraph) : boolean

+ printChart() : void

+ getCategorySpending() : Map<String, Double>

-----------------------------------------
*/

public class BarGraph extends Chart {
    //DECLARATION SECTION
    /***** STATIC VARIABLES *****/
    public static final String DEFAULT_LEGEND_TITLE = "Bar Graph Legend";
    public static final int DEFAULT_ARRAY_LENGTH = 0;

    /***** INSTANCE VARIABLES *****/
    private double[] data;
    private String[] dataLabels;
    //will develop legend later
    private Legend legend;
    static Map<String, Double> categorySpending = new HashMap<>();

    /***** CONSTRUCTORS *****/
    // Full constructor
    public BarGraph(String title, double[] data, String[] dataLabels, Legend legend) {
        super(title);
        if (data == null || dataLabels == null) {
            throw new IllegalArgumentException("ERROR: Data arrays cannot be null.");
        }
        if (data.length != dataLabels.length) {
            throw new MismatchedDataLengthException("ERROR: Data and DataLabels arrays must have equal length.");
        }
        this.data = Arrays.copyOf(data, data.length);
        this.dataLabels = Arrays.copyOf(dataLabels, dataLabels.length);
        //develop legend class later
        this.legend = legend;
    }

    // Default constructor
    public BarGraph() {
        this(DEFAULT_TITLE, new double[DEFAULT_ARRAY_LENGTH], new String[DEFAULT_ARRAY_LENGTH], new Legend(DEFAULT_LEGEND_TITLE));
    }

    // Copy constructor
    public BarGraph(BarGraph other) {
        super(other);
        if (other == null) {
            throw new IllegalArgumentException("ERROR: Null BarGraph passed to copy constructor.");
        }
        this.data = Arrays.copyOf(other.data, other.data.length);
        this.dataLabels = Arrays.copyOf(other.dataLabels, other.dataLabels.length);
        this.legend = new Legend(other.legend.getTitle());
    }

    /***** SETTERS *****/
    public void setData(double[] data) {
        if (data == null || dataLabels == null) {
            throw new IllegalArgumentException("ERROR: Data array cannot be null.");
        }
        if (data.length != dataLabels.length) {
            throw new MismatchedDataLengthException("ERROR: Data and DataLabels arrays must have equal length.");
        }
        this.data = Arrays.copyOf(data, data.length);
    }

    public void setDataLabels(String[] dataLabels) {
        if (dataLabels == null || data == null) {
            throw new IllegalArgumentException("ERROR: DataLabels array cannot be null.");
        }
        if (data.length != dataLabels.length) {
            throw new MismatchedDataLengthException("ERROR: Data and DataLabels arrays must have equal length.");
        }
        this.dataLabels = Arrays.copyOf(dataLabels, dataLabels.length);
    }

    public void setLegend(Legend legend) {
        this.legend = legend;
    }

    /***** GETTERS *****/
    public double[] getData() {
        return Arrays.copyOf(this.data, this.data.length);
    }

    public String[] getDataLabels() {
        return Arrays.copyOf(this.dataLabels, this.dataLabels.length);
    }

    public Legend getLegend() {
        return this.legend;
    }

    /***** REQUIRED METHODS *****/
    @Override
    public boolean equals(Object other) {
        if (other == null || (!(other instanceof BarGraph))) {
            return false;
        }
        BarGraph otherChart = (BarGraph) other;
        return this.getTitle().equals(otherChart.getTitle()) &&
                Arrays.equals(this.data, otherChart.data) &&
                Arrays.equals(this.dataLabels, otherChart.dataLabels) &&
                this.legend.equals(otherChart.legend);
    }

    @Override
    public String toString() {
        return super.toString() +
                "\nLegend = " + this.legend +
                "\nNumber of Data Points = " + this.data.length;
    }

    /***** ABSTRACT METHOD IMPLEMENTATION *****/
    //update this later to print data in nicer graph format
    @Override
    public String printChart() {
        String output = "test";
        System.out.println("BarGraph: " + this.getTitle());
        System.out.println(this.legend);
        for (int i = 0; i < data.length; i++) {
            System.out.println(dataLabels[i] + ": " + data[i]);
        }
        return output;
    }

    public Map<String, Double> getCategorySpending(){
        String label;
        double newVal;
        for (int i=0;i< this.dataLabels.length; i++){
            //set label to current label value, turn to lowercase for case insensitivity
            //trim whitespace
            label = this.dataLabels[i].toLowerCase().trim();
            //if the category string doesnt already exists in the map, add it and its value:
            if (!categorySpending.containsKey(label)){
                categorySpending.put(label, this.data[i]);
            }
            //if category already exists, update the spending in that category
            else {
                //update the spending in the category
                newVal = categorySpending.get(label) + this.data[i];
                categorySpending.put(label, newVal);
            }
        }
        return categorySpending;
    }

}