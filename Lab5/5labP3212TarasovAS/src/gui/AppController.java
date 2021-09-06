package gui;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class AppController {

    @FXML private ComboBox<String> equationsBox;
    @FXML private TextField xBeginInput;
    @FXML private TextField yBeginInput;
    @FXML private TextField xEndInput;
    @FXML private TextField accuraccyInput;
    @FXML private AnchorPane pane;
    @FXML private LineChart<Double, Double> chart;


    Functions currentFunction = (n,a)-> (n*n + 2*n);
    int currentFunctionIndx = 0;
    double xBegin, yBegin;
    double xEnd;
    double accuraccy;
    Double xVals[];
    ObservableList<String> functionsList;

    @FXML
    void solveBtnPressed(ActionEvent event) {
        xBegin = Double.parseDouble(xBeginInput.getText());
        yBegin = Double.parseDouble(yBeginInput.getText());
        xEnd = Double.parseDouble(xEndInput.getText());
        accuraccy = Double.parseDouble(accuraccyInput.getText());

        MilnaMethod.initParam(currentFunction, xBegin, yBegin, xEnd, accuraccy);

        LagrangePolynomial.initParam(MilnaMethod.getxValues(), MilnaMethod.getyValues(), MilnaMethod.getDotsAmnt());
        xVals = MilnaMethod.getxValues();
        drawGraph();
        drawPoints();


    }

    private void drawGraph(){
        double step = (xVals[1] - xVals[0])/40;
        XYChart.Series<Double, Double> series = new XYChart.Series<>();
        series.setName("LagrangePolynomial");
        for (double x = xBegin; x < xVals[MilnaMethod.getDotsAmnt()-1] ; x = x + step) {
            series.getData().add(new XYChart.Data<>(x, LagrangePolynomial.interpolite(x)));
        }
        chart.getData().setAll(series);
    }

    private void drawPoints() {
        XYChart.Series<Double, Double> series = new XYChart.Series<>();
        for (int i = 0; i < MilnaMethod.getDotsAmnt(); i++){
            XYChart.Data point = new XYChart.Data(LagrangePolynomial.getxValues().get(i), LagrangePolynomial.getyValues().get(i));
            point.setNode(new Circle(3.0, Color.YELLOW));
            series.getData().add(point);
        }
        series.setName("interpolation nodes");
        chart.getData().add(series);
    }



    @FXML
    void chooseEq(ActionEvent event) {
        switch (equationsBox.getValue()){
            case "x/y":
                Main.setModeIndx(0);
                currentFunction = (x, y)-> x/y;
                break;
            case "2(x^2 + y)":
                Main.setModeIndx(1);
                currentFunction = (x, y)-> 2*(x*x + y);
                break;
            case "x^2 + 2y":
                Main.setModeIndx(2);
                currentFunction = (x, y)-> (x*x + 2*y);
                break;
            case "x^4":
                Main.setModeIndx(3);
                currentFunction = (x, y)-> (x*x*x*x);
                break;
            case "sqrt(x)":
                Main.setModeIndx(4);
                currentFunction = (x, y)-> Math.sqrt(x);
                break;
            case "sin(x)":
                Main.setModeIndx(5);
                currentFunction = (x, y)-> Math.sin(x);
                break;
        }
    }

    @FXML
    void initialize() {
        pane.getStylesheets().add(getClass().getResource("\\fxml\\chart.css").toExternalForm());
        chart.setCreateSymbols(false);


        functionsList = FXCollections.observableArrayList("x/y", "2(x^2 + y)", "x^2 + 2y", "x^4",
                "sqrt(x)", "sin(x)");

        equationsBox.setItems(functionsList);
        equationsBox.setValue(functionsList.get(currentFunctionIndx)); // устанавливаем выбранный элемент по умолчанию

    }
}
