import java.util.LinkedList;

public abstract class Container {
    public static int counter;
    private int id;

    public Sender sender;
    public String containerType;
    public double netWeight;
    public double grossWeight;
    public LinkedList<String> certificates;

    public Container (Sender s, String containerType, double netWeight, double grossWeight, LinkedList<String> certificates){
        this.id = counter++;

    }

}
