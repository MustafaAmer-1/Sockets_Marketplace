import ServerInterface.AdminInterface;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.HashMap;

public class ClientHandler implements Runnable{
    private final Socket socket;
    private final ServerHandler handler = new ServerHandler();
    private final HashMap<String, RequestCommand> commands = init();
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final AdminInterface admin = new AdminInterface();
    private String clientEmail;

    public ClientHandler(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        System.out.println("Client connected: " + Thread.currentThread().getId());
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);

            while (true) {
                String msgFromClient = bufferedReader.readLine();
                if(msgFromClient == null){
                    continue;
                }
                System.out.println("Client: " + msgFromClient);
                if (msgFromClient.equalsIgnoreCase("BYE")) {
                    break;
                }
                JsonNode req = mapper.readTree(msgFromClient);
                JsonNode response = commands.get(
                                req.get("action").asText())
                        .execute(
                                req.get("data"));
                System.out.println("Server Response: " + response);
                bufferedWriter.write(String.valueOf(response));
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
            socket.close();
            inputStreamReader.close();
            outputStreamWriter.close();
            bufferedReader.close();
            bufferedWriter.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally{
            removeClientFromAdmin();
        }
    }

    private synchronized void addClientToAdmin(){
        admin.addClient(clientEmail);
    }

    private synchronized void removeClientFromAdmin(){
        admin.removeClient(clientEmail);
    }

    private HashMap<String, RequestCommand> init() {
        HashMap<String, RequestCommand> commands = new HashMap<>();
        commands.put("login",
                (data) -> {
                    ObjectNode res = mapper.createObjectNode();
                    boolean auth = handler.Authentication(data);
                    res.put("status", auth);
                    if(auth){
                        clientEmail = data.get("email").asText();
                        addClientToAdmin();
                        res.set("cart", handler.get_cart());
                    }
                    else{
                        res.put("error", "Invalid Login");
                    }
                    return res;
                });

        commands.put("order",
                (data) -> {
                    ObjectNode res = mapper.createObjectNode();
                    res.put("status", handler.submit_order(
                            data));
                    return res;
                });

        commands.put("register",
                (data) -> {
                    ObjectNode res = mapper.createObjectNode();
                    res.put("status", handler.Register(data));
                    return res;
                });

        commands.put("withdraw",
                (data) -> {
                    ObjectNode res = mapper.createObjectNode();
                    res.put("status", handler.withdraw_cash(data.asDouble()));
                    return res;
                });

        commands.put("deposite",
                (data) -> {
                    ObjectNode res = mapper.createObjectNode();
                    res.put("status", handler.Deposit_cash(data.asDouble()));
                    return res;
                });

        commands.put("logout",
                (data) -> {
                    handler.set_cart(data);
                    ObjectNode res = mapper.createObjectNode();
                    res.put("status", true); // should be the operation status
                    removeClientFromAdmin();
                    return res;
                });

        commands.put("all",
                (data) -> {
                    ObjectNode res = mapper.createObjectNode();
                    res.put("status", true); // should be the operation status
                    res.set("items",
                            handler.getAllItems());
                    return res;
                });

        commands.put("search",
                (data) -> {
                    ObjectNode res = mapper.createObjectNode();
                    res.put("status", true); // should be the operation status
                    res.set("items",
                            handler.search_item_name(data.asText()));
                    return res;
                });

        commands.put("category",
                (data) -> {
                    ObjectNode res = mapper.createObjectNode();
                    res.put("status", true); // should be the operation status
                    res.set("items",
                            handler.search_item_category(data.asText()));
                    return res;
                });

        commands.put("userinfo",
                (data) ->
                    handler.getUserInfo()
                );

        commands.put("orders_history",
                (data) -> {
                    ObjectNode res = mapper.createObjectNode();
                    res.put("status", true); // should be the operation status
                    res.set("orders",
                            handler.getCustomerHistory());
                    return res;
                });

        return commands;
    }
}
