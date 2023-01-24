package net.devscape.project.guilds.commands;

import org.bukkit.Location;
import java.util.Optional;
import java.util.Objects;
import org.bukkit.World;
import net.devscape.project.guilds.handlers.Guild;
import net.devscape.project.guilds.util.Message;
import org.bukkit.entity.Player;
import org.bukkit.command.CommandSender;
import net.devscape.project.guilds.Guilds;
import net.devscape.project.guilds.SubCommand;

public class SetWarp extends SubCommand {
    public SetWarp(final Guilds plugin, final CommandSender sender, final String[] args) {
        super(plugin, sender, args);
        this.execute();
    }
    
    private void execute() {
        final Player player = (Player)this.getSender();
        try {
            final String warpName = this.getArgs()[1];
            final Optional<Guild> guild = this.getPlugin().getData().getGuild(player.getUniqueId());
            if (!guild.isPresent()) {
                Message.send(this.getPlugin(), this.getSender(), "must-be-owner");
                return;
            }
            if (guild.get().isOwner(player.getUniqueId())) {
                final Location loc = player.getLocation();
                this.getPlugin().getData().saveWarp(warpName, Objects.requireNonNull(loc.getWorld()).getName(), loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch(), guild.get().getName());
                Message.sendPlaceholder(this.getPlugin(), this.getSender(), "set-warp", warpName);
            }
            else {
                Message.send(this.getPlugin(), this.getSender(), "must-be-owner");
            }
        }
        catch (NullPointerException e) {
            e.printStackTrace();
            Message.send(this.getPlugin(), this.getSender(), "generic-error");
        }
    }
}
