package dead.voidrunnerCore.data;

import dead.voidrunnerCore.builders.ServerStatusBuilder;
import dead.voidrunnerCore.util.SLPUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class ServerStatusData {

    private static final ConcurrentHashMap<String, SLPUtil.StatusResult> serverStatusMap = new ConcurrentHashMap<>();
    private static final long TTL = 15_000L;

    private static HashMap<String, InetSocketAddress> addresses = new HashMap<>();

    public static HashMap<String, InetSocketAddress> getAddresses() {
        return addresses;
    }
    public static void setAddresses(HashMap<String, InetSocketAddress> addresses) {
        ServerStatusData.addresses = addresses;
    }
    public static void addAddresses(String serverName, InetSocketAddress address) {
        addresses.put(serverName, address);
    }

    public static ConcurrentHashMap<String, SLPUtil.StatusResult> getServerStatusMap() {
        return serverStatusMap;
    }
    public static boolean exists(String serverName) {
        return serverStatusMap.containsKey(serverName);
    }

    public static SLPUtil.StatusResult getServerStatusBuilder(String serverName) {
        if (serverStatusMap.containsKey(serverName)) {
            return serverStatusMap.get(serverName);
        }
        return null;
    }

    public static void addServerStatusBuilder(String serverName, SLPUtil.StatusResult serverStatus) {
        serverStatusMap.put(serverName, serverStatus);
    }

    public static boolean ping(String host, int port) {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(host, port), 2000);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

}
