package dead.voidrunnerCore.data;

import dead.voidrunnerCore.VoidrunnerCore;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ItemData {

    private static Map<UUID, ItemData> items = new HashMap<>();

    public static final File file = new File(VoidrunnerCore.INSTANCE.getDataFolder(), "itemData.yml");


    private UUID itemID;
    private ItemStack itemStack;

    public static ItemData create() {
        ItemData itemData = new ItemData();
        itemData.itemID = UUID.randomUUID();
        return itemData;
    }

    public static ItemData create(ItemStack itemStack) {
        ItemData itemData = create();
        itemData.itemStack = itemStack.clone().asOne();
        return itemData;
    }

    public static ItemData get(UUID itemID) {
        if (items.containsKey(itemID)) {
            return items.get(itemID);
        }
        return null;
    }

    public ItemData set(ItemStack itemStack) {
        this.itemStack = itemStack.clone().asOne();
        return this;
    }

    public static Map<UUID, ItemData> getItems() {
        return items;
    }

    public ItemStack getItem(ItemData itemData) {
        return this.itemStack.clone();
    }



}
