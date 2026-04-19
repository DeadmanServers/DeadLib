package dead.voidrunnerCore.menu;

import dead.voidrunnerCore.api.ItemBuilder;
import dead.voidrunnerCore.itemstorage.ItemStorageMainMenu;
import dead.voidrunnerCore.util.Palette;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class CoreMenu extends AbsMenu {

    @Override
    public Inventory build() {
        this.inventory = Bukkit.createInventory(this, 9, MiniMessage.miniMessage().deserialize("Core Menu"));

        inventory.setItem(0, ItemBuilder.create(Material.CHEST, Palette.GOLD + "Item Storage", List.of("", "<green>Click to view")).build());

        return inventory;
    }

    @Override
    public void handleClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;
        ItemStack item = event.getCurrentItem();
        if (item == null || item.getType().isAir() || isGlass(item)) return;

        switch (event.getRawSlot()) {
            case 0 -> {
                new ItemStorageMainMenu().open(player);
            }
        }
    }

}
