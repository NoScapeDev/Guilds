package net.devscape.project.guilds.menus.pages;

import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.UUID;

import net.devscape.project.guilds.Guilds;
import net.devscape.project.guilds.handlers.Role;
import net.devscape.project.guilds.util.Message;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import net.devscape.project.guilds.menus.MenuUtil;
import net.devscape.project.guilds.menus.Menu;

import static net.devscape.project.guilds.util.Message.format;

public class GuildMenu extends Menu {

    public GuildMenu(final MenuUtil menuUtil) {
        super(menuUtil);
    }
    
    @Override
    public String getMenuName() {
        return "Guild Info";
    }
    
    @Override
    public int getSlots() {
        return 45;
    }
    
    @Override
    public void handleMenu(final InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        e.setCancelled(true);

        if (e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getType().equals(Material.PLAYER_HEAD)
                && e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(format("&2&lMembers &7(Right-Click)"))) {
            Role role = menuUtil.getGuild().getMembers().get(player.getUniqueId());

            if (role == Role.LEADER) {
                e.setCancelled(true);
                e.getWhoClicked().closeInventory();
                new MembersMenu(Guilds.getMenuUtil((Player) e.getWhoClicked(), menuUtil.getGuild())).open();
            } else {
                player.sendMessage(format("&cOnly the Leader can access this area."));
            }
        }
    }
    
    @Override
    public void setMenuItems() {
        this.inventory.setItem(10, this.makeItem(Material.NETHER_STAR, format("&2&lLeader: &f" + Bukkit.getOfflinePlayer(this.menuUtil.getGuild().getOwner()).getName())));
        final StringBuilder string = new StringBuilder();
        for (final UUID member : this.menuUtil.getGuild().getMembers().keySet()) {
            string.append(Bukkit.getOfflinePlayer(member).getName()).append(" ");
        }
        this.inventory.setItem(12, this.makeItem(Material.PLAYER_HEAD, format("&2&lMembers &7(Right-Click)")));
        this.inventory.setItem(14, this.makeItem(Material.PLAYER_HEAD, format("&2&lSize: &f" + this.menuUtil.getGuild().getMembers().size() + "/" + this.menuUtil.getGuild().getMaxMembers())));
        final List<String> whatAreGuilds = new ArrayList<String>();
        whatAreGuilds.add("&7Guilds is a grouping system allowing");
        whatAreGuilds.add("&7you to team up with other players and");
        whatAreGuilds.add("&7level up to be come the best!");
        this.inventory.setItem(17, this.makeItem(Material.BOOK, format("&2&lWhat is guilds?"), format(whatAreGuilds)));

        final List<String> guildCommands = new ArrayList<>();
        guildCommands.add("&a/guilds create <name> &7- Create a new guild");
        guildCommands.add("&a/guilds disband &7- Disband/Delete your guild");
        guildCommands.add("&a/guilds upgrade &7- Upgrade/level up your guild");
        guildCommands.add("&a/guilds warp <warpName> &7- Go to a warp");
        guildCommands.add("&a/guilds setwarp <name> &7- Set a warp");
        guildCommands.add("&a/guilds invite <player> &7- Invite a player");
        guildCommands.add("&a/guilds accept <guild> &7- Accept an invite");
        guildCommands.add("&a/guilds leave &7- Leave your guild");
        guildCommands.add("&a/guilds list &7- List the top guilds");
        this.inventory.setItem(35, this.makeItem(Material.OAK_SIGN, format("&2&lGuild Commands:"), format(guildCommands)));

        this.inventory.setItem(16, this.makeItem(Material.WHITE_STAINED_GLASS_PANE, format("&f")));
        this.inventory.setItem(25, this.makeItem(Material.WHITE_STAINED_GLASS_PANE, format("&f")));
        this.inventory.setItem(34, this.makeItem(Material.WHITE_STAINED_GLASS_PANE, format("&f")));

        int online = 0;
        for (final UUID member2 : this.menuUtil.getGuild().getMembers().keySet()) {
            if (Bukkit.getPlayer(member2) != null) {
                ++online;
            }
        }

        this.inventory.setItem(28, this.makeItem(Material.GREEN_DYE, format("&2&lOnline: &f" + online + "/" + this.menuUtil.getGuild().getMembers().size())));
        this.inventory.setItem(30, this.makeItem(Material.FEATHER, format("&2&lDescription: &f" + this.menuUtil.getGuild().getDescription())));

        final int level = menuUtil.getGuild().getLevel() + 1;
        final int upgradeCost = Guilds.getInstance().getConfig().getInt("settings.upgrade-costs.level-" + level);

        final List<String> guildUpgrade = new ArrayList<>();
        guildUpgrade.add("&7Next Level: &a" + level);
        guildUpgrade.add("&7Upgrade Cost: &a$" + upgradeCost);
        guildUpgrade.add("&7");
        guildUpgrade.add("&2/guild upgrade");

        this.inventory.setItem(32, this.makeItem(Material.EXPERIENCE_BOTTLE, format("&2&lLevel: &f" + this.menuUtil.getGuild().getLevel() + "/" + this.menuUtil.getGuild().getMaxLevel()), format(guildUpgrade)));

        fillEmpty();
    }
}
