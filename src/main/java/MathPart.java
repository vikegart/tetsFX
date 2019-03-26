public class MathPart {
    private static final double[][] equationArray = {
            {0, 1},
            {2, 3}
    };

    public static double[][] getEquationArray(){
        return equationArray.clone();
    }

    public static boolean isZeroDeterminant(){
        if(getDeterminant(equationArray) == 0){
            return true;
        }else{
            return false;
        }
    }

    public static void printEquationArray(){
        for (int i = 0; i < equationArray.length; i++) {
            double[] doubles = equationArray[i];
            for (int j = 0; j < doubles.length; j++) {
                double aDouble = doubles[j];
                System.out.print(aDouble + "\t");
            }
            System.out.println();
        }
        System.out.println(getDeterminant(equationArray));
    }
    public static double getDeterminant(double[][] mat) {
        double result = 0;
        if(mat.length == 2) {
            result = mat[0][0] * mat[1][1] - mat[0][1] * mat[1][0];
            return result;
        }
        for(int i = 0; i < mat[0].length; i++) {
            double temp[][] = new double[mat.length - 1][mat[0].length - 1];
            for(int j = 1; j < mat.length; j++) {
                System.arraycopy(mat[j], 0, temp[j-1], 0, i);
                System.arraycopy(mat[j], i+1, temp[j-1], i,mat[0].length - i - 1);
            }
            result += mat[0][i] * Math.pow(-1, i) * getDeterminant(temp);
        }
        return result;
    }
}
