//import java.util.Scanner;
//
//import static java.lang.System.exit;
//
//public class UserInterface {
//    static Scanner in;
//    static double accuracy;
//    static int high;
//    static int low;
//    public static void go(){
//
//        System.out.println("Welcome\n" +
//                "Выберете функцию для интегрирования: \n" +
//                Function.AllToString());
//        in = new Scanner(System.in);
//
//        Function currentFunc = null;
//        switch (in.nextInt()){
//            case 1: {currentFunc = Function.FUNC1; break;}
//            case 2: {currentFunc = Function.FUNC2; break;}
//            case 3: {currentFunc = Function.FUNC3; break;}
//            case 4: {currentFunc = Function.FUNC4; break;}
//            case 5: {currentFunc = Function.FUNC5; break;}
//        }
//
//        System.out.println("\n"+currentFunc.toString());
//
//        System.out.println("Введите через пробел точность, вверхнюю и нижнюю пределы интегрирования");
//        while (true){
//            in = new Scanner(System.in);
//            try {
//                accuracy = in.nextDouble();
//                low = in.nextInt();
//                high = in.nextInt();
//
//                break;
//            }catch (java.util.InputMismatchException ex){
//                System.out.println("Некорректный ввод данных.\n" +
//                        "Введите через пробел точнсть, раздеяя целую и дробную частии запятой, \n" +
//                        "вверхнюю и ннижнюю границы интегрирования");
//
//            }
//        }
//
////        SimpsonMethod method = new SimpsonMethod(accuracy, high, low, currentFunc);
////        System.out.println(method.solve());
////        System.out.println("Желаете вычислить еще один интеграл?\n" +
////                "y - да\n" +
////                "n - нет");
////        in = new Scanner(System.in);
////        String flag  = in.nextLine();
////        if(flag.equals("y")){
////            go();
////        }else exit(0);
//
//    }
//
//}
