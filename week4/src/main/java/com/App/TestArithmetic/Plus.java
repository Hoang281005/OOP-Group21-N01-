package week4.src.main.java.com.App.TestArithmetic;

public class Plus extends Binop {
    public Plus(Node l, Node r) {
        super(l, r);
    }
        public double eval() {
            return lChild.eval() + rChild.eval(); 
    }
}
