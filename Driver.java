import java.io.File;
import java.io.IOException;

public class Driver {

	public static void main(String [] args) {
		Polynomial p = new Polynomial();
		System.out.println(p.evaluate(3));
		double [] c1 = {6,5};
		int [] pow1 = {0,3};
		Polynomial p1 = new Polynomial(c1, pow1);
		double [] c2 = {0,-9};
		int [] pow2 = {1, 4};
		Polynomial p2 = new Polynomial(c2, pow2);
		Polynomial s = p1.add(p2);
		System.out.println("s(0.1) = " + s.evaluate(0.1));
		if (s.hasRoot(1)) {
			System.out.println("1 is a root of s");
		} else {
			System.out.println("1 is not a root of s");
		}

		System.out.println("----- CHAREL'S TESTS -----");

		try {
			Polynomial test1 = new Polynomial(new File("test_read.txt"));
			if ("5.0x0+1.0x1-2.0x2+3.0x5+3.25x22+69.0x57".equals(test1.toString())) {
				System.out.println("Test #1: File read passed");
			}
		} catch (IOException ignored) {}

		try {
			Polynomial test2 = new Polynomial(new double[]{1.0, -55.0, 99.0, -120.0}, new int[]{0, 1, 60, 102});
			test2.saveToFile("test_write.txt");
			Polynomial test2_2 = new Polynomial(new File("test_write.txt"));
			if ("1.0x0-55.0x1+99.0x60-120.0x102".equals(test2_2.toString())) {
				System.out.println("Test #2: File write/read passed");
			}
		} catch (IOException ignored) {}

		Polynomial test3 = new Polynomial(new double[]{1.0, -55.0, 99.0, -120.0}, new int[]{0, 1, 60, 102});
		Polynomial test3_2 = new Polynomial(new double[]{23.0, 22.5, 56.75, 0.5}, new int[]{7, 2, 5, 200});
		Polynomial test3_3 = test3.multiply(test3_2);
		// Verified with symbollab
		if ("22.5x2-1237.5x3+56.75x5-3121.25x6+23.0x7-1265.0x8+2227.5x62+5618.25x65+2277.0x67-2700.0x104-6810.0x107-2760.0x109+0.5x200-27.5x201+49.5x260-60.0x302".equals(test3_3.toString())) {
			System.out.println("Test #3: Big multiply passed");
		}

		try {
			test3_3.saveToFile("test_write.txt");
			Polynomial test4 = new Polynomial(new File("test_write.txt"));
			if ("22.5x2-1237.5x3+56.75x5-3121.25x6+23.0x7-1265.0x8+2227.5x62+5618.25x65+2277.0x67-2700.0x104-6810.0x107-2760.0x109+0.5x200-27.5x201+49.5x260-60.0x302".equals(test4.toString())) {
				System.out.println("Test #4: Big read/write passed");
			}
		} catch (IOException ignored) {}


		Polynomial test5 = new Polynomial(new double[]{1.0, -55.0, 99.0, -120.0}, new int[]{0, 1, 60, 102});
		Polynomial test5_2 = new Polynomial(new double[0], new int[0]);
		Polynomial test5_3 = test5.multiply(test5_2);
		if ("0".equals(test5_3.toString())) {
			System.out.println("Test #5: Zero multiply passed");
		}

		double test6 = test3_3.evaluate(1);
		double test6_2 = test3_3.evaluate(1.0625);
		// Verified with symbollab
		if (test6 == -7706.25 && test6_2 + 5029964786.56046 < 0.0001) {
			System.out.println("Test #6: Big evaluate passed");
		}

	}
}