package DataUtility;

import java.util.HashMap;
import java.util.Map;

public class Container {
    private final Map<Item, Integer> items = new HashMap<Item, Integer>();

    public void addItem(Item item, int count){
        items.put(item, count);
    }

    public int getItemCount(Item item){
        return items.get(item);
    }
    
    public int getTotalPrice(){
        int result = 0;
        for (var entry:
             items.entrySet()) {
            result += entry.getKey().getPrice() * entry.getValue();
        }
        return result;
    }
}
