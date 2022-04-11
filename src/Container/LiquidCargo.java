package Container;

import Program.*;

public class LiquidCargo extends StandardContainer implements Liquid{

    private final String substance; // Liquid Type

    public LiquidCargo(Sender sender, String security, double tare, double netWeight, double grossWeight, String certificates, String substance) {
        super(sender, security, tare, netWeight, grossWeight, certificates);
        this.substance = substance;
    }

    public String print() {
        return "(id=" + id + ") Liquid container";
    }

    @Override
    public String toString(){
        return  id + ";" + "Liquid;" + sender.getId() + ";" + shipId + ";" + security + ";" + tare + ";" + netWeight + ";" + grossWeight + ";" + certificates + ";" + substance;
    }
}
