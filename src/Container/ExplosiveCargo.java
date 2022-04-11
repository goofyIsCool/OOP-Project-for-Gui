package Container;

import java.util.LinkedList;
import Program.*;

public class ExplosiveCargo extends HeavyCargo {

    private final String type; //mechanical, nuclear, and chemical
    public static String[] types = {"Explosives with a mass explosion hazard", "Explosives with a severe projection hazard", "Explosives with a fire", "Minor fire or projection hazard", "An insensitive substance with a mass explosion hazard", "Extremely insensitive articles"};

    public ExplosiveCargo(Sender sender, String security, double tare, double netWeight, double grossWeight, String certificates, String doubleDoor, String type) {
        super(sender, security, tare, netWeight, grossWeight, certificates, doubleDoor);
        this.type = type;
    }

    public String print() {
        return "(id=" + id + ") Explosive container";
    }

    @Override
    public String toString(){
        return id + ";" + "Explosive;" + sender.getId() + ";" + shipId + ";" + security + ";" + tare + ";" + netWeight + ";" + grossWeight + ";" + certificates + ";" + doubleDoor + ";" + type;
    }
}
