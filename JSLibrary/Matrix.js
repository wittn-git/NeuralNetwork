class Matrix{

    //create empty matrix with dim m*n
    constructor(m, n){
        this.m = m;
        this.n = n;
        this.values = Array(m).fill().map(() => Array(n).fill(0));
    }

    fromArray(array){
        this.m = array.length;
        this.n = 1;
        this.values = Array(this.m).fill().map(() => Array(this.n).fill(0));
        this.map((x,i,j) => array[i])
    }

    toArray() {
        let arr = [];
        for (let i = 0; i < this.m; i++) {
            for (let j = 0; j < this.n; j++) {
                arr.push(this.values[i][j]);
            }
        }
        return arr;
    }

    copy(){
        let matrix1 = new Matrix(this.m, this.n);
        for (let i = 0; i < this.m; i++) {
            for (let j = 0; j < this.n; j++) {
                matrix1.values[i][j] = this.values[i][j];
            }
        }
        return matrix1;
    }

    put(i, j, value){
        this.values[i][j] = value;
    }

    get(i, j){
        return this.values[i][j];
    }

    randomize(lowerbound, upperbound){
        this.map(x => random(lowerbound, upperbound))
    }

    absvalue(){
        let sum = 0;
        for(let i=0; i<this.m; i++){
            for(let j=0; j<this.n; j++){
                sum += abs(this.get(i, j));
            }
        }
        return sum;
    }

    //apply function to every entry
    map(funct){
        for(let i=0; i<this.m; i++){
            for(let j=0; j<this.n; j++){
                this.put(i, j, funct(this.get(i, j), i, j))
            }
        }
    }

    static map(funct, matrix1){
        let matrix2 = matrix1.copy()
        matrix2.map(funct);
        return matrix2;
    }

    add(matrix1){
        this.map((x, i, j) => x + matrix1.values[i][j]);
    }

    static add(matrix1, matrix2){
        let matrix3 = matrix1.copy();
        matrix3.add(matrix2);
        return matrix3;
    }

    sub(matrix1){
        this.map((x, i, j) => x - matrix1.values[i][j]);
    }

    static sub(matrix1, matrix2){
        let matrix3 = matrix1.copy();
        matrix3.sub(matrix2);
        return matrix3;
    }

    transpose(){
        let matrix1 = new Matrix(this.n, this.m);
        matrix1.map((x, i, j) => this.values[j][i])
        this.values = matrix1.values;
        this.m = matrix1.m;
        this.n = matrix1.n;
    }

    static transpose(matrix1){
        let matrix2 = matrix1.copy();
        matrix2.transpose();
        return matrix2;
    }

    scale(t){
        this.map(x => t*x);
    }

    static scale(matrix1, t){
        let matrix2 = matrix1.copy();
        matrix2.scale(t);
        return matrix2;
    }

    hadamard(matrix1){
        this.map((x, i, j) => x * matrix1.values[i][j]);
    }

    static hadamard(matrix1, matrix2){
        let matrix3 = matrix1.copy();
        matrix3.hadamard(matrix2);
        return matrix3;
    }

    multiply(matrix1){
        let new_values = [];
        for(let i=0; i<this.m; i++){
            let row = [];
            for(let j=0; j<matrix1.n; j++){
                let sum = 0;
                for(let k=0; k<this.n; k++){
                    sum += this.get(i, k) * matrix1.get(k, j);
                }
                row.push(sum);
            }
            new_values.push(row);
        }
        this.values = new_values;
        this.n = matrix1.n;
    }

    static multiply(matrix1, matrix2){
        let matrix3 = matrix1.copy();
        matrix3.multiply(matrix2);
        return matrix3;
    }

}