// if else w/o influence on arg
// NO_DIV_ZERO
// NO_OUT_OF_BOUNDS
// Timon Blattner timonbl@ethz.ch

public class Test14 {
    public static void foo(int i) {
        int x = 1;
        PrinterArray p = new PrinterArray(5);
        if (i > -3 && i < 3) {
            x = x / 1;
        }

        p.sendJob(x);
    }
}
