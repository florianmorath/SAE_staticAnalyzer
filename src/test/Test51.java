// if clausel
// NO_DIV_ZERO
// NO_OUT_OF_BOUNDS
// ..

public class Test51 {
    public static void foo(int i) {
        int x = 0;
        if (i < 0) {
            x = 1 - i;
            i = 0 - i;
        } else {
            x = i + 1;
        }
        
        PrinterArray pa = new PrinterArray(2);
        pa.sendJob(1);
    }
}