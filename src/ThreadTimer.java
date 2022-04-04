public class ThreadTimer extends Thread{

    public static int days = 0;

    public void run() {
        // increase number of days every 5s.
        while (true){

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            increaseNumberOfDays();
        }
    }

    public synchronized static void increaseNumberOfDays (){
        days += 1;
        System.out.println("Day"+days);
    }
}