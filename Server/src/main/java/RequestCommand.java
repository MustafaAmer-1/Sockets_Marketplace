import com.fasterxml.jackson.databind.JsonNode;

public interface RequestCommand {
    public JsonNode execute(JsonNode data);
}
