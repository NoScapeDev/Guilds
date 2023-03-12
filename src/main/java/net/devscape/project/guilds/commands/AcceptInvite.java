package net.devscape.project.guilds.commands;

import java.util.Optional;
import java.util.Objects;
import org.bukkit.Bukkit;
import net.devscape.project.guilds.handlers.Guild;
import net.devscape.project.guilds.handlers.GPlayer;
import net.devscape.project.guilds.handlers.Role;
import net.devscape.project.guilds.util.Message;
import org.bukkit.entity.Player;
import org.bukkit.command.CommandSender;
import net.devscape.project.guilds.Guilds;
import net.devscape.project.guilds.SubCommand;

public class AcceptInvite extends SubCommand {

    public AcceptInvite(final Guilds plugin, final CommandSender sender, final String[] args) {
        super(plugin, sender, args);
        this.execute();
    }
    
    private void execute() {
        Player player2 = (Player) this.getSender();
        String guildName = this.getArgs()[1];

        boolean noGuild = this.getPlugin().getData().getGuild(player2.getUniqueId()).isPresent();

        boolean invited = this.getPlugin().getCache().hasBeenInvited(player2);

        String g = this.getPlugin().getCache().getGuildInvited(player2);

        if (invited && g.equalsIgnoreCase(guildName)) {
            final Optional<Guild> guild = this.getPlugin().getData().getGuild(guildName);
            final boolean guildExists = guild.isPresent();
            if (guildExists && noGuild) {
                this.getPlugin().getData().savePlayer(new GPlayer(player2.getUniqueId(), guildName, Role.MEMBER));
                Message.sendPlaceholder(this.getPlugin(), this.getSender(), "invite-accepted", guild.get().getName());
                this.getPlugin().getCache().removeInvite(player2, guildName);
                if (Bukkit.getPlayer(guild.get().getOwner()) != null) {
                    Message.sendPlaceholder(this.getPlugin(), Objects.requireNonNull(Bukkit.getPlayer(guild.get().getOwner())), "player-joined-guild", player2.getName());
                }
            } else if (!guildExists) {
                player2.sendMessage(guildName + " does not exist anymore!");
            } else {
                Message.send(this.getPlugin(), this.getSender(), "already-in-guild");
            }
        } else {
            Message.sendPlaceholder(this.getPlugin(), this.getSender(), "no-invite", guildName);
        }
    }
}
