package dead.deadLib.api.menu;

import dead.deadLib.api.item.ItemBuilder;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;

public final class MenuIcons {
    public static ItemStack back() {
        ItemStack back = ItemBuilder.create(Material.TIPPED_ARROW, "<red>Back").build();
        PotionMeta meta = (PotionMeta) back.getItemMeta();
        meta.setColor(Color.fromRGB(255, 89, 86));
        back.setItemMeta(meta);
        return back;
    }
    public static ItemStack next() {
        ItemStack next = ItemBuilder.create(Material.TIPPED_ARROW, "<green>Next").build();
        PotionMeta meta = (PotionMeta) next.getItemMeta();
        meta.setColor(Color.fromRGB(91, 255, 86));
        next.setItemMeta(meta);
        return next;
    }
    public static ItemStack close() {
        return ItemBuilder.create(Material.BARRIER, "<red>Close").build();
    }
    public static ItemStack glass() {
        ItemStack glass = ItemBuilder.glass();
        ItemMeta glassMeta = glass.getItemMeta();
        glassMeta.setHideTooltip(true);
        glass.setItemMeta(glassMeta);
        return glass;
    }
    public static ItemStack saveButton() {
        return ItemBuilder.create(Material.EMERALD, "<green>Save").build();
    }
    public static ItemStack empty() {
        return ItemBuilder.create(Material.STONE_BUTTON, "<gray>EMPTY").build();
    }
}
