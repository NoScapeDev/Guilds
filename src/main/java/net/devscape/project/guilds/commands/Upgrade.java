package net.devscape.project.guilds.commands;

import java.util.Optional;
import org.bukkit.ChatColor;
import java.util.Objects;
import org.bukkit.Bukkit;
import net.devscape.project.guilds.handlers.Guild;
import net.devscape.project.guilds.util.Message;
import org.bukkit.entity.Player;
import org.bukkit.command.CommandSender;
import net.devscape.project.guilds.Guilds;
import net.devscape.project.guilds.SubCommand;

public class Upgrade extends SubCommand {

    private final String label;
    
    public Upgrade(final Guilds plugin, final CommandSender sender, final String[] args, final String label) {
        super(plugin, sender, args);
        this.label = label;
        this.execute();
    }
    
    private void execute() {
        if (this.getSender() instanceof Player) {
            final Player player = (Player)this.getSender();
            final Optional<Guild> guild = this.getPlugin().getData().getGuild(player.getUniqueId());
            if (!guild.isPresent()) {
                Message.send(this.getPlugin(), this.getSender(), "not-in-guild");
                return;
            }
            final int level = guild.get().getLevel();
            final int upgradeCost = this.getPlugin().getConfig().getInt("settings.upgrade-costs.level-" + level);
            if (this.getPlugin().getConfig().getBoolean("settings.enable-guild-upgrades")) {
                if (!guild.get().isOwner(player.getUniqueId())) {
                    Message.send(this.getPlugin(), this.getSender(), "must-be-owner");
                }
                else if (guild.get().getLevel() == guild.get().getMaxLevel()) {
                    Message.send(this.getPlugin(), this.getSender(), "already-max-level");
                }
                else if (Guilds.getEcon().has(Bukkit.getOfflinePlayer(player.getUniqueId()), (double)upgradeCost)) {
                    Guilds.getEcon().withdrawPlayer(Bukkit.getOfflinePlayer(player.getUniqueId()), (double)upgradeCost);
                    guild.get().setLevel(guild.get().getLevel() + 1);
                    this.getPlugin().getData().saveGuild(guild.get());
                    final String message = ChatColor.translateAlternateColorCodes('&', (String)Objects.requireNonNull(this.getPlugin().getConfig().getString("messages.guild-level-up"))).replace("%placeholder%", String.valueOf(guild.get().getLevel()));
                    player.sendMessage(message);
                }
            }
            else {
                Message.sendMessage(player, "&cUpgrades feature is disabled.");
            }
        }
        else {
            Message.send(this.getPlugin(), this.getSender(), "not-console-command");
        }
    }
}
