// for loop
// MAY_DIV_ZERO
// MAY_OUT_OF_BOUNDS
// Stefan Oancea
public class Test101 {
    public static void foo(int i) {
    	PrinterArray pa1 = new PrinterArray(1);
    	PrinterArray pa2 = new PrinterArray(100);
    	int x;
    	int k = 2;
    	for (int j = -2; j < 22; j++) {
    		x = 2 / j;
    		k = j + 20;
    	}
    	pa1.sendJob(k);
    }
}
