package dead.deadLib.api.text;

import net.kyori.adventure.audience.Audience;

public final class Messages {
    private final String prefix;

    public Messages(String prefix) { this.prefix = prefix; }
    public void success(Audience to, String message)    {send(to, Palette.SUCCESS, message);}
    public void error(Audience to, String message)      {send(to, Palette.ERROR, message);}
    public void warn(Audience to, String message)       {send(to, Palette.WARNING, message);}
    public void info(Audience to, String message)       {send(to, Palette.TEXT_PRIMARY, message);}

    private void send(Audience to, String baseColour, String message) {
        to.sendMessage(MyMini.normalizeComp(prefix + baseColour + message));
    }
}
