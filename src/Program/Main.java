package Program;

import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import Container.*;

public class Main {

    public static Scanner sc = new Scanner(System.in);
    public static LinkedList<Ship> ships = new LinkedList<>();
    public static LinkedList<Sender> senders = new LinkedList<>();
    public static Warehouse warehouse = new Warehouse(20);
    public static Train train = new Train(3);
    public static String[] containerTypes = {"Standard", "Heavy", "Refrigerated", "Liquid", "Explosive", "Toxic Liquid", "Toxic Powdery"};
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

    public static String input(LinkedList<String> options) {
        boolean valid = false;
        String n = "";

        while (!valid) {
            System.out.print("Input: ");

            n = sc.nextLine();
            valid = options.contains(n);
            if (!valid) ConsoleColors.printInRed("Invalid input, please try again.");
        }

        return n;
    }

    public static int inputInteger() {
        boolean valid = false;
        int n = 0;

        while (!valid) {
            try {
                n = Integer.parseInt(sc.nextLine());
                valid = true;
            } catch (NumberFormatException e) {
                ConsoleColors.printInRed("Invalid input, please try again.");
                System.out.print("Input: ");
            }
        }

        return n;
    }

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

        if (!list.isEmpty()) {
            if (type.equals("Ship"))
                ships.stream().filter(e -> !e.getDeparted()).forEach(Ship::print);

            else if (type.equals("Sender"))
                senders.forEach(Sender::print);

        } else
            ConsoleColors.printInYellow("There are no " + type + "s to show.");

    }

    public static <T> T getT(LinkedList<T> list, String name) {
        if (list.isEmpty()) {
            ConsoleColors.printInYellow("There are no " + name + "s to chose from, since none were created.");
            return null;
        }

        System.out.println("Choose a " + name + ": ");
        list(list, name);

        LinkedList<String> range = new LinkedList<>();
        if (list.get(0) instanceof Ship) ships.stream().filter(e -> !e.getDeparted()).forEach(e -> range.add(String.valueOf(e.getId())));
        else if (list.get(0) instanceof Sender) senders.forEach(e -> range.add(String.valueOf(e.getId())));
        int n = Integer.parseInt(input(range));

        return list.get(n);
    }

    public static Container getContainer(HashMap<Integer, Container> hashMap, String name) {

        if (hashMap.isEmpty()) {
            ConsoleColors.printInYellow("There are no Containers");
            return null;
        }

        System.out.println("Choose a " + name + ": ");
        hashMap.forEach((key, value) -> System.out.println(value.toString()));

        LinkedList<String> range = new LinkedList<>();
        hashMap.forEach((k, v) -> range.add(String.valueOf(v.getId())));
        int n = Integer.parseInt(input(range));

        return hashMap.get(n);
    }

    public static Ship createShip() {
        System.out.println("1. Creating a Ship");
        System.out.println("------------------");
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

        System.out.println("\nPlease specify the capacity and dead weight of the ship:");
        System.out.println("-----------------------------------------------------------");
        System.out.println("Capacity - is the maximum number of Containers.");
        System.out.println("Dead weight - is the maximum weight load");
        ConsoleColors.printInYellow("Remember that Heavy containers include, Refrigerated, Explosive and Toxic containers.");
        System.out.println("-----------------------------------------------------------");

        System.out.print("Maximum number of Toxic or Explosive containers: ");
        int maxNumToxicExplosive = inputInteger();
        System.out.print("Maximum number of containers requiring connection to the electricity network: ");
        int maxNumElectric = inputInteger();
        System.out.print("Maximal number of Heavy containers: ");
        int maxNumHeavy = inputInteger();
        while (!(maxNumHeavy >= maxNumToxicExplosive + maxNumElectric)){
            ConsoleColors.printInRed("The entered number of Heavy containers is to low. \nToxic or Explosive containers = " + maxNumToxicExplosive + "\nContainers requiring connection to the electricity network = " +maxNumElectric + ".");
            System.out.println("Input");
            maxNumHeavy = inputInteger();
        }

        System.out.print("Capacity = ");
        int numContainers = inputInteger();
        while (!(numContainers >= maxNumHeavy)){
            ConsoleColors.printInRed("The entered maximum number of all containers is to low.\nSince the maximum number of heavy containers is " + maxNumHeavy);
            System.out.print("Input: ");
            numContainers = inputInteger();
        }

        System.out.print("Dead weight = ");
        double weight = inputDouble();

        ship.defineCapacity(numContainers, weight, maxNumToxicExplosive, maxNumHeavy, maxNumElectric);

        ConsoleColors.printInGreen("Your ship has been successfully created!");

        return ship;
    }

    public static Sender createSender() {
        System.out.println("3. Creating a Sender");
        System.out.println("--------------------");
        System.out.print("Name: ");
        String senderName = sc.nextLine();
        System.out.print("Surname: ");
        String senderSurName = sc.nextLine();
        System.out.print("Address: ");
        String senderAddress = sc.nextLine();
        System.out.print("PESEL: ");
        String senderPESEL = sc.nextLine();

        Sender sender = new Sender(senderName, senderSurName, senderPESEL, senderAddress, 0);
        System.out.println(sender.getDateOfBirth());
        senders.add(sender);

        ConsoleColors.printInGreen("Your Sender has been successfully created!");

        return sender;
    }

    public static Container container(String data) {
        String[] tmp = data.split(";");
        Container c = null;
        int id = Integer.parseInt(tmp[0]);
        Sender s = senders.get(Integer.parseInt(tmp[2]));

        switch (tmp[1]) {
            // sender;security;tare;netWeight;GrossWeight;Certificates;...
            case "Standard" -> c = new StandardContainer(s, tmp[4], Double.parseDouble(tmp[5]), Double.parseDouble(tmp[6]), Double.parseDouble(tmp[7]), tmp[8]);
            case "Heavy" -> c = new HeavyCargo(s, tmp[4], Double.parseDouble(tmp[5]), Double.parseDouble(tmp[6]), Double.parseDouble(tmp[7]), tmp[8], tmp[9]); // ok
            case "Refrigerated" -> c = new RefrigeratedCargo(s, tmp[4], Double.parseDouble(tmp[5]), Double.parseDouble(tmp[6]), Double.parseDouble(tmp[7]), tmp[8], tmp[9], Double.parseDouble(tmp[10])); // ok
            case "Liquid" -> c = new LiquidCargo(s, tmp[4], Double.parseDouble(tmp[5]), Double.parseDouble(tmp[6]), Double.parseDouble(tmp[7]), tmp[8], tmp[9]); //ok
            case "Explosive" -> c = new ExplosiveCargo(s, tmp[4], Double.parseDouble(tmp[5]), Double.parseDouble(tmp[6]), Double.parseDouble(tmp[7]), tmp[8], tmp[9], tmp[10]); // ok
            case "Toxic Liquid" -> c = new ToxicLiquidContainer(s, tmp[4], Double.parseDouble(tmp[5]), Double.parseDouble(tmp[6]), Double.parseDouble(tmp[7]), tmp[8], tmp[9], tmp[10], tmp[11]);
            case "Toxic Powdery" -> c = new ToxicPowderyCargo(s, tmp[4], Double.parseDouble(tmp[5]), Double.parseDouble(tmp[6]), Double.parseDouble(tmp[7]), tmp[8], tmp[9], tmp[10]);
        }

        assert c != null;
        c.setId(id);

        return c;
    }

    public static void createContainer() {
        System.out.println("2. Creating a Container");
        System.out.println("-----------------------");
        System.out.println("In order to create a container, you'll need to specify the sender.");

        Sender sender;
        if (!senders.isEmpty()) {
            System.out.println("1) Would you like to create a sender?\n" +
                    "2) Or choose an existing one?");
            int input = Integer.parseInt(input(new LinkedList<>(Arrays.asList("1", "2"))));
            System.out.println();
            if (input == 1)
                sender = createSender();
            else
                sender = getT(senders, "Sender");

        } else {
            System.out.println("Since there are no senders created, you will have to add one.");
            System.out.println("-----------------------\n");
            sender = createSender();
        }

        System.out.println("\nChoose a type of the container:");
        System.out.println("1. Standard Container");
        System.out.println("2. Heavy Container");
        System.out.println("3. Refrigerated Container");
        System.out.println("4. Liquid Container");
        System.out.println("5. Explosive Container");
        System.out.println("6. Toxic Liquid Container");
        System.out.println("7. Toxic Powdery Container");

        int input = Integer.parseInt(input(new LinkedList<>(Arrays.asList("1", "2", "3", "4", "5", "6", "7"))));

        assert sender != null;
        String data = Container.n++ + ";" + containerTypes[input - 1] + ";" + sender.getId() + ";" + " " + ";";
        System.out.println("\nContainer");
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

        switch (containerTypes[input - 1]) {
            case "Liquid" -> {
                System.out.print("Substance: ");
                data += ";" + sc.nextLine();
            }
            case "Heavy", "Refrigerated", "Explosive", "Toxic Liquid", "Toxic Powdery" -> {
                System.out.println("Is the container double doored? (yes/no)");
                data += ";" + input(new LinkedList<>(Arrays.asList("yes", "Yes", "no", "No")));
                String typeOfToxicity;
                switch (containerTypes[input - 1]) {
                    case "Refrigerated" -> {
                        System.out.print("Voltage: ");
                        data += ";" + sc.nextLine();
                    }
                    case "Explosive" -> {
                        System.out.println("Explosion category(1-6): ");
                        String[] t = ExplosiveCargo.types;
                        AtomicInteger i = new AtomicInteger();
                        Arrays.asList(t).forEach(e -> System.out.println(i.getAndIncrement() + 1 + " " + e));
                        int explosionCategory = Integer.parseInt(input(new LinkedList<>(Arrays.asList("1", "2", "3", "4", "5", "6")))) - 11;
                        data += ";" + explosionCategory;
                    }
                    case "Toxic Liquid" -> {
                        System.out.print("Substance: ");
                        data += ";" + sc.nextLine();
                        System.out.print("Type of toxicity:");
                        System.out.println("chemical, biological or physical");
                        typeOfToxicity = input(new LinkedList<>(Arrays.asList("chemical", "biological", "physical")));
                        data += ";" + typeOfToxicity;
                    }
                    case "Toxic Powdery" -> {
                        System.out.print("Type of toxicity:");
                        System.out.println("chemical, biological or physical");
                        typeOfToxicity = input(new LinkedList<>(Arrays.asList("chemical", "biological", "physical")));
                        data += ";" + typeOfToxicity;
                    }
                }
            }
        }

        Container container = container(data); // Creating container

        ConsoleColors.printInGreen("Your Container has been successfully created!\n");

        System.out.println("On which ship would you like to send your container.");
        Ship ship;
        if (!ships.isEmpty()) {
            ship = getT(ships, "Ship"); // Choosing ship
        } else {
            System.out.println("Since there are no Ships created, you will have to add one.");
            ship = createShip();
        }

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
        System.out.println("4. Unloading Container.");
        System.out.println("-----------------------");
//        System.out.println("Choose a ship from which you want to unload a container.");
        Ship ship = getT(ships, "Ship");
        if (ship == null) return;

        System.out.println("Choose a container to be unloaded.");
        Container container = getContainer(ship.getContainers(), "Container");
        if (container == null) {
            ConsoleColors.printInYellow("I'm sorry but this ship has no containers to be unloaded.");
            return;
        }
        System.out.println("Where would you like to unload your container?");
        System.out.println("1) Train");
        System.out.println("2) Warehouse");
        int input =  Integer.parseInt(input(new LinkedList<>(Arrays.asList("1", "2"))));

        if (input == 1) ship.unloadContainer(container, train);
        else ship.unloadContainer(container, warehouse, ThreadTimer.getSeconds());
    }

    public static void listContainersShip() {
        System.out.println("8. List all containers based on a chosen ship.");
        System.out.println("----------------------------------------------");
        Ship ship = getT(ships, "Ship");

        if (ship != null) ship.showAllContainers();
    }

    public static void departureShip() {
        System.out.println("9. Chose a ship for departure from the Port.");
        System.out.println("--------------------------------------------");
        Ship ship = getT(ships, "Ship");
        if (ship == null) return;

        ship.takeOff();
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

    public static void saveContainersWareHouse(Map<Integer, Container> list, String path) {
        FileWriter data;
        try {
            data = new FileWriter(path);
            BufferedWriter bw = new BufferedWriter(data);

            bw.write("ContainerId;Type;SenderId;ShipId;Security;Tare;Net;Gross;Certificate;seconds\n");

            for (Map.Entry<Integer, Container> obj : list.entrySet()) {
                bw.write(obj.getValue() + ";" + obj.getValue().getTimeJoined() + "\n");
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
                        ship.defineCapacity(Integer.parseInt(tmp[5]), Double.parseDouble(tmp[6]), Integer.parseInt(tmp[7]), Integer.parseInt(tmp[8]), Integer.parseInt(tmp[9]));
                        ship.setId(id);
                        ship.setDeparted(Boolean.valueOf(tmp[10]));
                        ships.add(ship);
                    }
                    case "sender" -> {
                        id = Integer.parseInt(tmp[0]);
                        Sender sender = new Sender(tmp[1], tmp[2], tmp[3], tmp[4], Integer.parseInt(tmp[5]));
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
                bw.write("ShipId;Name;HomePort;Origin;Destination;maxNumAllContainers;maxWeightLoad;maxNumHeavy;maxNumToxicExplosive;maxNumElectric;departed\n");
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
        ships.sort(Comparator.comparing(Ship::getName));
        saveData(ships, "ship", path + "/ship.txt");

        senders.sort(Comparator.comparing(Sender::getName));
        saveData(senders, "sender", path + "/sender.txt");

        LinkedList<Container> containerShips = new LinkedList<>();
        for (Ship ship: ships){
            for (Map.Entry<Integer, Container> obj : ship.getContainers().entrySet()){
                containerShips.add(obj.getValue());
            }
        }
        containerShips.sort(Comparator.comparing(Container::getGrossWeight));
        saveContainersShip(ships, path + "/containerShip.txt");

        LinkedList<Container> containerWarehouse = new LinkedList<>();
        for (Map.Entry<Integer, Container> obj : warehouse.getContainers().entrySet()){
            containerWarehouse.add(obj.getValue());
        }

        containerWarehouse.sort(Comparator.comparing(Container::getTimeJoined).thenComparing(e -> e.getSender().getName()));
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
                    --------------Menu--------------
                    1. Create a ship.
                    2. Create a container.
                    3. Create a sender.
                    4. Unload a container.
                    5. Ship details.
                    6. Sender details.
                    7. List all containers in the warehouse.
                    8. List all containers based on a chosen ship.
                    9. Chose a ship for departure from the Port.
                    0. Save & Exit.
                    --------------END---------------""");

            input =  Integer.parseInt(input(new LinkedList<>(Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "0"))));
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
                case 5 -> {
                    System.out.println("5. Ship details.");
                    System.out.println("------------------");
                    System.out.println(Objects.requireNonNull(getT(ships, "Ship")).getDetails());
                }
                case 6 -> {
                    System.out.println("6. Sender details.");
                    System.out.println("--------------------");
                    System.out.println(Objects.requireNonNull(getT(senders, "Sender")).getDetails());
                }
                case 7 -> {
                    System.out.println("7. List all containers in the warehouse.");
                    System.out.println("----------------------------------------");
                    warehouse.printAllContainers();
                }
                case 8 -> listContainersShip();
                case 9 -> departureShip();
                default -> ConsoleColors.printInRed("Please choose a number between [0-8].");
            }

            pressEnterContinue();
        }
    }
}