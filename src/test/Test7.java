// pointer assignment with inv arg
// NO_DIV_ZERO
// MAY_OUT_OF_BOUNDS
// Timon Blattner timonbl@ethz.ch

public class Test7 {
    public static void foo() {
        PrinterArray p1 = new PrinterArray(5);
        PrinterArray p2 = new PrinterArray(1);
        PrinterArray xx = p1;
        PrinterArray yy = p2;

        xx.sendJob(4);
        yy.sendJob(2);
    }
}
