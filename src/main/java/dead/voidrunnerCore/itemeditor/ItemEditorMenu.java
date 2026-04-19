package dead.voidrunnerCore.itemeditor;

import com.google.common.collect.Multimap;
import dead.voidrunnerCore.VoidrunnerCore;
import dead.voidrunnerCore.api.ItemBuilder;
import dead.voidrunnerCore.chat.ChatInputManager;
import dead.voidrunnerCore.chat.PendingInput;
import dead.voidrunnerCore.menu.AbsMenu;
import dead.voidrunnerCore.util.MyMini;
import dead.voidrunnerCore.util.Palette;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;

public class ItemEditorMenu extends AbsMenu {

    private ItemStack selectedItem;

    public ItemEditorMenu() {
        this.selectedItem = null;
    }
    public ItemEditorMenu(ItemStack itemStack) {
        this.selectedItem = itemStack.clone();
    }

    @Override
    public Inventory build() {
        this.inventory = Bukkit.createInventory(this, 45, MiniMessage.miniMessage().deserialize(Palette.GOLD + "Item Editor"));

        inventory.setContents(glassContents(45));

        inventory.setItem(4, ItemBuilder.create(Material.OAK_SIGN, Palette.TEXT_PRIMARY + "Change Name").build());
        inventory.setItem(11, ItemBuilder.create(Material.ITEM_FRAME, Palette.TEXT_PRIMARY + "Change Material").build());
        inventory.setItem(15, ItemBuilder.create(Material.WRITABLE_BOOK, Palette.TEXT_PRIMARY + "Edit Lore").build());
        inventory.setItem(29, ItemBuilder.create(Material.ENCHANTING_TABLE, Palette.TEXT_PRIMARY + "Edit Enchantments").build());
        ItemStack unbreakableIcon = ItemBuilder.create(Material.BEDROCK, Palette.TEXT_PRIMARY + "Toggle Unbreakable", List.of("", Palette.TEXT_SECONDARY + "Currently:" + Palette.SUCCESS + "ENABLED")).build();
        if (selectedItem != null) {
            if (!selectedItem.getItemMeta().isUnbreakable()) {
                unbreakableIcon = ItemBuilder.create(Material.SPONGE, Palette.TEXT_PRIMARY + "Toggle Unbreakable", List.of("", Palette.TEXT_SECONDARY + "Currently:" + Palette.ERROR + "DISABLED")).build();
            }
        }
        inventory.setItem(33, unbreakableIcon);
        inventory.setItem(40, ItemBuilder.create(Material.EMERALD, Palette.SUCCESS + "Save to file").build());
        if (selectedItem == null) {
            inventory.setItem(22, ItemBuilder.create(Material.STONE_BUTTON, "Select an item from your inventory").build());
        } else {
            inventory.setItem(22, selectedItem);
        }
        return inventory;

    }

    @Override
    public void handleClick(InventoryClickEvent event) {

        if (!(event.getWhoClicked() instanceof Player player)) return;

        if (event.getRawSlot() >= inventory.getSize()) {
            ItemStack item = event.getCurrentItem();
            if (item != null && item.getType() != Material.AIR) {
                new ItemEditorMenu(item.asOne()).open(player);
                return;
            }
        }

        if (selectedItem == null) {
            player.sendRichMessage(Palette.ERROR + "Select an item from your inventory.");
            return;
        }

        switch (event.getRawSlot()) {

            case 4 -> {
                UUID playerUUID = player.getUniqueId();
                Consumer<String> consumer = s -> {
                    ItemStack itemEdit = selectedItem;
                    ItemMeta itemMeta = itemEdit.getItemMeta();
                    itemMeta.displayName(MyMini.normalizeComp(s));
                    itemEdit.setItemMeta(itemMeta);
                    player.sendRichMessage(Palette.SUCCESS + "SUCCESS" + Palette.TEXT_PRIMARY + "You have set a new name for the item.");
                    Bukkit.getScheduler().runTask(VoidrunnerCore.INSTANCE, () -> {
                        new ItemEditorMenu(itemEdit).open(player);
                    });
                };

                PendingInput input = new PendingInput(consumer, Palette.ERROR + "You have cancelled setting a new name");
                ChatInputManager.awaitInput(playerUUID, input);
                ChatInputManager.awaitItemEdit(playerUUID, selectedItem);
                player.closeInventory();
                player.sendRichMessage("");
                player.sendRichMessage(Palette.GOLD + "Changing name:" + Palette.TEXT_PRIMARY + "Type a new name" + MyMini.sprite(selectedItem.getType()));
            }
            case 11 -> {
                UUID playerUUID = player.getUniqueId();
                Consumer<String> consumer = s -> {
                    if (!s.equalsIgnoreCase("confirm")) {
                        ChatInputManager.cancel(playerUUID);
                        player.sendRichMessage(Palette.ERROR + "You have cancelled changing the item's material");
                        return;
                    }
                    ItemStack newItem = player.getInventory().getItemInMainHand().clone();
                    if (newItem.getType() == Material.AIR) {
                        ChatInputManager.cancel(playerUUID);
                        player.sendRichMessage(Palette.ERROR + "You have cancelled changing the item's material");
                        return;
                    }
                    ItemMeta oldMeta = selectedItem.getItemMeta();
                    ItemMeta newMeta = newItem.getItemMeta();
                    if (oldMeta == null) {
                        Bukkit.getScheduler().runTask(VoidrunnerCore.INSTANCE, () -> {
                            new ItemEditorMenu(newItem).open(player);
                        });
                        return;
                    }
                    Component oldName = oldMeta.displayName();
                    List<Component> oldLore = oldMeta.lore();
                    PersistentDataContainer oldPDC = oldMeta.getPersistentDataContainer();
                    Set<ItemFlag> oldItemFlags = oldMeta.getItemFlags();
                    Multimap<Attribute, AttributeModifier> oldModifiers = oldMeta.getAttributeModifiers();

                    if (oldName != null) {
                        newMeta.displayName(oldName);
                    }
                    if (oldLore != null) {
                        newMeta.lore(oldLore);
                    }
                    if (!oldPDC.isEmpty()) {
                        oldPDC.copyTo(newMeta.getPersistentDataContainer(), true);
                    }
                    if (oldItemFlags != null) {
                        for (ItemFlag oldFlag : oldItemFlags) {
                            newMeta.addItemFlags(oldFlag);
                        }
                    }
                    if (oldModifiers != null) {
                        newMeta.setAttributeModifiers(oldModifiers);
                    }
                    Bukkit.getScheduler().runTask(VoidrunnerCore.INSTANCE, () -> {
                        newItem.setItemMeta(newMeta);
                        new ItemEditorMenu(newItem).open(player);
                    });
                };

                PendingInput input = new PendingInput(consumer, Palette.ERROR + "You have cancelled setting a new material");
                ChatInputManager.awaitInput(playerUUID, input);
                ChatInputManager.awaitItemEdit(playerUUID, selectedItem);
                player.closeInventory();
                player.sendRichMessage("");
                player.sendRichMessage(Palette.SUCCESS + "Changing material: " + Palette.TEXT_PRIMARY + "Type " + Palette.SUCCESS + "confirm " + Palette.TEXT_PRIMARY + "to select your held item, or " + Palette.ERROR + "cancel " + Palette.TEXT_PRIMARY + "to go back.");
            }
            case 15 -> {
                if (selectedItem == null) return;
                new ItemLoreMenu(selectedItem).open(player);
            }

            case 22 -> {
                if (selectedItem == null || selectedItem.getType() == Material.AIR) return;
                ItemStack item = event.getCurrentItem();
                if (item == null || item.getType() == Material.AIR) return;
                player.give(selectedItem);
                new ItemEditorMenu().open(player);
            }
            case 29 -> {
                ItemEnchantMenu menu = new ItemEnchantMenu(selectedItem);
                menu.open(player);
            }
            case 33 -> {
                if (selectedItem == null || selectedItem.getType() == Material.AIR) return;
                ItemMeta itemMeta = selectedItem.getItemMeta();
                if (itemMeta.isUnbreakable()) {
                    itemMeta.setUnbreakable(false);
                } else {
                    itemMeta.setUnbreakable(true);
                }
                selectedItem.setItemMeta(itemMeta);
                new ItemEditorMenu(selectedItem).open(player);
            }
        }

    }
}
