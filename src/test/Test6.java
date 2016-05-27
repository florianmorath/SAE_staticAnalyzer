// pointer assignment
// NO_DIV_ZERO
// NO_OUT_OF_BOUNDS
// Timon Blattner timonbl@ethz.ch

public class Test6 {
    public static void foo() {
        PrinterArray pa = new PrinterArray(5);
        PrinterArray xx = pa;
        xx.sendJob(2);
    }
}
