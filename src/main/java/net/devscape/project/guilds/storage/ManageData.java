package net.devscape.project.guilds.storage;

import java.util.List;
import net.devscape.project.guilds.handlers.Warp;
import java.util.Optional;
import java.util.UUID;
import net.devscape.project.guilds.handlers.GPlayer;
import net.devscape.project.guilds.handlers.Guild;

public interface ManageData
{
    boolean saveGuild(final Guild p0);
    
    boolean savePlayer(final GPlayer p0);
    
    boolean saveInvite(final UUID p0, final String p1);
    
    boolean saveWarp(final String p0, final String p1, final double p2, final double p3, final double p4, final float p5, final float p6, final String p7);
    
    boolean deletePlayer(final String p0);
    
    boolean deleteGuild(final String p0);
    
    boolean deleteInvite(final UUID p0, final String p1);
    
    boolean deleteWarp(final String p0, final String p1);
    
    Optional<Guild> getGuild(final String p0);
    
    Optional<Guild> getGuild(final UUID p0);
    
    Optional<GPlayer> getPlayer(final UUID p0);
    
    Optional<Warp> getWarp(final UUID p0, final String p1);
    
    boolean hasInvite(final UUID p0, final String p1);
    
    List<Guild> getAllGuilds();
    
    boolean saveAllData();
    
    boolean loadAllData();
}
