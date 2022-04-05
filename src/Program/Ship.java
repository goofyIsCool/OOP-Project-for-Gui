package Program;

import java.util.HashMap;
import java.util.Map;
import Container.*;

public class Ship{
    public static int n;
    private int id;

    private String name;
    private String homePort;
    private String origin;
    private String destination;
    private int maxNumContainers;
    private double maxWeight;
    protected HashMap<Integer, Container> containers = new HashMap<>(); // All containers that were loaded on the ship.

    public Ship(String name, String homePort, String origin, String destination){
        this.id = n++;
        this.name = name;
        this.homePort = homePort;
        this.origin = origin;
        this.destination = destination;
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

        for (Map.Entry<Integer,Container> obj: containers.entrySet()) {
            weight += obj.getValue().getGrossWeight();
        }

        return weight;
    }

    public void defineCapacityDeadWeight(int maxNumContainers, double maxWeight){
        this.maxNumContainers = maxNumContainers;
        this.maxWeight = maxWeight;
    }

    public int getMaxNumContainers() {
        return maxNumContainers;
    }

    public double getMaxWeight() {
        return maxWeight;
    }

    public HashMap<Integer, Container> getContainers() {
        return containers;
    }

    public void showAllContainers(){
        if (containers.isEmpty()) System.out.println("There are no containers on this ship.");
        else containers.forEach((key, value) -> System.out.println(value.toString()));
    }

    //Instance off
    public void loadContainer(Container c) throws ShipOverloaded {

//        !containers.contains(c) &&
        if ( containers.size() < maxNumContainers && getTotalWeightContainers() + c.getGrossWeight() <= maxWeight){
            containers.put(c.getId(), c);
        }
        else{
            System.out.println(" Loading the container will exceed the permissible safe load capacity of the ship.");
            throw new ShipOverloaded("\nShip is already fully loaded.");
        }

    }

    public void unloadContainer(Container c) {
        if (containers.isEmpty())
            System.out.println("There are no containers to be unloaded from the ship.");
        else {
            containers.remove(c.getId());
            System.out.println("Your container has been moved to the train.");
        }
    }

    public void unloadContainer(Container c, Warehouse warehouse, int seconds) {
        if (containers.isEmpty())
            System.out.println("There are no containers to be unloaded from the ship.");
        else {
            warehouse.loadContainer(c, seconds);
            containers.remove(c.getId());
            System.out.println("Your container has been moved to the Warehouse.");
        }
    }

    public String print(){
        return "(id=" + id + ") Ship: " + name + ", Home Port: " + homePort + ", Origin: " + origin + ", Destination" + destination + ".";
    }

    @Override
    public String toString(){
        return id + ";" + name + ";" + homePort + ";" + origin + ";" + destination + ";" + maxNumContainers + ";" + maxWeight;
    }
}
