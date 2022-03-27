import java.util.LinkedList;

public class ToxicExplosiveCargo extends Container{

    public ToxicExplosiveCargo(Sender sender, String security, double tare, double netWeight, double grossWeight, LinkedList<String> certificates) {
        super(sender, security, tare, netWeight, grossWeight, certificates);
    }
}
