package dead.deadLib.api.menu;

import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public class Button {
    private final ItemStack icon;
    private Consumer<ClickContext> handler = ctx -> {};

    private Button(ItemStack icon) {
        this.icon = icon;
    }
    public static Button of(ItemStack icon) {
        return new Button(icon);
    }
    public Button onClick(Consumer<ClickContext> handler) {
        this.handler = handler;
        return this;
    }

    public ItemStack icon() {
        return icon.clone();
    }
    void click(ClickContext ctx) {
        handler.accept(ctx);
    }
}
