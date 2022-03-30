import java.util.LinkedList;

public abstract class Container {

    public static int n;
    protected int id;

    protected Sender sender;
    protected String security;
    protected double tare;
    protected double netWeight;
    protected double grossWeight;
    protected LinkedList<String> certificates;

    public Container(Sender sender, String security, double tare, double netWeight, double grossWeight, LinkedList<String> certificates){
        this.id = n++;
        this.sender = sender;
        this.security = security;
        this.tare = tare; // The weight of the container
        this.netWeight = netWeight;
        this.grossWeight = grossWeight;
        this.certificates = certificates;
    }

    public Sender getSender() {
        return sender;
    }

    public void setSender(Sender sender) {
        this.sender = sender;
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
    public String toString(){
        return id + "," + sender.getId() + "," + security + "," + tare + "," + netWeight + "," + grossWeight;
    }

    public String  print() {
        return "(id=" + id + ")" + " Standard container";
    }
}
