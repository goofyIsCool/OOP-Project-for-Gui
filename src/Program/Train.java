package Program;

import java.util.LinkedList;
import Container.*;

public class Train {

    private final int capacity;
    private int seconds; // Time when the train got full.
    private Thread thread;

    private boolean left = false; // Boolean value which tells if the train is ready or not.
    private LinkedList<Container> containers = new LinkedList<>();

    public Train(int capacity) {
        this.capacity = capacity;
        thread = new Thread(() -> {
            seconds = ThreadTimer.getSeconds();
            this.left = true;
            try {
                Thread.sleep(30000);
                thread.interrupt();
            } catch (InterruptedException e) { }

            this.containers.clear();
            this.left = false;
            ConsoleColors.printInGreen("New train has arrived.");
        });
    }
// Returns true if a container has been successfully moved to the train.
    public boolean loadContainer(Container c) {
        if (containers.size() + 1 == capacity && !left){
            System.out.println("Your container has been moved to the train.");
            containers.add(c);
            this.thread.start();
        }
        else if(left){
            ConsoleColors.printInYellow("The next train will arrive in "+(30-(ThreadTimer.getSeconds()-seconds)) + "s.");
            return false;
        }
        else {
            System.out.println("Your container has been moved to the train.");
            containers.add(c);
        }

        return true;
    }


//    public int getCapacity() {
//        return capacity;
//    }
//
//    public void setCapacity(int capacity) {
//        this.capacity = capacity;
//    }
//
//    public Thread getThread() {
//        return thread;
//    }
//
//    public LinkedList<Container> getContainers() {
//        return containers;
//    }
}
