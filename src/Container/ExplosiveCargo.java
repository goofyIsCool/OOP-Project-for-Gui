package Container;

import java.util.LinkedList;
import Program.*;

public class ExplosiveCargo extends HeavyCargo {

    public ExplosiveCargo(Sender sender, String security, double tare, double netWeight, double grossWeight, String certificates) {
        super(sender, security, tare, netWeight, grossWeight, certificates);
    }

    @Override
    public String toString(){
        return id + ";" + "Explosive;" + sender.getId() + ";" + shipId + ";" + security + ";" + tare + ";" + netWeight + ";" + grossWeight + ";" + certificates;
    }
}
