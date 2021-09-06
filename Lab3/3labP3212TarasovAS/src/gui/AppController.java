package gui;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Parent;

public class AppController {


    @FXML private ComboBox<String> comboChangeTask;
    @FXML private ComboBox<String> equationsBox;
    @FXML private TextField leftBorderInput;
    @FXML private TextField rightBorderInput;
    @FXML private TextField accuracyInput;
    @FXML private ComboBox<String> methodOfSolvingBox;
    @FXML private Button solveBtn;
    @FXML private Label solveOut;
    @FXML private LineChart<Double, Double> chart;

    Functions currentFunction = (n,a)-> (n*n + 25*n);
    SolvingMethod currentMethod = new BsectionMethod();
    int currentFunctionIndx = 0;
    int currentMethodIndx = 0;
    double leftBorder;
    double rightBorder;

    @FXML
    void solveBtnPressed(ActionEvent event) {
        leftBorder = Double.parseDouble(leftBorderInput.getText());
        rightBorder = Double.parseDouble(rightBorderInput.getText());


        currentMethod.setLeftBorder(leftBorder);
        currentMethod.setRightBorder(rightBorder);
        currentMethod.setAccuracy(Double.parseDouble(accuracyInput.getText()));
        currentMethod.setFunction(currentFunction);
        currentMethod.setFunstionStr(methodOfSolvingBox.getValue());

        solveOut.setText(currentMethod.solve());

        double step = 0.02;
        XYChart.Series<Double, Double> series = new XYChart.Series<>();
        for (double x = leftBorder; x <rightBorder; x = x + step) {
            series.getData().add(new XYChart.Data<>(x, currentFunction.solve(x,0)));
        }
        // сформированный массив точек, передаем графику для отображения
        chart.getData().setAll(series);

    }


    @FXML
    void changeTask(ActionEvent event) {

        switch (comboChangeTask.getValue()){
            case "Решение уравнения":
                Main.setModeIndx(0);
                break;
            case "Решение системы уравнений":
                Main.setModeIndx(1);
                break;
        }

        Stage stage  = (Stage)comboChangeTask.getScene().getWindow();

        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("\\fxml\\system.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setTitle("Hello World");
        stage.setScene(new Scene(root, 1200, 700));
    }


    @FXML
    void chooseMethod(ActionEvent event) {
        switch (methodOfSolvingBox.getValue()) {
            case "метод половинного деления":
                currentMethodIndx = 0;
                currentMethod = new BsectionMethod();
                break;
            case "метод хорд":
                currentMethodIndx = 1;
                currentMethod = new ChordMethod();
                break;
        }
    }


    @FXML
    void chooseEq(ActionEvent event) {
        switch (equationsBox.getValue()){
            case "x^2 + 25x = 0":
                Main.setModeIndx(0);
                currentFunction = (n, a)-> (n*n + 25*n);
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




    @FXML
    void initialize() {
        chart.setCreateSymbols(false);
        //
        ObservableList<String> modeList = FXCollections.observableArrayList("Решение уравнения", "Решение системы уравнений");

        comboChangeTask.setItems(modeList);
        comboChangeTask.setValue(modeList.get(Main.getModeIndx())); // устанавливаем выбранный элемент по умолчанию

        //
        ObservableList<String> functionsList = FXCollections.observableArrayList("x^2 + 25x = 0", "x^3+23x-56 = 0",
                "sin(x)", "x^3 - x + 4");


        equationsBox.setItems(functionsList);
        equationsBox.setValue(functionsList.get(currentFunctionIndx)); // устанавливаем выбранный элемент по умолчанию


        //
        ObservableList<String> methodsList = FXCollections.observableArrayList(BsectionMethod.getNameOfMethod(),
                ChordMethod.getNameOfMethod());

        methodOfSolvingBox.setItems(methodsList);
        methodOfSolvingBox.setValue(methodsList.get(currentMethodIndx)); // устанавливаем выбранный элемент по умолчанию

    }
}
