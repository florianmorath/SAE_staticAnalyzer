// ..
// NO_DIV_ZERO
// MAY_OUT_OF_BOUNDS
// ..
public class Test37 {
  public static void l() {
    PrinterArray r = new PrinterArray(6);
    int i = -10;
    while(i < 10)
    {
    	i++;
    }
    r.sendJob(i);
  }
}
