package dead.voidrunnerCore;

import dead.voidrunnerCore.chat.ChatInputListener;
import dead.voidrunnerCore.itemeditor.ItemEditorCommand;
import dead.voidrunnerCore.menu.VoidrunnerCoreCommand;
import dead.voidrunnerCore.itemstorage.ItemData;
import dead.voidrunnerCore.protection.BlockPlaceExploits;
import dead.voidrunnerCore.serverstatus.ServerStatusData;
import dead.voidrunnerCore.menu.MenuClickListener;
import dead.voidrunnerCore.serverstatus.PlaceholderManager;
import dead.voidrunnerCore.serverstatus.SLPUtil;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.net.InetSocketAddress;
import java.util.HashMap;

public final class VoidrunnerCore extends JavaPlugin {

    public static VoidrunnerCore INSTANCE;

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

        ItemData.init(this);
        getCommand("vrcore").setExecutor(new VoidrunnerCoreCommand());
        getCommand("itemeditor").setExecutor(new ItemEditorCommand());

        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new MenuClickListener(), this);
        pm.registerEvents(new ChatInputListener(), this);
        pm.registerEvents(new BlockPlaceExploits(), this);

    }

    @Override
    public void onDisable() {
        ItemData.save();
    }
}
