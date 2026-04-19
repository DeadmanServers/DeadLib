package dead.voidrunnerCore.itemeditor;

import dead.voidrunnerCore.api.ItemBuilder;
import dead.voidrunnerCore.api.LoreBuilder;
import dead.voidrunnerCore.api.NBT;
import dead.voidrunnerCore.menu.AbsMenu;
import dead.voidrunnerCore.util.MyMini;
import dead.voidrunnerCore.util.Utilities;
import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ItemEnchantMenu extends AbsMenu {

    ItemStack selectedItem;
    int page;
    int level;
    boolean activeOnlyFilter = false;
    private final Registry<Enchantment> registry = RegistryAccess.registryAccess().getRegistry(RegistryKey.ENCHANTMENT);

    public ItemEnchantMenu(ItemStack selectedItem, int page, int level, boolean activeOnlyFilter) {
        this.selectedItem = selectedItem;
        this.page = page;
        this.level = level;
    }

    public ItemEnchantMenu(ItemStack selectedItem, int page, int level) {
        this.selectedItem = selectedItem;
        this.page = page;
        this.level = level;
    }

    public ItemEnchantMenu(ItemStack selectedItem, int page) {
        this.selectedItem = selectedItem;
        this.page = page;
        this.level = 1;
    }

    public ItemEnchantMenu(ItemStack selectedItem) {
        this.selectedItem = selectedItem;
        this.page = 0;
        this.level = 1;
    }


    @Override
    public Inventory build() {
        this.inventory = Bukkit.createInventory(this, 54, MyMini.normalize("<black>Enchantment Menu"));

        inventory.setContents(glassContents(54));

        ItemBuilder filterbutton = ItemBuilder.create(Material.HOPPER);
        if (activeOnlyFilter) {
            filterbutton.displayName("<yellow>Click to view all enchantments");
        } else {
            filterbutton.displayName("<green>Click to view active enchantments");
        }
        inventory.setItem(4, filterbutton.build());
        inventory.setItem(45, backButton());
        if (page > 0) {
            inventory.setItem(46, innerBackButton());
        }
        inventory.setItem(47, ItemBuilder.create(Material.REDSTONE).displayName("<yellow>-1 <gray>level").build());
        inventory.setItem(49, ItemBuilder.create(Material.EXPERIENCE_BOTTLE).displayName("<gray>Current Level: <light_purple>" + Utilities.toRoman(level) + "<dark_gray>(" + level + ")").build());
        inventory.setItem(51, ItemBuilder.create(Material.REDSTONE).displayName("<green>+1 <gray>level").build());


        int index = page * 28;



        List<Enchantment> allEnchants = new ArrayList<>();
        registry.forEach(allEnchants::add);

        List<Enchantment> activeEnchants = new ArrayList<>();
        if (activeOnlyFilter) {
            for (Enchantment enchantment : registry) {
                if (selectedItem.getItemMeta().hasEnchant(enchantment)) {
                    activeEnchants.add(enchantment);
                }
            }
            allEnchants = activeEnchants;
        }

        allEnchants.sort(Comparator.comparing(e -> e.getKey().getKey()));

        if (index + 28 < allEnchants.size()) {
            inventory.setItem(52, innerNextButton());
        }


        for (int slot = 10; slot <= 43; ++slot) {
            if (slot == 17 || slot == 26 || slot == 35) {
                slot += 2;
            }
            if (index >= allEnchants.size()) break;
            Enchantment enchantment = allEnchants.get(index);
            if (enchantment == null) {
                index++;
                continue;
            }
            String key = enchantment.getKey().asString();

            LoreBuilder lore = LoreBuilder.create(Material.ENCHANTED_BOOK)
                    .name(MiniMessage.miniMessage().serialize(enchantment.displayName(level)))
                    .blank();
            if (selectedItem.getItemMeta().getEnchantLevel(enchantment) != level) {
                lore.line("<green>Left-Click to add");
            }
            if (selectedItem.getItemMeta().hasEnchant(enchantment)) {
                lore.line("<red>Right-Click to remove");
            }
            ItemStack enchantBook = lore.buildItem();
            NBT.setString(enchantBook, "enchantment", key);
            inventory.setItem(slot, enchantBook);
            index++;
        }
        return inventory;
    }

    @Override
    public void handleClick(InventoryClickEvent event) {

        if (!(event.getWhoClicked() instanceof Player player)) {
            return;
        }

        ItemStack clickedItem = event.getCurrentItem();
        if (clickedItem == null || clickedItem.getType() == Material.AIR || isGlass(clickedItem)) {
            return;
        }

        switch (event.getRawSlot()) {
            case 4 -> {
                new ItemEnchantMenu(selectedItem, page, level, !activeOnlyFilter).open(player);
                return;
            }
            case 45 -> {
                ItemEditorMenu menu = new ItemEditorMenu(selectedItem);
                menu.open(player);
                return;
            }
            case 46 -> {
                page -= 1;
                if (page < 0) page = 0;
                ItemEnchantMenu menu = new ItemEnchantMenu(selectedItem, page, level);
                menu.open(player);
                return;
            }
            case 47 -> {
                level -= 1;
                if (level <= 0) level = 1;
                ItemEnchantMenu menu = new ItemEnchantMenu(selectedItem, page, level);
                menu.open(player);
                return;
            }
            case 51 -> {
                level += 1;
                ItemEnchantMenu menu = new ItemEnchantMenu(selectedItem, page, level);
                menu.open(player);
                return;
            }
            case 52 -> {
                ItemEnchantMenu menu = new ItemEnchantMenu(selectedItem, page + 1, level);
                menu.open(player);
                return;
            }
        }

        NamespacedKey key = null;
        if (NBT.has(clickedItem, "enchantment")) {
            String string = NBT.getString(clickedItem, "enchantment");
            key = NamespacedKey.fromString(string);
        }
        if (key == null)
            return;

        Enchantment enchantment = registry.get(key);
        if (enchantment == null) {
            return;
        }
        player.sendRichMessage(enchantment + " " + level);

        if (event.isLeftClick()) {
            ItemMeta meta = selectedItem.getItemMeta();
            meta.addEnchant(enchantment, level, true);
            selectedItem.setItemMeta(meta);
            new ItemEnchantMenu(selectedItem, page, level).open(player);
        } else if (event.isRightClick()) {
            ItemMeta meta = selectedItem.getItemMeta();
            meta.removeEnchant(enchantment);
            selectedItem.setItemMeta(meta);
            new ItemEnchantMenu(selectedItem, page, level).open(player);
        }

    }
}
