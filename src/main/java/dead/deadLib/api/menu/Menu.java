package dead.deadLib.api.menu;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class Menu implements InventoryHolder {
    private final Map<Integer, Button> buttons = new HashMap<>();
    private final Set<Integer> inputSlots = new HashSet<>();
    protected Inventory inventory;
    private Player viewer;

    protected abstract Inventory create();

    protected abstract void decorate();

    protected void input(int slot) { inputSlots.add(slot); }

    protected void set(int slot, Button button) {
        buttons.put(slot, button);
        inventory.setItem(slot, button.icon());
    }
    protected void fill(ItemStack filler) {
        for (int i = 0; i < inventory.getSize(); i++) inventory.setItem(i, filler);
    }

    protected void onClose() {}

    public void open(Player player) {
        this.viewer = player;
        this.inventory = create();
        decorate();
        player.openInventory(inventory);
    }

    public void render() {
        buttons.clear();
        inputSlots.clear();
        inventory.clear();
        decorate();
    }

    void handle(InventoryClickEvent event) {
        if (event.getClickedInventory() != inventory) {
            if (event.isShiftClick() || event.getClick() == ClickType.DOUBLE_CLICK) { event.setCancelled(true); }
            return;
        }
        int raw = event.getRawSlot();
        Button button = buttons.get(raw);
        if (button != null) {
            event.setCancelled(true);
            button.click(new ClickContext(viewer, event, raw, this));
            return;
        }
        if (inputSlots.contains(raw)) return;

        event.setCancelled(true);
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inventory;
    }

    void closed(InventoryCloseEvent event) {
        for (int slot : inputSlots) {
            ItemStack item = inventory.getItem(slot);
            if (item == null || item.getType().isAir()) continue;
            var leftover = viewer.getInventory().addItem(item);
            leftover.values().forEach(extra ->
                    viewer.getInventory().addItem(extra));
        }
        onClose();
    }
}
