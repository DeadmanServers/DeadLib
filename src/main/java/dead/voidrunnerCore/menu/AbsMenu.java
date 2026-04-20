package dead.voidrunnerCore.menu;

import dead.voidrunnerCore.api.ItemBuilder;
import dead.voidrunnerCore.api.LoreBuilder;
import dead.voidrunnerCore.util.MyMini;
import dead.voidrunnerCore.api.NBT;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public abstract class AbsMenu implements InventoryHolder {

    protected Inventory inventory;

    public static ItemStack next = ItemBuilder.create(Material.ARROW, "<green><bold>Next").build();
    public static ItemStack back = ItemBuilder.create(Material.ARROW, "<red><bold>Back").build();
    public static ItemStack save = ItemBuilder.create(Material.EMERALD, "<green><bold>Save").build();
    public static ItemStack innerBack = ItemBuilder.create(Material.ARROW, "<gray><b><<").build();
    public static ItemStack innerNext = ItemBuilder.create(Material.ARROW, "<gray><b>>>").build();
    public static ItemStack close = ItemBuilder.create(Material.BARRIER, "<red><bold>Close").build();
    public static ItemStack empty = ItemBuilder.create(Material.STONE_BUTTON, "<grey><i:true>EMPTY").build();
    public static ItemStack glass = ItemBuilder.glass();
    public static ItemStack brokenData = ItemBuilder.create(Material.BARRIER, "<red><bold>BROKEN DATA").build();
    static {
        NBT.setString(next, "next", "next");
        NBT.setString(back, "back", "back");
        NBT.setString(save, "save", "save");
        NBT.setString(innerBack, "innerBack", "innerBack");
        NBT.setString(innerNext, "innerNext", "innerNext");
        NBT.setString(close, "close", "close");
        NBT.setString(empty, "empty", "empty");
        NBT.setString(brokenData, "brokenData", "brokenData");
        NBT.setString(glass, "glass", "glass");
        ItemMeta glassMeta  = glass.getItemMeta();
        glassMeta.setHideTooltip(true);
        glass.setItemMeta(glassMeta);
    }
    public static ItemStack empty() {
        return empty.clone();
    }
    public static ItemStack backButton() {
        return back.clone();
    }
    public static ItemStack innerBackButton() {
        return innerBack.clone();
    }
    public static ItemStack innerNextButton() {
        return innerNext.clone();
    }
    public static ItemStack saveButton() {
        return save.clone();
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

    public static boolean isEmptyButton(ItemStack item) {
        return NBT.has(item, "empty");
    }

    public static boolean isBackButton(ItemStack item) {
        return NBT.has(item, "back");
    }
    public static boolean isInnerBackButton(ItemStack item) {
        return NBT.has(item, "innerBack");
    }
    public static boolean isInnerNextButton(ItemStack item) {
        return NBT.has(item, "innerNext");
    }
    public static boolean isSaveButton(ItemStack item) {
        return NBT.has(item, "save");
    }
    public static boolean isNextButton(ItemStack item) {
        return NBT.has(item, "next");
    }

    public static boolean isLoreButton(ItemStack item) {
        return NBT.has(item, "loreID");
    }

    public static boolean isGlass(ItemStack item) {
        return NBT.has(item, "glass");
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

    public static ItemStack descriptionFilledButton(String description, int index) {
        ItemStack textButton = LoreBuilder.create(Material.PAPER).name(MyMini.normalize(description))
                .blank()
                .line("<gray>Left-Click: <green>Create new")
                .line("<gray>Middle-Click: <green>Add blank line")
                .line("<gray>Right-Click: <red>Remove line")
                .buildItem();

        NBT.setInt(textButton, "loreID", index);
        return textButton;
    }

    public static ItemStack emptyLoreButton() {
        return LoreBuilder.create(empty.clone())
                .blank()
                .line("<gray>Left-Click: <green>Create new")
                .line("<gray>Middle-Click: <green>Add blank line")
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
