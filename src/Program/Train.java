package Program;

import java.util.LinkedList;
import Container.*;

public class Train {

    private int capacity;
    private int seconds; // Time when the train got full.
    private Thread thread = new Thread(() -> {
        ConsoleColors.printInRed("Train is full, wait for the next train to arrive.");
        seconds = ThreadTimer.getSeconds();
        this.left = true;
        try {
            Thread.sleep(30000);
        } catch (InterruptedException ignored) {
        }
        this.containers.clear();
        this.left = false;
        ConsoleColors.printInGreen("New train has arrived.");
    });
    private boolean left = false; // Boolean value which tells if the train is ready or not.
    private LinkedList<Container> containers = new LinkedList<>();

    public Train(int capacity) {
        this.capacity = capacity;
    }

    public void loadContainer(Container c) {
        if (containers.size() + 1 == capacity && !left){
            containers.add(c);
            this.thread.start();
            this.thread.interrupt();
        }
        else if(left){
            ConsoleColors.printInYellow("The next train will arrive in"+(30-(ThreadTimer.getSeconds()-seconds)) + "s.");
        }
        else
            containers.add(c);
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Thread getThread() {
        return thread;
    }

    public LinkedList<Container> getContainers() {
        return containers;
    }
}
