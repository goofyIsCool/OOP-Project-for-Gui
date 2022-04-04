public abstract class Container implements Comparable<Container>{

    public static int n;
    protected int id;

    protected Sender sender;
    protected String security;
    protected double tare;
    protected double netWeight;
    protected double grossWeight;
    protected String certificates;
    protected int shipId;

    public Container(Sender sender, String security, double tare, double netWeight, double grossWeight, String certificates) {
        this.id = n++;
        this.sender = sender;
        this.security = security;
        this.tare = tare; // The weight of the container
        this.netWeight = netWeight;
        this.grossWeight = grossWeight;
        this.certificates = certificates;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getShipId() {
        return shipId;
    }

    public void setShipId(int shipId) {
        this.shipId = shipId;
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

    public String print() {
        return "(id=" + id + ")" + " Standard container";
    }
}
