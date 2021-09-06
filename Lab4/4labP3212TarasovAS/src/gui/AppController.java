package gui;



import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class AppController {


    @FXML private ComboBox<String> equationsBox;
    @FXML private TextField leftBorderInput;
    @FXML private TextField rightBorderInput;
    @FXML private TextField dotsCountInput;


    @FXML private Label YOut;
    @FXML private TextField XInput;

    @FXML private ComboBox<String> dotNumberBox;
    @FXML private TextField changedYInput;

    @FXML private LineChart<Double, Double> chart;
    @FXML private AnchorPane pane;

    Functions currentFunction = (n,a)-> (n*n + 2*n);
    int currentFunctionIndx = 0;
    double leftBorder;
    double rightBorder;
    int dotsCount;
    ObservableList<String> functionsList;

    @FXML
    void solveBtnPressed(ActionEvent event) {

        leftBorder = Double.parseDouble(leftBorderInput.getText());
        rightBorder = Double.parseDouble(rightBorderInput.getText());
        dotsCount = Integer.parseInt(dotsCountInput.getText());

//        currentFunction = (x, y)-> (x*x + 2*x + 1);
        currentFunction = (x, y)-> (Math.sqrt(x));
        LagrangePolynomial.initParam(currentFunction, leftBorder, rightBorder, dotsCount);


        initDotsBox();
        drawGraph();
        drawPoints();


    }

    @FXML
    void resolveBtnClicked(ActionEvent event) {
        YOut.setText(String.format("%.10f", LagrangePolynomial.interpolite(Double.parseDouble(XInput.getText()))));
    }

    @FXML
    void changeValueBtnCliked(ActionEvent event) {
        LagrangePolynomial.changeValue(Integer.parseInt(dotNumberBox.getValue())-1, Double.parseDouble(changedYInput.getText()));
        drawGraph();
        drawPoints();
    }



    private void drawPoints() {
        XYChart.Series<Double, Double> series = new XYChart.Series<>();
        for (int i = 0; i < dotsCount; i++){
            XYChart.Data point = new XYChart.Data(LagrangePolynomial.getxValues().get(i), LagrangePolynomial.getyValues().get(i));
            point.setNode(new Circle(3.0, Color.GREEN));
            series.getData().add(point);
        }
        series.setName("interpolation nodes");
        chart.getData().add(series);
    }

    private void drawGraph(){
        double step = 0.02;
        XYChart.Series<Double, Double> series = new XYChart.Series<>();
        series.setName("LagrangePolynomial");
        for (double x = leftBorder; x <= rightBorder ; x = x + step) {
            series.getData().add(new XYChart.Data<>(x, LagrangePolynomial.interpolite(x)));
        }


        XYChart.Series<Double, Double> series2 = new XYChart.Series<>();
        series2.setName(functionsList.get(currentFunctionIndx));
        for (double x = leftBorder; x <= rightBorder; x = x + step) {
            series2.getData().add(new XYChart.Data<>(x, currentFunction.solve(x,0)));
        }
        chart.getData().setAll(series, series2);
    }


    @FXML
    void chooseEq(ActionEvent event) {
        switch (equationsBox.getValue()){
            case "x^2 + 2x = 0":
                Main.setModeIndx(0);
                currentFunction = (n, a)-> (n*n + 2*n);
                break;
            case "x^3+23x-56 = 0":
                Main.setModeIndx(1);
                currentFunction = (n,a)-> (n*n*n + 23*n - 56);
                break;
            case "sin(x)":
                Main.setModeIndx(2);
                currentFunction = (n,a)-> (Math.sin(n));
                break;
            case "x^3 - x + 4":
                Main.setModeIndx(3);
                currentFunction = (n,a)-> (n*n*n - n + 4);
                break;
        }
    }

    private void initDotsBox(){
        ObservableList<String> dotsNumberList = FXCollections.observableArrayList();
        for (int i = 0; i < dotsCount; i++){
            dotsNumberList.add(String.valueOf(i+1));
        }

        dotNumberBox.setItems(dotsNumberList);
        dotNumberBox.setValue("выберете точку"); // устанавливаем выбранный элемент по умолчанию
    }


    @FXML
    void initialize() {
        pane.getStylesheets().add(getClass().getResource("\\fxml\\chart.css").toExternalForm());
        chart.setCreateSymbols(false);


        functionsList = FXCollections.observableArrayList("x^2 + 2x = 0", "x^3+23x-56 = 0",
                "sin(x)", "x^3 - x + 4");

        equationsBox.setItems(functionsList);
        equationsBox.setValue(functionsList.get(currentFunctionIndx)); // устанавливаем выбранный элемент по умолчанию

    }
}
