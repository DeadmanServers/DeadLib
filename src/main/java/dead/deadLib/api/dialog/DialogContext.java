package dead.deadLib.api.dialog;

import io.papermc.paper.dialog.DialogResponseView;
import org.bukkit.entity.Player;

public class DialogContext {
    private final Player player;
    private final DialogResponseView response;

    public DialogContext(Player player, DialogResponseView response) {
        this.player = player;
        this.response = response;
    }

    public Player player() {
        return player;
    }
    public String text(String key) { return response.getText(key);}
}
