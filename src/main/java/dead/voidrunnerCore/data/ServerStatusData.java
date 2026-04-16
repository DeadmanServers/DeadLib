package dead.voidrunnerCore.data;

import dead.voidrunnerCore.VoidrunnerCore;
import dead.voidrunnerCore.builders.ServerStatusBuilder;
import org.bukkit.Server;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.concurrent.ConcurrentHashMap;

public class ServerStatusData {

    private final ConcurrentHashMap<String, ServerStatusBuilder> serverStatusMap = new ConcurrentHashMap<>();

    public ConcurrentHashMap<String, ServerStatusBuilder> getServerStatusMap() {
        return serverStatusMap;
    }
    public boolean exists(String serverName) {
        if (serverStatusMap.containsKey(serverName)) {
            ServerStatusBuilder serverStatusBuilder = serverStatusMap.get(serverName);
            if (serverStatusBuilder != null) {
                if (Time.valueOf(serverStatusBuilder.getTimestamp()) > Timestamp.)
            }
        }
        return serverStatusMap.containsKey(serverName);
    }

    public ServerStatusBuilder getServerStatusBuilder(String serverName) {
        return serverStatusMap.get(serverName);
    }

    public void addServerStatusBuilder(String serverName, ServerStatusBuilder serverStatusBuilder) {
        serverStatusMap.put(serverName, serverStatusBuilder);
    }


}
