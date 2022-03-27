import java.util.LinkedList;

public class RefrigeratedCargo extends Container{

    private double electricityVoltage;

    public RefrigeratedCargo(Sender sender, String security, double tare, double netWeight, double grossWeight, LinkedList<String> certificates, double voltage) {
        super(sender, security, tare, netWeight, grossWeight, certificates);
        this.electricityVoltage = voltage;
    }
}
