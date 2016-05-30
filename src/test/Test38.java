// ..
// NO_DIV_ZERO
// NO_OUT_OF_BOUNDS
// ..
public class Test38 {
  public static void l() {
      PrinterArray r = new PrinterArray(6);
      int i = 0;
      int k = 0;
      while(k < 3)
      {
          k++;
          while(i < 2) {
              i++;
          }
      }
      r.sendJob(i);
  }
}
