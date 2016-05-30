// ..
// NO_DIV_ZERO
// NO_OUT_OF_BOUNDS
// ..
public class Test36 {
  public static void foo() {
  		PrinterArray a = new PrinterArray(6);
  		PrinterArray b = new PrinterArray(6);
  		PrinterArray c = new PrinterArray(6);
  		int i,j;
  		if (b == c) {
  			i = 3;
  		} else {
  			i = 5;
  		}

  		if (b == c) {
  			j = 0;
  		} else {
  			j = 2;
  		}

  		a.sendJob(i);
  		a.sendJob(j);
  	}
}
