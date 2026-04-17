package dead.voidrunnerCore.menu;

import dead.voidrunnerCore.builders.ItemBuilder;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.List;

public class CoreMenu extends AbsMenu {

    @Override
    public Inventory build() {
        this.inventory = Bukkit.createInventory(this, 9, MiniMessage.miniMessage().deserialize("Core Menu"));

        inventory.setItem(0, ItemBuilder.create(Material.CHEST, "<gold>Component List", List.of("", "<green>Click to view")).build());

        return inventory;
    }

    @Override
    public void handleClick(InventoryClickEvent event) {
        event.setCancelled(true);

    }

}
