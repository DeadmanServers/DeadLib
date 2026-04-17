package dead.voidrunnerCore.menu;

import dead.voidrunnerCore.builders.ItemBuilder;
import dead.voidrunnerCore.util.LoreBuilder;
import dead.voidrunnerCore.util.NBT;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public abstract class AbsMenu implements InventoryHolder {

    protected Inventory inventory;

    public static ItemStack next = ItemBuilder.create(Material.ARROW, "<green><bold>Next").build();
    public static ItemStack back = ItemBuilder.create(Material.ARROW, "<red><bold>Back").build();
    public static ItemStack close = ItemBuilder.create(Material.BARRIER, "<red><bold>Close").build();
    public static ItemStack empty = ItemBuilder.create(Material.STONE_BUTTON, "<grey><i:true>EMPTY").build();
    public static ItemStack glass = ItemBuilder.glass();
    public static ItemStack brokenData = ItemBuilder.create(Material.BARRIER, "<red><bold>BROKEN DATA").build();
    static {
        NBT.tag(next, "menu", "next");
        NBT.tag(back, "menu", "back");
        NBT.tag(close, "menu", "close");
        NBT.tag(empty, "menu", "empty");
        NBT.tag(brokenData, "menu", "brokenData");
        NBT.tag(glass, "menu", "glass");
    }
    public static ItemStack empty() {
        return empty.clone();
    }
    public static ItemStack backButton() {
        return back.clone();
    }
    public static ItemStack closeButton() {
        return close.clone();
    }
    public static ItemStack nextButton() {
        return next.clone();
    }
    public static ItemStack glass() {
        return glass.clone();
    }
    public static ItemStack brokenData() {
        return brokenData.clone();
    }
    public static ItemStack freeSpaceButton() {
        return LoreBuilder.create(empty())
                .blank()
                .line(" <dark_gray>• <green>Click to create new")
                .buildItem();
    }

    public static ItemStack[] glassContents(int inventorySize) {
        ItemStack[] glassContents = new ItemStack[inventorySize];
        Arrays.fill(glassContents, glass);
        return glassContents;
    }

    public abstract Inventory build();
    public abstract void handleClick(InventoryClickEvent event);
    public void open(Player player) {player.openInventory(build());}

    @Override
    public @NotNull Inventory getInventory() { return inventory; }
}
