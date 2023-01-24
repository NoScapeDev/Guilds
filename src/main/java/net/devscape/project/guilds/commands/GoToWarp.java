package net.devscape.project.guilds.commands;

import org.bukkit.plugin.Plugin;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;
import java.util.Optional;
import net.devscape.project.guilds.util.Message;
import net.devscape.project.guilds.handlers.Warp;
import org.bukkit.entity.Player;
import org.bukkit.command.CommandSender;
import net.devscape.project.guilds.Guilds;
import net.devscape.project.guilds.SubCommand;

public class GoToWarp extends SubCommand {

    public GoToWarp(final Guilds plugin, final CommandSender sender, final String[] args) {
        super(plugin, sender, args);
        this.execute();
    }
    
    private void execute() {
        final Player player = (Player)this.getSender();
        final String warpName = this.getArgs()[1];
        final Optional<Warp> warp = this.getPlugin().getData().getWarp(player.getUniqueId(), warpName);
        if (warp.isPresent()) {
            this.teleportWarmup(player, warp.get());
        }
        else {
            Message.sendPlaceholder(this.getPlugin(), this.getSender(), "warp-error", warpName);
        }
    }
    
    private void teleportWarmup(final Player player, final Warp warp) {
        final Location loc = player.getLocation();
        new BukkitRunnable() {
            int secondsLeft = 5;
            
            public void run() {
                if (loc.getX() != player.getLocation().getX() || loc.getZ() != player.getLocation().getZ()) {
                    player.sendMessage("§cTeleportation cancelled due to movement!");
                    this.cancel();
                    return;
                }
                if (this.secondsLeft == 0) {
                    player.teleport(warp.toLocation());
                    Message.sendPlaceholder(GoToWarp.this.getPlugin(), GoToWarp.this.getSender(), "warp", warp.getId());
                    this.cancel();
                    return;
                }
                player.sendMessage("§7Teleporting in §2§l" + this.secondsLeft + " seconds§7...");
                --this.secondsLeft;
            }
        }.runTaskTimer((Plugin)this.getPlugin(), 0L, 20L);
    }
}
