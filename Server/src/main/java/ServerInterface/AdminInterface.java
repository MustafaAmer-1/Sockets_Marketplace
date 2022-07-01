package ServerInterface;

import java.util.HashSet;
import java.util.Set;

public class AdminInterface {
    Set<String> clients = new HashSet<>();

    public void addClient(String email){
        clients.add(email);
    }

    public void removeClient(String email){
        clients.remove(email);
    }
}
