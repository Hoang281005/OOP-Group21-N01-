import main2;
import Signal;
public class Main1{


class DiscreteSignal implements Signal {
    @Override
    public void transmit() {
        System.out.println("Transmitting discrete signal.");
    }
}

class ContinuousSignal implements Signal {
    @Override
    public void transmit() {
        System.out.println("Transmitting continuous signal.");
    }
}

public class Main1 {
    public static void main (String[] args) {
        Signal discrete = new DiscreteSignal();
        Signal continuous = new ContinuousSignal();

        discrete.transmit();
        continuous.transmit();
    }
}
}
