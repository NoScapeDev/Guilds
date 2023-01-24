// 
// Decompiled by Procyon v0.5.36
// 

package net.devscape.project.guilds.commands;

import org.bukkit.OfflinePlayer;
import java.util.Iterator;
import java.util.Optional;
import net.devscape.project.guilds.util.Message;
import net.devscape.project.guilds.handlers.GPlayer;
import java.util.Objects;
import org.bukkit.Bukkit;
import java.util.UUID;
import net.devscape.project.guilds.handlers.Guild;
import org.bukkit.entity.Player;
import org.bukkit.command.CommandSender;
import net.devscape.project.guilds.Guilds;
import net.devscape.project.guilds.SubCommand;

public class Chat extends SubCommand
{
    public Chat(final Guilds plugin, final CommandSender sender, final String[] args) {
        super(plugin, sender, args);
        this.execute();
    }
    
    public void execute() {
        final Player player = (Player)this.getSender();
        if (this.getArgs().length >= 2) {
            final StringBuilder builder = new StringBuilder();
            for (int i = 1; i < this.getArgs().length; ++i) {
                builder.append(this.getArgs()[i]).append(" ");
            }
            final String msg = builder.toString();
            final Optional<Guild> guild = this.getPlugin().getData().getGuild(player.getUniqueId());
            final Optional<GPlayer> gPlayer = this.getPlugin().getData().getPlayer(player.getUniqueId());
            if (guild.isPresent()) {
                if (gPlayer.isPresent()) {
                    for (final UUID uuid : guild.get().getMembers().keySet()) {
                        final OfflinePlayer member = Bukkit.getOfflinePlayer(uuid);
                        if (member.isOnline()) {
                            Message.sendMessage(Objects.requireNonNull(member.getPlayer()), "&2[Guild] &2&l" + gPlayer.get().getRole() + " &7" + player.getName() + "&8:&f " + msg);
                        }
                    }
                }
                else {
                    Message.sendMessage(player, "&2[Guild] &cYou're not in a guild.");
                }
            }
            else {
                Message.sendMessage(player, "&2[Guild] &cYou're not in a guild.");
            }
        }
        else {
            Message.sendMessage(player, "&2[Guild] &cUsage: /guilds chat <message>");
        }
    }
}
