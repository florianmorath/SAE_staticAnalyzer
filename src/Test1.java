/**
 * An example application you may want to analyze to test your analysis.
 *
 */
public class Test1 {
    public static void foo() {
    	int x = 1;
    	PrinterArray pa = new PrinterArray(4);
    	while (x < 3) {
    			pa.sendJob(x);
    			x++;
    	}
    	
    	int d = 0;
    	int y = 2 / d;
    }
}
