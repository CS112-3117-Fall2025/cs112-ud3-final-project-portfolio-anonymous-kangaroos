[![Open in Codespaces](https://classroom.github.com/assets/launch-codespace-2972f46106e565e64193e422d61a12cf1da4916b45550586e14ef0a7c637dd04.svg)](https://classroom.github.com/open-in-codespaces?assignment_repo_id=21238319)
# Unit Deliverable 3 - Final Project Portfolio
<br><br>
## Project Description
This project is a budgeting app. The application uses Javafx to allow the user to upload files with their financial information. The driver then parses that data and displays the user's finances to the GUI. The user's spending is grouped into categories for users to track their spending habits. The 3 graph types are parsed into 3 distinct classes: PieChart, BarGraph, and LineGraph. Each class is derived from the abstract parent class Chart. The user can click the ">>" button to toggle which chart type they want to display their financial data.
<br>

## Group Members
<br>
Isabella Watson
<br>

## Project Name
<br>
JavaBudget
<br>

## Project Requirements
<br>

<b>Wireframe Design:</b> png image included in GitHub repo resources folder: src/main/resources/images and Readme
<br>

<b> Java GUI in IntelliJ:</b> .gif of GUI function included in README and repo: 
<br>

<b>Program Function:</b> Integrated the abstract class "Chart" and derived classes "PieChart", "LineGraph", and "BarGraph" to manage budget data uploaded by the user. The GUI allows the user to upload a .txt file of their spending and the program parses the data into category, amount spent, and date. The program then feeds the category array and amount spent array into a PieChart, Linegraph, and barGraph objects to manipulate the data. The formatted data is then fed into JavaFx graph objects: Piechart, Barchart, and XYGraph to display back to the user.
<br><br>
<b>Included:</b> 4 concrete classes (PieChart, LineGraph, BarGraph, Legend), 3 tester clases (PieChartTester, LineGraphTester, BarGraphTester), 1 abstract class (Graph), inheritance/polymorphism, custom exception class (MismatchedDataLengthException)
<br><br>
<b>Not Included:</b> an inner class was not included here because it does not make sense. None of the chart types are logically ties to each other. They are all child classes of the abstract class Graph.java. They exist independently and dont depend on the state of other classes.



## Wireframe
<br>

![Wireframe](src/main/resources/images/wireframe.png)


<br>

## GUI Gif
<br><br>
![Wireframe](src/main/resources/images/UD3Demo.gif)
<br><br>
## Data Input Example
<br><br>
![Data](src/main/resources/images/DataExample.png)
<br><br>
## UML
<br><br>
![UML](src/main/resources/images/UML.png)
<br><br>