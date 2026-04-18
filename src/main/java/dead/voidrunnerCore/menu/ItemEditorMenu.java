package dead.voidrunnerCore.menu;

import dead.voidrunnerCore.VoidrunnerCore;
import dead.voidrunnerCore.builders.ItemBuilder;
import dead.voidrunnerCore.chatinputmanager.ChatInputManager;
import dead.voidrunnerCore.chatinputmanager.PendingInput;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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
        this.inventory = Bukkit.createInventory(this, 45, MiniMessage.miniMessage().deserialize("<black>Item Editor"));

        inventory.setContents(glassContents(45));

        inventory.setItem(4, ItemBuilder.create(Material.OAK_SIGN, "<green>Change Name").build());
        inventory.setItem(11, ItemBuilder.create(Material.ITEM_FRAME, "<green>Change Material").build());
        inventory.setItem(15, ItemBuilder.create(Material.WRITABLE_BOOK, "<green>Edit Lore").build());
        inventory.setItem(29, ItemBuilder.create(Material.ENCHANTING_TABLE, "<green>Edit Enchantments").build());
        inventory.setItem(33, ItemBuilder.create(Material.BEDROCK, "<green>Toggle Unbreakable").build());
        inventory.setItem(40, ItemBuilder.create(Material.EMERALD, "<green>Save to file").build());
        if (selectedItem == null) {
            inventory.setItem(22, ItemBuilder.create(Material.STONE_BUTTON, "<gray>Select an item from your inventory").build());
        } else {
            inventory.setItem(22, selectedItem);
        }
        return inventory;

    }

    @Override
    public void handleClick(InventoryClickEvent event) {
        event.setCancelled(true);
        if (!(event.getWhoClicked() instanceof Player player)) return;

        if (event.getRawSlot() >= inventory.getSize()) {
            ItemStack item = event.getCurrentItem();
            if (item != null && item.getType() != Material.AIR) {
                new ItemEditorMenu(item).open(player);
                return;
            }
        }

        switch (event.getRawSlot()) {
            case 4 -> {
                if (selectedItem == null) {
                    player.sendRichMessage("<red>Select an item from your inventory");
                    return;
                }
                UUID playerUUID = player.getUniqueId();
                Consumer<String> consumer = s -> {
                    ItemStack itemEdit = ChatInputManager.getItemEdit(playerUUID);
                    if (itemEdit == null) {
                        ChatInputManager.cancel(playerUUID);
                        return;
                    }
                    ItemMeta itemMeta = itemEdit.getItemMeta();
                    itemMeta.displayName(MiniMessage.miniMessage().deserialize(s));
                    itemEdit.setItemMeta(itemMeta);
                    player.sendRichMessage("<green><b>SUCCESS!</b> <white>You have set a new name for the item.");
                    Bukkit.getScheduler().runTask(VoidrunnerCore.INSTANCE, () -> {
                        new ItemEditorMenu(itemEdit).open(player);
                    });
                };

                PendingInput input = new PendingInput(consumer, "<red>You have cancelled setting a new name");
                ChatInputManager.awaitInput(playerUUID, input);
                ChatInputManager.awaitItemEdit(playerUUID, selectedItem);
            }
        }
    }
}
