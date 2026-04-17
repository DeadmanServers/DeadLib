package dead.voidrunnerCore.data;

import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.*;

public class ItemData {

    private static DataFile dataFile;
    private static Map<String, Map<UUID, ItemStack>> itemsMap = new HashMap<>();

    public static void init(Plugin plugin) {
        dataFile = new DataFile(plugin, "items.yml");
        load();
    }
    public static void load() {
        Set<String> categories = dataFile.getKeys("Categories");
        for (String category: categories) {

            Set<String> itemIDs = dataFile.getKeys("Categories." + category);
            for (String itemStringID : itemIDs) {

                UUID itemID;
                try {
                    itemID = UUID.fromString(itemStringID);
                } catch (Exception e) {
                    continue;
                }
                ItemStack item = dataFile.getSerializedItem("Categories." + category + "." + itemStringID);

                itemsMap.computeIfAbsent(category, k -> new HashMap<>()).put(itemID, item);
            }
        }
    }
    public static void save() {
        for (String category : itemsMap.keySet()) {
            for (UUID itemID : itemsMap.get(category).keySet()) {
                ItemStack item = itemsMap.get(category).get(itemID);
                if (item == null) continue;
                dataFile.setSerializedItem("Categories." + category + "." + itemID, item);
            }
        }
    }

    public ItemStack get(String itemStringID) {
        UUID itemID;
        try {
            itemID = UUID.fromString(itemStringID);
        } catch (Exception e) {
            return null;
        }
        return get(itemID);
    }

    public ItemStack get(UUID itemID) {
        for (String category : itemsMap.keySet()) {
            if (!itemsMap.get(category).containsKey(itemID)) continue;
            return itemsMap.get(category).get(itemID);
        }
        return null;
    }
    public Map<UUID, ItemStack> getCategory(String category) {
        return itemsMap.get(category);
    }

    public Map<String, Map<UUID, ItemStack>> getCategories() {
        return itemsMap;
    }
    public void remove(String category) {
        itemsMap.remove(category);
    }
    public void removeItem(String itemStringID) {
        UUID itemID;
        try {
            itemID = UUID.fromString(itemStringID);
        }  catch (Exception e) {
            return;
        }
        removeItem(itemID);
    }
    public void removeItem(UUID itemID) {
        for (String category : itemsMap.keySet()) {
            if (!itemsMap.get(category).containsKey(itemID)) continue;
            itemsMap.get(category).remove(itemID);
        }
    }
}
