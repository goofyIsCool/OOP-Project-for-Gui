import java.util.LinkedList;

public class HeavyCargo extends StandardContainer {

    public HeavyCargo(Sender sender, String security, double tare, double netWeight, double grossWeight, LinkedList<String> certificates) {
        super(sender, security, tare, netWeight, grossWeight, certificates);
    }

    public String print() {
        return "(id=" + id + ") Heavy container";
    }

    @Override
    public String toString(){
        return "heavy," + id + "," + sender.getId() + "," + security + "," + tare + "," + netWeight + "," + grossWeight;
    }
}
