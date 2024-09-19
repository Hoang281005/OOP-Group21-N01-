package week4.src.main.java.com.App.TestArithmetic;

public class TestArithmetic {
    public static void main(String[] args) {
        Node n = new Plus(
            new Plus(
            new Const(1.1), new Const(2.2)),
            new Const(3.3));
        System.out.println(""+ n.eval());
    }
}
