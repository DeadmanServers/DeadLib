package dead.voidrunnerCore;

import dead.voidrunnerCore.commands.VoidrunnerCoreCommand;
import dead.voidrunnerCore.data.ServerStatusData;
import dead.voidrunnerCore.placeholderAPI.PlaceholderManager;
import dead.voidrunnerCore.util.SLPUtil;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
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

        getCommand("vrcore").setExecutor(new VoidrunnerCoreCommand());




    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
