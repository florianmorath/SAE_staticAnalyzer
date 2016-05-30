// equality
// NO_DIV_ZERO
// MAY_OUT_OF_BOUNDS
// ..

public class Test44 {
	
	public static void foo(int i) {
		
		PrinterArray pa = new PrinterArray(6);
		
		while (i < 6)
			i++;
		
		if (i == 7)
			pa.sendJob(100);
			
	}
}
