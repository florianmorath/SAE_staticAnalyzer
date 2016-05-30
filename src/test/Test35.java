// ..
// NO_DIV_ZERO
// NO_OUT_OF_BOUNDS
// ..
public class Test35 {
  public static void foo() {
  		PrinterArray a = new PrinterArray(7);
  		PrinterArray b = a;
  		int i = 1;
  		int j = 3;
  		while(i < 4) {
  			i++;
  			j++;
  		}
  		b.sendJob(j);
  	}
}
