package gui;


public class MilnaMethod {

    static int dotsAmnt;
    static double A, B, E, k0, k1, k2, k3, xBegin, xEnd;
    static double h;
    static Double[] xValues;
    static Double[] yValues;
    static double[] derValues;
    static Functions function;


    public static void initParam(Functions func, double xBgn, double yBegin, double xnd, double accuracy){

        function = func;
        xBegin = xBgn;
        xEnd = xnd;
        E = accuracy;

        dotsAmnt = (int) Math.ceil(  (xEnd - xBegin)/E) + 1;
        h =  (xEnd - xBegin)/ (dotsAmnt - 1);

        xValues = new Double[dotsAmnt];
        yValues = new Double[dotsAmnt];
        derValues = new double[dotsAmnt];
        xValues[0] = xBegin;
        yValues[0] = yBegin;
        derValues[0] = function.solve(xValues[0], yValues[0]);

        solve();
    }


    public static void solve(){
        for (int i = 1; i <= 3; i++){
            k0 = h*function.solve(xValues[i-1], yValues[i-1]);
            k1 = h*function.solve(xValues[i-1] + h/2,yValues[i-1] + k0/2);
            k2 = h*function.solve(xValues[i-1] + h/2,yValues[i-1] + k1/2);
            k3 = h*function.solve(xValues[i-1] + h,yValues[i-1] + k2);
            xValues[i] =  xValues[i-1] + h;
            yValues[i] =  yValues[i-1] + (k0 + 2*k1 + 2*k2 + k3)/6;
            derValues[i] = function.solve(xValues[i], yValues[i]);
        }

       int i=3;

        while (i < dotsAmnt - 1){
            yValues[i+1]  = yValues[i-3] +  (4/3)*h*(2*derValues[i] - derValues[i-1] + 2*derValues[i-2]);
            xValues[i+1] = xValues[i] + h;

            B = yValues[i+1];
           do{
                A = B ;
                derValues[i+1] = function.solve(xValues[i+1], A);
                B = yValues[i-1] + h*(derValues[i+1] + 4*derValues[i] + derValues[i-1])/3;
            } while(Math.abs(A-B) > E);
            yValues[i+1] = B;
            i++;
        }
    }

    public static Double[] getxValues() {
        return xValues;
    }

    public static Double[] getyValues() {
        return yValues;
    }

    public static int getDotsAmnt() {
        return dotsAmnt;
    }

}
