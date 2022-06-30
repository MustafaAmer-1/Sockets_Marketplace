import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.HashMap;

public class ClientHandler implements Runnable{
    private final Socket socket;
    private static final ServerHandler handler = new ServerHandler();
    private static final HashMap<String, RequestCommand> commands = init();
    private static final ObjectMapper mapper = new ObjectMapper();

    public ClientHandler(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);

            while (true) {
                String msgFromClient = bufferedReader.readLine();
                JsonNode req = mapper.readTree(msgFromClient);
                JsonNode response = commands.get(
                        req.get("action").asText())
                        .execute(
                                req.get("data"));
                bufferedWriter.write(response.asText());
                bufferedWriter.newLine();
                bufferedWriter.flush();
                if (msgFromClient == null || msgFromClient.equalsIgnoreCase("BYE"))
                    break;
            }

            socket.close();
            inputStreamReader.close();
            outputStreamWriter.close();
            bufferedReader.close();
            bufferedWriter.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    private static HashMap<String, RequestCommand> init() {
        HashMap<String, RequestCommand> commands = new HashMap<>();
        commands.put("login",
                (data) -> {
                    ObjectNode res = mapper.createObjectNode();
                    res.put("status", handler.Authentication(
                            data.get("data")));
                    return res;
                });

        commands.put("order",
                (data) -> {
                    ObjectNode res = mapper.createObjectNode();
                    res.put("status", handler.submit_order(
                            data.get("data")));
                    return res;
                });

        commands.put("register",
                (data) -> {
                    ObjectNode res = mapper.createObjectNode();
                    res.put("status", handler.Register(
                            data.get("data")));
                    return res;
                });

        commands.put("withdraw",
                (data) -> {
                    ObjectNode res = mapper.createObjectNode();
                    res.put("status", handler.withdraw_cash(
                            data.get("data")));
                    return res;
                });

        commands.put("logout", // need to set the user cart
                (data) -> {
                    ObjectNode res = mapper.createObjectNode();
                    res.put("status", true); // should be the operation status
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

        return commands;
    }
}
