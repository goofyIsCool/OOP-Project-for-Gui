import java.nio.charset.StandardCharsets;

public class Sender {
    protected static int n;

    private int id;
    private String name;
    private String surname;
    private String PESEL;
    private String address;

    public Sender(String name, String surname, String PESEL, String address) {
        this.id = n++;
        this.name = name;
        this.surname = surname;
        this.PESEL = PESEL;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPESEL() {
        return PESEL;
    }

    public void setPESEL(String PESEL) {
        this.PESEL = PESEL;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    // LocalDate getDateOfBirth(String PESEL);

    @Override
    public String toString(){
        return "(id="+id+")"+name+" "+surname+", PESEL:" + getPESEL() + ", Address:" + address;
    }
}
