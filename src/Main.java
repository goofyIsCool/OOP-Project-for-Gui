import java.util.Arrays;
import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {

        Ship ship1 = new Ship("Ship1", "London", "Hong Kong", "New York");
        ship1.setMaxNumContainers(3);
        ship1.setMaxWeight(80);

        Sender s1 = new Sender("Dawid", "Do", "02291310170", "asdsada");
//        s1.DateOfBirth();

        Container c1 = new HeavyCargo(s1, "Heavy", 5, 10.0, 20.0, new LinkedList<String>(Arrays.asList("asd", "asda")));
        Container c2 = new HeavyCargo(s1, "Heavy", 5, 10.0, 20.0, new LinkedList<String>(Arrays.asList("asd", "asda")));
        Container c3 = new HeavyCargo(s1, "Heavy", 5, 10.0, 20.0, new LinkedList<String>(Arrays.asList("asd", "asda")));
        Container c4 = new HeavyCargo(s1, "Heavy", 5, 10.0, 20.0, new LinkedList<String>(Arrays.asList("asd", "asda")));

        try {
            ship1.loadContainer(c1);
            ship1.loadContainer(c2);
            ship1.loadContainer(c3);
            ship1.loadContainer(c4);
        }
        catch (ShipOverloaded e) {
            e.printStackTrace();
        }
    }
}
