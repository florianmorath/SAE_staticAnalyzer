// zero diff w/o undefined arg
// MAY_DIV_ZERO
// NO_OUT_OF_BOUNDS
// Timon Blattner timonbl@ethz.ch

public class Test5 {
    public static void foo() {
        int c = 10;
        int n = 0;
        int x = c / n;
        PrinterArray pa = new PrinterArray(c);
        pa.sendJob(1);
    }
}
