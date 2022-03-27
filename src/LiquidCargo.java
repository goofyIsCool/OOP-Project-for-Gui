import java.util.LinkedList;

public class LiquidCargo extends Container{

    public LiquidCargo(Sender sender, String security, double tare, double netWeight, double grossWeight, LinkedList<String> certificates) {
        super(sender, security, tare, netWeight, grossWeight, certificates);
    }
}
