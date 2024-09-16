package com.giuaky21;

public class Main {
    public static void main(String[] args) {
        DiscreteSignal discreteSignal = new DiscreteSignal(5, 2); 
        System.out.println("Signal Type: " + discreteSignal.getSignalType());
        System.out.println("Amplitude: " + discreteSignal.getAmplitude());
        System.out.println("Frequency: " + discreteSignal.getFrequency());
        discreteSignal.sampleSignal(10); 
        System.out.println();
        ContinuousSignal continuousSignal = new ContinuousSignal(3, 1); 
        System.out.println("Signal Type: " + continuousSignal.getSignalType());
        System.out.println("Amplitude: " + continuousSignal.getAmplitude());
        System.out.println("Frequency: " + continuousSignal.getFrequency());
        continuousSignal.sampleSignal(10); 
    }
}
