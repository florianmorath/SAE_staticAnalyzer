// loop that will end up positive
// NO_DIV_ZERO
// NO_OUT_OF_BOUNDS
// Timon Blattner timonbl@ethz.ch

public class Test12 {
    public static void foo(int i) {
        int x;

        for (x = i; x <= 3; x++)
            ;

        PrinterArray pa = new PrinterArray(x);
        pa.sendJob(2);
    }
}
