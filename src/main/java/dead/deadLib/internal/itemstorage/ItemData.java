package dead.deadLib.internal.itemstorage;

import dead.deadLib.api.data.DataFile;
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
            Map<UUID, ItemStack> map = new HashMap<>();
            Set<String> itemIDs = dataFile.getKeys("Categories." + category);
            for (String itemStringID : itemIDs) {

                UUID itemID;
                try {
                    itemID = UUID.fromString(itemStringID);
                } catch (Exception e) {
                    continue;
                }
                ItemStack item = dataFile.getItem("Categories." + category + "." + itemStringID);
                map.put(itemID, item);
            }
            itemsMap.put(category, map);
        }
    }
    public static void save() {
        for (String category : itemsMap.keySet()) {
            for (UUID itemID : itemsMap.get(category).keySet()) {
                ItemStack item = itemsMap.get(category).get(itemID);
                if (item == null) continue;
                dataFile.setItem("Categories." + category + "." + itemID, item);
            }
        }
        dataFile.save();
    }

    public static ItemStack getItem(String itemStringID) {
        UUID itemID;
        try {
            itemID = UUID.fromString(itemStringID);
        } catch (Exception e) {
            return null;
        }
        return getItem(itemID);
    }

    public static ItemStack getItem(UUID itemID) {
        for (String category : itemsMap.keySet()) {
            if (!itemsMap.get(category).containsKey(itemID)) continue;
            return itemsMap.get(category).get(itemID).clone();
        }
        return null;
    }

    public static ItemStack getItemFromCategory(String category, UUID itemID) {
        return itemsMap.get(category).get(itemID).clone();
    }
    public static Map<UUID, ItemStack> getCategory(String category) {
        return itemsMap.get(category);
    }
    public static List<UUID> getItemIDs(String category) {
        return new ArrayList<>(itemsMap.get(category).keySet());
    }

    public static Map<String, Map<UUID, ItemStack>> getCategories() {
        return itemsMap;
    }
    public static List<String> getCategoryList() {
        return new ArrayList<>(itemsMap.keySet());
    }
    public static void createCategory(String category) {
        HashMap<UUID, ItemStack> emptyMap = new HashMap<>();
        itemsMap.put(category, emptyMap);
        dataFile.set("Categories." + category, emptyMap);
        dataFile.save();
    }
    public static void removeCategory(String category) {
        itemsMap.remove(category);
        dataFile.remove("Categories." + category);
        dataFile.save();
    }
    public static void removeItem(String itemStringID) {
        UUID itemID;
        try {
            itemID = UUID.fromString(itemStringID);
        }  catch (Exception e) {
            return;
        }
        removeItem(itemID);
    }
    public static void removeItem(UUID itemID) {
        for (String category : itemsMap.keySet()) {
            if (!itemsMap.get(category).containsKey(itemID)) continue;
            itemsMap.get(category).remove(itemID);
            dataFile.remove("Categories." + category + "." + itemID);
        }
        dataFile.save();
    }
    public static void removeFromCategory(String category, UUID itemID) {
        itemsMap.get(category).remove(itemID);
        dataFile.remove("Categories." + category + "." + itemID);
        dataFile.save();
    }
    public static UUID saveItem(String category, ItemStack item) {
        UUID itemID = UUID.randomUUID();
        ItemStack clone = item.clone();
        itemsMap.computeIfAbsent(category, k -> new HashMap<>()).put(itemID, clone);
        dataFile.setItem("Categories." + category + "." + itemID, clone);
        dataFile.save();
        return itemID;
    }
    public static boolean exists(String category) {
        return itemsMap.containsKey(category);
    }
}
