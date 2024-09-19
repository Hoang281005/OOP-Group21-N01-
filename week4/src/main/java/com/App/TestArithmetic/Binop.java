package week4.src.main.java.com.App.TestArithmetic;

public class Binop extends Node{
    protected Node lChild, rChild;
    public Binop(Node l, Node r) {
        lChild = l;
        rChild = r;
    }
}
