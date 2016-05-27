// PrinterArray(division by hard-0)
// MAY_DIV_ZERO
// MAY_OUT_OF_BOUNDS
// Elias August
public class Test18 {
    public static void bar() {

    	int a=10;
    	int b=1;
       	    b=b/0;

        PrinterArray pa = new PrinterArray(5);
        pa.sendJob(b);

    }
}
