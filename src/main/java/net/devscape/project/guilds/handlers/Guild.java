package net.devscape.project.guilds.handlers;

import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Guild implements Comparable<Guild> {

    private String name;
    private UUID owner;
    private String description;
    private Map<UUID, Role> members;
    private Map<String, Warp> warps;
    private int level;
    
    public Guild(final UUID owner, final String name) {
        this.owner = owner;
        this.name = name;
        (this.members = new HashMap<UUID, Role>()).put(owner, Role.LEADER);
        this.warps = new HashMap<String, Warp>();
        this.description = "A new guild!";
        this.level = 1;
    }
    
    public Guild(final String name) {
        this.owner = null;
        this.name = name;
        this.members = new HashMap<UUID, Role>();
        this.warps = new HashMap<String, Warp>();
        this.description = "A new guild!";
        this.level = 1;
    }
    
    public Guild(final String name, final UUID ownerId, final String description, final List<Object> memberObjects, final Object warps, final int level) {
        this.members = new HashMap<UUID, Role>();
        this.warps = new HashMap<String, Warp>();
        this.name = name;
        this.owner = ownerId;
        this.description = description;
        memberObjects.forEach(member -> this._addMember((JSONObject) member));
        ((JSONArray)warps).forEach(warp -> this._addWarps((JSONObject) warp));
        this.level = level;
    }
    
    private void _addMember(final JSONObject member) {
        final String id = String.valueOf(member.get("player"));
        final String role = String.valueOf(member.get("role"));
        this.members.put(UUID.fromString(id), Role.valueOf(role));
    }
    
    private void _addWarps(final JSONObject warp) {
        final String name = String.valueOf(warp.get("name"));
        final String world = String.valueOf(warp.get("world"));
        final double x = (double) warp.get("x");
        final double y = (double) warp.get("y");
        final double z = (double) warp.get("z");
        final double yaw = (double) warp.get("yaw");
        final double pitch = (double) warp.get("pitch");
        this.warps.put(name, new Warp(name, world, x, y, z, (float)yaw, (float)pitch));
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(final String description) {
        this.description = description;
    }
    
    public Map<UUID, Role> getMembers() {
        return this.members;
    }
    
    public boolean isOwner(final UUID playerUuid) {
        return playerUuid.equals(this.owner);
    }
    
    public boolean containsMember(final UUID playerUuid) {
        return this.members.containsKey(playerUuid);
    }
    
    public boolean containsWarp(final String name) {
        return this.warps.get(name) != null;
    }
    
    public UUID getOwner() {
        return this.owner;
    }
    
    public void setOwner(final UUID owner) {
        this.owner = owner;
    }
    
    public Map<String, Warp> getWarps() {
        return this.warps;
    }
    
    public int getLevel() {
        return this.level;
    }
    
    public void setLevel(final int level) {
        if (level >= 1 && level <= 10) {
            this.level = level;
        }
        else {
            if (level < 1) {
                this.level = 1;
            }
            if (level > 10) {
                this.level = 10;
            }
        }
    }
    
    public int getMaxWarps() {
        return this.level;
    }
    
    public int getMaxMembers() {
        return this.level * 3;
    }
    
    public int getMaxLevel() {
        return 10;
    }
    
    @Override
    public String toString() {
        final JSONObject json = new JSONObject();
        json.put("name", this.name);
        if (this.owner != null) {
            json.put("owner", this.owner.toString());
        }
        json.put("description", this.description);
        final JSONArray jsonArray = new JSONArray();
        if (this.members != null) {
            for (final UUID member : this.members.keySet()) {
                final JSONObject jsonObject = new JSONObject();
                jsonObject.put("player", member.toString());
                jsonObject.put("role", this.members.get(member).toString());
                jsonArray.add(jsonObject);
            }
            json.put("members", jsonArray);
        }
        final JSONArray warpArray = new JSONArray();
        for (final String key : this.warps.keySet()) {
            final Warp location = this.warps.get(key);
            final String world = location.getWorld();
            final double x = location.getX();
            final double y = location.getY();
            final double z = location.getZ();
            final double yaw = location.getYaw();
            final double pitch = location.getPitch();
            final JSONObject jsonObject2 = new JSONObject();
            jsonObject2.put("name", key);
            jsonObject2.put("world", world);
            jsonObject2.put("x", x);
            jsonObject2.put("y", y);
            jsonObject2.put("z", z);
            jsonObject2.put("yaw", yaw);
            jsonObject2.put("pitch", pitch);
            warpArray.add(jsonObject2);
        }
        json.put("warps", warpArray);
        json.put("level", this.level);
        return json.toString();
    }
    
    @Override
    public int compareTo(final Guild o) {
        if (this.members.size() > o.getMembers().size()) {
            return 1;
        }
        return -1;
    }
    
    public void setMembers(final Map<UUID, Role> members) {
        this.members = members;
    }
    
    public void setWarps(final Map<String, Warp> warps) {
        this.warps = warps;
    }
    
    public void addMember(final UUID uuid) {
        this.members.put(uuid, Role.MEMBER);
    }
    
    public void removeMember(final UUID uuid) {
        this.members.remove(uuid);
    }
    
    public void addWarp(final Warp warp) {
        this.warps.put(warp.getId(), warp);
    }
    
    public void removeWarp(final String id) {
        this.warps.remove(id);
    }
    
    public void updateWarp(final Warp warp) {
        this.warps.replace(warp.getId(), warp);
    }
}
