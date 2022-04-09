package Program;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import Container.*;

public class Main {

    public static Scanner sc = new Scanner(System.in);
    public static LinkedList<Ship> ships = new LinkedList<>();
    public static LinkedList<Sender> senders = new LinkedList<>();
    public static Warehouse warehouse = new Warehouse(20);
    public static Train train = new Train(10);
    public static String[] containerTypes = {"Standard", "Heavy", "Refrigerated", "Liquid", "Explosive", "Toxic"};
    public static ThreadTimer timer = new ThreadTimer();

    // Copied code, starts here.
    public static void clearScreen() {
        try {
            String operatingSystem = System.getProperty("os.name"); //Check the current operating system

            if (operatingSystem.contains("Windows")) {
                ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "cls");
                Process startProcess = pb.inheritIO().start();
                startProcess.waitFor();
            } else {
                ProcessBuilder pb = new ProcessBuilder("clear");
                Process startProcess = pb.inheritIO().start();

                startProcess.waitFor();
            }
        } catch (IOException | InterruptedException ignored) {
        }
    }
    // Ends here, //https://www.delftstack.com/howto/java/java-clear-console/

    public static void pressEnterContinue() {
        System.out.println("\nPress enter to continue:");
        sc.nextLine();
    }

    public static int inputInteger() {
        boolean valid = false;
        int n = 0;

        while (!valid) {
            System.out.print("Input: ");
            try {
                n = Integer.parseInt(sc.nextLine());
                valid = true;
            } catch (NumberFormatException e) {
                ConsoleColors.printInRed("Invalid input, please try again.");
            }
        }

        return n;
    }

    public static int inputInteger(LinkedList<Integer> range) {
        boolean valid = false;
        int n = 0;

        while (!valid) {
            System.out.print("Input: ");

            try {
                n = Integer.parseInt(sc.nextLine());
                valid = range.contains(n);
                if (!valid) ConsoleColors.printInRed("Invalid input, please try again.");
            } catch (NumberFormatException e) {
                ConsoleColors.printInRed("Invalid input, please try again.");
            }
        }

        return n;
    } // range is a list of options to chose from.

    public static double inputDouble() {
        boolean valid = false;
        double n = 0;

        while (!valid) {
            try {
                n = Double.parseDouble(sc.nextLine());
                valid = true;
            } catch (NumberFormatException e) {
                ConsoleColors.printInRed("Invalid input, please try again.");
                System.out.print("Input: ");
            }
        }

        return n;
    }

    public static <T> void list(LinkedList<T> list, String type) {
        clearScreen();
        if (!list.isEmpty()) {
            if (type.equals("Ship"))
                System.out.println("Id;Name;HomePort;Origin;Destination");
            else if (type.equals("Sender"))
                System.out.println("Id;Name;Surname;PESEL;Address");

            list.forEach(System.out::println);
        } else
            ConsoleColors.printInYellow("There are no " + type + "s to show.");
    }

    public static <T> T getT(LinkedList<T> list, String name) {
        if (list.isEmpty()) {
            ConsoleColors.printInYellow("There are no " + name + "s.");
            return null;
        }

        System.out.println("Choose a " + name + ": ");
        list(list, name);

        LinkedList<Integer> range = new LinkedList<>();
        if (list.get(0) instanceof Ship) ships.forEach(e -> range.add(e.getId()));
        else if (list.get(0) instanceof Sender) senders.forEach(e -> range.add(e.getId()));
        int n = inputInteger(range);

        return list.get(n);
    }

    public static Container getContainer(HashMap<Integer, Container> hashMap, String name) {

        if (hashMap.isEmpty()) {
            ConsoleColors.printInYellow("There are no Containers");
            return null;
        }

        System.out.println("Choose a " + name + ": ");
        hashMap.forEach((key, value) -> System.out.println(value.toString()));

        LinkedList<Integer> range = new LinkedList<>();
        hashMap.forEach((k, v) -> range.add(v.getId()));
        int n = inputInteger(range);

        return hashMap.get(n);
    }

    public static Ship createShip() {
        System.out.println("Creating a ship");

        System.out.print("Name of the ship: ");
        String shipName = sc.nextLine();

        System.out.print("Home port: ");
        String homePort = sc.nextLine();

        System.out.print("Origin: ");
        String origin = sc.nextLine();

        System.out.print("Destination: ");
        String destination = sc.nextLine();

        Ship ship = new Ship(shipName, homePort, origin, destination);
        ships.add(ship);

        System.out.println("Please specify the capacity and dead weight of the ship:");
        System.out.println("-----------------------------------------------------------");
        System.out.println("Capacity - is the maximum number of Containers.");
        System.out.println("Dead weight - is the maximum weight, that a ship can carry.");
        System.out.println("-----------------------------------------------------------");
        System.out.print("Capacity = ");
        int numContainers = inputInteger();
        System.out.print("Dead weight = ");
        double weight = inputDouble();

        ship.defineCapacityDeadWeight(numContainers, weight);

        ConsoleColors.printInGreen("Your ship has been successfully created!\n");

        return ship;
    }

    public static Sender createSender() {
        System.out.println("Creating a Sender:");
        System.out.print("Name:");
        String senderName = sc.nextLine();
        System.out.print("Surname:");
        String senderSurName = sc.nextLine();
        System.out.print("Address:");
        String senderAddress = sc.nextLine();
        System.out.print("PESEL:");
        String senderPESEL = sc.nextLine();
        while (!WrongPesel.checkIfValid(senderPESEL)) {
            ConsoleColors.printInRed("Invalid PESEL, try again");
            senderPESEL = sc.nextLine();
        }

        Sender sender = new Sender(senderName, senderSurName, senderPESEL, senderAddress);
        System.out.println(sender.getDateOfBirth());
        senders.add(sender);

        ConsoleColors.printInGreen("Your Sender has been successfully created!\n");

        return sender;
    }

    public static Container container(String data) {
        String[] tmp = data.split(";");
        Container c = null;
        int id = Integer.parseInt(tmp[0]);
        Sender s = senders.get(Integer.parseInt(tmp[2]));

        switch (tmp[1]) {
            case "Standard" -> c = new StandardContainer(s, tmp[4], Double.parseDouble(tmp[5]), Double.parseDouble(tmp[6]), Double.parseDouble(tmp[7]), tmp[8]);
            case "Heavy" -> c = new HeavyCargo(s, tmp[4], Double.parseDouble(tmp[5]), Double.parseDouble(tmp[6]), Double.parseDouble(tmp[7]), tmp[8]);
            case "Refrigerated" -> c = new RefrigeratedCargo(s, tmp[4], Double.parseDouble(tmp[5]), Double.parseDouble(tmp[6]), Double.parseDouble(tmp[7]), tmp[8], Double.parseDouble(tmp[9]));
            case "Liquid" -> c = new LiquidCargo(s, tmp[4], Double.parseDouble(tmp[5]), Double.parseDouble(tmp[6]), Double.parseDouble(tmp[7]), tmp[8]);
            case "Explosive" -> c = new ExplosiveCargo(s, tmp[4], Double.parseDouble(tmp[5]), Double.parseDouble(tmp[6]), Double.parseDouble(tmp[7]), tmp[8]);
            case "Toxic" -> c = new ToxicCargo(s, tmp[4], Double.parseDouble(tmp[5]), Double.parseDouble(tmp[6]), Double.parseDouble(tmp[7]), tmp[8]);
        }

        assert c != null;
        c.setId(id);

        return c;
    }

    public static void createContainer() {
        clearScreen();
        System.out.println("Creating a Container");
        System.out.println("In order to create a container, you need to specify a sender.");

        Sender sender;
        if (!senders.isEmpty()) {
            System.out.println("1) Would you like to create a sender?\n" +
                    "2) Or choose an existing one?");
            int input = inputInteger(new LinkedList<>(Arrays.asList(1, 2)));
            if (input == 1)
                sender = createSender();
            else
                sender = getT(senders, "Sender");

        } else {
            System.out.println("Since there are no senders created, you will have to add one.");
            sender = createSender();
        }

        System.out.println("Choose the type of the container");
        System.out.println("1. Standard Container");
        System.out.println("2. Heavy Container");
        System.out.println("3. Refrigerated Container");
        System.out.println("4. Liquid Container");
        System.out.println("5. Explosive Container");
        System.out.println("6. Toxic Container");

        int input = inputInteger(new LinkedList<>(Arrays.asList(1, 2, 3, 4, 5, 6)));

        assert sender != null;
        String data = Container.n++ + ";" + containerTypes[input - 1] + ";" + sender.getId() + ";" + " " + ";";
        System.out.println("Container");
        System.out.print("Security: ");
        data += sc.nextLine() + ";";
        System.out.print("Tare: ");
        double tare = inputDouble();
        data += tare + ";";
        System.out.print("Net Weight: ");
        double netWeight = inputDouble();
        data += netWeight + ";";
        System.out.print("Gross Weight: ");
        double grossWeight = inputDouble();
        data += grossWeight + ";";
        System.out.print("Certificates: ");
        data += sc.nextLine();

        Container container = container(data); // Creating container

        ConsoleColors.printInGreen("Your Container has been successfully created!\n");

        Ship ship;
        if (!ships.isEmpty())
            ship = getT(ships, "Ship"); // Choosing ship
        else
            ship = createShip();

        assert ship != null;
        container.setShipId(ship.getId());

        ConsoleColors.printInGreen("Loading container...");
        try {
            ship.loadContainer(container);
        } catch (ShipOverloaded e) {
            e.printStackTrace();
        }
    }

    public static void unloadContainer() {
        clearScreen();
        System.out.println("Choose a ship from which you want to unload a container.");
        Ship ship = getT(ships, "Ship");
        if (ship == null) {
            ConsoleColors.printInYellow("I'm sorry, but there are no ships.");
            return;
        }
        System.out.println("Choose a container to be unloaded.");
        Container container = getContainer(ship.getContainers(), "Container");
        if (container == null) {
            ConsoleColors.printInYellow("I'm sorry but this ship has no containers to be unloaded.");
            return;
        }
        System.out.println("Where would you like to unload your container?");
        System.out.println("1) Train");
        System.out.println("2) Warehouse");
        int input = inputInteger(new LinkedList<>(Arrays.asList(1, 2)));

        if (input == 1) ship.unloadContainer(container, train);
        else ship.unloadContainer(container, warehouse, ThreadTimer.getSeconds());
    }

    public static void listContainersShip(){
        Ship ship = getT(ships, "Ship");

        if (ship != null) ship.showAllContainers();
        else ConsoleColors.printInYellow("There are no ships, therefore no containers.");
    }

    public static void departureShip() {
        clearScreen();
        System.out.println("Pick a ship for departure.");
        Ship ship = getT(ships, "Ship");
        if (ship == null) {
            ConsoleColors.printInYellow("I'm sorry, but there are no ships.");
            return;
        }

        ship.takeOff(ships);
    }

    public static void saveContainersShip(LinkedList<Ship> ships, String path) {
        FileWriter data;
        try {
            data = new FileWriter(path);
            BufferedWriter bw = new BufferedWriter(data);
            bw.write("ContainerId;Type;SenderId;ShipId;Security;Tare;Net;Gross;Certificate\n");

            LinkedList<Container> tmp = new LinkedList<>();
            for (Ship s : ships) {
                HashMap<Integer, Container> containers = s.getContainers();
                tmp.addAll(containers.values());
            }

            tmp.sort((c1, c2) -> (int) (c2.getGrossWeight() - c1.getGrossWeight()));

            for (Container c : tmp) {
                bw.write(c + "\n");
            }

            bw.close();
        } catch (IOException except) {
            except.printStackTrace();
        }
    }

    public static void saveContainersWareHouse(HashMap<Integer, Pair<Container, Integer>> list, String path) {
        FileWriter data;
        try {
            data = new FileWriter(path);
            BufferedWriter bw = new BufferedWriter(data);

            bw.write("ContainerId;Type;SenderId;ShipId;Security;Tare;Net;Gross;Certificate;seconds\n");

            for (Map.Entry<Integer, Pair<Container, Integer>> obj : list.entrySet()) {
                bw.write(obj.getValue().key + ";" + obj.getValue().value + "\n");
            }

            bw.close();
        } catch (IOException except) {
            except.printStackTrace();
        }
    }

    public static void loadData(String name) {
        FileReader data;
        try {
            data = new FileReader("data/" + name + ".txt");
            BufferedReader bw = new BufferedReader(data);

            String line = bw.readLine();
            while ((line = bw.readLine()) != null) {
                String[] tmp = line.split(";");
                Ship ship;
                int id;
                switch (name) {
                    case "ship" -> {
                        id = Integer.parseInt(tmp[0]);
                        ship = new Ship(tmp[1], tmp[2], tmp[3], tmp[4]);
                        ship.defineCapacityDeadWeight(Integer.parseInt(tmp[5]), Double.parseDouble(tmp[6]));
                        ship.setId(id);
                        ships.add(ship);
                    }
                    case "sender" -> {
                        id = Integer.parseInt(tmp[0]);
                        Sender sender = new Sender(tmp[1], tmp[2], tmp[3], tmp[4]);
                        sender.setId(id);
                        senders.add(sender);
                    }
                    case "containerShip", "containerWarehouse" -> {
                        Container container = container(line);
                        id = Integer.parseInt(tmp[0]);
                        container.setId(id);
                        if (name.equals("containerShip")) {
                            int ship_id = Integer.parseInt(tmp[3]);
                            container.setShipId(ship_id);
                            ship = ships.get(ship_id);
                            ship.loadContainer(container);
                        } else {
                            warehouse.loadContainer(container, Integer.parseInt(tmp[tmp.length - 1]));
                        }
                    }
                }
            }

            bw.close();
        } catch (IOException | ShipOverloaded ignored) {

        }
    }

    public static <T> void saveData(LinkedList<T> list, String name, String path) {
        FileWriter data;
        try {
            data = new FileWriter(path);
            BufferedWriter bw = new BufferedWriter(data);

            if (name.equals("ship")) {
                bw.write("ShipId;Name;HomePort;Origin;Destination\n");
            } else {
                bw.write("SenderId;Name;Surname;PESEL;Address\n");
            }

            for (T obj : list) {
                bw.write(obj.toString() + "\n");
            }

            bw.close();
        } catch (IOException except) {
            except.printStackTrace();
        }
    }

    public static void loadAttr() {
        FileReader data;
        try {
            data = new FileReader("data/attr.txt");
            BufferedReader bw = new BufferedReader(data);
            String line = bw.readLine();
            if (line != null) {
                line = bw.readLine();
                String[] tmp = line.split(";");
                Ship.n = Integer.parseInt(tmp[0]);
                Sender.n = Integer.parseInt(tmp[1]);
                Container.n = Integer.parseInt(tmp[2]);

                int s = Integer.parseInt(tmp[3]);
                ThreadTimer.setSeconds(s);
                ThreadTimer.setDays(s / 5);
                bw.close();
            }
        } catch (IOException ignored) {
        }
    }

    public static void saveAttr() {
        FileWriter data;
        try {
            data = new FileWriter("data/attr.txt");
            BufferedWriter bw = new BufferedWriter(data);

            bw.write("Ship;Sender;Container;seconds\n");
            bw.write(Ship.n + ";" + Sender.n + ";" + Container.n + ";" + ThreadTimer.getSeconds());

            bw.close();
        } catch (IOException except) {
            except.printStackTrace();
        }
    }

    public static void loadDataApp() {
        loadData("ship");
        ships.sort(Comparator.comparingInt(Ship::getId));
        loadData("sender");
        senders.sort(Comparator.comparingInt(Sender::getId));
        loadData("containerShip");
        loadData("containerWarehouse");
        loadAttr();
    }

    public static void saveDataApp(String path) {
        ships.sort((s1, s2) -> (int) (s1.getMaxWeight() - s2.getMaxWeight()));
        senders.sort(Comparator.comparing(Sender::getName));
        saveData(ships, "ship", path + "/ship.txt");
        saveData(senders, "sender", path + "/sender.txt");
        saveContainersShip(ships, path + "/containerShip.txt");
        saveContainersWareHouse(warehouse.getContainers(), path + "/containerWareHouse.txt");
        if (!path.equals("dataUser")) saveAttr();
    }

    public static void main(String[] args) {

        // Loading Data
        loadDataApp();

        //Time Thread
        timer.start();
        warehouse.thread.start(); //Thread which checks if any containers are expired.

        //Menu
        int input = 1;
        while (input != 0) {
            clearScreen();
            ConsoleColors.printInBlue("<-Seaport Logistics->");
            System.out.println("Day" + ThreadTimer.getDays());
            ConsoleColors.printInGreen("""
                    -------Menu-------
                    1. Create a ship.
                    2. Create a container.
                    3. Create a sender.
                    4. Unload a container.
                    5. List all ships.
                    6. List all senders.
                    7. List all containers in the warehouse.
                    8. List all containers based on a chosen ship.
                    9. Departure a ship from the Port.
                    0. Save & Exit.
                    -------END--------""");

            input = inputInteger(new LinkedList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 0)));
            clearScreen();
            switch (input) {
                case 0 -> {
                    //Saving data to txt
                    saveDataApp("data");
                    ConsoleColors.printInYellow("The data has been saved in the data folder.");
                    ConsoleColors.printInGreen("Quitting...");

                    sc.close();
                    timer.interrupt();
                    warehouse.thread.interrupt();
                    System.exit(0);
                }
                case 1 -> createShip();
                case 2 -> createContainer();
                case 3 -> createSender();
                case 4 -> unloadContainer();
                case 5 -> list(ships, "Ship");
                case 6 -> list(senders, "Sender");
                case 7 -> warehouse.printAllContainers();
                case 8 -> listContainersShip();
                case 9 -> departureShip();
                default -> ConsoleColors.printInRed("Please choose a number between [0-8].");
            }

            pressEnterContinue();
        }
    }
}