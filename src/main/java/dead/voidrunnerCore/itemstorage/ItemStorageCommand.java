package dead.voidrunnerCore.itemstorage;

import dead.voidrunnerCore.util.Palette;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ItemStorageCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player player)) return true;

        if (args.length < 1) {
            new ItemStorageMainMenu().open(player);
            return true;
        }

        /*
        /itemstorage addItem [category]
        /itemstorage create [category]
        /itemstorage view [category]
         */

        final String category = args[1].toLowerCase();
        switch (args[0]) {
            case "add" -> {
                if (args.length != 2) {
                    player.sendRichMessage(Palette.ERROR + "Usage: " + Palette.TEXT_PRIMARY + "/itemstorage add " + Palette.GOLD + "[category]");
                    return true;
                }
                ItemStack item = player.getInventory().getItemInMainHand();
                if (item.getType().isAir()) {
                    player.sendRichMessage(Palette.ERROR + "You need to be holding an item.");
                }
                if (!ItemData.exists(category)) {
                    player.sendRichMessage(Palette.ERROR + "There is no category with that name.");
                    return true;
                }
                ItemData.saveItem(category, item);

            }
            case "create" -> {
                if (args.length != 2) {
                    player.sendRichMessage(Palette.ERROR + "Usage: " + Palette.TEXT_PRIMARY + "/itemstorage create " + Palette.GOLD + "[name]");
                    return true;
                }
            }
            case "view" -> {
                if (args.length != 2) {
                    player.sendRichMessage(Palette.ERROR + "Usage: " + Palette.TEXT_PRIMARY + "/itemstorage view " + Palette.GOLD + "[category]");
                    return true;
                }
                if (!ItemData.exists(args[1])) {
                    player.sendRichMessage(Palette.ERROR + "There is no category with that name.");
                    return true;
                }
                ItemStorageViewer menu = new ItemStorageViewer();
                menu.setCategory(category);
                menu.open(player);
                return true;
            }
        }

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        List<String> inputs = new ArrayList<>();
        List<String> outputs = new ArrayList<>();

        if (args.length == 1) {
            inputs.addAll(List.of("add", "create", "view"));
        }
        if (args.length == 2) {
            switch (args[0].toLowerCase()) {
                case "add", "view" -> inputs.addAll(ItemData.getCategoryList());
            }
        }

        for (String input : inputs) {
            if (input.startsWith(args[args.length - 1]))
                outputs.add(input);
        }

        return outputs;
    }

}
