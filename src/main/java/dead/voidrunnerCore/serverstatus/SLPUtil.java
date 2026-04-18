package dead.voidrunnerCore.serverstatus;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class SLPUtil {

    public static StatusResult ping(String host, int port){
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(host, port), 2000);
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            DataInputStream in = new DataInputStream(socket.getInputStream());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            DataOutputStream handShake = new DataOutputStream(baos);
            writeVarInt(handShake, -1);
            byte[] hostBytes = host.getBytes(StandardCharsets.UTF_8);
            writeVarInt(handShake, hostBytes.length);
            handShake.write(hostBytes);
            handShake.writeShort(port);
            writeVarInt(handShake, 1);
            byte[] payload = baos.toByteArray();
            writeVarInt(out, 1 + payload.length);
            writeVarInt(out, 0);
            out.write(payload);
            writeVarInt(out, 1);
            writeVarInt(out, 0);
            readVarInt(in);
            readVarInt(in);
            int jsonLength = readVarInt(in);
            byte[] jsonBytes = new byte[jsonLength];
            in.readFully(jsonBytes);
            String json = new String(jsonBytes, StandardCharsets.UTF_8);
            JsonObject obj = JsonParser.parseString(json).getAsJsonObject();
            JsonObject players = obj.getAsJsonObject("players");
            int online = players.get("online").getAsInt();
            int max = players.get("max").getAsInt();

            StatusResult result = new StatusResult();
            result.onlinePlayers = online;
            result.maxPlayers = max;
            result.online = true;
            return result;

        } catch (IOException e) {
            StatusResult result = new StatusResult();
            result.onlinePlayers = 0;
            result.maxPlayers = 0;
            result.online = false;
            return result;
        }
    }

    private static void writeVarInt(DataOutputStream out, int value) throws IOException {
        while (true) {
            if ((value & ~0x7F) == 0) {
                out.writeByte(value);
                return;
            }
            out.writeByte((value & 0x7F) | 0x80);
            value >>>= 7;
        }
    }

    private static int readVarInt(DataInputStream in) throws IOException {
        int result = 0;
        int shift = 0;
        while (true) {
            int b = in.read();
            result |= (b & 0x7F) << shift;
            if ((b & 0x80) == 0) {
                return result;
            }
            shift += 7;
        }
    }

    public static class StatusResult {
        int onlinePlayers;
        int maxPlayers;
        boolean online;

        public int getOnlinePlayers() {
            return onlinePlayers;
        }

        public int getMaxPlayers() {
            return maxPlayers;
        }

        public boolean isOnline() {
            return online;
        }
    }
}
