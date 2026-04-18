package dead.voidrunnerCore.events;

import dead.voidrunnerCore.menu.declaration.AbsMenu;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class MenuClickListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player player)) return;
        if (!(e.getInventory().getHolder() instanceof AbsMenu menu)) return;

        e.setCancelled(true);
        menu.handleClick(e);
    }
}
