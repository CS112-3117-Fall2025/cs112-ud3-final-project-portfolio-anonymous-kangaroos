package cs112.ud3;


import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;

/**
 * Represents a pie chart.
 * @author Isabella Watson
 * @version 1.0.0
 */
/* UML CLASS DIAGRAM:
-----------------------------------------
PieChart
-----------------------------------------
- data: double[]
- dataLabels: String[]
- legend: Legend
-----------------------------------------
//default constructor method:
+ PieChart()

//full constructor method
+ PieChart(data : double[], dataLabels : String[], legend : Legend)

//Copy constructor method
+ PieChart(original : PieChart)

+ setData(data : double[]) : void

+ setDataLabels(dataLabels : String[]) : void

+ setDataAndDataLabels(data : double[], dataLabels : String[]) : void

+ setLegend(legend : Legend) : void

+ getData() : double[]

+ getDataLabels() : String[]

+ getLegend() : Legend

+ toString() : String

+ equals(other : PieChart) : boolean

+ printChart() : void

+ getPercentSpending() : Map<String, Double>

- groupData() : Map<String, Double>
-----------------------------------------
*/
public class PieChart extends Chart {
    //DECLARATION SECTION
    /***** STATIC VARIABLES *****/
    public static final String DEFAULT_LEGEND_TITLE = "Pie Chart Legend";
    public static final int DEFAULT_ARRAY_LENGTH = 0;
    private static Map<String, Double> categorySpending = new HashMap<>();
    /***** INSTANCE VARIABLES *****/
    private double[] data;
    private String[] dataLabels;
    //will develop legend later
    private Legend legend;
    //create map to track repeat categories for summing amounts in same category

    /***** CONSTRUCTORS *****/
    // Full constructor
    public PieChart(String title, double[] data, String[] dataLabels, Legend legend) {
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
    public PieChart() {
        this(DEFAULT_TITLE, new double[DEFAULT_ARRAY_LENGTH], new String[DEFAULT_ARRAY_LENGTH], new Legend(DEFAULT_LEGEND_TITLE));
    }

    // Copy constructor
    public PieChart(PieChart other) {
        super(other);
        if (other == null) {
            throw new IllegalArgumentException("ERROR: Null PieChart passed to copy constructor.");
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

    //added because if you try to use individual setters in main but array size has changed, will throwerror
    public void setDataAndDataLabels(double[] data, String[] dataLabels) {
        if (dataLabels == null || data == null) {
            throw new IllegalArgumentException("ERROR: DataLabels array cannot be null.");
        }
        if (data.length != dataLabels.length) {
            throw new MismatchedDataLengthException("ERROR: Data and DataLabels arrays must have equal length.");
        }
        this.dataLabels = Arrays.copyOf(dataLabels, dataLabels.length);
        this.data = Arrays.copyOf(data, data.length);
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
        if (other == null || (!(other instanceof PieChart))) {
            return false;
        }
        PieChart otherChart = (PieChart) other;
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
    /***** CUSTOM METHOD *****/
    //groups data into categories
    private Map<String, Double> groupData(){
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

    public Map<String, Double> getPercentSpending() {
        //initialize variables
        double sum = 0, percent = 0, spending = 0;
        String category;
        Map<String, Double> percentSpending = new HashMap<>();
        //group the data into categories
        groupData();
        //sum all spending
        for (double amount : categorySpending.values()) {
            sum += amount; //sum the total
        }
        for (Map.Entry<String, Double> entry : categorySpending.entrySet()) {
            category = entry.getKey();
            spending = entry.getValue();
            percent = 100 * (spending / sum);
            percentSpending.put(category, percent);
        }
        return percentSpending;
    }

    /***** ABSTRACT METHOD IMPLEMENTATION *****/
    //prints all data
    @Override
    public String printChart() {
        StringBuilder sb = new StringBuilder();
        //add all instance variables to string
        sb.append(this.getTitle()).append("\n");
        //call method to categorize data
        groupData();
        sb.append(categorySpending.toString());
        //for (int i = 0; i < data.length; i++) {
        //sb.append(dataLabels[i]).append(": ").append(data[i]).append("\n");
        //}
        return sb.toString();
    }
}
