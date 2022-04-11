package Program;
import java.time.LocalDate;

public class Sender {
    protected static int n;

    private int id;
    private final String name;
    private final String surname;
    private final String PESEL;
    private final String address;

    private int warningCounter;

    public Sender(String name, String surname, String PESEL, String address, int warningCounter) {
        this.id = n++;
        this.name = name;
        this.surname = surname;
        this.PESEL = PESEL;
        this.address = address;
        this.warningCounter = warningCounter;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getSurname() {
//        return surname;
//    }
//
//    public void setSurname(String surname) {
//        this.surname = surname;
//    }
//
//    public String getPESEL() {
//        return PESEL;
//    }
//
//    public void setPESEL(String PESEL) {
//        this.PESEL = PESEL;
//    }
//
//    public String getAddress() {
//        return address;
//    }
//
//    public void setAddress(String address) {
//        this.address = address;
//    }

    public int getWarningCounter() {
        return warningCounter;
    }

    public void setWarningCounter(int warningCounter) {
        this.warningCounter = warningCounter;
    }

    public LocalDate getDateOfBirth() {
        String year = PESEL.substring(0, 2);
        String month = PESEL.substring(2, 4);
        String day = PESEL.substring(4, 6);

        if (Integer.parseInt(month) <= 12) {
            year = String.valueOf(Integer.parseInt(year) + 1900);
        }
        else if (Integer.parseInt(month) > 12) {
            year = String.valueOf(Integer.parseInt(year) + 2000);
            month = String.valueOf(Integer.parseInt(month) % 20);

            if (Integer.parseInt(month) < 10) month = "0" + month;
        }

        return LocalDate.parse(year + "-" + month + "-" + day);
    }

    public String getDetails() {
        return  "\nSender details:" + '\n' +
                "id = " + id + "\n" +
                "name = " + name + '\n' +
                "surname = " + surname + '\n' +
                "PESEL = " + PESEL + '\n' +
                "Date of Birth: " + getDateOfBirth() + '\n' +
                "address = " + address + "\n" +
                "Number of warnings = " + warningCounter;
    }

    public void print() {
        System.out.println("(id=" + id + ")" + name + " " + surname);
    }

    @Override
    public String toString() {
        return id + ";" + name + ";" + surname + ";" + PESEL + ";" + address + ";" + warningCounter;
    }
}
