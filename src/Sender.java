import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Date;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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


    public LocalDate getDateOfBirth() throws WrongPesel {
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

        if (Integer.parseInt(year)%4!=0 && day.equals("29") && month.equals("02")){
            throw new WrongPesel("Wrong pesel");
        }
        else return LocalDate.parse(year + "-" + month + "-" + day);
    }

    public String print() {
        return "(id=" + id + ")" + name + " " + surname + ", PESEL:" + PESEL + ", Address:" + address;
    }

    @Override
    public String toString() {
        return id + ";" + name + ";" + surname + ";" + PESEL + ";" + address;
    }
}
