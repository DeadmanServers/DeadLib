package dead.voidrunnerCore.util;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class SLPUtil {

    public static StatusResult ping(String host, int port){
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(host, port), 2000);
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            DataInputStream in = new DataInputStream(socket.getInputStream());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

        } catch (IOException e) {
            return null;
        }
    }

    private static class StatusResult {
        int onlinePlayers;
        int maxPlayers;
        boolean online;
    }
}
