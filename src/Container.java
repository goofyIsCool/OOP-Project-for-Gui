import java.util.LinkedList;

public interface Container {

    public Sender getSender();

    public void setSender(Sender sender);

    public String getSecurity();

    public void setSecurity(String security);

    public double getTare();

    public void setTare(double tare);

    public double getNetWeight();

    public void setNetWeight(double netWeight);

    public double getGrossWeight();

    public void setGrossWeight(double grossWeight);

    @Override
    public String toString();
}
