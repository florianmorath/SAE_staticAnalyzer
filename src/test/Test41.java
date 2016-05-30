// ..
// NO_DIV_ZERO
// MAY_OUT_OF_BOUNDS
// ..
public class Test41 {
  public static void foo() {
      PrinterArray a = new PrinterArray(4);
      PrinterArray b = a;
      int i = 1;
      int j = 1;
      while(i < 10000) {
          i++;
      }
      int k = 1;
      while(j < 3) {
          if(i > 11000) {
              k = 2;
          } else {
              k = 6;
          }
          j++;
      }
      b.sendJob(k);
  }
}
