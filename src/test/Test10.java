// extended pointer assignment with back-forth assgn
// NO_DIV_ZERO
// NO_OUT_OF_BOUNDS
// Timon Blattner timonbl@ethz.ch

public class Test10 {
    public static void foo() {
        PrinterArray p1 = new PrinterArray(5);
        PrinterArray p2 = new PrinterArray(1);
        PrinterArray xx = p2;

        PrinterArray yy = xx;
        xx = p1;

        xx.sendJob(4);
    }
}
