package Container;

import Program.*;

public class ToxicPowderyCargo extends HeavyCargo{

    private String typeOfToxicity; //chemical, biological, and physical

    public ToxicPowderyCargo(Sender sender, String security, double tare, double netWeight, double grossWeight, String certificates, String doubleDoor, String typeOfToxicity) {
        super(sender, security, tare, netWeight, grossWeight, certificates, doubleDoor);
        this.typeOfToxicity = typeOfToxicity;
    }

    public String print() {
        return "(id=" + id + ") Toxic Powdery container";
    }

    @Override
    public String toString(){
        return id + ";" + "Toxic Powdery;" + sender.getId() + ";" + shipId + ";" + security + ";" + tare + ";" + netWeight + ";" + grossWeight + ";" + certificates + ";" + doubleDoor + ";" + typeOfToxicity;
    }
}
