package net.devscape.project.guilds.handlers;

import org.bukkit.Bukkit;
import java.util.Objects;
import org.bukkit.World;
import org.bukkit.Location;

public class Warp {

    private final String id;
    private final String world;
    private final double x;
    private final double y;
    private final double z;
    private final float yaw;
    private final float pitch;
    
    public Warp(final String id, final String world, final double x, final double y, final double z, final float yaw, final float pitch) {
        this.id = id;
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }
    
    public Warp(final String id, final Location loc) {
        this.id = id;
        this.world = Objects.requireNonNull(loc.getWorld()).getName();
        this.x = loc.getX();
        this.y = loc.getY();
        this.z = loc.getZ();
        this.yaw = loc.getYaw();
        this.pitch = loc.getPitch();
    }
    
    public String getWorld() {
        return this.world;
    }
    
    public double getX() {
        return this.x;
    }
    
    public double getY() {
        return this.y;
    }
    
    public double getZ() {
        return this.z;
    }
    
    public float getYaw() {
        return this.yaw;
    }
    
    public float getPitch() {
        return this.pitch;
    }
    
    public String getId() {
        return this.id;
    }
    
    public Location toLocation() {
        return new Location(Bukkit.getWorld(this.world), this.x, this.y, this.z, this.yaw, this.pitch);
    }
}
