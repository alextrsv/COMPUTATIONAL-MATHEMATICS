package gui;

import java.util.ArrayList;


public class LagrangePolynomial {

    static Functions function;
    static double leftBorder, rightBorder;
    static int dotsCount;
    static ArrayList<Double> xValues;
    static ArrayList<Double> yValues;

    public static void initParam(Functions func, double leftBrdr, double rightBrdr, int dotsCnt){
        function = func;
        leftBorder = leftBrdr;
        rightBorder = rightBrdr;
        dotsCount = dotsCnt;
        xValues = new ArrayList<>();
        yValues = new ArrayList<>();
        prepareData();
    }


    public static void prepareData(){
        double step = Math.abs(rightBorder - leftBorder) / (dotsCount-1);
        double j = leftBorder;

        for (int i = 0; i < dotsCount; i++){
            xValues.add(i, j);
            yValues.add(i, function.solve(j, 0));
            j += step;
        }
    }


    public static void changeValue(int index, double yValue){
        yValues.set(index, yValue);

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
