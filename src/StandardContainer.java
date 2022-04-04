public class StandardContainer extends Container {

    public StandardContainer(Sender sender, String security, double tare, double netWeight, double grossWeight, String certificates) {
        super(sender, security, tare, netWeight, grossWeight, certificates);
    }

    @Override
    public int compareTo(Container o) {
        return this.getGrossWeight() > o.getGrossWeight() ? 1 : (this.getGrossWeight() == o.getGrossWeight() ? 0 : -1);
    }

    @Override
    public String toString(){
        return  id + ";" + "Standard;" + sender.getId() + ";" + shipId + ";" + security + ";" + tare + ";" + netWeight + ";" + grossWeight + ";" + certificates;
    }
}
