import java.io.FileWriter;
import java.io.IOException;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {

    public static Scanner sc = new Scanner(System.in);
    public static LinkedList<Ship> ships = new LinkedList<>();
    public static LinkedList<Container> containers = new LinkedList<>();
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

        ship.defineCapacityDeadweight(numContainers,weight);
        System.out.println("----------------");
        System.out.println("Your ship has been successfully created!");
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

        containers.add(container);
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

    public static void loadContainer() throws ShipOverloaded {
        clearScreen();
        int input;
        Ship ship;
        Container container;
        if (ships.isEmpty()) {
            System.out.println("There are no ships to load containers onto.");
            System.out.println("Would you like to create a ship? y/n");
            String inputS = sc.nextLine();
            if (inputS.equals("y")) {
                createShip();
                ship = ships.get(0);
            }
            else return;
        }
        else {
            System.out.println("Choose a ship.");
            Arrays.stream(ships.toArray()).forEach(System.out::println);
            input = inputInt(sc.nextLine());
            ship = ships.get(input);
        }

        if (containers.isEmpty()) {
            System.out.println("There are no containers to load onto the ship.");
            System.out.println("Would you like to create a container? y/n");
            String inputS = sc.nextLine();
            if (inputS.equals("y")) {
                createContainer();
                container = containers.get(0);
            }
            else return;
        }
        else {
            System.out.println("Choose a container you'd like to load.");
            Arrays.stream(containers.toArray()).forEach(System.out::println);
            input = inputInt(sc.nextLine());
            container = containers.get(input);
        }

        System.out.println(ship);
        System.out.println(container);
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

    public static void saveData() throws IOException {
        clearScreen();
        Date date = new Date();
        long timeMilli = date.getTime();

        FileWriter writer = new FileWriter(timeMilli+".txt");

    }

    public static void main(String[] args) throws ShipOverloaded {
        clearScreen();
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
                default:
                    System.out.println("Wrong input!");
                    break;
            }
        }

        sc.close();
    }
}