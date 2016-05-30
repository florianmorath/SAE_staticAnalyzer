// ..
// MAY_DIV_ZERO
// NO_OUT_OF_BOUNDS
// ..
public class Test43 {
  public static void foo(){
    int k = 3;
    int a = 0;
    if(k/a < 0){
      a = 0;
    }
  }
}
