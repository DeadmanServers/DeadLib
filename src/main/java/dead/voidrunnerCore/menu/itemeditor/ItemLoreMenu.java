package dead.voidrunnerCore.menu.itemeditor;

import dead.voidrunnerCore.VoidrunnerCore;
import dead.voidrunnerCore.chatinputmanager.ChatInputManager;
import dead.voidrunnerCore.chatinputmanager.PendingInput;
import dead.voidrunnerCore.menu.declaration.AbsMenu;
import dead.voidrunnerCore.util.LoreBuilder;
import dead.voidrunnerCore.util.NBT;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

public class ItemLoreMenu extends AbsMenu {

    private ItemStack selectedItem;
    private List<Component> lore;

    public ItemLoreMenu(ItemStack itemStack) {
        this.selectedItem = itemStack.clone();
    }

    @Override
    public Inventory build() {
        this.inventory = Bukkit.createInventory(this, 27, MiniMessage.miniMessage().deserialize("<black>Ability Description Editor"));
        inventory.setContents(glassContents(27));
        inventory.setItem(18, backButton());

        this.lore = LoreBuilder.getLoreComponents(selectedItem);

        for (int i = 0; i < 18; i++) {
            if (lore.isEmpty() || lore.size() <= i) {
                inventory.setItem(i, emptyLoreButton());
                continue;
            }
            Component component = lore.get(i);
            if (component != null) {
                ItemStack icon = descriptionFilledButton(component, i);

                inventory.setItem(i, icon);
            }

        }

        return inventory;
    }


    @Override
    public void handleClick(InventoryClickEvent event) {

        event.setCancelled(true);
        if (!(event.getWhoClicked() instanceof Player player)) {
            return;
        }

        ItemStack item = event.getCurrentItem();
        if (item == null) {
            return;
        }

        UUID id = player.getUniqueId();
        if (ChatInputManager.isAwaiting(id)) {
            ChatInputManager.cancel(id);
            return;
        }

        ItemStack clone = item.clone();

        if (isBackButton(clone)) {

            ItemMeta meta = clone.getItemMeta();
            meta.lore(lore);
            clone.setItemMeta(meta);

            new ItemEditorMenu(clone).open(player);
            return;
        }

        int loreID = NBT.getInt(clone, "loreID", -1);

        ClickType click = event.getClick();


        /* Empty Description ID

        LEFT_CLICK - Create New
        MIDDLE_CLICK - Insert Empty Line

         */

        if (loreID == -1) {
            int size = lore.size();
            if (click == ClickType.LEFT) {

                Consumer<String> consumer = s -> {
                    lore.add(size, MiniMessage.miniMessage().deserialize("<i:false>" + s));
                    Bukkit.getScheduler().runTask(VoidrunnerCore.INSTANCE, () -> {
                        open(player);
                    });
                };

                PendingInput input = new PendingInput(consumer, "<red>You have cancelled adding lore.");
                ChatInputManager.awaitInput(id, input);
                player.closeInventory();
                player.sendRichMessage("");
                player.sendRichMessage("<green>Adding lore: <white>Type out a line of text to add.");
                return;
            }
            if (click == ClickType.MIDDLE) {
                lore.add(size, Component.text(" "));
                open(player);
            }
        }

        /* Existing Description ID

        LEFT_CLICK - Edit
        RIGHT_CLICK - Remove
        MIDDLE_CLICK - Set to Empty Line

         */
        if (loreID >= 0) {
            if (click == ClickType.LEFT) {

                Consumer<String> consumer = s -> {
                    if (lore == null || lore.isEmpty() || lore.size() <= loreID) {
                        return;
                    }
                    lore.add(loreID, MiniMessage.miniMessage().deserialize("<i:false>" + s));
                    Bukkit.getScheduler().runTask(VoidrunnerCore.INSTANCE, t -> {
                        open(player);
                    });
                };

                PendingInput input = new PendingInput(consumer, "<red>You have cancelled setting a lore line.");
                ChatInputManager.awaitInput(player.getUniqueId(), input);
                player.closeInventory();
                player.sendRichMessage("");
                player.sendRichMessage("<green>Changing lore: <white>Type out a line of text.");
                return;
            }
            if (click == ClickType.RIGHT) {
                lore.remove(loreID);
                open(player);
            }
            if (click == ClickType.MIDDLE) {
                lore.set(loreID, Component.text(" "));
                open(player);
            }
        }

    }
}
