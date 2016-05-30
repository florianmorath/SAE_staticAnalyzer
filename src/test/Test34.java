// ..
// NO_DIV_ZERO
// NO_OUT_OF_BOUNDS
// ..
public class Test34 {
  public static void foo() {
  		PrinterArray b = new PrinterArray(6);
  		int i = 2;
  		int j = 2;
  		b.sendJob(i * j + 1);
  		b.sendJob(i * j - 4);
  	}
}
