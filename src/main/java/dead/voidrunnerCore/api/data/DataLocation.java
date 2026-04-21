package dead.voidrunnerCore.api.data;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class DataLocation {

    private DataLocation() {}

    public static void save(DataFile data, String path, Location loc) {
        data.set(path + ".World", loc.getWorld().getName());
        data.set(path + ".X", loc.getX());
        data.set(path + ".Y", loc.getY());
        data.set(path + ".Z", loc.getZ());
        data.set(path + ".Yaw", loc.getYaw());
        data.set(path + ".Pitch", loc.getPitch());
    }

    public static Location load(DataFile data, String path) {
        if (!data.contains(path + ".World")) return null;

        World world = Bukkit.getWorld(data.getString(path + ".World", null));
        if (world == null) return null;
        double x = data.getDouble(path + ".X", 0.0);
        double y = data.getDouble(path + ".Y", 0.0);
        double z = data.getDouble(path + ".Z", 0.0);
        float yaw = (float) data.getDouble(path + ".Yaw", 0.0);
        float pitch = (float) data.getDouble(path + ".Pitch", 0.0);

        return new Location(world, x, y, z, yaw, pitch);
    }
}
