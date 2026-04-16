package dead.voidrunnerCore.menu;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

public abstract class AbsMenu implements InventoryHolder {

    protected Inventory inventory;

    public abstract Inventory build();
    public abstract void handleClick(InventoryClickEvent event);
    public void open(Player player) {player.openInventory(build());}

    @Override
    public @NotNull Inventory getInventory() { return inventory; }
}
