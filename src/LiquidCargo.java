import java.util.LinkedList;

public class LiquidCargo extends StandardContainer {

    public LiquidCargo(Sender sender, String security, double tare, double netWeight, double grossWeight, String certificates) {
        super(sender, security, tare, netWeight, grossWeight, certificates);
    }

    @Override
    public String toString(){
        return  id + ";" + "Liquid;" + sender.getId() + ";" + shipId + ";" + security + ";" + tare + ";" + netWeight + ";" + grossWeight + ";" + certificates;
    }
}
