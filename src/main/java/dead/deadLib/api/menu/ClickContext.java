package dead.deadLib.api.menu;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

public class ClickContext {

    private final Player player;
    private final ClickType clickType;
    private final int slot;
    private final Menu menu;

    public ClickContext(Player player, ClickType clickType, int slot, Menu menu) {
        this.player = player;
        this.clickType = clickType;
        this.slot = slot;
        this.menu = menu;
    }

    public Player player() {
        return player;
    }
    public ClickType clickType() {
        return clickType;
    }
    public int slot() {
        return slot;
    }

    public void close() {player.closeInventory();}
    public void open(Menu other) {other.open(player);}
    public void refresh() {menu.render();}
}
