package ServerInterface;

public class AdminManager extends Thread {
    @Override
    public void run() {
        new Adminstrator_Interface();
    }
}
