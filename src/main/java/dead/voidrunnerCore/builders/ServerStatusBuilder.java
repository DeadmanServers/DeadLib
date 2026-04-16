package dead.voidrunnerCore.builders;

public class ServerStatusBuilder {

    boolean isOnline = false;
    long cachedAt;

    public ServerStatusBuilder isOnline(boolean isOnline) {
        this.isOnline = isOnline;
        return this;
    }
    public ServerStatusBuilder setTime() {
        this.cachedAt = System.currentTimeMillis();
        return this;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public long getCachedAt() {
        return cachedAt;
    }


}
