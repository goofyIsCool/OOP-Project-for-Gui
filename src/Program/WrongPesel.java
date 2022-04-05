package Program;

import java.time.DateTimeException;

public class WrongPesel extends DateTimeException {
    public WrongPesel(String msg){
        super(msg);
    }

    public static boolean checkIfValid(String PESEL){

        String year = PESEL.substring(0, 2);
        String month = PESEL.substring(2, 4);
        String day = PESEL.substring(4, 6);

        if (Integer.parseInt(year)%4!=0 && day.equals("29") && (month.equals("02")||month.equals("22"))){
            return false;
        }

        return true;
    }
}
