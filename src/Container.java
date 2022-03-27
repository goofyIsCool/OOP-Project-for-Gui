import java.util.LinkedList;

public abstract class Container {
    public static int n;
    protected int id;

    private Sender sender;
    private String security;
    private double tare;
    private double netWeight;
    private double grossWeight;
    public LinkedList<String> certificates;

    public Container (Sender sender, String security, double tare, double netWeight, double grossWeight, LinkedList<String> certificates){
        this.id = n++;
        this.sender = sender;
        this.security = security;
        this.tare = tare; // The weight of the container
        this.netWeight = netWeight;
        this.grossWeight = grossWeight;
        this.certificates = certificates;
    }

    public String getSecurity() {
        return security;
    }

    public void setSecurity(String security) {
        this.security = security;
    }

    public double getTare() {
        return tare;
    }

    public void setTare(double tare) {
        this.tare = tare;
    }

    public double getNetWeight() {
        return netWeight;
    }

    public void setNetWeight(double netWeight) {
        this.netWeight = netWeight;
    }

    public double getGrossWeight() {
        return grossWeight;
    }

    public void setGrossWeight(double grossWeight) {
        this.grossWeight = grossWeight;
    }

    @Override
    public String toString() {
        return "(id=" + id + ")" + "standard container";
    }
}
