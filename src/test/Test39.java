// ..
// NO_DIV_ZERO
// NO_OUT_OF_BOUNDS
// ..
public class Test39 {
  public void foo() {
      PrinterArray q = new PrinterArray(6);
      int k=0;
      int i=0;
      if (i==0)
          k=5;
      q.sendJob(1);
      q.sendJob(k);
  }
}
