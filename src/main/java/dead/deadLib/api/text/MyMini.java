package dead.deadLib.api.text;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    private static final Map<Character, String> LEGACY_CODES = Map.ofEntries(
            Map.entry('0', "<black>"),
            Map.entry('1', "<dark_blue>"),
            Map.entry('2', "<dark_green>"),
            Map.entry('3', "<dark_aqua>"),
            Map.entry('4', "<dark_red>"),
            Map.entry('5', "<dark_purple>"),
            Map.entry('6', "<gold>"),
            Map.entry('7', "<gray>"),
            Map.entry('8', "<dark_gray>"),
            Map.entry('9', "<blue>"),
            Map.entry('a', "<green>"),
            Map.entry('b', "<aqua>"),
            Map.entry('c', "<red>"),
            Map.entry('d', "<light_purple>"),
            Map.entry('e', "<yellow>"),
            Map.entry('f', "<white>"),
            Map.entry('k', "<obfuscated>"),
            Map.entry('l', "<bold>"),
            Map.entry('m', "<strikethrough>"),
            Map.entry('n', "<underlined>"),
            Map.entry('o', "<italic>"),
            Map.entry('r', "<reset>")
    );

    private static String legacyToMini(String input) {
        StringBuilder out = new StringBuilder(input.length());
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c == '&' && i + 1 < input.length()) {
                char next = Character.toLowerCase(input.charAt(i + 1));
                String replacement = LEGACY_CODES.get(next);
                if (replacement != null) {
                    out.append(replacement);
                    i++; // skip the code char we just handled
                    continue;
                }
            }
            out.append(c);
        }
        return out.toString();
    }

    public static String normalize(String input) {
        if (input == null) return "";
        input = input.replace("{bar}", "<st>                    </st>");
        return legacyToMini(input);
    }

    public static List<String> normalize(List<String> input) {
        List<String> out = new ArrayList<>(input.size());
        for (String s : input) {
            out.add(normalize(s));
        }
        return out;
    }

    public static Component normalizeComp(String input) {
        if (input == null) return Component.empty();
        return MiniMessage.miniMessage().deserialize(normalize(input));
    }

    public static List<Component> normalizeComp(List<String> input) {
        List<Component> out = new ArrayList<>(input.size());
        for (String s : input) {
            out.add(normalizeComp(s));
        }
        return out;
    }



/*    public static String normalize(String input) {
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
            return LegacyComponentSerializer.legacyAmpersand().deserialize(input);
        }
        return MiniMessage.miniMessage().deserialize(input);
    }

    public static List<Component> normalizeComp(List<String> input) {
        List<Component> normalized = new ArrayList<>();
        for (String s : input) {
            s = s.replace("{bar}", "<st>                    </st>");
            if (s.matches(".*&[0-9a-fk-orA-FK-OR].*")) {
                Component component = LegacyComponentSerializer.legacyAmpersand().deserialize(s);
                normalized.add(component);
            } else {
                normalized.add(MiniMessage.miniMessage().deserialize(s));
            }
        }
        return normalized;
    }

 */
}
