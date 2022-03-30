import java.util.LinkedList;

public class ToxicCargo extends HeavyCargo {

    private String type; //powdery or liquid

    public ToxicCargo(Sender sender, String security, double tare, double netWeight, double grossWeight, LinkedList<String> certificates) {
        super(sender, security, tare, netWeight, grossWeight, certificates);
    }

    @Override
    public String toString(){
        return "toxic," + id + "," + sender.getId() + "," + security + "," + tare + "," + netWeight + "," + type;
    }
}
