package dead.voidrunnerCore.menu;

import dead.voidrunnerCore.builders.ItemBuilder;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public class ItemEditorMenu extends AbsMenu {

    @Override
    public Inventory build() {
        this.inventory = Bukkit.createInventory(this, 45, MiniMessage.miniMessage().deserialize("&0Item Editor"));

        inventory.setContents(glassContents(45));

        inventory.setItem(4, ItemBuilder.create(Material.OAK_SIGN, "<green>Change Name").build());
        inventory.setItem(11, ItemBuilder.create(Material.ITEM_FRAME, "<green>Change Material").build());
        inventory.setItem(15, ItemBuilder.create(Material.WRITABLE_BOOK, "<green>Edit Lore").build());
        inventory.setItem(29, ItemBuilder.create(Material.ENCHANTING_TABLE, "<green>Edit Enchantments").build());
        inventory.setItem(33, ItemBuilder.create(Material.BEDROCK, "<green>Toggle Unbreakable").build());
        inventory.setItem(40, ItemBuilder.create(Material.EMERALD, "<green>Save to file").build());
        inventory.setItem(22, ItemBuilder.create(Material.STONE_BUTTON, "<gray>Select an item from your inventory").build());
        return inventory;

    }

    @Override
    public void handleClick(InventoryClickEvent event) {
        event.setCancelled(true);
        if (!(event.getWhoClicked() instanceof Player player)) return;

        if (event.getRawSlot() >= inventory.getSize()) {
            if (event.getCurrentItem() != null && event.getCurrentItem().getType() != Material.AIR) {
                inventory.setItem(22, event.getCurrentItem().clone());
                return;
            }
        }

        switch (event.getRawSlot()) {
            case 4 -> {
                Consumer<String> consumer = s -> {

                };
            }
        }
    }
}
