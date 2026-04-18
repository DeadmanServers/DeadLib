package dead.voidrunnerCore.util;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class NBT {

    private static NamespacedKey key(String key) {
        return new NamespacedKey("voidrunnercore", key.toLowerCase());
    }

    public static void setString(ItemStack item, String key, String value) {
        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(key(key), PersistentDataType.STRING, value);
        item.setItemMeta(meta);
    }
    public static void setInt(ItemStack item, String key, int value) {
        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(key(key), PersistentDataType.INTEGER, value);
        item.setItemMeta(meta);
    }
    public static void setBoolean(ItemStack item, String key, boolean value) {
        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(key(key), PersistentDataType.BOOLEAN, value);
        item.setItemMeta(meta);
    }
    public static void setDouble(ItemStack item, String key, double value) {
        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(key(key), PersistentDataType.DOUBLE, value);
        item.setItemMeta(meta);
    }
    public static void setLong(ItemStack item, String key, long value) {
        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(key(key), PersistentDataType.LONG, value);
        item.setItemMeta(meta);
    }
    public static void setFloat(ItemStack item, String key, float value) {
        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(key(key), PersistentDataType.FLOAT, value);
        item.setItemMeta(meta);
    }
    public static void setByte(ItemStack item, String key, byte value) {
        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(key(key), PersistentDataType.BYTE, value);
        item.setItemMeta(meta);
    }
    public static void setShort(ItemStack item, String key, short value) {
        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(key(key), PersistentDataType.SHORT, value);
        item.setItemMeta(meta);
    }
    public static void setByteArray(ItemStack item, String key, byte[] value) {
        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(key(key), PersistentDataType.BYTE_ARRAY, value);
        item.setItemMeta(meta);
    }
    public static void setIntArray(ItemStack item, String key, int[] value) {
        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(key(key), PersistentDataType.INTEGER_ARRAY, value);
        item.setItemMeta(meta);
    }
    public static void setLongArray(ItemStack item, String key, long[] value) {
        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(key(key), PersistentDataType.LONG_ARRAY, value);
        item.setItemMeta(meta);
    }

    public static String getString(ItemStack item, String key) {
        if (!has(item, key)) return null;
        return item.getItemMeta().getPersistentDataContainer().get(key(key), PersistentDataType.STRING);
    }
    public static int getInt(ItemStack item, String key, int def) {
        if (!has(item, key)) return def;
        return item.getItemMeta().getPersistentDataContainer().get(key(key), PersistentDataType.INTEGER);
    }
    public static boolean getBoolean(ItemStack item, String key, boolean def) {
        if (!has(item, key)) return def;
        return item.getItemMeta().getPersistentDataContainer().get(key(key), PersistentDataType.BOOLEAN);
    }
    public static double getDouble(ItemStack item, String key, double def) {
        if (!has(item, key)) return def;
        return item.getItemMeta().getPersistentDataContainer().get(key(key), PersistentDataType.DOUBLE);
    }
    public static long getLong(ItemStack item, String key, long def) {
        if (!has(item, key)) return def;
        return item.getItemMeta().getPersistentDataContainer().get(key(key), PersistentDataType.LONG);
    }
    public static float getFloat(ItemStack item, String key, float def) {
        if (!has(item, key)) return def;
        return item.getItemMeta().getPersistentDataContainer().get(key(key), PersistentDataType.FLOAT);
    }
    public static byte getByte(ItemStack item, String key, byte def) {
        if (!has(item, key)) return def;
        return item.getItemMeta().getPersistentDataContainer().get(key(key), PersistentDataType.BYTE);
    }
    public static short getShort(ItemStack item, String key, short def) {
        if (!has(item, key)) return def;
        return item.getItemMeta().getPersistentDataContainer().get(key(key), PersistentDataType.SHORT);
    }
    public static byte[] getByteArray(ItemStack item, String key, byte[] def) {
        if (!has(item, key)) return def;
        return item.getItemMeta().getPersistentDataContainer().get(key(key), PersistentDataType.BYTE_ARRAY);
    }
    public static int[] getIntArray(ItemStack item, String key, int[] def) {
        if (!has(item, key)) return def;
        return item.getItemMeta().getPersistentDataContainer().get(key(key), PersistentDataType.INTEGER_ARRAY);
    }
    public static long[] getLongArray(ItemStack item, String key, long[] def) {
        if (!has(item, key)) return def;
        return item.getItemMeta().getPersistentDataContainer().get(key(key), PersistentDataType.LONG_ARRAY);
    }


    public static boolean has(ItemStack item, String key) {
        if (item == null || !item.hasItemMeta()) return false;
        return item.getItemMeta().getPersistentDataContainer().has(key(key));
    }

    public static void remove(ItemStack item, String key) {
        if (!has(item, key)) return;
        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().remove(key(key));
        item.setItemMeta(meta);
    }
}
