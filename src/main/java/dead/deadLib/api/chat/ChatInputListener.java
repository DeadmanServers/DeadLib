package dead.deadLib.api.chat;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.UUID;

public class ChatInputListener implements Listener {

    @EventHandler
    public void onChat(AsyncChatEvent e) {
        Player player = e.getPlayer();
        UUID id = player.getUniqueId();
        if (!ChatInputManager.isAwaiting(id)) {
            return;
        }
        e.setCancelled(true);
        String message = PlainTextComponentSerializer.plainText().serialize(e.message()).trim();
        if (message.equalsIgnoreCase("cancel")) {
            String cancelMessage = ChatInputManager.getCancelMessage(id);
            ChatInputManager.cancel(id);
            player.sendMessage(MiniMessage.miniMessage().deserialize(cancelMessage));
        } else {
            ChatInputManager.complete(id, message);
        }
    }
}
