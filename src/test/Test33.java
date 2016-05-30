// ..
// NO_DIV_ZERO
// MAY_OUT_OF_BOUNDS
// ..
public class Test33 {
  public static void l() {
    PrinterArray r = new PrinterArray(10);
    int i = 2;
    int j = 8;

    while (i < 5) {
      i = i+1;
    }
    r.sendJob(i);

    if (i == 3) {
    r.sendJob(j);
  }

    PrinterArray m = new PrinterArray(6);
    while (i >=2) {
      i = i + 1;
    }
    m.sendJob(i);
  }
}
