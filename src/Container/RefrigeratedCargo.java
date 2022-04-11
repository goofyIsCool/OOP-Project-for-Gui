package Container;

import java.util.LinkedList;
import Program.*;

public class RefrigeratedCargo extends HeavyCargo {

    private final double electricityVoltage;

    public RefrigeratedCargo(Sender sender, String security, double tare, double netWeight, double grossWeight, String certificates, String doubleDoor, double voltage) {
        super(sender, security, tare, netWeight, grossWeight, certificates, doubleDoor);
        this.electricityVoltage = voltage;
    }

    public String print() {
        return "(id=" + id + ") Refrigerated container";
    }

    @Override
    public String toString(){
        return id + ";" + "Refrigerated;" + sender.getId() + ";" + shipId + ";" + security + ";" + tare + ";" + netWeight + ";" + grossWeight + ";" + certificates + ";" + doubleDoor + ";" + electricityVoltage;
    }
}
