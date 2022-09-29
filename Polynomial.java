import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Polynomial {

    private static final Pattern POLYNOMIAL_PATTERN = Pattern.compile("\\+?(?<coefficient>-?[\\d.]*)(?<power>x?\\d*)");

    public double[] coefficients;
    public int[] powers;

    public Polynomial() {
        this.coefficients = new double[0];
        this.powers = new int[0];
    }

    public Polynomial(double[] coefficients, int[] powers) {
        this.coefficients = coefficients.clone();
        this.powers = powers.clone();
    }

    public Polynomial(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String polynomialString = reader.readLine();
        reader.close();


        // Use regex to match the polynomials
        Matcher matcher = Polynomial.POLYNOMIAL_PATTERN.matcher(polynomialString);
        Map<Integer, Double> polynomials = new TreeMap<>();
        while (matcher.find()) {
            if ("".equals(matcher.group())) {
                continue;
            }
            String powerString = matcher.group("power");
            if ("".equals(powerString)) powerString = "0"; // ex. 5
            else if ("x".equals(powerString)) powerString = "1"; // ex. 5x
            else powerString = powerString.replace("x", "");
            String coefficientString = matcher.group("coefficient");
            if ("".equals(coefficientString)) coefficientString = "1"; // ex. x
            else if ("-".equals(coefficientString)) coefficientString = "-1"; // ex. -x

            Integer power = Integer.valueOf(powerString);
            double coefficient = Double.parseDouble(coefficientString);
            // Skip zeros
            if (coefficient == 0) {
                continue;
            }
            polynomials.put(power, polynomials.getOrDefault(power, 0.0) + coefficient);
        }

        // Put map back into arrays
        this.coefficients = new double[polynomials.size()];
        this.powers = new int[polynomials.size()];
        int i = 0;
        for (Map.Entry<Integer, Double> entry : polynomials.entrySet()) {
            this.powers[i] = entry.getKey();
            this.coefficients[i] = entry.getValue();
            i++;
        }
    }

    public Polynomial add(Polynomial other) {
        // Create map with this polynomial's values
        Map<Integer, Double> polynomials = new TreeMap<>();
        for (int i = 0; i < this.powers.length; i++) {
            polynomials.put(this.powers[i], this.coefficients[i]);
        }
        // Add other polynomial's values
        for (int i = 0; i < other.powers.length; i++) {
            polynomials.put(other.powers[i], polynomials.getOrDefault(other.powers[i], 0.0) + other.coefficients[i]);
        }
        // Remove 0s
        polynomials.entrySet().removeIf(entry -> entry.getValue() == 0.0);
        // Put back into arrays
        return Polynomial.fromMap(polynomials);
    }

    public Polynomial multiply(Polynomial other) {
        Map<Integer, Double> polynomials = new TreeMap<>();
        for (int i = 0; i < this.powers.length; i++) {
            int power = this.powers[i];
            double coefficient = this.coefficients[i];
            for (int j = 0; j < other.powers.length; j++) {
                int power2 = other.powers[j];
                double coefficient2 = other.coefficients[j];

                polynomials.put(power + power2, polynomials.getOrDefault(power + power2, 0.0) + coefficient * coefficient2);
            }
        }

        // Remove 0s
        polynomials.entrySet().removeIf(entry -> entry.getValue() == 0.0);
        // Put back into arrays
        return Polynomial.fromMap(polynomials);
    }

    public void saveToFile(String fileName) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        writer.write(this.toString());
        writer.close();
    }

    private static Polynomial fromMap(Map<Integer, Double> map) {
        double[] coefficients = new double[map.size()];
        int[] powers = new int[map.size()];
        int i = 0;
        for (Map.Entry<Integer, Double> entry : map.entrySet()) {
            powers[i] = entry.getKey();
            coefficients[i] = entry.getValue();
            i++;
        }
        return new Polynomial(coefficients, powers);
    }

    public double evaluate(double x) {
        double value = 0;
        for (int i = 0; i < this.powers.length; i++) {
            value += this.coefficients[i] * Math.pow(x, this.powers[i]);
        }
        return value;
    }

    public boolean hasRoot(double x) {
        return this.evaluate(x) == 0;
    }

    @Override
    public String toString() {
        if (this.powers.length == 0) {
            return "0";
        }

        StringBuilder string = new StringBuilder();
        for (int i = 0; i < this.powers.length; i++) {
            string.append(this.coefficients[i]).append("x").append(this.powers[i]);
            if (i + 1 < this.powers.length && this.coefficients[i + 1] >= 0) {
                string.append("+");
            }
        }
        return string.toString();
    }
}
