package dead.voidrunnerCore.api;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class DataFile {

    private Plugin plugin;
    private File file;
    private YamlConfiguration config;

    public DataFile(Plugin plugin, String fileName) {
        this.plugin = plugin;
        this.file = new File(plugin.getDataFolder(), fileName);
        file.getParentFile().mkdirs();
        this.config = YamlConfiguration.loadConfiguration(this.file);
    }

    public void set(String path, Object value) {
        config.set(path, value);
    }

    public void setSerializedItem(String path, ItemStack item) {
        byte[] bytes = item.serializeAsBytes();
        String encoded = Base64.getEncoder().encodeToString(bytes);
        config.set(path, encoded);
    }

    public void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void reload() {
        config = YamlConfiguration.loadConfiguration(file);
    }


    public String getString(String path, String def) {
        return config.getString(path, def);
    }
    public int getInt(String path, int def) {
        return config.getInt(path, def);
    }
    public boolean getBoolean(String path, boolean def) {
        return config.getBoolean(path, def);
    }
    public double getDouble(String path, double def) {
        return config.getDouble(path, def);
    }
    public List<String> getStringList(String path) {
        return config.getStringList(path);
    }
    public boolean contains(String path) {
        return config.contains(path);
    }
    public void remove(String path) {
        config.set(path, null);
    }
    public Set<String> getKeys(String path) {
        ConfigurationSection section = config.getConfigurationSection(path);
        if (section == null) {
            return Collections.emptySet();
        }
        return section.getKeys(false);
    }
    public ItemStack getSerializedItem(String path) {
        String encoded = config.getString(path, null);
        if (encoded == null) return null;
        byte[] bytes = Base64.getDecoder().decode(encoded);

        return ItemStack.deserializeBytes(bytes);
    }
}
