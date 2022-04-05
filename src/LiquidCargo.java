
public class LiquidCargo extends HeavyCargo implements Liquid {

    String type; // Liquid Type
    double litres;
    double priceOfLitre;

    public LiquidCargo(Sender sender, String security, double tare, double netWeight, double grossWeight, String certificates) {
        super(sender, security, tare, netWeight, grossWeight, certificates);
    }

    @Override
    public String toString(){
        return  id + ";" + "Liquid;" + sender.getId() + ";" + shipId + ";" + security + ";" + tare + ";" + netWeight + ";" + grossWeight + ";" + certificates;
    }

    @Override
    public double getLiquidWorth(double litres, double priceOfLitre) {
        return litres*priceOfLitre;
    }
}
