package dead.deadLib.api.item;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemBuilder {

    private Material material;
    private Component displayName;
    private List<Component> lore = new ArrayList<>();

    public static ItemBuilder create() {
        return new ItemBuilder();
    }
    public static ItemBuilder create(Material material) {
        ItemBuilder itemBuilder = new ItemBuilder();
        itemBuilder.material = material;
        return itemBuilder;
    }
    public static ItemBuilder create(Material material, String displayName) {
        ItemBuilder itemBuilder = create(material);
        itemBuilder.displayName = MiniMessage.miniMessage().deserialize("<i:false>" + displayName);
        return itemBuilder;
    }
    public static ItemBuilder create(Material material, String displayName, String lore) {
        ItemBuilder itemBuilder = create(material, displayName);
        itemBuilder.lore.add(MiniMessage.miniMessage().deserialize("<i:false>" + lore));
        return itemBuilder;
    }
    public static ItemBuilder create(Material material, String displayName, List<String> lore) {
        ItemBuilder itemBuilder = create(material, displayName);
        for (String l : lore) {
            itemBuilder.lore.add(MiniMessage.miniMessage().deserialize("<i:false>" + l));
        }
        return itemBuilder;
    }
    public ItemBuilder displayName(String displayName) {
        this.displayName = MiniMessage.miniMessage().deserialize("<i:false>" + displayName);
        return this;
    }
    public ItemBuilder displayName(Component displayName) {
        this.displayName = displayName;
        return this;
    }
    public ItemBuilder loreLine(Component lore) {
        this.lore.add(lore);
        return this;
    }
    public ItemBuilder loreLine(String lore) {
        this.lore.add(MiniMessage.miniMessage().deserialize("<i:false>" + lore));
        return this;
    }
    public ItemBuilder lore(List<Component> lore) {
        this.lore = lore;
        return this;
    }
    public ItemBuilder material(Material material) {
        this.material = material;
        return this;
    }

    public ItemStack build() {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(displayName);
        meta.lore(lore);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack glass() {
        ItemStack item = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta meta = item.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);
        return item;
    }
}
