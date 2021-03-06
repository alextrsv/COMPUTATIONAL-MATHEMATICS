package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SystemController {

    @FXML private Label labelUserName;
    @FXML private ComboBox<String> comboChangeTask;
    @FXML private ComboBox<String> firstEqBox;
    @FXML private ComboBox<String> secondEqBox;
    @FXML private Label xLabel;
    @FXML private TextField xInput;
    @FXML private Label yLabel;
    @FXML private TextField yInput;
    @FXML private Label accuracyLabel;
    @FXML private TextField accuracyInput;
    @FXML private Button solveBtn;
    @FXML private Label xSolveOut;
    @FXML private Label ySolveOut;
    @FXML private LineChart<Double, Double> chart;

    Functions firstFunc, secondFunc;
    String firstFuncStr, secondFuncStr;


    @FXML
    void chooseFirstEq(ActionEvent event) {
        firstFuncStr = firstEqBox.getValue();
        chooseFunc(firstFuncStr, 1);
    }

    @FXML
    void chooseSecondEq(ActionEvent event) {
        secondFuncStr = secondEqBox.getValue();
        chooseFunc(secondFuncStr, 2);
    }

    private void chooseFunc(String funcStr, int mode) {
        Functions func = null;
        switch (funcStr){
            case "y - x^3 - 4 = 0":
                func = (x, y)-> (y - x*x*x - 4.0);
                break;
            case  "y + e^x + 1 = 0":
                Main.setModeIndx(1);
                func = (x, y)-> ( y + Math.exp(x) + 1);
                break;
            case "x^2 + y = 0":
                Main.setModeIndx(3);
                func = (x, y)-> (x * x + y);
                break;
            case "y - x^3 - 4":
                func = (x, y) -> (x * x * x  + 4.0);
                break;
            case "y + e^x + 1":
                func = (x, y) -> (-Math.exp(x) - 1);
                break;
            case "x^2 + y":
                func = (x, y) ->  -(x * x);
                break;
        }
        if (mode == 1) firstFunc = func;
        else secondFunc = func;
    }

    @FXML
    void solveBtnPressed(ActionEvent event) {
        NewtonMethod newtonMethod = new NewtonMethod(firstFunc, secondFunc, Double.parseDouble(xInput.getText()),
                Double.parseDouble(yInput.getText()), Double.parseDouble(accuracyInput.getText()));
        newtonMethod.setFirtsFuncStr(firstEqBox.getValue());
        newtonMethod.setSecondFuncStr(secondEqBox.getValue());
        xSolveOut.setText(newtonMethod.solve());

        double step = 0.02;
        XYChart.Series<Double, Double> series1 = new XYChart.Series<>();
        XYChart.Series<Double, Double> series2 = new XYChart.Series<>();
        firstFuncStr = firstFuncStr.replace(" = 0", "");
        secondFuncStr = secondFuncStr.replace(" = 0", "");
        System.out.println("firstFuncStr: " + firstFuncStr);
        System.out.println("secondFuncStr: " + secondFuncStr);

        chooseFunc(firstFuncStr, 1);
        chooseFunc(secondFuncStr, 2);

        for (double x = -20; x <20; x = x + step) {
            series1.getData().add(new XYChart.Data<>(x, firstFunc.solve(x,0)));
            series2.getData().add(new XYChart.Data<>(x, secondFunc.solve(x,0)));
        }
//        // ???????????????????????????? ???????????? ??????????, ???????????????? ?????????????? ?????? ??????????????????????
        chart.getData().addAll(series1, series2);


    }


    @FXML
    void changeTask(ActionEvent event) {
        switch (comboChangeTask.getValue()){
            case "?????????????? ??????????????????":
                Main.setModeIndx(0);
                break;
            case "?????????????? ?????????????? ??????????????????":
                Main.setModeIndx(1);
                break;
        }

        Stage stage  = (Stage)comboChangeTask.getScene().getWindow();

        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("\\fxml\\single.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setTitle("Hello World");
        stage.setScene(new Scene(root, 1200, 700));
    }


    @FXML
    void initialize() {
        chart.setCreateSymbols(false);
        ObservableList<String> mode = FXCollections.observableArrayList("?????????????? ??????????????????", "?????????????? ?????????????? ??????????????????");

        comboChangeTask.setItems(mode);
        comboChangeTask.setValue(mode.get(Main.getModeIndx())); // ?????????????????????????? ?????????????????? ?????????????? ???? ??????????????????


        //
        ObservableList<String> EqList = FXCollections.observableArrayList("y - x^3 - 4 = 0", "y + e^x + 1 = 0", "x^2 + y = 0" );


        firstEqBox.setItems(EqList);
        firstEqBox.setValue(EqList.get(Main.getModeIndx())); // ?????????????????????????? ?????????????????? ?????????????? ???? ??????????????????

        //
        secondEqBox.setItems(EqList);
        secondEqBox.setValue(EqList.get(Main.getModeIndx())); // ?????????????????????????? ?????????????????? ?????????????? ???? ??????????????????
    }
}
