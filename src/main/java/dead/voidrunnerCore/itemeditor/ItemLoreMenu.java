package dead.voidrunnerCore.itemeditor;

import dead.voidrunnerCore.VoidrunnerCore;
import dead.voidrunnerCore.chat.ChatInputManager;
import dead.voidrunnerCore.chat.PendingInput;
import dead.voidrunnerCore.menu.AbsMenu;
import dead.voidrunnerCore.api.LoreBuilder;
import dead.voidrunnerCore.util.MyMini;
import dead.voidrunnerCore.api.NBT;
import dead.voidrunnerCore.util.Palette;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

public class ItemLoreMenu extends AbsMenu {

    private ItemStack selectedItem;
    private List<String> lore;

    public ItemLoreMenu(ItemStack itemStack) {
        this.selectedItem = itemStack.clone();
    }
    public ItemLoreMenu(ItemStack itemStack, List<String> lore) {
        this.selectedItem = itemStack.clone();
        this.lore = lore;
    }

    @Override
    public Inventory build() {
        this.inventory = Bukkit.createInventory(this, 27, MiniMessage.miniMessage().deserialize(Palette.GOLD + "Item Lore Editor"));
        inventory.setContents(glassContents(27));
        inventory.setItem(18, backButton());

        if (lore == null) {
            lore = (LoreBuilder.getLoreStrings(selectedItem));
        }

        for (int i = 0; i < 18; i++) {
            if (lore.isEmpty() || lore.size() <= i) {
                inventory.setItem(i, emptyLoreButton());
                continue;
            }
            String line = lore.get(i);
            if (line != null) {
                ItemStack icon = descriptionFilledButton(line, i);

                inventory.setItem(i, icon);
            }

        }

        return inventory;
    }


    @Override
    public void handleClick(InventoryClickEvent event) {

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

            ItemMeta meta = selectedItem.getItemMeta();
            meta.lore(MyMini.normalizeComp(lore));
            selectedItem.setItemMeta(meta);

            new ItemEditorMenu(selectedItem).open(player);
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
                    lore.add(size, MyMini.normalize(s));
                    player.sendMessage(MiniMessage.miniMessage().deserialize("<i:false>" ));
                    Bukkit.getScheduler().runTask(VoidrunnerCore.INSTANCE, () -> {
                        new ItemLoreMenu(selectedItem, lore).open(player);
                    });
                };

                PendingInput input = new PendingInput(consumer, Palette.ERROR + "You have cancelled adding lore.");
                ChatInputManager.awaitInput(id, input);
                player.closeInventory();
                player.sendRichMessage("");
                player.sendRichMessage(Palette.SUCCESS + "Adding lore: " + Palette.TEXT_PRIMARY + "Type out a line of text to add.");
                return;
            }
            if (click == ClickType.MIDDLE) {
                lore.add(size," ");
                new ItemLoreMenu(selectedItem, lore).open(player);
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
                    lore.set(loreID, MyMini.normalize(s));
                    Bukkit.getScheduler().runTask(VoidrunnerCore.INSTANCE, t -> {
                        new ItemLoreMenu(selectedItem, lore).open(player);
                    });
                };

                PendingInput input = new PendingInput(consumer, Palette.ERROR + "You have cancelled setting a lore line.");
                ChatInputManager.awaitInput(player.getUniqueId(), input);
                player.closeInventory();
                player.sendRichMessage("");
                player.sendRichMessage(Palette.SUCCESS + "Changing lore " + Palette.TEXT_PRIMARY + "Type out a line of text.");
                return;
            }
            if (click == ClickType.RIGHT) {
                lore.remove(loreID);
                new ItemLoreMenu(selectedItem, lore).open(player);
            }
            if (click == ClickType.MIDDLE) {
                lore.set(loreID, " ");
                new ItemLoreMenu(selectedItem, lore).open(player);
            }
        }

    }
}
