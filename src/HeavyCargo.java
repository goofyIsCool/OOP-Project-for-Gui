import java.util.LinkedList;

public class HeavyCargo extends Container {



    public HeavyCargo(Sender sender, String security, double tare, double netWeight, double grossWeight, LinkedList<String> certificates) {
        super(sender, security, tare, netWeight, grossWeight, certificates);
    }

    @Override
    public String toString() {
        return "(id=" + id + ") Heavy container";
    }

}
