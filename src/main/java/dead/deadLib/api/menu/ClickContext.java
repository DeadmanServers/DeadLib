package dead.deadLib.api.menu;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class ClickContext {

    private final Player player;
    private final ClickType clickType;
    private final InventoryAction action;
    private final ItemStack cursorItem;
    private final int slot;
    private final Menu menu;

    public ClickContext(Player player, InventoryClickEvent event, int slot, Menu menu) {
        this.player = player;
        this.clickType = event.getClick();
        this.action = event.getAction();
        this.cursorItem = event.getCursor();
        this.slot = slot;
        this.menu = menu;
    }

    public Player player() {
        return player;
    }
    public ClickType clickType() {
        return clickType;
    }
    public InventoryAction action() {
        return action;
    }
    public ItemStack cursor() {
        return cursorItem;
    }
    public int slot() {
        return slot;
    }

    public void close() {player.closeInventory();}
    public void open(Menu other) {other.open(player);}
    public void refresh() {menu.render();}
}
