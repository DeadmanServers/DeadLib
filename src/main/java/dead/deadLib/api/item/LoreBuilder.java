package dead.deadLib.api.item;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class LoreBuilder {

    private final List<Component> loreList = new ArrayList<>();
    private String name = null;
    private Material material = null;
    private ItemStack item = null;

    public static LoreBuilder create() {
        return new LoreBuilder();
    }
    public static LoreBuilder create(Component component) {
        LoreBuilder loreBuilder = new LoreBuilder();
        loreBuilder.loreList.add(component);
        return loreBuilder;
    }
    public static LoreBuilder create(List<Component> loreList) {
        LoreBuilder loreBuilder = new LoreBuilder();
        loreBuilder.loreList.addAll(loreList);
        return loreBuilder;
    }
    public static LoreBuilder create(ItemStack itemStack) {
        LoreBuilder loreBuilder = new LoreBuilder();
        loreBuilder.item = itemStack;
        loreBuilder.material = itemStack.getType();
        ItemMeta meta = itemStack.getItemMeta();
        if (meta == null) return loreBuilder;
        List<Component> itemLore = meta.lore();
        if (itemLore != null && !itemLore.isEmpty()) {
            loreBuilder.loreList.addAll(itemLore);
        }
        return loreBuilder;
    }
    public static LoreBuilder create(Material material) {
        LoreBuilder loreBuilder = new LoreBuilder();
        loreBuilder.material = material;
        return loreBuilder;
    }

    public static LoreBuilder createOrDefault(ItemStack itemStack, Material material) {
        LoreBuilder loreBuilder = new LoreBuilder();
        if (itemStack != null && !itemStack.getType().isAir()) {
            loreBuilder.item = itemStack;
        } else {
            loreBuilder.material = material;
        }
        return loreBuilder;
    }

    public LoreBuilder line(String line) {
        loreList.add(MiniMessage.miniMessage().deserialize("<i:false>" + line));
        return this;
    }
    public LoreBuilder line(Component component) {
        loreList.add(component);
        return this;
    }
    public LoreBuilder line(int index, Component component) {
        loreList.add(index, component);
        return this;
    }
    public LoreBuilder lines(List<String> lines) {
        for (String line : lines) {
            loreList.add(MiniMessage.miniMessage().deserialize("<i:false>" + line));
        }
        return this;
    }
    public LoreBuilder components(List<Component> components) {
        loreList.addAll(components);
        return this;
    }
    public LoreBuilder raw(String raw) {
        loreList.add(Component.text(raw));
        return this;
    }
    public LoreBuilder blank()  {
        loreList.add(Component.empty());
        return this;
    }
    public LoreBuilder name(String name) {
        this.name = name;
        return this;
    }
    public LoreBuilder material(Material material) {
        this.material = material;
        return this;
    }
    public void applyTo(ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null) {
            return;
        }
        itemMeta.lore(loreList);
        if (name != null) {
            itemMeta.displayName(MiniMessage.miniMessage().deserialize("<i:false>" + name));
        }

        itemStack.setItemMeta(itemMeta);
    }
    public void appendTo(ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null) {
            return;
        }
        List<Component> newLore = new ArrayList<>();
        List<Component> lore = itemMeta.lore();
        if (lore != null && !lore.isEmpty()) {
            newLore.addAll(lore);
        }
        newLore.addAll(loreList);
        itemMeta.lore(newLore);
        if (name != null) {
            itemMeta.displayName(MiniMessage.miniMessage().deserialize("<i:false>" + name));
        }

        itemStack.setItemMeta(itemMeta);
    }
    public List<Component> build() {
        return loreList;
    }
    public List<String> buildString() {
        List<String> loreString = new ArrayList<>();
        for (Component component : loreList) {
            loreString.add(MiniMessage.miniMessage().serialize(component));
        }
        return loreString;
    }
    public ItemStack buildItem() {
        if (item == null && material == null) {
            return null;
        }

        ItemStack itemStack;
        if (item != null) {
            itemStack = item;
        } else {
            itemStack = new ItemStack(material);
        }
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta != null) {
            itemMeta.lore(loreList);
            if (name != null) {
                itemMeta.displayName(MiniMessage.miniMessage().deserialize("<i:false>" + name));
            }
            itemStack.setItemMeta(itemMeta);
        }
        return itemStack;
    }
    public static List<Component> getLoreComponents(ItemStack itemStack) {
        List<Component> loreList = new ArrayList<>();
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null) return loreList;
        List<Component> lore = itemMeta.lore();
        if (lore != null && !lore.isEmpty()) {
            loreList.addAll(lore);
        }
        return loreList;
    }

    public static List<String> getLoreStrings(ItemStack itemStack) {
        List<String> loreStrings = new ArrayList<>();
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null) return loreStrings;
        List<Component> lore = itemMeta.lore();
        if (lore != null && !lore.isEmpty()) {
            for (Component component : lore) {
                loreStrings.add(MiniMessage.miniMessage().serialize(component));
            }
        }
        return loreStrings;
    }
}
