package dead.deadLib;

import dead.deadLib.api.chat.ChatInputListener;
import dead.deadLib.api.menu.AbsMenu;
import dead.deadLib.api.nbt.NBT;
import dead.deadLib.internal.itemeditor.ItemEditorCommand;
import dead.deadLib.internal.itemstorage.ItemStorageCommand;
import dead.deadLib.internal.commands.DeadLibCommand;
import dead.deadLib.internal.itemstorage.ItemData;
import dead.deadLib.internal.protection.BlockPlaceExploits;
import dead.deadLib.internal.serverstatus.ServerStatusData;
import dead.deadLib.api.menu.MenuClickListener;
import dead.deadLib.internal.serverstatus.PlaceholderManager;
import dead.deadLib.internal.serverstatus.SLPUtil;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.net.InetSocketAddress;
import java.util.HashMap;

public final class DeadLib extends JavaPlugin {

    public static DeadLib INSTANCE;

    @Override
    public void onEnable() {
        INSTANCE = this;
        saveDefaultConfig();
        AbsMenu.init(this);
        ItemData.init(this);

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

        getCommand("deadlib").setExecutor(new DeadLibCommand());
        getCommand("itemeditor").setExecutor(new ItemEditorCommand());
        getCommand("itemstorage").setExecutor(new ItemStorageCommand());

        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new MenuClickListener(), this);
        pm.registerEvents(new ChatInputListener(), this);
        pm.registerEvents(new BlockPlaceExploits(), this);


    }

    @Override
    public void onDisable() {
        ItemData.saveSync();
    }
}
