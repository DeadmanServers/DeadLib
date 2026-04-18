package dead.voidrunnerCore.util;

import org.bukkit.Material;

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
}
