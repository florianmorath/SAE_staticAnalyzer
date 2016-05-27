// maybe a negative arg
// MAY_DIV_ZERO
// MAY_OUT_OF_BOUNDS
// Timon Blattner timonbl@ethz.ch

public class Test13 {
    public static void foo(int i) {
        int x = 1;
        PrinterArray p = new PrinterArray(5);
        if (i > -3 && i < 3) {
            x = x / i;
        }

        p.sendJob(x);
    }
}
