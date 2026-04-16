package dead.voidrunnerCore.builders;

import java.security.Timestamp;

public class ServerStatusBuilder {

    boolean isOnline = false;
    Timestamp timestamp;

    public ServerStatusBuilder isOnline(boolean isOnline) {
        this.isOnline = isOnline;
        return this;
    }
    public ServerStatusBuilder timestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }


}
