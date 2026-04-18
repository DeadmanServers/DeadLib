package dead.voidrunnerCore.commands;

import dead.voidrunnerCore.VoidrunnerCore;
import dead.voidrunnerCore.menu.itemeditor.ItemEditorMenu;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ItemEditorCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {

        if (!(sender instanceof Player player)) {
            VoidrunnerCore.INSTANCE.getLogger().warning("How do you expect to see the inventory?");
            return true;
        }

        new ItemEditorMenu().open(player);

        return false;
    }
}
