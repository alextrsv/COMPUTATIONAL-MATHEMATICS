package gui;

import java.util.ArrayList;
import java.util.Collections;


public class LagrangePolynomial {

    static int dotsCount;
    static ArrayList<Double> xValues;
    static ArrayList<Double> yValues;

    public static void initParam(Double xVals[], Double yVals[], int dotsCnt){
        dotsCount = dotsCnt;
        xValues= new ArrayList<>();
        yValues= new ArrayList<>();
        xValues.clear();
        yValues.clear();
        Collections.addAll(xValues, xVals);
        Collections.addAll(yValues, yVals);
    }


   public static double interpolite (double x)
    {
        double lagrangePol = 0;
        for (int i = 0; i < dotsCount; i++)
        {
            double basicsPol = 1;
            for (int j = 0; j < dotsCount; j++)
            {
                if (j != i)
                {
                    basicsPol *= (x - xValues.get(j))/(xValues.get(i) - xValues.get(j));
                }
            }
            lagrangePol += basicsPol * yValues.get(i);
        }

        return lagrangePol;
    }

    public static ArrayList<Double> getxValues() {
        return xValues;
    }

    public static void setxValues(ArrayList<Double> xValues) {
        LagrangePolynomial.xValues = xValues;
    }

    public static ArrayList<Double> getyValues() {
        return yValues;
    }

    public static void setyValues(ArrayList<Double> yValues) {
        LagrangePolynomial.yValues = yValues;
    }
}
