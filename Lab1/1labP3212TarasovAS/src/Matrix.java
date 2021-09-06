import static java.lang.Math.abs;

public class Matrix {
    private double accuracy;
    private int size;
    private String description = "";
    private double[] xVector;
    double[] oldXVector;
    int[] maxValuesIndex;

    private double[][] content = null;

    public double[][] getContent() {
        return content;
    }

    public void setContent(double[][] content) {
        this.content = content;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }

    public double[] getXVector() {
        return xVector;
    }

    public void setXVector(double[] xVector) {
        this.xVector = xVector;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }


    public String show() {
        String showDescription = "size:  " + size + "\n" +
                "accuracy:  " + accuracy + "\n" +
                "-------------------\n";
        for (double[] contentLine : content) {
            for (double contentLineElement : contentLine) {
                showDescription += String.format("%11.6f ", contentLineElement);
            }
            showDescription += "\n";
        }
        return showDescription;
    }

    public String start() {
        oldXVector = new double[size];
        xVector = new double[size];
        maxValuesIndex = new int[size];


        if (checkDiagonalDominance()) {
            description = "Diagonal dominance is detected...\n";
            solve();
        } else{
            description = "! there is no diagonal dominance in this matrix !\n";
            if(isTransformPossible()) {
                description += "transforming matrix...\n";
                transform();
                description += show() + "\n";
                solve();
            }else description += "transforming is impossible\nIt's impossible to solve this task by iteration method";
        }
        return description;
    }

    private void solve(){
        initXVector();

        int iteratorCount = 0;
        double[][] cmatrix = makeCmatrix();
        double xCircleTest = 0;

        while (!isTheEnd()) {
            iteratorCount++;
            for (int i = 0; i < size; i++) {
                oldXVector[i] = xVector[i];
                xVector[i] = 0;
            }
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    xVector[i] += cmatrix[i][j] * oldXVector[j];
                }
                xVector[i] += content[i][size] / content[i][i];
            }
        }

        description += "Решение: \n";
        for (int i = 0; i < size; i++) {
            description += "x" + (i + 1) + " =  " + xVector[i] + "\n";
        }
        description += "Погрешности: \n";
        for (int i = 0; i < size; i++)
            description +=  "dx[" + i + "]=  " + Math.abs(xVector[i] - oldXVector[i]) + "\n";

        description += "\niter: " + iteratorCount;

    }

    private void initXVector() {
        for (int i = 0; i < size; i++)
            xVector[i] = content[i][size] / content[i][i];
    }

    private boolean isTheEnd() {
//        double max = -999999;
        for (int i = 0; i < size; i++) {
//            if (abs(xVector[i] - oldXVector[i]) > max)
//                max = abs(xVector[i] - oldXVector[i]);
            if(abs(xVector[i] - oldXVector[i]) < accuracy)
                return true;
        }
        return false;
//        return max < accuracy;
    }

    private double[][] makeCmatrix() {
        double[][] fcmatrix = new double[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                fcmatrix[i][j] = -doubleRound(content[i][j] / content[i][i], 4);
            }
            fcmatrix[i][i] = 0;
        }
        return fcmatrix;
    }

    private double doubleRound(double value, int places) {
        double scale = Math.pow(10, places);
        return Math.round(value * scale) / scale;
    }

    private boolean checkDiagonalDominance() {
        boolean isOK = true;
        for (int i = 0; i < size; i++) {
            int lineSum = 0;
            for (int j = 0; j < size; j++) {
                if (i != j)
                    lineSum += abs(content[i][j]);
            }
            isOK &= (abs(content[i][i]) >= lineSum);
        }
        return isOK;
    }

    private boolean isTransformPossible() {
        double maxValue;
        int indexMax;
        for (int i = 0; i < size; i++) {
            maxValue = Math.abs(content[i][0]);
            indexMax = 0;
            for (int j = 1; j < size; j++) {
                if (Math.abs(content[i][j]) > maxValue) {
                    maxValue = Math.abs(content[i][j]);
                    indexMax = j;
                }
            }
            maxValuesIndex[i] = indexMax;
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i != j) {
                    if (maxValuesIndex[i] == maxValuesIndex[j]) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private void transform() {
        double[][] tmpContent = new double[size][size];

        for (int i = 0; i < size; i++) {
            int lineIndex = maxValuesIndex[i];
            tmpContent[lineIndex] =  content[i];
        }
        content = tmpContent;
    }

}

