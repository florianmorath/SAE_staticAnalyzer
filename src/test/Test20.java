// for loop -- widening;
// NO_DIV_ZERO
// NO_OUT_OF_BOUNDS
// Elias August
// theoretically no_div_zero and no_out_of_bounds
public class Test20 {
    public static void bar(PrinterArray pa) {
    	int b=1;
  	  	for (int j=2 ;j<10;j++){
  	  		int f=1/j;
  	  		if (j==1){b=10;}
        }
  	  	pa = new PrinterArray(5);
  	  	pa.sendJob(b);

    }
}
