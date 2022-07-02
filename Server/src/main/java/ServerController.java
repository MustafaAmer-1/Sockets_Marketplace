import ServerInterface.AdminManager;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerController {
    private static final int PORT = 9000;
    private static ExecutorService executor = Executors.newCachedThreadPool();

    public static void main(String[] args) throws IOException {
        AdminManager manager = new AdminManager();
        manager.start();
        ServerSocket serversocket = new ServerSocket(PORT);

        while (true) {
            Socket socket = serversocket.accept();

            Runnable worker = new ClientHandler(socket);
            executor.execute(worker);
        }
    }

}
