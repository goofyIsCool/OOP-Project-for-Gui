package Program;

public class ThreadTimer extends Thread{

    public static int days = 0;
    public static int seconds = 0;

    public void run() {
        while (true){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            increaseNumberOfDays();
        }
    }

    public synchronized static void increaseNumberOfDays (){
        seconds += 1;
        days = seconds/5;
    }

    public static int getDays() {
        return days;
    }

    public static int getSeconds() {
        return seconds;
    }

    public static void setDays(int days) {
        ThreadTimer.days = days;
    }

    public static void setSeconds(int seconds) {
        ThreadTimer.seconds = seconds;
    }
}