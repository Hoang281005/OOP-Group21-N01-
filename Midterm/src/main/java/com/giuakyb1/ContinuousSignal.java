package com.giuaky21;


public class ContinuousSignal implements Signal {
    private double amplitude;
    private double frequency;
    public ContinuousSignal(double amplitude, double frequency) {
        this.amplitude = amplitude;
        this.frequency = frequency;
    }
    @Override
    public double getAmplitude() {
        return amplitude;
    }
    @Override
    public double getFrequency() {
        return frequency;
    }
    @Override
    public String getSignalType() {
        return "Continuous";
    }
    public void sampleSignal(int durationInSeconds) {
        System.out.println("Sampling continuous signal for " + durationInSeconds + " seconds:");
        for (int t = 0; t < durationInSeconds; t++) {
            System.out.println("x(" + t + ") = " + calculateContinuousSignal(t));
        }
    }
    private double calculateContinuousSignal(int t) {
        return amplitude * Math.sin(2 * Math.PI * frequency * t);
    }
}
