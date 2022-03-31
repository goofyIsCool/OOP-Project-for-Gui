import java.util.LinkedList;

public class RefrigeratedCargo extends HeavyCargo {

    private double electricityVoltage;

    public RefrigeratedCargo(Sender sender, String security, double tare, double netWeight, double grossWeight, String certificates, double voltage) {
        super(sender, security, tare, netWeight, grossWeight, certificates);
        this.electricityVoltage = voltage;
    }

    @Override
    public String toString(){
        return "Refrigerated;" + id + ";" + sender.getId() + ";" + shipId + ";" + security + ";" + tare + ";" + netWeight + ";" + grossWeight + ";" + electricityVoltage;
    }
}
