package dead.voidrunnerCore;

import dead.voidrunnerCore.api.chat.ChatInputListener;
import dead.voidrunnerCore.api.data.DataFile;
import dead.voidrunnerCore.api.nbt.NBT;
import dead.voidrunnerCore.internal.itemeditor.ItemEditorCommand;
import dead.voidrunnerCore.internal.itemstorage.ItemStorageCommand;
import dead.voidrunnerCore.api.menu.VoidrunnerCoreCommand;
import dead.voidrunnerCore.internal.itemstorage.ItemData;
import dead.voidrunnerCore.internal.protection.BlockPlaceExploits;
import dead.voidrunnerCore.internal.serverstatus.ServerStatusData;
import dead.voidrunnerCore.api.menu.MenuClickListener;
import dead.voidrunnerCore.internal.serverstatus.PlaceholderManager;
import dead.voidrunnerCore.internal.serverstatus.SLPUtil;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.net.InetSocketAddress;
import java.util.HashMap;

public final class VoidrunnerCore extends JavaPlugin {

    public static VoidrunnerCore INSTANCE;
    private NBT nbt;

    @Override
    public void onEnable() {
        INSTANCE = this;
        saveDefaultConfig();

        ConfigurationSection section = getConfig().getConfigurationSection("servers");
        for (String serverName : section.getKeys(false)) {
            String value = section.getString(serverName);
            String host = value.split(":")[0];
            int port = Integer.parseInt(value.split(":")[1]);
            ServerStatusData.addAddresses(serverName, new InetSocketAddress(host, port));
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                HashMap<String, InetSocketAddress> addresses = ServerStatusData.getAddresses();
                for (String serverName : addresses.keySet()) {
                    InetSocketAddress inetSocketAddress = addresses.get(serverName);

                    String host = inetSocketAddress.getHostName();
                    int port = inetSocketAddress.getPort();

                    ServerStatusData.addServerStatusBuilder(serverName, SLPUtil.ping(host, port));
                }
            }
        }.runTaskTimerAsynchronously(this, 0L, 200L);

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new PlaceholderManager().register();
        }

        this.nbt = new NBT(this);

        ItemData.init(this);
        getCommand("vrcore").setExecutor(new VoidrunnerCoreCommand());
        getCommand("itemeditor").setExecutor(new ItemEditorCommand());
        getCommand("itemstorage").setExecutor(new ItemStorageCommand());

        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new MenuClickListener(), this);
        pm.registerEvents(new ChatInputListener(), this);
        pm.registerEvents(new BlockPlaceExploits(), this);

    }

    public NBT getNBT() { return nbt; }

    @Override
    public void onDisable() {
        ItemData.save();
    }
}
