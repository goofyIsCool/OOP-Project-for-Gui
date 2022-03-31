import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;


public class Main {

    public static Scanner sc = new Scanner(System.in);
    public static LinkedList<Ship> ships = new LinkedList<>();
    public static LinkedList<Container> containersShip = new LinkedList<>();
    public static LinkedList<Container> containersWarehouse = new LinkedList<>();
    public static LinkedList<Sender> senders = new LinkedList<>();

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static int inputInt(String line) {
        return Integer.parseInt(line);
    }

    public static double inputDouble(String line) {
        return Double.parseDouble(line);
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
        int numContainers = inputInt(sc.nextLine());
        System.out.print("Dead weight = ");
        int weight = inputInt(sc.nextLine());

        ship.defineCapacityDeadWeight(numContainers,weight);
        System.out.println("----------------");
        System.out.println("Your ship has been successfully created!");
    }

    public static void listAllShips(){
        ships.stream().forEach(e -> System.out.println(e.print()));
    }

    public static void createContainer() {
        clearScreen();
        System.out.println("----Creating a Container----");
        System.out.println("In order to create a container, you need to specify a sender.");

        Sender sender = getOrCreateSender();

        System.out.println("Container");
        System.out.print("Security: ");
        String security = sc.nextLine();
        System.out.print("Tare: ");
        double tare = inputDouble(sc.nextLine());
        System.out.print("Net Weight: ");
        double netWeight = inputDouble(sc.nextLine());
        System.out.print("Gross Weight: ");
        double grossWeight = inputDouble(sc.nextLine());

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
        int containerType = (inputInt(sc.nextLine()));

        LinkedList<String> certificates = new LinkedList<>();
        Container container;
        switch (containerType) {
            case 1:
            default:
                container = new StandardContainer(sender, security, tare, netWeight, grossWeight, certificates);
                break;
            case 2:
                container = new HeavyCargo(sender, security, tare, netWeight, grossWeight, certificates);
                break;
            case 3:
                container = new RefrigeratedCargo(sender, security, tare, netWeight, grossWeight, certificates, 10);
                break;
            case 4:
                container = new LiquidCargo(sender, security, tare, netWeight, grossWeight, certificates);
                break;
            case 5:
                container = new ExplosiveCargo(sender, security, tare, netWeight, grossWeight, certificates);
                break;
            case 6:
                container = new ToxicCargo(sender, security, tare, netWeight, grossWeight, certificates);
                break;
        }

        containersShip.add(container);
    }

    public static void listAllContainers() {
        System.out.println("Containers that are on ship:");
        if (containersShip.isEmpty()) System.out.println("There are no containers on ships.");
        else containersShip.stream().forEach(e -> System.out.println(e.print()));

        System.out.println("Containers that are stored in the warehouse:");
        if (containersWarehouse.isEmpty()) {
            System.out.println("There are no containers in the warehouse.");
        }
        else containersWarehouse.stream().forEach(e -> System.out.println(e.print()));
    }

    public static Sender getOrCreateSender() {
        clearScreen();
        System.out.println("-------Sender-------");
        System.out.println("Choose your sender or create one: ");
        for (int i = 0; i < senders.size(); i++) {
            Sender s = senders.get(i);
            System.out.println(i + 1 + ". " + s);
        }
        System.out.println("0. Create a new Sender: ");
        System.out.println("-------END-------");
        System.out.print("input: ");
        int senderInput = (inputInt(sc.nextLine()));
        System.out.println();

        Sender sender;
        if (senderInput == 0 || senders.isEmpty()) {
            System.out.println("Please provide all the information about the sender: ");
            System.out.print("Name:");
            String senderName = sc.nextLine();
            System.out.print("Surname:");
            String senderSurName = sc.nextLine();

            System.out.print("PESEL:");
            String senderPESEL = sc.nextLine();
            while(!WrongPesel.checkIfValid(senderPESEL)) {
                System.out.println("Wrong PESEL, please try again!");
                senderPESEL = sc.nextLine();
            }

            System.out.print("Address:");
            String senderAddress = sc.nextLine();
            sender = new Sender(senderName, senderSurName, senderPESEL, senderAddress);
        } else {
            sender = senders.get(senderInput - 1);
        }

        if (!senders.contains(sender)) senders.add(sender);

        return sender;
    }

    public static void listAllSenders(){
        senders.stream().forEach(e -> System.out.println(e.print()));
    }

    public static void loadContainer() throws ShipOverloaded {
        clearScreen();
        int input;
        Ship ship;
        Container container;
        if (ships.isEmpty()) {
            System.out.println("There are no ships to load containers onto.");
            System.out.println("Would you like to create a ship? y/n");
            System.out.print("Input:");
            String inputS = sc.nextLine();
            if (inputS.equals("y")||inputS.equals("Y")) {
                createShip();
                ship = ships.get(0);
            }
            else return;
        }
        else {
            System.out.println("Choose a ship.");
            Arrays.stream(ships.toArray()).forEach(System.out::println);
            System.out.print("Input:");
            input = inputInt(sc.nextLine());
            ship = ships.get(input);
        }

        if (containersShip.isEmpty()) {
            System.out.println("There are no containers to load onto the ship.");
            System.out.println("Would you like to create a container? y/n");
            System.out.print("Input:");
            String inputS = sc.nextLine();
            if (inputS.equals("y")||inputS.equals("Y")) {
                createContainer();
                container = containersShip.get(0);
            }
            else return;
        }
        else {
            System.out.println("Choose a container you'd like to load.");
            Arrays.stream(containersShip.toArray()).forEach(System.out::println);
            System.out.print("Input:");
            input = inputInt(sc.nextLine());
            container = containersShip.get(input);
        }

        try{
            ship.loadContainer(container);
        }
        catch (ShipOverloaded e){
            e.printStackTrace();
        }

    }

    public static void unloadContainer(){
        clearScreen();
    }

    public static <T> void saveData(LinkedList<T> list, String name) throws IOException {
        FileWriter data;
        try
        {
            data = new FileWriter("data/" + name +".txt", true);
            BufferedWriter bw = new BufferedWriter(data);
            System.out.println("Saving " + name + "data:)");

            for(T obj: list){
                bw.write(obj.toString()+"\n");
            }

            bw.close();
            System.out.println("Written successfully");
        }
        catch (IOException except)
        {
            except.printStackTrace();
        }
    }

    public static <T> void loadData(String name) throws IOException {
        FileReader data;
        try
        {
            data = new FileReader("data/" + name +".txt");
            BufferedReader bw = new BufferedReader(data);
            String line;

            while((line=bw.readLine())!=null){
                String[] tmp = line.split(",");
                switch (name) {
                    case "ship":
                        System.out.println(tmp[1]);
                        Ship ship = new Ship(tmp[1], tmp[2], tmp[3], tmp[4]);
                        ship.defineCapacityDeadWeight(Integer.parseInt(tmp[5]), Double.parseDouble(tmp[6]));
                        ships.add(ship);
                    case "sender":
                        Sender sender = new Sender(tmp[0], tmp[1], tmp[2], tmp[3]);
                        
                        senders.add(sender);
                    case "containersShip", "containersWarehouse":
                        Container container = null;
                        LinkedList<String> certificates = null;
                        switch(tmp[0]){
                            case "heavy":
                                container = new HeavyCargo(senders.get(Integer.parseInt(tmp[1])), tmp[2], Double.parseDouble(tmp[3]), Double.parseDouble(tmp[4]), Double.parseDouble(tmp[4]), certificates);
                            case "liquid":
                                container = new LiquidCargo(senders.get(Integer.parseInt(tmp[1])), tmp[2], Double.parseDouble(tmp[3]), Double.parseDouble(tmp[4]), Double.parseDouble(tmp[4]), certificates);
                            case "explosive":
                                container = new ExplosiveCargo(senders.get(Integer.parseInt(tmp[1])), tmp[2], Double.parseDouble(tmp[3]), Double.parseDouble(tmp[4]), Double.parseDouble(tmp[4]), certificates);
                            case "refrigerated":
                                container = new RefrigeratedCargo(senders.get(Integer.parseInt(tmp[1])), tmp[2], Double.parseDouble(tmp[3]), Double.parseDouble(tmp[4]), Double.parseDouble(tmp[4]), certificates, Double.parseDouble(tmp[5]));
                            case "toxic":
                                container = new ToxicCargo(senders.get(Integer.parseInt(tmp[1])), tmp[2], Double.parseDouble(tmp[3]), Double.parseDouble(tmp[4]), Double.parseDouble(tmp[4]), certificates);
                            case "standard":
                                container = new StandardContainer(senders.get(Integer.parseInt(tmp[1])), tmp[2], Double.parseDouble(tmp[3]), Double.parseDouble(tmp[4]), Double.parseDouble(tmp[4]), certificates);
                        }
                        if (name.equals("containersShip")) containersShip.add(container);
                        else if (name.equals("containersWarehouse")) containersWarehouse.add(container);
                }
            }

            bw.close();
            System.out.println("Written successfully");
        }
        catch (IOException except)
        {
        }
    }

    public static void main(String[] args) throws ShipOverloaded, IOException {
        clearScreen();

        loadData("ship");
        loadData("sender");
        loadData("containersShip");
        loadData("containersWarehouse");


        System.out.println("<-Seaport Logistics->");
        int input = 1;

        while (input != 0) {
            System.out.println("-------Menu-------");
            System.out.println("1. Create a ship.");
            System.out.println("2. Create a container.");
            System.out.println("3. Load a container.");
            System.out.println("4. List all ships.");
            System.out.println("5. Unload a container.");
            System.out.println("6. List all containers.");
            System.out.println("7. List all sedners.");
            System.out.println("0. Save & Exit.");
            System.out.println("-------END-------");
            System.out.print("Input: ");

            try {
                input = (inputInt(sc.nextLine()));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

            switch (input) {
                case 0:
                    saveData(ships, "ship");
                    saveData(containersShip, "containerShip");
                    saveData(containersWarehouse, "containersWarehouse");
                    saveData(senders, "sender");
                    System.out.println("Quitting...");
                    break;
                case 1:
                    createShip();
                    break;
                case 2:
                    createContainer();
                    break;
                case 3:
                    loadContainer();
                    break;
                case 4:
                    listAllShips();
                    break;
                case 5:
                    unloadContainer();
                    break;
                case 6:
                    listAllContainers();
                    break;
                case 7:
                    listAllSenders();
                    break;
                default:
                    System.out.println("Wrong input!");
                    break;
            }
        }

        sc.close();
    }
}