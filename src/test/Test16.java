// undefined arg n, division by 0 only for n!=0
// NO_DIV_ZERO
// NO_OUT_OF_BOUNDS
// Elias August
public class Test16 {
    public static void bar(int n) {
    	if (n>0){
    		int b=1/n;
    	} else if(n<0) {
    		int b=1/n;
    	} else {
    		int b=0;
    	}

    }
}
