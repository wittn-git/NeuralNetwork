import java.util.Random;
import java.util.function.Function;

public class Matrix {

    private float[][] values;
    public int m, n;

    //init matrix size m*n with all entries 0
    public Matrix(int m, int n){
        values = new float[m][n];
        this.m = m;
        this.n = n;
        for(int i=0; i<m; i++){
            for(int j=0; j<n; j++){
                put(i, j, 0);
            }
        }
    }

    //substiture entry i,j with value
    public void put(int i, int j, float value){
        values[i][j] = value;
    }

    public void toVectorColumn(float[] values){
        m = 1;
        n = values.length;
        this.values = new float[][]{values};
        this.values = Matrix.transpose(this).toArray();
        m = values.length;
        n = 1;
    }

    //get entry at i,j
    public float get(int i, int j) {
        return values[i][j];
    }

    //randomize every entry of matrix
    public void randomize(int lowerbound, int upperbound){
        Random random = new Random();
        random.setSeed(90);
        for(int i=0; i<m; i++){
            for(int j=0; j<n; j++){
                //values[i][j] = lowerbound + random.nextFloat() * (upperbound - lowerbound);
                values[i][j] = random.nextInt(10)-5;
            }
        }
    }

    //get the matrix as array
    public float[][] toArray(){
        return values;
    }

    public float[] getColumn(int col){
        float[] column = new float[m];
        for(int i=0; i<m; i++){
            column[i] = get(i, col);
        }
        return column;
    }

    public float[] getRow(int row){
        return values[row];
    }

    public static Matrix multiply(Matrix matrix1, Matrix matrix2) {
        Matrix result = new Matrix(matrix1.m, matrix2.n);
        for(int i=0; i<matrix1.m; i++){
            for(int j=0; j<matrix2.n; j++){
                float sum = 0;
                for(int k=0; k<matrix1.n; k++){
                    sum += matrix1.get(i, k)*matrix2.get(k, j);
                }
                result.put(i, j, sum);
            }
        }
        return result;
    }

    static public Matrix transpose(Matrix matrix1){
        Matrix result = new Matrix(matrix1.n, matrix1.m);
        for(int i=0; i<matrix1.m; i++){
            for(int j=0; j<matrix1.n; j++){
                result.put(j, i, matrix1.get(i, j));
            }
        }
        return result;
    }

    //apply function to every entry of matrix
    public static Matrix map(Matrix matrix1, Function<Float, Float> function){
        Matrix result = new Matrix(matrix1.m, matrix1.n);
        for(int i=0; i<matrix1.m; i++){
            for(int j=0; j<matrix1.n; j++){
                result.put(i, j, function.apply(matrix1.get(i, j)));
            }
        }
        return result;
    }

    public static Matrix add(Matrix matrix1, Matrix matrix2) {
        Matrix result = new Matrix(matrix1.m, matrix1.n);
        for(int i=0; i<matrix1.m; i++){
            for(int j=0; j<matrix1.n; j++){
                result.put(i, j, matrix1.get(i, j)+matrix2.get(i, j));
            }
        }
        return result;
    }

    public static Matrix subtract(Matrix matrix1, Matrix matrix2) {
        Matrix result = new Matrix(matrix1.m, matrix1.n);
        for(int i=0; i<matrix1.m; i++){
            for(int j=0; j<matrix1.n; j++){
                result.put(i, j, matrix1.get(i, j)-matrix2.get(i, j));
            }
        }
        return result;
    }

    //elementwise multiplication of two matrices
    public static Matrix hadamard_product(Matrix matrix1, Matrix matrix2){
        Matrix result = new Matrix(matrix1.m, matrix1.n);
        for(int i=0; i<matrix1.m; i++){
            for(int j=0; j<matrix1.n; j++){
                result.put(i, j, matrix1.get(i, j)*matrix2.get(i, j));
            }
        }
        return result;
    }

    public static Matrix scale(Matrix matrix1, float scale){
        Matrix result = new Matrix(matrix1.m, matrix1.n);
        for(int i=0; i<matrix1.m; i++){
            for(int j=0; j<matrix1.n; j++){
                result.put(i, j, matrix1.get(i, j)*scale);
            }
        }
        return result;
    }

    public float sumAbs(){
        float sum = 0;
        for(int i=0; i<m; i++){
            for(int j=0; j<n; j++){
                sum += Math.abs(get(i, j));
            }
        }
        return sum;
    }

    public String toString(){
        String s = "";
        for(float[] row: values){
            for(float entry: row){
                s += String.format("%.2f ", entry);
            }
            s += "\n";
        }
        return s;
    }

}
