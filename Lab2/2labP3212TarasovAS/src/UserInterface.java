import java.util.Scanner;
import static java.lang.System.exit;

public class UserInterface {

        static Scanner in;
        static double accuracy;
        static int high;
        static int low;

        public static void go(){

            String[] functionsList = {"y = (x^2 - 25)/(x-5)", "y = x/(x-1)", "y = x^2", "y = 2*x + 1"};
            System.out.println("Welcome\n" +
                    "Выберете функцию для интегрирования: \n");
            for (int i = 0; i < 4; i++) {
                System.out.println("[" + (i+1) +"]  "  + functionsList[i] + ";");
            }
            in = new Scanner(System.in);

            Functions currentFunc = null;
            switch (in.nextInt()){
                case 1: {currentFunc = (n)-> (n*n - 25)/(n-5); break;}
                case 2: {currentFunc = (n)-> n/(n-1); break;}
                case 3: {currentFunc = (n)-> n * n; break;}
                case 4: {currentFunc = (n)-> 2*n + 1; break;}
            }

            System.out.println("Введите через пробел точность, вверхнюю и нижнюю пределы интегрирования");
            while (true){
                in = new Scanner(System.in);
                try {
                    accuracy = in.nextDouble();
                    low = in.nextInt();
                    high = in.nextInt();

                    break;
                }catch (java.util.InputMismatchException ex){
                    System.out.println("Некорректный ввод данных.\n" +
                            "Введите через пробел точнсть, раздеяя целую и дробную частии запятой, \n" +
                            "вверхнюю и ннижнюю границы интегрирования");

                }
            }

            SimpsonMethod method = new SimpsonMethod(accuracy, high, low, currentFunc);
            System.out.println(method.solve());
            System.out.println("Желаете вычислить еще один интеграл?\n" +
                    "y - да\n" +
                    "n - нет");
            in = new Scanner(System.in);
            String flag  = in.nextLine();
            if(flag.equals("y")){
                go();
            }else exit(0);

        }
}


