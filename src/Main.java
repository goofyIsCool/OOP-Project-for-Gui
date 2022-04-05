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
    public static String[] containerTypes = {"Standard", "Heavy", "Refrigerated", "Liquid", "Explosive", "Toxic"};
    public static ThreadTimer timer = new ThreadTimer();

    //https://www.delftstack.com/howto/java/java-clear-console/
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
    } catch (Exception e) {
        System.out.println(e);
    }
    }

    public static void printInBlue(String str){
        System.out.println(ConsoleColors.BLUE + str + ConsoleColors.RESET);
    }

    public static void printInGreen(String str){
        System.out.println(ConsoleColors.GREEN + str + ConsoleColors.RESET);
    }

    public static <T> void list(LinkedList<T> list, String type) {

        if (type.equals("Ship"))
            System.out.println("Id;Name;HomePort;Origin;Destination");
        else if (type.equals("Sender"))
            System.out.println("Id;Name;Surname;PESEL;Address");

        list.stream().forEach(System.out::println);
    }

    public static <T> T getT(LinkedList<T> l, String name) {
        clearScreen();
        System.out.println("-------" + name + "-------");
        System.out.println("Choose a " + name + ": ");

        list(l, name);

        System.out.println("-------END-------");
        System.out.print("input: ");
        int input = (Integer.parseInt(sc.nextLine()));

        return l.get(input);
    }

    public static <K, T> K getK(HashMap<T, K> hm, String name) {
        clearScreen();
        System.out.println("-------" + name + "-------");
        System.out.println("Choose a " + name + ": ");

        hm.forEach((key, value) -> System.out.println(value.toString()));

        System.out.println("-------END-------");
        System.out.print("input: ");
        int input = (Integer.parseInt(sc.nextLine()));

        return hm.get(input);
    }

    public static void createShip() {
        clearScreen();
        System.out.println("-------Creating a ship-------");
        System.out.println("Please enter the following: ");

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

        System.out.println("Please specify the capacity and deadweight of the ship:");
        System.out.println("----------------");
        System.out.println("Capacity - is the maximum number of Containers.");
        System.out.println("Dead weight - is the maximum weight, that a ship can carry.");
        System.out.println("----------------");
        System.out.print("Capacity = ");
        int numContainers = Integer.parseInt(sc.nextLine());
        System.out.print("Dead weight = ");
        int weight = Integer.parseInt(sc.nextLine());

        ship.defineCapacityDeadWeight(numContainers, weight);
        System.out.println("----------------");
        System.out.println("Your ship has been successfully created!");
    }

    public static Sender createSender() {

        System.out.println("-------Sender-------");
        System.out.print("Name:");
        String senderName = sc.nextLine();
        System.out.print("Surname:");
        String senderSurName = sc.nextLine();

        String senderPESEL;
        System.out.print("PESEL:");
        while (!WrongPesel.checkIfValid(senderPESEL = sc.nextLine())) {
            System.out.println("Wrong PESEL, please try again!");
            senderPESEL = sc.nextLine();
        }

        System.out.print("Address:");
        String senderAddress = sc.nextLine();
        Sender sender = new Sender(senderName, senderSurName, senderPESEL, senderAddress);
        senders.add(sender);

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

    public static void createContainer() throws ShipOverloaded {
        clearScreen();
        System.out.println("----Creating a Container----");
        Sender sender = null;
        if (!senders.isEmpty()) {
            sender = getT(senders, "Sender");
        } else {
            System.out.println("In order to create a container, you need to specify a sender.");
            sender = createSender();
        }

        System.out.println("-------Container-------");
        System.out.println("Choose the type of the container");
        System.out.println("1. Standard Container");
        System.out.println("2. Heavy Container");
        System.out.println("3. Refrigerated Container");
        System.out.println("4. Liquid Container");
        System.out.println("5. Explosive Container");
        System.out.println("6. Toxic Container");
        System.out.println("-------END-------");
        System.out.print("Input: ");
        int input = (Integer.parseInt(sc.nextLine()));

        String data = Container.n++ + ";" + containerTypes[input - 1] + ";" + sender.getId() + ";" + " " + ";";
        System.out.println("Container");
        System.out.print("Security: ");
        data += sc.nextLine() + ";";
        System.out.print("Tare: ");
        data += sc.nextLine() + ";";
        System.out.print("Net Weight: ");
        data += sc.nextLine() + ";";
        System.out.print("Gross Weight: ");
        data += sc.nextLine() + ";";
        System.out.print("Certificates: ");
        data += sc.nextLine();

        Container container = container(data); // Creating container
        Ship ship = getT(ships, "Ship"); // Choosing ship
        container.setShipId(ship.getId());

        System.out.println("Loading container...");
        try {
            ship.loadContainer(container);
        } catch (ShipOverloaded e) {
            e.printStackTrace();
        }
    }

    private static void loadContainer() {
    }

    private static void unloadContainer() {
        System.out.println("Choose a ship from which you want to unload a container");
        Ship ship = getT(ships, "Ship");
        System.out.println("Choose a container to be unloaded");
        Container container = getK(ship.getContainers(), "Container");
        ship.unloadContainer(container, warehouse, timer.getSeconds());
    }

    public static <T> void saveData(LinkedList<T> list, String name, String path) throws IOException {
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

    public static void saveContainersShip(LinkedList<Ship> ships, String path) {
        FileWriter data;
        try {
            data = new FileWriter(path);
            BufferedWriter bw = new BufferedWriter(data);
            bw.write("ContainerId;Type;SenderId;ShipId;Security;Tare;Net;Gross;Certificate\n");
            LinkedList<Container> tmp = new LinkedList<>();
            for (Ship s : ships) {
                HashMap<Integer, Container> containers = s.getContainers();
                for (Container c : containers.values()) {
                    tmp.add(c);
                }
            }

            tmp.sort((c1, c2) -> (int) (c2.getGrossWeight() - c1.grossWeight));

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
            for (Map.Entry<Integer, Pair<Container, Integer>> obj : list.entrySet()){
                bw.write(obj.getValue().key + ";" + obj.getValue().value);
            }

            bw.close();
        } catch (IOException except) {
            except.printStackTrace();
        }
    }

    public static void saveDataApp(String path) throws IOException {
        saveData(ships, "ship", path + "/ship.txt");
        saveData(senders, "sender", path + "/sender.txt");
        saveContainersShip(ships, path + "/containerShip.txt");
        saveContainersWareHouse(warehouse.getContainers(), path + "/containerWareHouse.txt");
        if (!path.equals("DataUser")) saveAttr();
    }

    public static void saveDataForUser(String path) throws IOException {
        ships.sort((s1, s2) -> (int) (s1.getMaxWeight() - s2.getMaxWeight()));
        senders.sort(Comparator.comparing(Sender::getName));
        saveDataApp(path);
    }

    public static void saveAttr() {
        FileWriter data;
        try {
            data = new FileWriter("data/attr.txt");
            BufferedWriter bw = new BufferedWriter(data);

            bw.write("Ship;Sender;Container;seconds\n");
            bw.write(Ship.n + ";" + Sender.n + ";" + Container.n + ";" + timer.getSeconds());

            bw.close();
        } catch (IOException except) {
            except.printStackTrace();
        }
    }

    public static void loadAttr() throws IOException {
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
                timer.setSeconds(s);
                timer.setDays(s/5);
                bw.close();
            } else
                return;
        } catch (IOException ignored) {
        }
    }

    public static <T> void loadData(String name) throws IOException {
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
                            warehouse.loadContainer(container, Integer.parseInt(tmp[tmp.length-1]));
                        }
                    }
                }
            }

            bw.close();
        } catch (IOException | ShipOverloaded except) {
        }
    }

    public static void main(String[] args) throws ShipOverloaded, IOException, InterruptedException {

        clearScreen();

        // Loading Data
        loadData("ship");
        loadData("sender");
        loadData("containerShip");
        loadData("containerWarehouse");
        loadAttr();

        //Time Thread
        timer.start();

        //Menu
        printInBlue("<-Seaport Logistics->");
        int input = 1;
        while (input != 0) {
            printInGreen("-------Menu-------\n" +
                    "1. Create a ship.\n" +
                    "2. Create a container.\n" +
                    "3. Create a sender.\n" +
                    "4. Load a container.\n" +
                    "5. Unload a container.\n" +
                    "6. List all ships.\n" +
                    "7. List all senders.\n" +
                    "8. List all containers in the warehouse.\n" +
                    "9. List all containers based on a chosen ship.\n" +
                    "0. Save & Exit.\n" +
                    "-------END-------");
            System.out.print("Input: ");

            try {
                input = (Integer.parseInt(sc.nextLine()));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

            switch (input) {
                case 0 -> {
                    //Saving data to txt
                    saveDataApp("data"); // data unsorted for app
                    saveDataForUser("DataUser"); // data sorted for user
                    System.out.println("The data has been saved in the DataUser folder.");
                    System.out.println("Quitting...");
                }
                case 1 -> createShip();
                case 2 -> createContainer();
                case 3 -> createSender();
                case 4 -> loadContainer();
                case 5 -> unloadContainer();
                case 6 -> list(ships, "Ship");
                case 7 -> list(senders, "Sender");
                case 8 -> warehouse.printAllContainers();
                case 9 -> getT(ships, "Ship").showAllContainers();

                default -> System.out.println("Wrong input!");
            }
        }
        sc.close();
    }
}