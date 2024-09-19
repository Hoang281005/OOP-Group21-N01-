package com.App.Time;

public class TestTime {
    public static void main(String[] args) {
        Time t1 = new Time();
        Time t2 = new Time(20, 3, 45);
        t1.setHour(7).setMinute(32).setSecond(23);
        System.out.println("t1 is " + t1);
        System.out.println("t2 is " + t2);
    }
}
