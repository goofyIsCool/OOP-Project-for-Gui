import java.util.LinkedList;

public class LiquidCargo extends StandardContainer {



    public LiquidCargo(Sender sender, String security, double tare, double netWeight, double grossWeight, LinkedList<String> certificates) {
        super(sender, security, tare, netWeight, grossWeight, certificates);
    }
}
