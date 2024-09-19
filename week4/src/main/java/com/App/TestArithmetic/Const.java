package week4.src.main.java.com.App.TestArithmetic;

public class Const extends Node{
    private double value;
    public Const(double d) { value = d; }
    public double eval() { return value; }
}
