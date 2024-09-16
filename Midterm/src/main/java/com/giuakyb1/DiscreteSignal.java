package com.giuaky21;


public class DiscreteSignal implements Signal {
    private double amplitude;
    private double frequency;
    public DiscreteSignal(double amplitude, double frequency) {
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
        return "Discrete";
    }
    public void sampleSignal(int durationInSeconds) {
        System.out.println("Sampling discrete signal for " + durationInSeconds + " seconds:");
        for (int t = 0; t < durationInSeconds; t++) {
            System.out.println("x[" + t + "] = " + calculateDiscreteSignal(t));
        }
    }
    private double calculateDiscreteSignal(int t) {
        return amplitude * Math.sin(2 * Math.PI * frequency * t);
    }
}
