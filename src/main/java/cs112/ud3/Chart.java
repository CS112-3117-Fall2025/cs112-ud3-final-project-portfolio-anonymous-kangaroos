package cs112.ud3;

/**
 * Represents a generic chart
 *
 * @author Isabella Watson
 * @version 1.0.0
 *
/* UML CLASS DIAGRAM:
-----------------------------------------
Chart
-----------------------------------------
- title: String
-----------------------------------------
//default constructor method:
+ Chart()

//full constructor method
+ Chart(title : String)

//Copy constructor method
+ Chart(original : Chart)

+ setTitle(title : String) : boolean

+ getTitle() : String

+ toString() : String

+ equals(other : Chart) : boolean

+ printChart() : String {abstract}
-----------------------------------------
 */

public abstract class Chart {
    //DECLARATION SECTION
    /***** STATIC VARIABLES *****/
    public static final String DEFAULT_TITLE = "Chart";
    public static final int LENGTH_MINIMUM = 0;

    /***** INSTANCE VARIABLES *****/
    private String title;

    /***** CONSTRUCTORS *****/
    //full constructor
    public Chart(String title) throws IllegalArgumentException {
        if (!this.setTitle(title)) {
            throw new IllegalArgumentException("ERROR: Invalid chart title given to constructor method.");
        }
    }

    //default construcor
    public Chart() {
        this(DEFAULT_TITLE);
    }

    //copy constructor
    public Chart(Chart chart) throws IllegalArgumentException {
        if (chart == null) {
            throw new IllegalArgumentException("ERROR: Null Chart passed to copy constructor.");
        }
        this.setTitle(chart.title);
    }

    /***** SETTERS *****/
    public boolean setTitle(String title) {
        if (title != null && title.length() > LENGTH_MINIMUM) {
            this.title = title;
            return true;
        } else {
            return false;
        }
    }

    /***** GETTERS *****/
    public String getTitle() {
        return this.title;
    }

    /***** REQUIRED METHODS *****/
    @Override
    public boolean equals(Object other) {
        if (other == null || this.getClass() != other.getClass()) {
            return false;
        } else {
            Chart otherChart = (Chart) other;
            return this.title.equals(otherChart.title);
        }
    }

    @Override
    public String toString() {
        return "Chart Title = " + this.title;
    }

    /***** ABSTRACT METHODS *****/
    public abstract String printChart();
}