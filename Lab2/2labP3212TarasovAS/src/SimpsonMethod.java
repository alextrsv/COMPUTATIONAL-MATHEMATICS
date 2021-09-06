public class SimpsonMethod {
     double  high, low;
     double accuracy;
     String description = "";
     Functions currentFunc = null;
     boolean isChange = false;

    public SimpsonMethod(double accuracy, int high, int low, Functions currentFunc) {
        this.accuracy = accuracy;
        this.currentFunc = currentFunc;
        if (low > high) {
            this.low = high;
            this.high = low;
            isChange  = true;
        }
        else{
            this.high = high;
            this.low = low;
        }
    }
    public String solve(){

        double IntgrN, Intgr2N, h, result, error, stepsNumber;;

        if (high != low)
        {
            for (int n = 4; n <= 1000000; n += 2)
            {
                IntgrN = integrate(n);
                Intgr2N = integrate(2 * n);

                if ((Math.abs(Intgr2N - IntgrN) / 15) < accuracy)
                {
                    result = Intgr2N;
                    if (isChange) result = - result;
                    stepsNumber = n;
                    error = Math.abs(Intgr2N - IntgrN) / 15;
                    description += "\nresult: "+result+ "\nerror= " + error;
                    break;
                }
                if (n == 10000) { System.out.println("Заданная точность не достигнута. Интеграл не имеет решения."); stepsNumber = 0; }
            }
        }
        else { result = 0; stepsNumber = 0; System.out.println("Пределы интегрирования равны. Result = 0."); }

        return description;
    }
    private double integrate(int n)
    {
        double sum = 0;
        double h = (high - low) / n; //вычисление размера шага
        for (int i = 1; i < n; i++)
        {
            sum += 4 * currentFunc.solve(low + i * h);
            ++i;
            sum += 2 * currentFunc.solve(low + i * h);
        }
        return (sum + currentFunc.solve(low) - currentFunc.solve(high)) * h / 3;
    }

}
