package dead.voidrunnerCore.api.nbt;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataHolder;
import org.bukkit.plugin.Plugin;

import java.util.function.Consumer;
import java.util.function.Function;

public class NBT {

    private final Plugin plugin;

    public NBT(Plugin plugin) {
        this.plugin = plugin;
    }

    public void modify(ItemStack item, Consumer<PdcHandle> mutator) {
        if (item == null) return;
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;

        PdcHandle handle = new PdcHandle(meta.getPersistentDataContainer(), plugin);
        mutator.accept(handle);
        item.setItemMeta(meta);
    }

    public void modify(PersistentDataHolder holder, Consumer<PdcHandle> mutator) {
        if (holder == null) return;
        PdcHandle handle = new PdcHandle(holder.getPersistentDataContainer(), plugin);
        mutator.accept(handle);
    }

    public <T> T read(ItemStack item, Function<PdcHandle, T> reader) {
        if (item == null) return null;
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return null;

        PdcHandle handle = new PdcHandle(meta.getPersistentDataContainer(), plugin);
        return reader.apply(handle);
    }

    public <T> T read(PersistentDataHolder holder, Function<PdcHandle, T> reader) {
        if (holder == null) return null;
        PdcHandle handle = new PdcHandle(holder.getPersistentDataContainer(), plugin);
        return reader.apply(handle);
    }


    public void setString(ItemStack item, String key, String value) {
        modify(item, h -> h.setString(key, value));
    }

    public void setString(PersistentDataHolder holder, String key, String value) {
        modify(holder, h -> h.setString(key, value));
    }


    public String getString(ItemStack item, String key) {
        return read(item, h -> h.getString(key, null));
    }

    public String getString(ItemStack item, String key, String def) {
        return read(item, h -> h.getString(key, def));
    }

    public String getString(PersistentDataHolder holder, String key) {
        return read(holder, h -> h.getString(key, null));
    }

    public String getString(PersistentDataHolder holder, String key, String def) {
        return read(holder, h -> h.getString(key, def));
    }


    public void setInt(ItemStack item, String key, int value) {
        modify(item, h -> h.setInt(key, value));
    }

    public void setInt(PersistentDataHolder holder, String key, int value) {
        modify(holder, h -> h.setInt(key, value));
    }


    public int getInt(ItemStack item, String key) {
        return read(item, h -> h.getInt(key, 0));
    }

    public int getInt(ItemStack item, String key, int def) {
        return read(item, h -> h.getInt(key, def));
    }

    public int getInt(PersistentDataHolder holder, String key) {
        return read(holder, h -> h.getInt(key, 0));
    }

    public int getInt(PersistentDataHolder holder, String key, int def) {
        return read(holder, h -> h.getInt(key, def));
    }


    public void setLong(ItemStack item, String key, long value) {
        modify(item, h -> h.setLong(key, value));
    }

    public void setLong(PersistentDataHolder holder, String key, long value) {
        modify(holder, h -> h.setLong(key, value));
    }


    public long getLong(ItemStack item, String key) {
        return read(item, h -> h.getLong(key, 0L));
    }

    public long getLong(ItemStack item, String key, long def) {
        return read(item, h -> h.getLong(key, def));
    }

    public long getLong(PersistentDataHolder holder, String key) {
        return read(holder, h -> h.getLong(key, 0L));
    }

    public long getLong(PersistentDataHolder holder, String key, long def) {
        return read(holder, h -> h.getLong(key, def));
    }


    public void setBoolean(ItemStack item, String key, boolean value) {
        modify(item, h -> h.setBoolean(key, value));
    }

    public void setBoolean(PersistentDataHolder holder, String key, boolean value) {
        modify(holder, h -> h.setBoolean(key, value));
    }


    public boolean getBoolean(ItemStack item, String key) {
        return read(item, h -> h.getBoolean(key, false));
    }

    public boolean getBoolean(ItemStack item, String key, boolean def) {
        return read(item, h -> h.getBoolean(key, def));
    }

    public boolean getBoolean(PersistentDataHolder holder, String key) {
        return read(holder, h -> h.getBoolean(key, false));
    }

    public boolean getBoolean(PersistentDataHolder holder, String key, boolean def) {
        return read(holder, h -> h.getBoolean(key, def));
    }


    public boolean has(ItemStack item, String key) {
        return read(item, h -> h.has(key));
    }

    public boolean has(PersistentDataHolder holder, String key) {
        return read(holder, h -> h.has(key));
    }


    public void remove(ItemStack item, String key) {
        modify(item, h -> h.remove(key));
    }

    public void remove(PersistentDataHolder holder, String key) {
        modify(holder, h -> h.remove(key));
    }

}
