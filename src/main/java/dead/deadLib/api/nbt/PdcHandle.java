package dead.deadLib.api.nbt;

import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PdcHandle {

    private final PersistentDataContainer pdc;
    private final Plugin plugin;

    private NamespacedKey key(String name) {
        return new NamespacedKey(plugin, name.toLowerCase());
    }

    public PdcHandle(PersistentDataContainer pdc, Plugin plugin) {
        this.plugin = plugin;
        this.pdc = pdc;
    }

    public boolean has(String key) {
        return pdc.has(key(key));
    }

    public void remove(String key) {
        pdc.remove(key(key));
    }

    // SETTERS
    public void setString(String key, String value) {
        pdc.set(key(key), PersistentDataType.STRING, value);
    }

    public void setInt(String key, int value) {
        pdc.set(key(key), PersistentDataType.INTEGER, value);
    }

    public void setLong(String key, long value) {
        pdc.set(key(key), PersistentDataType.LONG, value);
    }

    public void setDouble(String key, double value) {
        pdc.set(key(key), PersistentDataType.DOUBLE, value);
    }

    public void setBoolean(String key, boolean value) {
        pdc.set(key(key), PersistentDataType.BOOLEAN, value);
    }

    public void setByte(String key, byte value) {
        pdc.set(key(key), PersistentDataType.BYTE, value);
    }

    public void setShort(String key, short value) {
        pdc.set(key(key), PersistentDataType.SHORT, value);
    }

    public void setFloat(String key, float value) {
        pdc.set(key(key), PersistentDataType.FLOAT, value);
    }

    public void setByteArray(String key, byte[] value) {
        pdc.set(key(key), PersistentDataType.BYTE_ARRAY, value);
    }

    public void setIntArray(String key, int[] value) {
        pdc.set(key(key), PersistentDataType.INTEGER_ARRAY, value);
    }

    public void setLongArray(String key, long[] value) {
        pdc.set(key(key), PersistentDataType.LONG_ARRAY, value);
    }

    public void setStringList(String key, List<String> value) {
        pdc.set(key(key), PersistentDataType.LIST.strings(), value);
    }

    // GETTERS
    public String getString(String key, String def) {
        if (!pdc.has(key(key))) return def;
        return pdc.get(key(key), PersistentDataType.STRING);
    }

    public String getString(String key) {
        if (!pdc.has(key(key))) return null;
        return pdc.get(key(key), PersistentDataType.STRING);
    }

    public long getLong(String key, long def) {
        if (!pdc.has(key(key))) return def;
        return pdc.get(key(key), PersistentDataType.LONG);
    }

    public double getDouble(String key, double def) {
        if (!pdc.has(key(key))) return def;
        return pdc.get(key(key), PersistentDataType.DOUBLE);
    }

    public int getInt(String key, int def) {
        if (!pdc.has(key(key))) return def;
        return pdc.get(key(key), PersistentDataType.INTEGER);
    }

    public boolean getBoolean(String key, boolean def) {
        if (!pdc.has(key(key))) return def;
        return pdc.get(key(key), PersistentDataType.BOOLEAN);
    }

    public byte getByte(String key, byte def) {
        if (!pdc.has(key(key))) return def;
        return pdc.get(key(key), PersistentDataType.BYTE);
    }

    public short getShort(String key, short def) {
        if (!pdc.has(key(key))) return def;
        return pdc.get(key(key), PersistentDataType.SHORT);
    }

    public float getFloat(String key, float def) {
        if (!pdc.has(key(key))) return def;
        return pdc.get(key(key), PersistentDataType.FLOAT);
    }

    public byte[] getByteArray(String key, byte[] def) {
        if (!pdc.has(key(key))) return def;
        return pdc.get(key(key), PersistentDataType.BYTE_ARRAY);
    }

    public byte[] getByteArray(String key) {
        if (!pdc.has(key(key))) return null;
        return pdc.get(key(key), PersistentDataType.BYTE_ARRAY);
    }

    public int[] getIntArray(String key, int[] def) {
        if (!pdc.has(key(key))) return def;
        return pdc.get(key(key), PersistentDataType.INTEGER_ARRAY);
    }

    public int[] getIntArray(String key) {
        if (!pdc.has(key(key))) return null;
        return pdc.get(key(key), PersistentDataType.INTEGER_ARRAY);
    }

    public long[] getLongArray(String key, long[] def) {
        if (!pdc.has(key(key))) return def;
        return pdc.get(key(key), PersistentDataType.LONG_ARRAY);
    }

    public long[] getLongArray(String key) {
        if (!pdc.has(key(key))) return null;
        return pdc.get(key(key), PersistentDataType.LONG_ARRAY);
    }

    public List<String> getStringList(String key, List<String> def) {
        if (!pdc.has(key(key))) return def;
        return pdc.get(key(key), PersistentDataType.LIST.strings());
    }

    public List<String> getStringList(String key) {
        if (!pdc.has(key(key))) return null;
        return pdc.get(key(key), PersistentDataType.LIST.strings());
    }

    public Set<String> getKeys() {
        String myNamespace = plugin.getName().toLowerCase();
        Set<String> result = new HashSet<>();
        for (NamespacedKey key : pdc.getKeys()) {
            if (key.getNamespace().equals(myNamespace)) {
                result.add(key.getKey());
            }
        }
        return result;
    }

}
