import java.util.Arrays;
import java.util.LinkedList;

public class Ship {
    public static int n;
    private int id;

    private String name;
    private String homePort;
    private String origin;
    private String destination;

    protected int maxNumContainers;
    protected double maxWeight;
    private LinkedList<Container> containers; // All containers that were loaded on the ship.

    public Ship(String name, String homePort, String origin, String destination){
        this.id = n++;
        this.name = name;
        this.homePort = homePort;
        this.origin = origin;
        this.destination = destination;
        this.containers = new LinkedList<Container>();
    }

    public void defineCapacityDeadweight(int maxNumContainers, double maxWeight){
        this.maxNumContainers = maxNumContainers;
        this.maxWeight = maxWeight;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHomePort() {return homePort;}

    public void setHomePort(String homePort) {
        this.homePort = homePort;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public double getTotalWeightContainers(){
        double weight = 0;
        for (Container c: containers) {
            weight += c.getGrossWeight();
        }

        return weight;
    }

    public void loadContainer(Container c) throws ShipOverloaded {
        if (!containers.contains(c) && containers.size() < maxNumContainers && getTotalWeightContainers() + c.getGrossWeight() <= maxWeight){
            containers.add(c);
            System.out.println("Container:" + c + " has been successfully loaded on the " + name + ".");
        }
        else{
            System.out.println(" Loading the container will exceed the permissible safe load capacity of the ship.");
            throw new ShipOverloaded("Ship is already fully loaded.");
        }
    }

    public void unloadContainer(Container c){}

    @Override
    public String toString(){
        return "(id=" + id + ") Ship: " + name + ", Home Port: " + homePort + ", Origin: " + origin + ", Destination" + destination + ".";
    }
}
