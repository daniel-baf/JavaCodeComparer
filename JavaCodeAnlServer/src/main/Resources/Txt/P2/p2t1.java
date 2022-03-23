public class MyClass {
    int myInt;
    
        /* un comentario largo */

    public p2t1() {
        // constructor
    }

    public p2t1(int myInt) {
        this.myInt = myInt;
    }

    public boolean isNumHigherThan(int numCompare) {
        return this.myInt > numCompare;
    }

    public void print(String message, boolean myBool) {
        message= "ESTE MENSAJE ES: " + message;
        boolean print = false;
        if(print) {
            // nothing
        } else {
            for(int k = 1; k < 21; k+=2) {
                while(true) {
                    print(message);
                    break;
                }
                // end of function
            }
        }
    }
}