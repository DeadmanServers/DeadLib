package dead.deadLib.internal.itemstorage;

import dead.deadLib.DeadLib;
import dead.deadLib.api.item.ItemBuilder;
import dead.deadLib.api.item.LoreBuilder;
import dead.deadLib.api.nbt.NBT;
import dead.deadLib.api.menu.AbsMenu;
import dead.deadLib.internal.menu.CoreMenu;
import dead.deadLib.api.text.MyMini;
import dead.deadLib.api.text.Palette;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ItemStorageMainMenu extends AbsMenu {

    private final NBT nbt = new NBT(DeadLib.INSTANCE);
    private int page = 0;
    public void setPage(int page) {
        this.page = page;
    }

    @Override
    public Inventory build() {
        this.inventory = Bukkit.createInventory(this, 54, MyMini.normalize(Palette.GOLD + "Storage Main Menu"));
        inventory.setContents(glassContents(54));

        inventory.setItem(45, backButton());

        int index = page * 28;

        List<String> categoryList = new ArrayList<>(ItemData.getCategoryList());

        if (index + 28 < categoryList.size()) {
            inventory.setItem(53, nextButton());
        }


        for (int slot = 10; slot <= 43; slot++) {
            if (slot == 17 || slot == 26 || slot == 35) {
                slot += 2;
            }
            if (index >= categoryList.size()) {
                inventory.setItem(slot, empty());
                continue;
            }
            String category = categoryList.get(index);
            if (category.isEmpty()) {
                index++;
                inventory.setItem(slot, empty());
                continue;
            }
            List<Component> lore = LoreBuilder.create()
                    .blank()
                    .line("<green>Click to view")
                    .build();

            ItemStack icon = ItemBuilder.create(Material.CHEST, MyMini.normalize(Palette.GOLD + category)).build();

            nbt.setString(icon, "categoryID", category);

            inventory.setItem(slot, icon);
            index++;
        }
        return inventory;
    }

    @Override
    public void handleClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;

        ItemStack item = event.getCurrentItem();
        if (item == null || item.getType().isAir() || isGlass(item)) return;

        if (isBackButton(item)) {
            if (page-1 < 0) {
                new CoreMenu().open(player);
                return;
            }
            page--;
            open(player);
        }
        if (isNextButton(item)) {
            page++;
            open(player);
        }
        if (!nbt.has(item, "categoryID")) {
            return;
        }
        String category = nbt.getString(item, "categoryID");
        ItemStorageViewer menu = new ItemStorageViewer();
        menu.setMainMenuPage(page);
        menu.setCategory(category);
        menu.open(player);
    }
}
