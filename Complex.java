public class Complex {
    private final double r, i;

    public Complex(double r, double i) {
        this.r = r;
        this.i = i;
    }

    public Complex add(Complex o) {
        return new Complex(r + o.r, i + o.i);
    }

    public Complex sub(Complex o) {
        return new Complex(r - o.r, i - o.i);
    }

    public Complex mul(Complex o) {
        return new Complex(r * o.r - i * o.i, r * o.i + i * o.r);
    }

    public Complex div(Complex o) {
        double d = o.r * o.r + o.i * o.i;
        if (d == 0) {
            throw new ArithmeticException("Деление на ноль");
        }
        return new Complex((r * o.r + i * o.i) / d, (i * o.r - r * o.i) / d);
    }

    public Complex neg() {
        return new Complex(-r, -i);
    }

    public double modSq() {
        return r * r + i * i;
    }

    public String getText() {
        if (i == 0) {
            return String.format("%.2f", r);
        }
        if (r == 0) {
            return String.format("%.2fi", i);
        }
        return String.format("%.2f %s %.2fi", r, (i < 0 ? "-" : "+"), Math.abs(i));
    }
}
