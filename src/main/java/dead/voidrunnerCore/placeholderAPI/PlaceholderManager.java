package dead.voidrunnerCore.placeholderAPI;

import dead.voidrunnerCore.builders.ServerStatusBuilder;
import dead.voidrunnerCore.data.ServerStatusData;
import dead.voidrunnerCore.util.SLPUtil;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PlaceholderManager extends PlaceholderExpansion {
    @Override
    public @NotNull String getIdentifier() {
        return "voidrunners";
    }

    @Override
    public @NotNull String getAuthor() {
        return "";
    }

    @Override
    public @NotNull String getVersion() {
        return "";
    }

    @Override
    public boolean persist() {
        return true;
    }
    @Override
    public boolean canRegister() {
        return true;
    }
    @Override
    public String onPlaceholderRequest(Player player, @NotNull String params) {
        String[] split = params.split("_", 2);
        switch (split[0]) {
            case "status" -> {
                String serverName = split[1];
                SLPUtil.StatusResult serverStatus = ServerStatusData.getServerStatusBuilder(serverName);
                boolean online = serverStatus.isOnline();
                if (online) {
                    return "&aOnline";
                }
                return "&cOffline";
            }
            case "players" -> {
                String serverName = split[1];
                SLPUtil.StatusResult serverStatus = ServerStatusData.getServerStatusBuilder(serverName);
                if (serverStatus == null) {
                    return "&cFailed";
                }
                return Math.max(serverStatus.getOnlinePlayers(), 0) + "";
            }
        }
        return "&aLoading...";
    }
}
