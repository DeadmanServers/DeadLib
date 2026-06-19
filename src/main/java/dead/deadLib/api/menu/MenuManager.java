package dead.deadLib.api.menu;

import dead.deadLib.DeadLib;

public class MenuManager {
    public MenuManager(DeadLib plugin) {
        plugin.getServer().getPluginManager().registerEvents(new MenuListener(), plugin);
    }
}
