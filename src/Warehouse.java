import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.TreeSet;

public class Warehouse {

    private int maxNumberOfContainers; // Capacity

    private HashMap<Integer,Pair<Container, Integer>> containers = new HashMap<>(); // containers with time when loaded Container;

    public Warehouse(int maxNumberOfContainers) {
        this.maxNumberOfContainers = maxNumberOfContainers;
    }

    // arrival - seconds since when Container arrived.
    public void loadContainer (Container c, int arrival){
        Pair<Container, Integer> pair = new Pair<>(c, arrival);
        if (containers.size() < maxNumberOfContainers) containers.put(c.getId(), pair);
    }

    public boolean checkIfExpired(Container c, int maxNumberOfDays) throws IrresponsibleSenderWithDangerousGoods {
        boolean expired = false;
        Pair<Container, Integer> info = containers.get(c.getId());

        if (info.value-maxNumberOfDays >= 0){
            expired = true;
            throw new IrresponsibleSenderWithDangerousGoods("");
        }

        return expired;
    }

    public HashMap<Integer, Pair<Container, Integer>> getContainers() {
        return containers;
    }

    public void printAllContainers(){
        containers.forEach((K,V) -> System.out.println(V)) ;
    }

}
