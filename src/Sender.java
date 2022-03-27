public class Sender {
    private String name;
    private String surname;
    private String PESEL;
    private String address;

    public Sender(String name, String surname, String PESEL, String address) {
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

    // public LocalDate getDateOfBirth(String PESEL);
}
