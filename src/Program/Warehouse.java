package Program;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import Container.*;

public class Warehouse {

    private final int maxNumberOfContainers; // Capacity
    private HashMap<Integer,Pair<Container, Integer>> containers = new HashMap<>(); // containers with time when loaded Container;

    Thread thread = new Thread(()-> {

        Iterator<Map.Entry<Integer, Pair<Container, Integer>>> iter = containers.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<Integer, Pair<Container, Integer>> entry = iter.next();

            try {
                IrresponsibleSenderWithDangerousGoods.checkIf(entry.getValue().key, entry.getValue().value);
            } catch (IrresponsibleSenderWithDangerousGoods e) {
                System.out.println(e.getMessage());
                iter.remove();
            }

        }
    });

    public Warehouse(int maxNumberOfContainers) {
        this.maxNumberOfContainers = maxNumberOfContainers;
    }

    // arrival - seconds since when Container arrived.
    public void loadContainer(Container c, int seconds){
        Pair<Container, Integer> pair = new Pair<>(c, seconds);
        if (containers.size() < maxNumberOfContainers) containers.put(c.getId(), pair);
    }

    public HashMap<Integer, Pair<Container, Integer>> getContainers() {
        return containers;
    }

    public void printAllContainers(){
        if (!containers.isEmpty())
            containers.forEach((K,V) -> System.out.println(V.key)) ;
        else
            System.out.println("\nThere are no Containers in the warehouse\n");
    }
}
