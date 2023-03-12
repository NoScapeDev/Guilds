package net.devscape.project.guilds.storage.database;

import org.bukkit.OfflinePlayer;

import java.util.HashMap;
import java.util.UUID;
import java.util.Map;

public class DatabaseCache {
    private final Map<UUID, Double> transactionConfirmations;

    private final Map<OfflinePlayer, String> inviteMap;
    
    public DatabaseCache() {
        this.transactionConfirmations = new HashMap<>();
        this.inviteMap = new HashMap<>();
    }
    
    public void addTransactionConfirmation(final UUID uuid, final double value) {
        this.transactionConfirmations.put(uuid, value);
    }
    
    public void removeTransactionConfirmation(final UUID uuid) {
        this.transactionConfirmations.remove(uuid);
    }
    
    public boolean hasPendingTransaction(final UUID uuid) {
        return this.transactionConfirmations.containsKey(uuid);
    }

    public Map<OfflinePlayer, String> getInviteMap() {
        return inviteMap;
    }

    public void addInvite(OfflinePlayer receiver, String guild) {
        inviteMap.put(receiver, guild);
    }

    public void removeInvite(OfflinePlayer receiver, String guild) {
        inviteMap.remove(receiver, guild);
    }

    public boolean hasBeenInvited(OfflinePlayer receiver) {
        return inviteMap.containsKey(receiver);
    }

    public String getGuildInvited(OfflinePlayer receiver) {
        return inviteMap.get(receiver);
    }
}
