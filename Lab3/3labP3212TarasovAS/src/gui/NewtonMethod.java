package gui;

public class NewtonMethod  extends SolvingMethod{

    double currentX, currentY, previousX, previousY;
    int iterCount = 0;
    Functions firstFunc, secondFunc;
    String firtsFuncStr, secondFuncStr;
    private double det;
    double hlpX, hlpY;
    double jacobiMatrix[][] = new double[2][2];
    double reverseMatrix[][] = new double[2][2];

    public NewtonMethod(Functions firstFunc, Functions secondFunc, double x, double y, double accuracy){
        this.firstFunc = firstFunc;
        this.secondFunc = secondFunc;
        this.currentX = x;
        this.currentY = y;
        this.accuracy = accuracy;

    }

    @Override
    public String solve() {
//        System.out.println("fistsrtFunc: " + firtsFuncStr +
//                "\nsecondFunc: " + secondFuncStr +
//                "\nfirst: " + firstFunc +
//                "\nsecond: " + secondFunc);
        while (firstFunc.solve(currentX, currentY) > accuracy || secondFunc.solve(currentX, currentY) > accuracy){
            iterCount++;
            previousX = currentX;
            previousY = currentY;
            initMatrix(previousX, previousY);

            currentX = previousX - hlpX;
            currentY = previousY - hlpY;
 
            if (iterCount >= 1000000)
                return "превышен лимит итераций";
        }

        return "X =  " + String.format("%4.10f ",currentX) + "   Y =   " + currentY;
    }

    private void initMatrix(double x, double y){
        switch (firtsFuncStr){
            case "y - x^3 - 4 = 0":
                jacobiMatrix[0][0] = -3.0 * x * x;
                jacobiMatrix[0][1] = 1.0;
                break;
            case  "y + e^x + 1 = 0":
                jacobiMatrix[0][0] = Math.exp(x);
                jacobiMatrix[0][1] = 1.0;
                break;
            case "x^2 + y = 0":
                jacobiMatrix[0][0] = 2.0 * x;
                jacobiMatrix[0][1] = 1.0;
                break;
        }
        switch (secondFuncStr){
            case "y - x^3 - 4 = 0":
                jacobiMatrix[1][0] = -3.0 * x * x;
                jacobiMatrix[1][1] = 1.0;
                break;
            case  "y + e^x + 1 = 0":
                jacobiMatrix[1][0] = Math.exp(x);
                jacobiMatrix[1][1] = 1.0;
                break;
            case "x^2 + y = 0":
                jacobiMatrix[1][0] = 2.0 * x;
                jacobiMatrix[1][1] = 1.0;
                break;
        }

        det = jacobiMatrix[0][0] * jacobiMatrix[1][1] - jacobiMatrix[0][1] * jacobiMatrix[1][0];

//        reverseMatrix[0][0] = jacobiMatrix[0][0]/det;
//        reverseMatrix[0][1] = jacobiMatrix[1][0]/det;
//        reverseMatrix[1][0] = jacobiMatrix[0][1]/det;
//        reverseMatrix[1][1] = jacobiMatrix[1][1]/det;

        hlpX = (firstFunc.solve(x,y)* jacobiMatrix[1][1] - secondFunc.solve(x,y) * jacobiMatrix[0][1])/det;
        hlpY = (jacobiMatrix[0][0] * secondFunc.solve(x,y) - jacobiMatrix[1][0] * firstFunc.solve(x,y))/det;

//        System.out.println("matrix: \n"+ jacobiMatrix[0][0] + "   " + jacobiMatrix[0][1]+
//                "\n" +jacobiMatrix[1][0] + "   "+ jacobiMatrix[1][1]);
//        System.out.println("\nreversMatrix: \n"+ reverseMatrix[0][0] + "   " + reverseMatrix[0][1]+
//                "\n" +reverseMatrix[1][0] + "   "+ reverseMatrix[1][1]);
    }


    public Functions getFirstFunc() {
        return firstFunc;
    }

    public void setFirstFunc(Functions firstFunc) {
        this.firstFunc = firstFunc;
    }

    public Functions getSecondFunc() {
        return secondFunc;
    }


    public void setFirtsFuncStr(String firtsFuncStr) {
        this.firtsFuncStr = firtsFuncStr;
    }

    public void setSecondFuncStr(String secondFuncStr) {
        this.secondFuncStr = secondFuncStr;
    }

}
