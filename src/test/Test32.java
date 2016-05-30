// ..
// NO_DIV_ZERO
// NO_OUT_OF_BOUNDS
// ..
public class Test32 {
  public static void IF_TESTER() {
		int i = 20;
		int ii = 20;
		PrinterArray b = new PrinterArray(2);

		int ten = 10;
		// NEQ
		if(20 != i){
			b.sendJob(i);
		}
		if(ii != i){
			b.sendJob(i);
		}
		if(i != 20){
			b.sendJob(i);
		}

		// LE
		if(i < 10){
			b.sendJob(i);
		}
		if(i < ten){
			b.sendJob(i);
		}
		if(10 < i){
		}else{
			b.sendJob(i);
		}

		// LEQ
		if(i <= 10){
			b.sendJob(i);
		}
		if(i <= ten){
			b.sendJob(i);
		}
		if(10 <= i){
		}else{
			b.sendJob(i);
		}

		// EQ
		if(i == 9){
			b.sendJob(i);
		}
		if(i == ten){
			b.sendJob(i);
		}
		if(9 == i){
			b.sendJob(i);
		}

		//GE
		if(10 > i){
			b.sendJob(i);
		}
		if(ten > i){
			b.sendJob(i);
		}
		if(i > 30){
			b.sendJob(i);
		}

		//GEQ
		if(10 >= i){
			b.sendJob(i);
		}
		if(ten >= i){
			b.sendJob(i);
		}
		if(i >= 30){
			b.sendJob(i);
		}

		b.sendJob(1);

	  }
}
