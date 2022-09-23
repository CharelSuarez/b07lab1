public class Polynomial {

    double[] coefficients;

    public Polynomial() {
        this.coefficients = new double[0];
    }

    public Polynomial(double[] coefficients) {
        this.coefficients = coefficients.clone();
    }

    public Polynomial add(Polynomial other) {
        double[] longest = this.coefficients.length >= other.coefficients.length ? this.coefficients : other.coefficients;
        double[] shortest = longest == this.coefficients ? other.coefficients : this.coefficients;

        double[] newCoefficients = longest.clone();
        for (int i = 0; i < shortest.length; i++) {
            newCoefficients[i] += shortest[i];
        }
        return new Polynomial(newCoefficients);
    }

    public double evaluate(double x) {
        double value = 0;
        for (int i = 0; i < this.coefficients.length; i++) {
            value += this.coefficients[i] * Math.pow(x, i + 1);
        }
        return value;
    }

    public boolean hasRoot(double x) {
        return this.evaluate(x) == 0;
    }


}
