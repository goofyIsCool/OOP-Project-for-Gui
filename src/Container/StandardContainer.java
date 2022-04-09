package Container;

import Program.*;

public class StandardContainer extends Container {

    public StandardContainer(Sender sender, String security, double tare, double netWeight, double grossWeight, String certificates) {
        super(sender, security, tare, netWeight, grossWeight, certificates);
    }

    @Override
    public String toString(){
        return  id + ";" + "Standard;" + sender.getId() + ";" + shipId + ";" + security + ";" + tare + ";" + netWeight + ";" + grossWeight + ";" + certificates;
    }
}
