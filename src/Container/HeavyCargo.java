package Container;

import java.util.LinkedList;
import Program.*;

public class HeavyCargo extends StandardContainer {

    protected String doubleDoor;

    public HeavyCargo(Sender sender, String security, double tare, double netWeight, double grossWeight, String certificates, String doubleDoor) {
        super(sender, security, tare, netWeight, grossWeight, certificates);

        this.doubleDoor = doubleDoor;
    }

    public String print() {
        return "(id=" + id + ") Heavy container";
    }

    @Override
    public String toString(){
        return id + ";" + "Heavy;" + sender.getId() + ";" + shipId + ";" + security + ";" + tare + ";" + netWeight + ";" + grossWeight + ";" + certificates + ";" + doubleDoor;
    }
}
