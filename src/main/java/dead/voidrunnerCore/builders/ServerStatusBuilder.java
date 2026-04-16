package dead.voidrunnerCore.builders;

public class ServerStatusBuilder {

    private boolean isOnline = false;
    private int playerCount;
    private long cachedAt;

    public ServerStatusBuilder isOnline(boolean isOnline) {
        this.isOnline = isOnline;
        return this;
    }
    public ServerStatusBuilder setTime() {
        this.cachedAt = System.currentTimeMillis();
        return this;
    }
    public ServerStatusBuilder setPlayerCount(int playerCount) {
        this.playerCount = playerCount;
        return this;
    }

    public int getPlayerCount() {
        return playerCount;
    }
    public boolean isOnline() {
        return isOnline;
    }

    public long getCachedAt() {
        return cachedAt;
    }


}
