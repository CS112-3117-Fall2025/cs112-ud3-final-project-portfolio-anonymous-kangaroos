package cs112.ud3;

import javafx.application.Application;  //abstract class used for JavaFX GUI's
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.*;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;              //class for GUI window
import javafx.scene.Scene;              //class for specific view in GUI window
import javafx.scene.layout.VBox;        //class for layout pane, organized top-to-bottom
import javafx.scene.control.Button;     //class for button component
import javafx.event.EventHandler;       //interface for handling events
import javafx.event.ActionEvent;        //class for type of event for action (like button or key pressed)
import javafx.geometry.Pos; //for aligning vbox elements
import java.io.File;
//import libraries for file IO:
import java.io.FileNotFoundException;
//import for date formatting
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javafx.scene.control.Tooltip;
//import for reading files
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
//for alerts on invalid file selections
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


public class Main extends Application {
/*** DRIVER main ***/
//total number of chart types
        public static final int NUM_CHARTS = 3;
public static final String bgColor="-fx-background-color: #F3F4F6;";
public static final String textColor="-fx-text-fill: #111827;";
public static final int NUM_COLUMNS=3;
/*** GUI COMPONENTS ***/
private Button button;
private FileChooser file;
        //create scanner object for file IO
        BufferedReader inputStream=null;
        String[]lines;
        String[]allData;
        //create array of date objects to store dates from file
        Date[]dates;
        //create array to hold spending values
        double[]spending;
        String[]categories;
        //create array to hold data labels
        String temp=null;
        //create counter for next button
    int numClicks = 0;


/*** OVERRIDDEN Application METHODS ***/

@Override
public void start(Stage primaryStage) throws IOException {

        //create vbox using 20 px for vertical spacing
        VBox vBox=new VBox(20);
        vBox.setStyle(bgColor);
        // Set the alignment of the VBox's children to CENTER
        vBox.setAlignment(Pos.TOP_CENTER);

        //create icon object
        Image appIcon=new Image(getClass().getResourceAsStream("/images/MoneyIcon.png"));
        //set title and icon for primary stage
        primaryStage.setTitle("JavaBudget");
        primaryStage.getIcons().add(appIcon);

        //create a label to display instructions to user
        Label instructions=new Label("Select a text file to input spending data!");
        instructions.setStyle(textColor);

        //create textfield for file path
        TextField filePathBox=new TextField();
        filePathBox.setPrefWidth(400);
        filePathBox.setStyle("-fx-background-color: #FFFFFF;"+textColor);

        //create label for displaying spending breakdown after file upload
        //create header label
        Label spendingBreakdown=new Label();
        //style header
        spendingBreakdown.setStyle(textColor);

        //create file browser button
        Button button=new Button("Browse");
        //set style for button
        button.setStyle("-fx-background-color: #3B82F6;"+textColor);

        //create button to toggle chart, dont show until file is uploaded
        Button toggleButton=new Button(">>");
        //set style for button
        toggleButton.setStyle("-fx-font-size: 20px;-fx-background-color: #3B82F6;"+textColor);

        //set height of text box to height of button
        filePathBox.setPrefHeight(button.getHeight());

        // Create an HBox to hold the Label and Button horizontally
        HBox fileIO=new HBox(10); // 10 pixels spacing between elements
        fileIO.setAlignment(Pos.CENTER); // Align content within HBox
        fileIO.getChildren().addAll(filePathBox,button);

        //create hbox for chart and toggle button to sit next to each other
        HBox toggleChart=new HBox(10);  // spacing between labels
        VBox sliceLabels=new VBox(5);  // spacing between chart labels
        //create header label
        Label headerLabel=new Label("JavaBudget");
        //style header
        headerLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-padding: 10;"+textColor);
        // Add the children to the VBox
        vBox.getChildren().addAll(headerLabel,instructions,fileIO);

        Scene scene=new Scene(vBox,960,600);

        primaryStage.setScene(scene);
        primaryStage.show();

        //file browser button event handler
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //create file browser object
                file=new FileChooser();
                file.setTitle("Open Budgeting File (.txt format)");
                file.getExtensionFilters().addAll(
                //specify valid file extension (txt only)
                new FileChooser.ExtensionFilter("Text Files","*.txt")
                );
                //set file to one selected in file browser
                File selectedFile=file.showOpenDialog(primaryStage);

                //set filepath box to selected file
                filePathBox.setText(String.valueOf(selectedFile.getAbsoluteFile()));
                //parse data from file
                try{
                    parseData(readFile(selectedFile));
                }
                catch(IllegalArgumentException iae){
                //dont process data
                    showWarningAlert("Warning","Invalid File Data","The data in the file was not formatted correctly.");
                }
                catch(FileNotFoundException fnfe){
                    //String title, String header, String content
                    showWarningAlert("Warning","File Not Found","Please select a different file!");
                }
                catch(IOException ioe){
                    showWarningAlert("Warning","Failed to open file.","Please select a different file!");
                }

                //create piechart object
                PieChart pieChart=new PieChart(buildPieChart(spending,categories));
                //create title for piechart and add total to it
                pieChart.setTitle("Spending Breakdown By Category");
                //calc total spent
                double totalSpent = 0;
                //init total for total amount spent
                double percentSum=100;

                //sum total
                for(double amount:spending){
                    totalSpent+= amount;
                }
                //create title for legend
                Label legendLabel=new Label("Total Spent: $" + totalSpent);
                legendLabel.setStyle("-fx-font-weight: bold; -fx-padding: 10;"+textColor);
                sliceLabels.getChildren().add(legendLabel);
                for(PieChart.Data data:pieChart.getData()){
                    //calculate percentage of total
                    double percentage=(data.getPieValue()/percentSum)*100;
                    String percentVal=String.format("%.1f%%",percentage);
                    //calculate raw amount
                    double amountSpent=(data.getPieValue()/percentSum)*totalSpent;
                    String rawAmount=String.format("$%.2f",amountSpent);

                    Label label=new Label(data.getName()+": "+ rawAmount + " : " + percentVal);
                    label.setStyle(textColor);
                    sliceLabels.getChildren().add(label);
                }
                toggleChart.getChildren().addAll(sliceLabels,pieChart,toggleButton);
                // Set the alignment of the HBox to center its children horizontally
                toggleChart.setAlignment(Pos.CENTER);
                vBox.getChildren().addAll(toggleChart);
            }
        });//end of anonymous class

    //toggle button event handler
    toggleButton.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            //increment number of clicks counter
            numClicks++;
            //if next button has been pressed once, create bar chart
            if (numClicks == 1){
                //Define Axes
                CategoryAxis xAxis = new CategoryAxis();
                xAxis.setLabel("Spending Category"); // e.g., "Countries"

                NumberAxis yAxis = new NumberAxis();
                yAxis.setLabel("Amount Spent"); // e.g., "Sales"

                //Create BarChart
                BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
                barChart.setTitle("Spending by Category");


                //add Data to Chart
                barChart.getData().addAll(buildBarChartData(spending,categories));
                //remove old chart from hbox and update new
                toggleChart.getChildren().remove(1);
                toggleChart.getChildren().add(1, barChart);

            }
            else if (numClicks == 2){
                // Define Axes
                CategoryAxis xAxis = new CategoryAxis();
                NumberAxis yAxis = new NumberAxis();
                xAxis.setLabel("Date");
                yAxis.setLabel("Amount Spent");

                // 2. Create BarChart
                LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
                lineChart.setTitle("Spending Over Time");


                // 4. Add Data to Chart
                lineChart.getData().addAll(buildLineGraphData(spending,dates));
                //remove old chart from hbox and update new
                toggleChart.getChildren().remove(1);
                toggleChart.getChildren().add(1, lineChart);
            }
            else{
                //create piechart object
                PieChart pieChart=new PieChart(buildPieChart(spending,categories));
                //create title for piechart and add total to it
                pieChart.setTitle("Spending Breakdown By Category");
                //remove old chart from hbox and update new
                toggleChart.getChildren().remove(1);
                toggleChart.getChildren().add(1, pieChart);
                //reset counter to cycle through charts
                numClicks = 0;

            }
        }
    });//end of anonymous class
}
/*** DRIVER main ***/
public static void main(String[] args) {
        launch();
}


//HELPER METHODS

    public String[] readFile(File file) throws IllegalArgumentException, IOException {
        if (file == null) {
            throw new IllegalArgumentException("File cannot be null");
        }
        StringBuilder sb = new StringBuilder();
        try (BufferedReader inputStream = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = inputStream.readLine()) != null) {
                sb.append(line).append("\n"); // add newline to preserve structure
            }
        } catch (FileNotFoundException fnfe) {
            throw new FileNotFoundException("File cannot be found");
        } catch (IOException ioe) {
            throw new IOException("File cannot be opened");
        }
        //manipulation of data received from file:
        //split by new line or comma
        String[] lines = sb.toString().split("[,\\n\\r]+");
        return lines;
    }

    public void parseData(String[] data) throws IllegalArgumentException {
        boolean validData = true;
        //calc array size for date array
        int numRows = data.length / NUM_COLUMNS;
        //instantiate array sizes with proper lengths for given data set
        dates = new Date[numRows];
        spending = new double[numRows];
        categories = new String[numRows];

        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

        for (int row = 0; row < numRows; row++) {
            int baseIndex = row * NUM_COLUMNS;
            try {
                dates[row] = formatter.parse(data[baseIndex]);              // first column = date
                spending[row] = Double.parseDouble(data[baseIndex + 1]);   // second column = spending
                categories[row] = data[baseIndex + 2];                     // third column = category
            } catch (ParseException | NumberFormatException e) {
                throw new IllegalArgumentException("Invalid data format in text file!");
            }
        }
    }

    //create warning method for user errors
    public static void showWarningAlert(String title, String header, String content) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public ObservableList<javafx.scene.chart.PieChart.Data> buildPieChart(double[] spending, String[] categories) {
        //explicitely reference piechart custom class to avoid name collision with javafx piechart
        Map<String, Double> spendingBreakdown = new HashMap<>();
        cs112.ud3.PieChart myChart = new cs112.ud3.PieChart("Spending Breakdown", spending, categories, new Legend());
        spendingBreakdown = myChart.getPercentSpending();
        ObservableList<javafx.scene.chart.PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for (Map.Entry<String, Double> entry : spendingBreakdown.entrySet()) {
            pieChartData.add(new javafx.scene.chart.PieChart.Data(entry.getKey(), entry.getValue()));
        }
        return pieChartData;
    }

    public XYChart.Series<String, Number> buildBarChartData (double[] spending, String[] categories) {
        //explicitly reference piechart custom class to avoid name collision with javafx piechart
        Map<String, Double> spendingBreakdown = new HashMap<>();
        cs112.ud3.BarGraph barGraph = new cs112.ud3.BarGraph("Spending Breakdown", spending, categories, new Legend());
        spendingBreakdown = barGraph.getCategorySpending();
        XYChart.Series<String, Number> dataSeries = new XYChart.Series<>();
        for (Map.Entry<String, Double> entry : spendingBreakdown.entrySet()) {
            dataSeries.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }
        return dataSeries;
    }

    public XYChart.Series<String, Number> buildLineGraphData (double[] spending, Date[] dates) {
        //explicitly reference linegraph custom class to avoid name collision with javafx
        TreeMap<Date, Double> spendingByDate = new TreeMap<>();
        LineGraph graph = new LineGraph("Line Graph", dates, spending,"Date", "Spending");
        spendingByDate = graph.sumSpendingByDate();
        XYChart.Series<String, Number> dataSeries = new XYChart.Series<>();
        for (Map.Entry<Date, Double> entry : spendingByDate.entrySet()) {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            String formattedDateString = sdf.format(entry.getKey());
            dataSeries.getData().add(new XYChart.Data<>(formattedDateString, entry.getValue()));
        }
        return dataSeries;

    }


}

