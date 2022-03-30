import java.util.LinkedList;

public class StandardContainer extends Container {

    public StandardContainer(Sender sender, String security, double tare, double netWeight, double grossWeight, LinkedList<String> certificates) {
        super(sender, security, tare, netWeight, grossWeight, certificates);
    }

    @Override
    public String toString(){
        return "standard," + id + "," + sender.getId() + "," + security + "," + tare + "," + netWeight + "," + grossWeight;
    }
}
