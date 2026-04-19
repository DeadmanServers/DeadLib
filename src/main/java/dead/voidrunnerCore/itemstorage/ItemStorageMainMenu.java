package dead.voidrunnerCore.itemstorage;

import dead.voidrunnerCore.menu.AbsMenu;
import dead.voidrunnerCore.util.MyMini;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class ItemStorageMainMenu extends AbsMenu {
    @Override
    public Inventory build() {
        this.inventory = Bukkit.createInventory(this, 54, MyMini.normalize(""));
        return null;
    }

    @Override
    public void handleClick(InventoryClickEvent event) {

    }
}
