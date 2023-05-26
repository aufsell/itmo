import static java.lang.Math.*;

class Main {
    public static void main(String[] args) {
        long[] p = new long[]{7, 9, 11, 13, 15, 17, 19, 21};
        final int LEN = 18;
        double[] x = new double[LEN];
        for (int q = 0; q < x.length; q++) {
            x[q] = (double) ((Math.random() * 16) - 6);
        }
        double[][] k = new double[8][18];
        long sizeMax = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 18; ++j) {
                switch ((int)p[i]) {
                    case 7:{
                        k[i][j] = cbrt(pow((PI * ((2.0 / 3.0) + pow((2 * x[j]), 2))), 2));
                        break;
                    }
                    case 13,15,19,21:{
                        k[i][j] = pow(((pow(E, x[j]) / (1 - pow(((x[j] + 3) / 3.0), 3)) + 0.5) / (cos(sin(x[j])))), 3);
                        break;
                    }
                    default:{
                        k[i][j] = pow((pow((2 * (2 * (cos(cos(x[j])) + 1))), (cbrt(asin((x[j] + 2) / 16.0))))), (cbrt(pow(((pow(((3.0 / 4) / x[j]), 2)) / 0.5), 3))));
                    }
                }
                if (String.valueOf(k[i][j]).length() > sizeMax) {
                    sizeMax = Double.toString(k[i][j]).length();
                }
            }
        }
        for(double []l:k){
            for(double i:l){
                System.out.printf("%" + - (sizeMax + 5) + ".2f", i);
            }
            System.out.println();
        }
    }
}
