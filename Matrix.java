public class Matrix {
    private final int rows, cols;
    private final Complex[][] data;

    public Matrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.data = new Complex[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                data[i][j] = new Complex(0, 0);
            }
        }
    }

    public void set(int r, int c, Complex val) {
        data[r][c] = val;
    }

    public Complex get(int r, int c) {
        return data[r][c];
    }

    public Matrix add(Matrix o) {
        if (rows != o.rows || cols != o.cols) {
            throw new IllegalArgumentException("Размеры матриц должны совпадать");
        }
        Matrix res = new Matrix(rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                res.data[i][j] = data[i][j].add(o.data[i][j]);
            }
        }
        return res;
    }

    public Matrix mul(Matrix o) {
        if (cols != o.rows) {
            throw new IllegalArgumentException("Число столбцов первой матрицы должно быть равно числу строк второй");
        }
        Matrix res = new Matrix(rows, o.cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < o.cols; j++) {
                Complex sum = new Complex(0, 0);
                for (int k = 0; k < cols; k++) {
                    sum = sum.add(data[i][k].mul(o.data[k][j]));
                }
                res.data[i][j] = sum;
            }
        }
        return res;
    }

    public Matrix tr() {
        Matrix res = new Matrix(cols, rows);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                res.data[j][i] = data[i][j];
            }
        }
        return res;
    }

    // метод Гаусса
    public Complex det() {
        if (rows != cols) {
            throw new IllegalStateException("Матрица должна быть квадратной");
        }
        int n = rows;
        Complex[][] m = new Complex[n][n];
        for (int i = 0; i < n; i++) {
            System.arraycopy(data[i], 0, m[i], 0, n);
        }

        Complex detVal = new Complex(1, 0);
        for (int i = 0; i < n; i++) {
            int p = i;
            while (p < n && m[p][i].modSq() < 1e-12) {
                p++;
            }
            if (p == n) {
                return new Complex(0, 0);
            }
            if (p != i) {
                Complex[] t = m[i];
                m[i] = m[p];
                m[p] = t;
                detVal = detVal.neg();
            }
            detVal = detVal.mul(m[i][i]);
            for (int j = i + 1; j < n; j++) {
                Complex f = m[j][i].div(m[i][i]);
                for (int k = i; k < n; k++) {
                    m[j][k] = m[j][k].sub(f.mul(m[i][k]));
                }
            }
        }
        return detVal;
    }

    // метод присоединенной матрицы
    public Matrix inv() {
        if (rows != cols) {
            throw new IllegalStateException("Матрица должна быть квадратной");
        }
        int n = rows;
        Complex[][] aug = new Complex[n][2 * n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                aug[i][j] = data[i][j];
                aug[i][j + n] = (i == j) ? new Complex(1, 0) : new Complex(0, 0);
            }
        }
        for (int i = 0; i < n; i++) {
            int p = i;
            while (p < n && aug[p][i].modSq() < 1e-12) {
                p++;
            }
            if (p == n) {
                throw new ArithmeticException("Матрица вырожденная (нет обратной)");
            }
            if (p != i) {
                Complex[] t = aug[i];
                aug[i] = aug[p];
                aug[p] = t;
            }
            Complex pv = aug[i][i];
            for (int j = i; j < 2 * n; j++) {
                aug[i][j] = aug[i][j].div(pv);
            }
            for (int j = 0; j < n; j++) {
                if (j != i) {
                    Complex f = aug[j][i];
                    for (int k = i; k < 2 * n; k++) {
                        aug[j][k] = aug[j][k].sub(f.mul(aug[i][k]));
                    }
                }
            }
        }
        Matrix res = new Matrix(n, n);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                res.data[i][j] = aug[i][j + n];
            }
        }
        return res;
    }

    public Matrix div(Matrix o) {
        return this.mul(o.inv());
    }

    public void print() {
        for (int i = 0; i < rows; i++) {
            System.out.print("[ ");
            for (int j = 0; j < cols; j++) {
                System.out.print(data[i][j].getText() + "\t");
            }
            System.out.println("]");
        }
    }
}
