package dead.voidrunnerCore;

import dead.voidrunnerCore.commands.VoidrunnerCoreCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class VoidrunnerCore extends JavaPlugin {

    public static VoidrunnerCore INSTANCE;

    @Override
    public void onEnable() {
        INSTANCE = this;
        saveDefaultConfig();

        getCommand("vrcore").setExecutor(new VoidrunnerCoreCommand());


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
