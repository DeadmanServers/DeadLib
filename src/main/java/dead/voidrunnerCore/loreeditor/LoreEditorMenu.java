package dead.voidrunnerCore.loreeditor;

import dead.voidrunnerCore.VoidrunnerCore;
import dead.voidrunnerCore.chat.ChatInputManager;
import dead.voidrunnerCore.chat.PendingInput;
import dead.voidrunnerCore.itemeditor.ItemEditorMenu;
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

public class LoreEditorMenu extends AbsMenu {

    String title;
    List<String> lines;
    Consumer<List<String>> onSave;
    Runnable onClose;

    public LoreEditorMenu(String title, List<String> initialLines, Consumer<List<String>> onSave, Runnable onCancel) {
        this.title = title;
        this.lines = new ArrayList<>(initialLines);
        this.onSave = onSave;
        this.onClose = onCancel;
    }

    @Override
    public Inventory build() {
        this.inventory = Bukkit.createInventory(this, 27, MyMini.normalizeComp(title));
        inventory.setContents(glassContents(27));
        inventory.setItem(18, backButton());
        inventory.setItem(19, saveButton());

        for (int i = 0; i < 18; i++) {
            if (lines.isEmpty() || lines.size() <= i) {
                inventory.setItem(i, emptyLoreButton());
                continue;
            }
            String line = lines.get(i);
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
            onClose.run();
            return;
        }
        if (isSaveButton(clone)) {
            onSave.accept(lines);
            return;
        }

        int loreID = NBT.getInt(clone, "loreID", -1);

        ClickType click = event.getClick();


        /* Empty Description ID

        LEFT_CLICK - Create New
        MIDDLE_CLICK - Insert Empty Line

         */

        if (loreID == -1) {
            int size = lines.size();
            if (click == ClickType.LEFT) {
                Consumer<String> consumer = s -> {
                    lines.add(size, MyMini.normalize(s));
                    Bukkit.getScheduler().runTask(VoidrunnerCore.INSTANCE, () -> {
                        new LoreEditorMenu(title, lines, onSave, onClose).open(player);
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
                lines.add(size," ");
                new LoreEditorMenu(title, lines, onSave, onClose).open(player);
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
                    if (lines == null || lines.isEmpty() || lines.size() <= loreID) {
                        return;
                    }
                    lines.set(loreID, MyMini.normalize(s));
                    Bukkit.getScheduler().runTask(VoidrunnerCore.INSTANCE, t -> {
                        new LoreEditorMenu(title, lines, onSave, onClose).open(player);
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
                lines.remove(loreID);
                new LoreEditorMenu(title, lines, onSave, onClose).open(player);
            }
            if (click == ClickType.MIDDLE) {
                lines.set(loreID, " ");
                new LoreEditorMenu(title, lines, onSave, onClose).open(player);
            }
        }

    }
}
