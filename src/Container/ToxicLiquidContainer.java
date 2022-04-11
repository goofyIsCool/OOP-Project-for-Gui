package Container;

import Program.Sender;

public class ToxicLiquidContainer extends HeavyCargo implements Liquid{

    private final String substance;
    private final String type; // chemical, biological, and physical.
    public ToxicLiquidContainer(Sender sender, String security, double tare, double netWeight, double grossWeight, String certificates, String doubleDoor, String substance, String type) {
        super(sender, security, tare, netWeight, grossWeight, certificates, doubleDoor);
        this.substance = substance;
        this.type = type;
    }

    public String print() {
        return "(id=" + id + ") Toxic Liquid container";
    }

    @Override
    public String toString(){
        return id + ";" + "Toxic Liquid;" + sender.getId() + ";" + shipId + ";" + security + ";" + tare + ";" + netWeight + ";" + grossWeight + ";" + certificates + ";" + doubleDoor + ";" + substance + ";" + type;
    }
}
