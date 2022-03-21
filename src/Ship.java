public class Ship {
    public static int n = 0;
    private int id;
    private String name;
    private String homePort;
    private String origin;
    private String destination;
    protected int capacity;

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

    public String getHomePort() {
        return homePort;
    }

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

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public String toString(){
        return "(id=" + id + ") Ship: " + name + ",Destination: " + destination + ".";
    }
}
