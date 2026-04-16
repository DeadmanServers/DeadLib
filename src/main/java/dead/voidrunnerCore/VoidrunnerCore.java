package dead.voidrunnerCore;

import dead.voidrunnerCore.commands.VoidrunnerCoreCommand;
import dead.voidrunnerCore.data.ServerStatusData;
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
                    ServerStatusData.ping(inetSocketAddress.getHostName(), inetSocketAddress.getPort());
                }
            }
        }.runTaskTimerAsynchronously(this, 0L, 200L);

        getCommand("vrcore").setExecutor(new VoidrunnerCoreCommand());




    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
