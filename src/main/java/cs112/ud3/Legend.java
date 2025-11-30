package cs112.ud3;


import java.util.Arrays;

/**
 * Represents a legend for a chart.
 * will fully flesh out in later versions of project. Functional for now to not throw
 * errors in PieChart and BarGraph class
 *
 * @author Isabella Watson
 * @version 1.0.0
 */
/* UML CLASS DIAGRAM:
-----------------------------------------
Legend
-----------------------------------------
- title: String
-----------------------------------------
//default constructor method:
+ Legend()

//full constructor method
+ Legend(title : String)

+ setTitle(title : String) : void

+ getTitle() : String

+ toString() : String

+ Equals(): boolean
-----------------------------------------
*/
public class Legend {
    public static final String DEFAULT_TITLE = "DEFAULT LEGEND";

    private String title;

    // full Constructor
    public Legend(String title) {
        this.title = title;
    }

    // default Constructor
    public Legend() {
        this.title = DEFAULT_TITLE;
    }

    // Getter
    public String getTitle() {
        return this.title;
    }

    // Setter
    public void setTitle(String title) {
        this.title = title;
    }

    //update this later to print data in nicer legend format
    @Override
    public String toString() {
        return "Legend: " + this.title;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null || (!(other instanceof Legend))) {
            return false;
        }
        Legend legend = (Legend) other;
        return this.title.equals(legend.getTitle());
    }
}
