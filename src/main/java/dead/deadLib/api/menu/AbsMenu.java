package dead.deadLib.api.menu;

import dead.deadLib.DeadLib;
import dead.deadLib.api.item.ItemBuilder;
import dead.deadLib.api.item.LoreBuilder;
import dead.deadLib.api.nbt.NBT;
import dead.deadLib.api.text.MyMini;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public abstract class AbsMenu implements InventoryHolder {

    protected Inventory inventory;
    private static DeadLib plugin;
    private static NBT nbt;

    public static void init(DeadLib plugin) {
        nbt = plugin.getNBT();
    }

    public ItemStack empty() {
        ItemStack empty = LoreBuilder.create(Material.STONE_BUTTON)
                .name("<gray>EMPTY").buildItem();
        nbt.setString(empty, "menu_button", "empty");
        return empty;
    }

    public ItemStack backButton() {
        ItemStack back = ItemBuilder.create(Material.TIPPED_ARROW, "<red>Back").build();
        PotionMeta meta = (PotionMeta) back.getItemMeta();
        meta.setColor(Color.fromRGB(255, 89, 86));
        back.setItemMeta(meta);
        nbt.setString(back, "menu_button", "back");
        return back;
    }

    public ItemStack saveButton() {
        return ItemBuilder.create(Material.EMERALD, "<green>Save").build();
    }

    public ItemStack closeButton() {
        ItemStack close = ItemBuilder.create(Material.BARRIER, "<red>Close").build();
        nbt.setString(close, "menu_button", "close");
        return close;
    }

    public ItemStack nextButton() {
        ItemStack next = ItemBuilder.create(Material.TIPPED_ARROW, "<green>Next").build();
        PotionMeta meta = (PotionMeta) next.getItemMeta();
        meta.setColor(Color.fromRGB(91, 255, 86));
        next.setItemMeta(meta);
        nbt.setString(next, "menu_button", "next");
        return next;
    }

    public ItemStack glass() {
        ItemStack glass = ItemBuilder.glass();

        nbt.setString(glass, "menu_button", "glass");

        ItemMeta glassMeta = glass.getItemMeta();
        glassMeta.setHideTooltip(true);
        glass.setItemMeta(glassMeta);
        return glass;
    }

    public boolean isEmptyButton(ItemStack item) {
        return (nbt.getString(item, "menu_button").equals("empty"));
    }

    public boolean isBackButton(ItemStack item) {
        return nbt.getString(item, "menu_button").equals("back");
    }

    public boolean isSaveButton(ItemStack item) {
        return nbt.getString(item, "menu_button").equals("save");
    }

    public boolean isNextButton(ItemStack item) {
        return nbt.getString(item, "menu_button").equals("next");
    }

    public boolean isLoreButton(ItemStack item) {
        return nbt.getString(item, "menu_button").equals("loreID");
    }

    public boolean isGlass(ItemStack item) {
        return nbt.getString(item, "menu_button").equals("glass");
    }

    public ItemStack brokenData() {
        return ItemBuilder.create(Material.BARRIER, "<red>BROKEN DATA").build();
    }

    public ItemStack descriptionFilledButton(String description, int index) {
        ItemStack textButton = LoreBuilder.create(Material.PAPER).name(MyMini.normalize(description))
                .blank()
                .line("<gray>Left-Click: <green>Create new")
                .line("<gray>Middle-Click: <green>Add blank line")
                .line("<gray>Right-Click: <red>Remove line")
                .buildItem();

        nbt.setInt(textButton, "loreID", index);
        return textButton;
    }

    public ItemStack emptyLoreButton() {
        return LoreBuilder.create(empty())
                .blank()
                .line("<gray>Left-Click: <green>Create new")
                .line("<gray>Middle-Click: <green>Add blank line")
                .buildItem();
    }

    public ItemStack[] glassContents(int inventorySize) {
        ItemStack[] glassContents = new ItemStack[inventorySize];
        Arrays.fill(glassContents, glass());
        return glassContents;
    }
    public ItemStack[] fillContents(int inventorySize, Material material) {
        ItemStack[] fillContents = new ItemStack[inventorySize];
        Arrays.fill(fillContents, ItemStack.of(material));
        return fillContents;
    }

    public abstract Inventory build();

    public abstract void handleClick(InventoryClickEvent event);

    public void open(Player player) {
        player.openInventory(build());
    }

    public void syncOpen(Player player) {
        Bukkit.getScheduler().runTask(plugin, () ->
                open(player)
        );
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inventory;
    }
}
