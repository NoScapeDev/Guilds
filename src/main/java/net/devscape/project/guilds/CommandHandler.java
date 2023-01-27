package net.devscape.project.guilds;

import net.devscape.project.guilds.commands.*;

import java.util.Optional;
import net.devscape.project.guilds.util.InputChecker;
import net.devscape.project.guilds.menus.pages.GuildMenu;
import net.devscape.project.guilds.handlers.Guild;
import org.bukkit.entity.Player;
import net.devscape.project.guilds.util.Message;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;

public class CommandHandler implements CommandExecutor
{
    private final Guilds plugin;
    
    public CommandHandler(final Guilds plugin) {
        this.plugin = plugin;
    }
    
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (!command.getName().equalsIgnoreCase("guilds")) {
            return false;
        }
        if (sender instanceof ConsoleCommandSender) {
            Message.sendHelp(this.plugin, sender, "help", label);
        }
        final Player player = (Player)sender;
        if (args.length == 0) {
            final Optional<Guild> guild = this.plugin.getData().getGuild(player.getUniqueId());
            if (guild.isPresent()) {
                new GuildMenu(Guilds.getMenuUtil(player, guild.get())).open();
            }
            else {
                Message.sendHelp(this.plugin, sender, "help", label);
            }
            return true;
        }
        if (InputChecker.noSpecialCharacters(args)) {
            this.parseSubCommand(sender, args, label);
        }
        else if (args[0].equalsIgnoreCase("setdesc")) {
            this.parseSubCommand(sender, args, label);
        }
        else {
            Message.send(this.plugin, sender, "no-special-characters");
        }
        return true;
    }
    
    private void parseSubCommand(final CommandSender sender, final String[] args, final String label) {
        if (args[0].equalsIgnoreCase("create")) {
            this.guildCreate(sender, args, label);
        }
        else if (args[0].equalsIgnoreCase("disband")) {
            this.guildDisband(sender, args, label);
        }
        else if (args[0].equalsIgnoreCase("warp")) {
            this.guildWarp(sender, args, label);
        }
        else if (args[0].equalsIgnoreCase("invite")) {
            this.guildInvite(sender, args, label);
        }
        else if (args[0].equalsIgnoreCase("chat")) {
            this.guildChat(sender, args, label);
        }
        else if (args[0].equalsIgnoreCase("deny")) {
            this.guildDeny(sender, args, label);
        }
        else if (args[0].equalsIgnoreCase("list")) {
            new ListGuilds(this.plugin, sender, args);
        }
        else if (args[0].equalsIgnoreCase("reload")) {
            new Reload(this.plugin, sender, args);
        }
        else if (args[0].equalsIgnoreCase("setdesc")) {
            this.guildSetDesc(sender, args, label);
        }
        else if (args[0].equalsIgnoreCase("setwarp")) {
            this.guildSetWarp(sender, args, label);
        }
        else if (args[0].equalsIgnoreCase("upgrade")) {
            this.guildUpgrade(sender, args, label);
        }
        else if (args[0].equalsIgnoreCase("confirm")) {
            this.guildConfirmInvite(sender, args, label);
        }
        else if (args[0].equalsIgnoreCase("leave")) {
            this.guildLeave(sender, args, label);
        }
        else if (args[0].equalsIgnoreCase("accept")) {
            this.guildAccept(sender, args, label);
        }
        else if (args.length == 1) {
            this.guildInfo(sender, args, label);
        }
    }
    
    private void guildLeave(final CommandSender sender, final String[] args, final String label) {
        if (args.length == 1) {
            new LeaveGuild(this.plugin, sender, args);
        }
        else {
            Message.sendPlaceholder(this.plugin, sender, "syntax.leave", label);
        }
    }
    
    private void guildChat(final CommandSender sender, final String[] args, final String label) {
        if (args.length >= 2) {
            new Chat(this.plugin, sender, args);
        }
        else {
            Message.sendPlaceholder(this.plugin, sender, "syntax.chat", label);
        }
    }
    
    private void guildConfirmInvite(final CommandSender sender, final String[] args, final String label) {
        if (args.length == 1) {
            new Confirmation(this.plugin, sender, args);
        }
        else {
            Message.sendPlaceholder(this.plugin, sender, "syntax.confirm", label);
        }
    }
    
    private void guildUpgrade(final CommandSender sender, final String[] args, final String label) {
        if (args.length == 1) {
            new Upgrade(this.plugin, sender, args, label);
        }
        else {
            Message.sendPlaceholder(this.plugin, sender, "syntax.upgrade", label);
        }
    }
    
    private void guildSetWarp(final CommandSender sender, final String[] args, final String label) {
        if (args.length == 2) {
            new SetWarp(this.plugin, sender, args);
        }
        else {
            Message.sendPlaceholder(this.plugin, sender, "syntax.setwarp", label);
        }
    }
    
    private void guildSetDesc(final CommandSender sender, final String[] args, final String label) {
        if (args.length >= 2) {
            new SetDescription(this.plugin, sender, args);
        }
        else {
            Message.sendPlaceholder(this.plugin, sender, "syntax.setdesc", label);
        }
    }

    private void guildReload(final CommandSender sender, final String[] args, final String label) {
        new Reload(this.plugin, sender, args);
    }
    
    private void guildInfo(final CommandSender sender, final String[] args, final String label) {
        if (args.length == 1) {
            new GetGuildInfo(this.plugin, sender, args);
        }
        else {
            Message.sendPlaceholder(this.plugin, sender, "messages.guild-not-found", label);
        }
    }
    
    private void guildAccept(final CommandSender sender, final String[] args, final String label) {
        if (args.length == 2) {
            new AcceptInvite(this.plugin, sender, args);
        }
        else {
            Message.sendPlaceholder(this.plugin, sender, "syntax.accept-invite", label);
        }
    }
    
    private void guildDeny(final CommandSender sender, final String[] args, final String label) {
        if (args.length == 2) {
            new DenyInvite(this.plugin, sender, args);
        }
        else {
            Message.sendPlaceholder(this.plugin, sender, "syntax.deny-invite", label);
        }
    }
    
    private void guildCreate(final CommandSender sender, final String[] args, final String label) {
        if (args.length == 2) {
            new CreateGuild(this.plugin, sender, args);
        }
        else {
            Message.sendPlaceholder(this.plugin, sender, "syntax.create", label);
        }
    }
    
    private void guildDisband(final CommandSender sender, final String[] args, final String label) {
        if (args.length == 1) {
            new DisbandGuild(this.plugin, sender, args);
        }
        else {
            Message.sendPlaceholder(this.plugin, sender, "syntax.disband", label);
        }
    }
    
    private void guildWarp(final CommandSender sender, final String[] args, final String label) {
        if (args.length == 2) {
            new GoToWarp(this.plugin, sender, args);
        }
        else {
            Message.sendPlaceholder(this.plugin, sender, "syntax.warp", label);
        }
    }
    
    private void guildInvite(final CommandSender sender, final String[] args, final String label) {
        if (args.length == 2) {
            new InvitePlayer(this.plugin, sender, args);
        }
        else {
            Message.sendPlaceholder(this.plugin, sender, "syntax.invite", label);
        }
    }
}
