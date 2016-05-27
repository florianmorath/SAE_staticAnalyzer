/**
 * An example application you may want to analyze to test your analysis.
 *
 */
public class Test1 {
    public static void foo() {
    	int x = 2;
    	PrinterArray pa = new PrinterArray(2);
    	while (x != 1) {
    		if (x == 2)
    			pa.sendJob(5);
    	}
    }
}
