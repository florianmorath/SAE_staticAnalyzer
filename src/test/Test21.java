// for loop -- widening;
// NO_DIV_ZERO
// NO_OUT_OF_BOUNDS
// Elias August
public class Test21 {
    public static void bar(PrinterArray pa) {
    	int b=1;
  	  	for (int j=10 ;j>1;j--){
  	  		int f=1/j;
  	  		if (j==1){b=10;}
        }
  	  	pa = new PrinterArray(5);
  	  	pa.sendJob(b);

    }
}
