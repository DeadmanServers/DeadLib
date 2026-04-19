package dead.voidrunnerCore.itemstorage;

import dead.voidrunnerCore.api.LoreBuilder;
import dead.voidrunnerCore.api.NBT;
import dead.voidrunnerCore.menu.AbsMenu;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ItemStorageViewer extends AbsMenu {

    int page = 0;
    private int mainMenuPage = 0;
    private String category;

    public void setMainMenuPage(int mainMenuPage) {
        this.mainMenuPage = mainMenuPage;
    }
    public void setCategory(String category) {
        this.category = category;
    }


    @Override
    public Inventory build() {
        this.inventory = Bukkit.createInventory(null, 54, "Items");

        inventory.setContents(glassContents(54));

        inventory.setItem(45, backButton());
        int index = page * 28;

        List<UUID> itemIDs = ItemData.getItemIDs(category);
        if (index + 28 < itemIDs.size()) {
            inventory.setItem(53, nextButton());
        }

        for (int slot = 10; slot <= 43; slot++) {
            if (slot == 17 || slot == 26 || slot == 35) {
                slot += 2;
            }
            if (index >= itemIDs.size()) {
                index++;
                continue;
            }
            UUID itemID = itemIDs.get(index);
            ItemStack item = ItemData.getItemFromCategory(category, itemID);

            if (item.getType() == Material.AIR) {
                index++;
                continue;
            }
            ItemStack clone = LoreBuilder.create(item.clone())
                    .blank()
                    .line("<green>Left-Click to get item")
                    .line("<green>Shift-Left Click to edit")
                    .line("<yellow>Middle-Click to get ID")
                    .line("<red>Right-Click to remove").buildItem();
            NBT.setString(clone, "itemID", itemID.toString());
            inventory.setItem(slot, clone);
        }
        return null;
    }

    @Override
    public void handleClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;
        ItemStack item = event.getCurrentItem();
        if ( item == null || item.getType().isAir() || isGlass(item)) return;

        if (isBackButton(item)) {
            if (page-1 < 0) {
                ItemStorageMainMenu menu = new ItemStorageMainMenu();
                menu.setPage(mainMenuPage);
                menu.open(player);
                return;
            }
            page--;
            open(player);
        }
        if (isNextButton(item)) {
            page++;
            open(player);
        }
        if (!NBT.has(item, "itemID")) return;
        String itemID = NBT.getString(item, "itemID");

        ClickType click = event.getClick();
        player.sendRichMessage("Working on handling your " + click);

    }
}
