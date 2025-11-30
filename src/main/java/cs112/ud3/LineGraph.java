package cs112.ud3;

import java.util.*;

/**
 * Represents a line graph chart.
 * will be modified later to print the data appropriately
 * @author Isabella Watson
 * @version 1.0.0
 */
/* UML CLASS DIAGRAM:
-----------------------------------------
LineGraph
-----------------------------------------
- xData: Date[]
- yData: double[]
- xLabel: String
- yLabel: String
-----------------------------------------
//default constructor method:
+ LineGraph()

//full constructor method
+ LineGraph(xData : Date[], yData : double[], xLabel : String, yLabel : String)

//Copy constructor method
+ LineGraph(original : LineGraph)

+ setXData(xData : Date[]) : void

+ setYData(yData : double[]) : void

+ setXLabel(xLabel : String) : void

+ setYLabel(yLabel : String) : void

+ getXData() : Date[]

+ getYData() : double[]

+ getXLabel() : String

+ getYLabel() : String

+ toString() : String

+ equals(other : LineGraph) : boolean

+ printChart() : String

+ sumSpendingByDate() : TreeMap<Date, Double>

+ getSpendingByDate() : TreeMap<Date, Double>
-----------------------------------------
*/
public class LineGraph extends Chart {
    //DECLARATION SECTION
    /***** STATIC VARIABLES *****/
    public static final String DEFAULT_X_LABEL = "X-Axis";
    public static final String DEFAULT_Y_LABEL = "Y-Axis";
    public static final int DEFAULT_ARRAY_LENGTH = 0;
    private static TreeMap<Date, Double> spendingByDate = new TreeMap<>();

    /***** INSTANCE VARIABLES *****/
    private Date[] xData;
    private double[] yData;
    private String xLabel;
    private String yLabel;


    /***** CONSTRUCTORS *****/
    // Full constructor
    public LineGraph(String title, Date[] xData, double[] yData, String xLabel, String yLabel) {
        super(title);
        if (xData == null || yData == null) {
            throw new IllegalArgumentException("ERROR: x-axis data and y-axis data arrays cannot be null.");
        }
        if (xData.length != yData.length) {
            throw new MismatchedDataLengthException("ERROR: x-axis data and y-axis data arrays must have equal length.");
        }
        this.xData = Arrays.copyOf(xData, xData.length);
        this.yData = Arrays.copyOf(yData, yData.length);
        this.setXLabel(xLabel);
        this.setYLabel(yLabel);
    }

    // Default constructor
    public LineGraph() {
        this(DEFAULT_TITLE, new Date[DEFAULT_ARRAY_LENGTH], new double[DEFAULT_ARRAY_LENGTH], DEFAULT_X_LABEL, DEFAULT_Y_LABEL);
    }

    // Copy constructor
    public LineGraph(LineGraph other) {
        super(other); // calls Chart copy constructor
        if (other == null) {
            throw new IllegalArgumentException("ERROR: Null LineGraph passed to copy constructor.");
        }
        this.xData = Arrays.copyOf(other.xData, other.xData.length);
        this.yData = Arrays.copyOf(other.yData, other.yData.length);
        this.xLabel = other.xLabel;
        this.yLabel = other.yLabel;
    }

    /***** SETTERS *****/
    public void setXData(Date[] xData) {
        if (xData == null) {
            throw new IllegalArgumentException("ERROR: x-axis data array cannot be null.");
        }
        if (xData.length != yData.length) {
            throw new MismatchedDataLengthException("ERROR: x-axis data and y-axis data arrays must have equal length.");
        }
        this.xData = Arrays.copyOf(xData, xData.length);
    }

    public void setYData(double[] yData) {
        if (yData == null) {
            throw new IllegalArgumentException("ERROR: y-axis data array cannot be null.");
        }
        if (xData.length != yData.length) {
            throw new MismatchedDataLengthException("ERROR: x-axis data and y-axis data arrays must have equal length.");
        }
        this.yData = Arrays.copyOf(yData, yData.length);
    }

    public void setXLabel(String xLabel)
    {
        this.xLabel = xLabel;
    }

    public void setYLabel(String yLabel) {
        this.yLabel = yLabel;
    }

    /***** GETTERS *****/
    public Date[] getXData() {
        return Arrays.copyOf(this.xData, this.xData.length);
    }

    public double[] getYData() {
        return Arrays.copyOf(this.yData, this.yData.length);
    }

    public String getXLabel() {
        return this.xLabel;
    }

    public String getYLabel() {
        return this.yLabel;
    }

    /***** REQUIRED METHODS *****/
    @Override
    public boolean equals(Object other) {
        if (!super.equals(other)) {
            return false;
        }
        if (!(other instanceof LineGraph)) {
            return false;
        }
        LineGraph otherGraph = (LineGraph) other;
        return Arrays.equals(this.xData, otherGraph.xData) &&
                Arrays.equals(this.yData, otherGraph.yData) &&
                this.xLabel.equals(otherGraph.xLabel) &&
                this.yLabel.equals(otherGraph.yLabel);
    }

    @Override
    public String toString() {
        return super.toString() +
                "\nX Label = " + this.xLabel +
                "\nY Label = " + this.yLabel +
                "\nNumber of Points = " + this.xData.length;
    }

    /***** ABSTRACT METHOD IMPLEMENTATION *****/
    //update this later to print data in nicer graph format
    @Override
    public String printChart() {
        String output = "test";
        System.out.println("LineGraph: " + this.getTitle());
        System.out.println("X-Axis: " + this.xLabel + ", Y-Axis: " + this.yLabel);
        for (int i = 0; i < xData.length; i++) {
            System.out.println(xData[i] + " -> " + yData[i]);
        }
        return output;
    }

    /***** CUSTOM METHOD *****/
    //groups data into categories
    //TreeMap<Date, Double> spendingByDate
    //    private Date[] xData;
    //    private double[] yData;
    public TreeMap<Date, Double> getSpendingByDate(){
        Date day;
        double newVal;
        for (int i=0;i< this.xData.length; i++){
            //set label to current label value, turn to lowercase for case insensitivity
            //trim whitespace
            day = this.xData[i];
            //if the date doesnt already exists in the map, add it and its value:
            if (!spendingByDate.containsKey(day)){
                spendingByDate.put(day, this.yData[i]);
            }
            //if category already exists, update the spending in that category
            else {
                //update the spending in the category
                newVal = spendingByDate.get(day) + this.yData[i];
                spendingByDate.put(day, newVal);
            }
        }
        return spendingByDate;
    }

    public TreeMap<Date, Double> sumSpendingByDate(){
        double sum = 0;

        TreeMap<Date, Double> summedSpending = new TreeMap<>();
        //order data
        getSpendingByDate();
        //sum all spending
        for (Map.Entry<Date, Double> entry : spendingByDate.entrySet()) {
            sum += entry.getValue();
            summedSpending.put(entry.getKey(), sum);
        }
        return summedSpending;
    }
}

