// if with a correct sendJob inside
// MAY_DIV_ZERO
// NO_OUT_OF_BOUNDS
// Timon Blattner timonbl@ethz.ch

public class Test2 {
    public static void bar(int n) {
        int a = 5/n;
        PrinterArray pa = new PrinterArray(10);
        if ((a >= 0) && (a < 10)) {
            pa.sendJob(a);
        }
    }
}
