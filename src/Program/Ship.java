package Program;

import java.util.HashMap;
import java.util.Map;

import Container.*;

public class Ship {
    public static int n;
    private int id;

    private final String name;
    private final String homePort;
    private final String origin;
    private final String destination;
    private Boolean departed = false; // whether ship departed from the port or not.

    private int maxNumToxicExplosive;//    the maximum number of containers with toxic or explosive cargo that can be loaded within// the ship
    private int maxNumHeavy;//    the maximum number of heavy containers
    private int maxNumElectric;  //    the maximum number of Electric containers
    private int maxNumAllContainers;
    private double maxWeightLoad;
    protected HashMap<Integer, Container> containers = new HashMap<>(); // All containers that were loaded on the ship.

    public Ship(String name, String homePort, String origin, String destination) {
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

//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getHomePort() {
//        return homePort;
//    }
//
//    public void setHomePort(String homePort) {
//        this.homePort = homePort;
//    }
//
//    public String getOrigin() {
//        return origin;
//    }
//
//    public void setOrigin(String origin) {
//        this.origin = origin;
//    }
//
//    public String getDestination() {
//        return destination;
//    }
//
//    public void setDestination(String destination) {
//        this.destination = destination;
//    }
//
//    public int getMaxNumContainers() {
//        return maxNumAllContainers;
//    }
//
//    public double getMaxWeight() {
//        return maxWeightLoad;
//    }

    public Boolean getDeparted() {
        return departed;
    }

    public void setDeparted(Boolean departed) {
        this.departed = departed;
    }

    public double getTotalWeightContainers() {
        double weight = 0;

        for (Map.Entry<Integer, Container> obj : containers.entrySet()) {
            weight += obj.getValue().getGrossWeight();
        }

        return weight;
    }

    public void defineCapacity(int maxNumAllContainers, double maxWeightLoad, int maxNumHeavy, int maxNumToxicExplosive, int maxNumElectric) {
        this.maxNumAllContainers = maxNumAllContainers;
        this.maxWeightLoad = maxWeightLoad;
        this.maxNumToxicExplosive = maxNumToxicExplosive;
        this.maxNumHeavy = maxNumHeavy;
        this.maxNumElectric = maxNumElectric;
    }

    public HashMap<Integer, Container> getContainers() {
        return containers;
    }

    public void showAllContainers() {
        if (containers.isEmpty()) System.out.println("There are no containers on this ship.");
        else containers.forEach((key, value) -> System.out.println(value.print()));
    }

    //Instance off
    public void loadContainer(Container c) throws ShipOverloaded {
//        !containers.contains(c) &&
        if (containers.size() < maxNumAllContainers && getTotalWeightContainers() + c.getGrossWeight() <= maxWeightLoad) {
            containers.put(c.getId(), c);
        } else {
            System.out.println(" Loading the container will exceed the permissible safe load capacity of the ship.");
            throw new ShipOverloaded("\nShip is already fully loaded.");
        }

    }

    public void unloadContainer(Container c, Train train) { //Train
        if (train.loadContainer(c))
            containers.remove(c.getId());
        else
            System.out.println("No trains available.");
    }

    public void unloadContainer(Container c, Warehouse warehouse, int seconds) { // Warehouse
        if (warehouse.checkIfCanBeLoaded(c)) {
            warehouse.loadContainer(c, seconds);
            containers.remove(c.getId());
            ConsoleColors.printInGreen("Your container has been moved to the Warehouse.");
        }
        else {
            ConsoleColors.printInYellow("Your container cannot be moved to the Warehouse.");
        }
    }

    public void takeOff() {
        ConsoleColors.printInGreen(this.name + " took off!");
        this.departed = true;
    }

    public String getDetails() {
        return  "\nShip details: \n" +
                "id=" + id + "\n" +
                "Name = "+ name + '\n' +
                "Home Port = " + homePort + '\n' +
                "Origin = " + origin + '\n' +
                "Destination = " + destination + '\n' +
                "Departed = " + departed + '\n' +
                "Max. number of Toxic or Explosive containers =" + maxNumToxicExplosive + '\n' +
                "Max. number of Heavy containers = " + maxNumHeavy + '\n' +
                "Max. number of Electric containers = " + maxNumElectric + '\n' +
                "Max. number of All containers = " + maxNumAllContainers + '\n' +
                "Max. weight load = " + maxWeightLoad + '\n';
    }

    public void print() {
        System.out.println("(id=" + id + ") Ship: " + name);
    }

    @Override
    public String toString() {
        return id + ";" + name + ";" + homePort + ";" + origin + ";" + destination + ";" + maxNumAllContainers + ";" + maxWeightLoad + ";" + maxNumHeavy + ";" + maxNumToxicExplosive + ";" + maxNumElectric + ";" + departed;
    }
}
