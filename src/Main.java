import java.io.*;
import java.util.*;

public class Main {

    public static Scanner sc = new Scanner(System.in);
    public static HashMap<Integer, Ship> ships = new HashMap<>();
    public static HashMap<Integer, Sender> senders = new HashMap<>();
    public static LinkedList<Container> containersWarehouse = new LinkedList<>();

    //https://www.delftstack.com/howto/java/java-clear-console/
    public static void clearScreen(){
        try{
            String operatingSystem = System.getProperty("os.name"); //Check the current operating system

            if(operatingSystem.contains("Windows")){
                ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "cls");
                Process startProcess = pb.inheritIO().start();
                startProcess.waitFor();
            } else {

                ProcessBuilder pb = new ProcessBuilder("clear");
                Process startProcess = pb.inheritIO().start();

                startProcess.waitFor();
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }

    public static <T, K> void list(HashMap<T, K> hm) {
        hm.forEach((key, value) -> System.out.println(value.toString()));
    }

    public static <K, T> K getK(HashMap<T, K> hm, String name) {
        clearScreen();
        System.out.println("-------" + name + "-------");
        System.out.println("Choose a " + name + ": ");

        list(hm);

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
        ships.put(ship.getId(), ship);

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

        senders.put(sender.getId(), sender);

        return sender;
    }

    public static Container container(String data) {
        String[] tmp = data.split(";");
        Container c = null;
        int id = Integer.parseInt(tmp[1]);
        Sender s = senders.get(Integer.parseInt(tmp[2]));
        int ship_id = Integer.parseInt(tmp[3]);

        switch (tmp[0]) {
            case "Standard" -> c = new StandardContainer(s, tmp[4], Double.parseDouble(tmp[5]), Double.parseDouble(tmp[6]), Double.parseDouble(tmp[7]), tmp[8]);
            case "Heavy" -> c = new HeavyCargo(s, tmp[4], Double.parseDouble(tmp[5]), Double.parseDouble(tmp[6]), Double.parseDouble(tmp[7]), tmp[8]);
            case "Refrigerated" -> c = new RefrigeratedCargo(s, tmp[4], Double.parseDouble(tmp[5]), Double.parseDouble(tmp[6]), Double.parseDouble(tmp[7]), tmp[8], Double.parseDouble(tmp[9]));
            case "Liquid" -> c = new LiquidCargo(s, tmp[4], Double.parseDouble(tmp[5]), Double.parseDouble(tmp[6]), Double.parseDouble(tmp[7]), tmp[8]);
            case "Explosive" -> c = new ExplosiveCargo(s, tmp[4], Double.parseDouble(tmp[5]), Double.parseDouble(tmp[6]), Double.parseDouble(tmp[7]), tmp[8]);
            case "Toxic" -> c = new ToxicCargo(s, tmp[4], Double.parseDouble(tmp[5]), Double.parseDouble(tmp[6]), Double.parseDouble(tmp[7]), tmp[8]);
        }

        assert c != null;
        c.setId(id);
        c.setShipId(ship_id);

        return c;
    }

    public static void createContainer() throws ShipOverloaded {
        clearScreen();
        System.out.println("----Creating a Container----");
        Sender sender = null;
        if(!senders.isEmpty()){
            sender = getK(senders, "Sender");
        }else{
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
        int containerType = (Integer.parseInt(sc.nextLine()));

        String data = containerType + ";" + sender.getId() + ";";
        System.out.println("Container");
        System.out.print("Security: ");
        data += sc.nextLine() + ";";
        System.out.print("Tare: ");
        data += sc.nextLine() + ";";
        System.out.print("Net Weight: ");
        data += sc.nextLine() + ";";
        System.out.print("Gross Weight: ");
        data += sc.nextLine() + ";";

        Container container = container(data);
        System.out.println("Loading container...");
        Ship ship = getK(ships, "Ship");
        container.setShipId(ship.getId());

        try {
            ship.loadContainer(container);
        } catch (ShipOverloaded e) {
            e.printStackTrace();
        }
    }

    public static <T> void saveData(LinkedList<T> list, String name) throws IOException {
        FileWriter data;
        try {
            data = new FileWriter("data/" + name + ".txt");
            BufferedWriter bw = new BufferedWriter(data);
//            System.out.println("Saving " + name + "data");
            for (T obj : list) {
                bw.write(obj.toString() + "\n");
            }

            bw.close();
        } catch (IOException except) {
            except.printStackTrace();
        }
    }

    public static <T, K> void saveData(HashMap<T, K> map, String name) throws IOException {
        FileWriter data;
        try {
            data = new FileWriter("data/" + name + ".txt");
            BufferedWriter bw = new BufferedWriter(data);
//            System.out.println("Saving " + name + "data");
            for (Map.Entry<T, K> obj : map.entrySet()) {
                bw.write(obj.getValue().toString() + "\n");
            }

            bw.close();
        } catch (IOException except) {
            except.printStackTrace();
        }
    }

    public static void saveAttr() {
        FileWriter data;
        try {
            data = new FileWriter("data/attr.txt");
            BufferedWriter bw = new BufferedWriter(data);

            bw.write("Ship;Sender;Container\n");
            bw.write(Ship.n + ";" + Sender.n + ";" + Container.n);

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
            line = bw.readLine();
            String[] tmp = line.split(";");
            Ship.n = Integer.parseInt(tmp[0]);
            Sender.n = Integer.parseInt(tmp[1]);
            Container.n = Integer.parseInt(tmp[2]);
            bw.close();
        } catch (IOException ignored) {
        }
    }

    public static <T> void loadData(String name) throws IOException {
        FileReader data;
        try {
            data = new FileReader("data/" + name + ".txt");
            BufferedReader bw = new BufferedReader(data);
            String line;

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
                        ships.put(id, ship);
                    }
                    case "sender" -> {
                        id = Integer.parseInt(tmp[0]);
                        Sender sender = new Sender(tmp[1], tmp[2], tmp[3], tmp[4]);
                        sender.setId(id);
                        senders.put(id, sender);
                    }
                    case "containerShip", "containerWarehouse" -> {
                        Container container = container(line);
                        id = Integer.parseInt(tmp[1]);
                        container.setId(id);
                        if (name.equals("containerShip")) {
                            int ship_id = Integer.parseInt(tmp[3]);
                            ship = ships.get(ship_id);
                            ship.loadContainer(container);
                        } else containersWarehouse.add(container);
                    }
                }
            }

            bw.close();
        } catch (IOException | ShipOverloaded except) {
        }
    }

    public static void main(String[] args) throws ShipOverloaded, IOException {
        clearScreen();

        loadData("ship");
        loadData("sender");
        loadData("containerShip");
        loadData("containerWarehouse");
        loadAttr();

        System.out.println("<-Seaport Logistics->");
        int input = 1;

        while (input != 0) {
            System.out.println("-------Menu-------");
            System.out.println("1. Create a ship.");
            System.out.println("2. Create a container.");
            System.out.println("3. List all ships.");
            System.out.println("4. List all senders.");
            System.out.println("0. Save & Exit.");
            System.out.println("-------END-------");
            System.out.print("Input: ");

            try {
                input = (Integer.parseInt(sc.nextLine()));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

            switch (input) {
                case 0 -> {
                    saveData(ships, "ship");
                    saveData(containersWarehouse, "containerWarehouse");
                    saveData(senders, "sender");
                    saveAttr();
                    System.out.println("Quitting...");
                }
                case 1 -> createShip();
                case 2 -> createContainer();
                case 3 -> list(ships);
                case 4 -> list(senders);
                default -> System.out.println("Wrong input!");
            }
        }
        sc.close();
    }
}