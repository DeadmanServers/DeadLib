package dead.deadLib.api.text;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class MyMini {
    public static String sprite(Material material) {
        if (material.isItem()) {
            return "<sprite:\"minecraft:items\":item/" + material.name().toLowerCase() + ">";
        }
        if (material.isBlock()) {
            return "<sprite:blocks:block/" + material.name().toLowerCase() + ">";
        }
        return "";
    }

    public static String normalize(String input) {
        input = input.replace("{bar}", "<st>                    </st>");
        if (input.matches(".*&[0-9a-fk-orA-FK-OR].*")) {
            Component component = LegacyComponentSerializer.legacyAmpersand().deserialize(input);
            return MiniMessage.miniMessage().serialize(component);
        }
        return input;
    }

    public static List<String> normalize(List<String> input) {
        List<String> normalized = new ArrayList<>();
        for (String s : input) {
            s = s.replace("{bar}", "<st>                    </st>");
            Component component = LegacyComponentSerializer.legacyAmpersand().deserialize(s);
            normalized.add(MiniMessage.miniMessage().serialize(component));
        }
        return normalized;
    }

    public static Component normalizeComp(String input) {
        input = input.replace("{bar}", "<st>                    </st>");
        if (input.matches(".*&[0-9a-fk-orA-FK-OR].*")) {
            return LegacyComponentSerializer.legacyAmpersand().deserialize("<i:false>" + input);
        }
        return MiniMessage.miniMessage().deserialize("<i:false>" + input);
    }

    public static List<Component> normalizeComp(List<String> input) {
        List<Component> normalized = new ArrayList<>();
        for (String s : input) {
            s = s.replace("{bar}", "<st>                    </st>");
            if (s.matches(".*&[0-9a-fk-orA-FK-OR].*")) {
                Component component = LegacyComponentSerializer.legacyAmpersand().deserialize("<i:false>" + s);
                normalized.add(component);
            } else {
                normalized.add(MiniMessage.miniMessage().deserialize("<i:false>" + s));
            }
        }
        return normalized;
    }
}
