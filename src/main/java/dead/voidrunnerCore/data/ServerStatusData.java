package dead.voidrunnerCore.data;

import dead.voidrunnerCore.builders.ServerStatusBuilder;

import java.util.concurrent.ConcurrentHashMap;

public class ServerStatusData {

    private final ConcurrentHashMap<String, ServerStatusBuilder> serverStatusMap = new ConcurrentHashMap<>();
    private static final long TTL = 15_000L;

    public ConcurrentHashMap<String, ServerStatusBuilder> getServerStatusMap() {
        return serverStatusMap;
    }
    public boolean exists(String serverName) {
        return serverStatusMap.containsKey(serverName);
    }

    public ServerStatusBuilder getServerStatusBuilder(String serverName) {
        return serverStatusMap.get(serverName);
    }

    public void addServerStatusBuilder(String serverName, ServerStatusBuilder serverStatusBuilder) {
        serverStatusMap.put(serverName, serverStatusBuilder);
    }

    public void ping(String serverName) {
        if (exists(serverName)) {
            ServerStatusBuilder serverStatusBuilder = serverStatusMap.get(serverName);
        }

        ServerStatusBuilder serverStatusBuilder = serverStatusMap.get(serverName);
        if (serverStatusBuilder != null) {
            if (System.currentTimeMillis() - serverStatusBuilder.getCachedAt() > TTL) {

            }
        }
    }

}
