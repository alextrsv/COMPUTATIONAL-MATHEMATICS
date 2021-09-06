//public enum Function {
//    FUNC1("y = x;"){
//        @Override
//        public String toString() {
//            return this.expression;
//        }
//        public double func(double x){ return x; }
//    },
//    FUNC2("y = 2*x + 1;"){
//        @Override
//        public String toString() {
//            return this.expression;
//        }
//
//        public double func(double x){ return 2 * x + 1; }
//
//    },
//    FUNC3("y = x^2;"){
//        @Override
//        public String toString() {
//            return this.expression;
//        }
//
//        public double func(double x){ return x * x; }
//
//    },
//    FUNC4("y = sqrt(1+x^2);"){
//        @Override
//        public String toString() {
//            return this.expression;
//        }
//        public double func(double x){ return Math.sqrt(1 + Math.pow(x, 2)); }
//
//    },
//    FUNC5("y = 1 / x;"){
//        @Override
//        public String toString() {
//            return this.expression;
//        }
//        public double func(double x){ return 1 / x; }
//    };
//    String expression;
//
//    Function(String expression){
//        this.expression = expression;
//    }
//
//    public static String AllToString(){
//        String description = "";
//        for (Function func : Function.values() ) {
//            description += func.toString() + ",\n";
//        }
//        return description;
//    }
//
//    public abstract double func(double x);
//}
