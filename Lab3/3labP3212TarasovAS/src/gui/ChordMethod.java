package gui;

public class ChordMethod extends SolvingMethod{
    double previousX;
    double step = 0.5;//Math.abs(rightBorder  leftBorder) / 100.0;
    @Override
    public String solve() {

        if(checkSuffCondition(leftBorder, rightBorder)) {

            currentX = leftBorder;

            while (Math.abs(currentX - previousX) > accuracy || Math.abs(function.solve(currentX,0)) > accuracy){
                previousX = currentX;

                currentX = (leftBorder * function.solve(rightBorder,0) - rightBorder * function.solve(leftBorder,0))/
                        (function.solve(rightBorder,0) - function.solve(leftBorder,0));
                if (function.solve(leftBorder,0) * function.solve(currentX,0) > 0)
                    leftBorder = currentX;
                else rightBorder = currentX;
                steps++;
            }
            return String.format("%4.10f ", currentX);
        }else return "уравнене не имеет корней \nили имеет более одного корня на данном интервале";
    }


    public double getDerivativeFunction(double x) {
        switch (funstionStr){
            case "x^2 + 25x = 0":
                return 2*x + 25;
            case "x^3+23x-56 = 0":
                return 3*x*x+23;
            case "sin(x)":
                return Math.cos(x);
            case "x^3 - x + 4":
                return 3*x*x - 1;
        }
        return  0;
    }

    public double getSecondDerivativeFunction(double x){
        switch (funstionStr){
            case "x^2 + 25x = 0":
                return 2;
            case "x^3+23x-56 = 0":
                return 6*x;
            case "sin(x)":
                return -Math.sin(x);
            case "x^3 - x + 4":
                return 6*x;
        }
        return  0;
    }

    private boolean getCheckSign() {
        boolean sign;
        if(getDerivativeFunction(leftBorder) < 0) sign = false;
        else sign = true;

        if(getSecondDerivativeFunction(leftBorder) < 0) sign = false;
        else sign = true;

        return sign;
    }

    public static String getNameOfMethod() {
        return "метод хорд";
    }

}
