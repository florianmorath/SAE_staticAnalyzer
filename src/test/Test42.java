// ..
// NO_DIV_ZERO
// NO_OUT_OF_BOUNDS
// ..
public class Test42 {
  public static void foo(){
    int k = 3;
    int a = 4;
    if(k/a < 0){
      a = 0;
    }
  }
}
