import java.util.LinkedList;

public class ExplosiveCargo extends HeavyCargo {

    public ExplosiveCargo(Sender sender, String security, double tare, double netWeight, double grossWeight, LinkedList<String> certificates) {
        super(sender, security, tare, netWeight, grossWeight, certificates);
    }
}
