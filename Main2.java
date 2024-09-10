import java.util.Arrays;
import java.util.List;

class DiscreteSignal {
    private List<Double> signalValues;

    public DiscreteSignal(List<Double> values) {
        this.signalValues = values;
    }

    public double getSignalAt(int n) {
        if (n >= 0 && n < signalValues.size()) {
            return signalValues.get(n);
        }
        return 0; 
    }

    public double sumOfSignals(int n) {
        double result = 0;
        for (int k = 0; k < signalValues.size(); k++) {
            result += signalValues.get(k) * delta(n - k);
        }
        return result;
    }

    private int delta(int n) {
        return n == 0 ? 1 : 0;
    }

    public void printSignal() {
        for (int i = 0; i < signalValues.size(); i++) {
            System.out.println("x(" + i + ") = " + signalValues.get(i));
        }
    }
}

class Radar {
    public static double radarSignal(int n) {
        if (n >= 0 && n <= 15) {
            return 1 - (n / 15.0);
        } else {
            return 0;
        }
    }

    public static void printRadarSignalForN(int maxN) {
        for (int n = 0; n <= maxN; n++) {
            System.out.println("Radar signal for n = " + n + ": " + radarSignal(n));
        }
    }
}

public class Main2 {
    public static void main(String[] args) {
        DiscreteSignal signal = new DiscreteSignal(Arrays.asList(1.0, 2.0, 3.0, 4.0));
        signal.printSignal();

        int n = 4;
        System.out.println("Tong tin hieu tai n = " + n + ": " + signal.sumOfSignals(n));

        Radar.printRadarSignalForN(4);
    }
}

