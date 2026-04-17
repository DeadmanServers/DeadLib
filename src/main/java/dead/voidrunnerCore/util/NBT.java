package dead.voidrunnerCore.util;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class NBT {


    public static void tag(ItemStack item, String key, String value) {
        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(new NamespacedKey("voidrunnercore", key), PersistentDataType.STRING, value);
        item.setItemMeta(meta);
    }
    public static String getTag(ItemStack item, String key) {
        if (item == null || !item.hasItemMeta()) return null;
        return item.getItemMeta().getPersistentDataContainer().get(new NamespacedKey("voidrunnercord", key), PersistentDataType.STRING);
    }
}
