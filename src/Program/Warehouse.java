package Program;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import Container.*;

public class Warehouse {

    private final int maxNumberOfContainers; // Capacity
    private Map<Integer, Container> containers = Collections.synchronizedMap(new HashMap<>()); // containers with time when loaded Container;

    Thread thread = new Thread(()-> {

        while(!Thread.interrupted()) {
            Iterator<Map.Entry<Integer, Container>> iter = containers.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry<Integer,Container> entry = iter.next();
                try {
                    IrresponsibleSenderWithDangerousGoods.checkIf(entry.getValue(), entry.getValue().getTimeJoined());
                } catch (IrresponsibleSenderWithDangerousGoods e) {
                    System.out.println(e.getMessage());
                    Sender sender = entry.getValue().getSender();
                    sender.setWarningCounter(sender.getWarningCounter() + 1); // Increasing the counter of warnings
                    iter.remove();
                }
            }
        }
    });

    public Warehouse(int maxNumberOfContainers) {
        this.maxNumberOfContainers = maxNumberOfContainers;
    }

    // arrival - seconds since when Container arrived.
    public void loadContainer(Container c, int seconds){

        if (containers.size() < maxNumberOfContainers && c.getSender().getWarningCounter() < 2){
            containers.put(c.getId(), c);
            c.setTimeJoined(seconds);
        }
        else if (containers.size() >= maxNumberOfContainers)
            ConsoleColors.printInRed("Warehouse if full.");
        else
            ConsoleColors.printInRed(c.getSender().getName() + " has to many warnings, container will be sent back!");
    }

    public boolean checkIfCanBeLoaded(Container c){
        if (containers.size() < maxNumberOfContainers && c.getSender().getWarningCounter() < 2)
            return true;
        else if (!(containers.size() < maxNumberOfContainers) && !(c.getSender().getWarningCounter() < 2))
            ConsoleColors.printInRed("Warehouse if full and " + c.getSender().getName() + " has to many warnings, container will be sent back!");
        else if (containers.size() >= maxNumberOfContainers)
            ConsoleColors.printInRed("Warehouse if full.");
        else
            ConsoleColors.printInRed(c.getSender().getName() + " has to many warnings, container will be sent back!");

        return false;
    }

    public Map<Integer,Container> getContainers() {
        return containers;
    }

    public void printAllContainers(){
        if (!containers.isEmpty())
            containers.forEach((K,V) -> System.out.println(V)) ;
        else
            ConsoleColors.printInYellow("There are no Containers in the warehouse");
    }
}
