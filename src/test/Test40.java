// ..
// NO_DIV_ZERO
// MAY_OUT_OF_BOUNDS
// ..
public class Test40 {
  public static void l() {
      PrinterArray r1 = new PrinterArray(97);
      int k = 5;
      int i = 94;
      
     while(k < 120) {
          k++;
      }

      while(i < 96) {
          i++;
      }
      r1.sendJob(i);
      r1.sendJob(k);
  }
}
