package net.devscape.project.guilds.menus.pages;

import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.UUID;

import net.devscape.project.guilds.Guilds;
import net.devscape.project.guilds.util.Message;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import net.devscape.project.guilds.menus.MenuUtil;
import net.devscape.project.guilds.menus.Menu;

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
        e.setCancelled(true);

        if (e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getType().equals(Material.PLAYER_HEAD)
                && e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(Message.format("&2&lMembers &7(Right-Click)"))) {
            e.setCancelled(true);
            e.getWhoClicked().closeInventory();
            new MembersMenu(Guilds.getMenuUtil((Player) e.getWhoClicked(), menuUtil.getGuild())).open();
        }
    }
    
    @Override
    public void setMenuItems() {
        this.inventory.setItem(10, this.makeItem(Material.NETHER_STAR, Message.format("&2&lLeader: &f" + Bukkit.getOfflinePlayer(this.menuUtil.getGuild().getOwner()).getName()), new String[0]));
        final StringBuilder string = new StringBuilder();
        for (final UUID member : this.menuUtil.getGuild().getMembers().keySet()) {
            string.append(Bukkit.getOfflinePlayer(member).getName()).append(" ");
        }
        this.inventory.setItem(12, this.makeItem(Material.PLAYER_HEAD, Message.format("&2&lMembers &7(Right-Click)")));
        this.inventory.setItem(14, this.makeItem(Material.PLAYER_HEAD, Message.format("&2&lSize: &f" + this.menuUtil.getGuild().getMembers().size() + "/" + this.menuUtil.getGuild().getMaxMembers()), new String[0]));
        final List<String> whatAreGuilds = new ArrayList<String>();
        whatAreGuilds.add("&7Guilds is a grouping system allowing");
        whatAreGuilds.add("&7you to team up with other players and");
        whatAreGuilds.add("&7level up to be come the best!");
        this.inventory.setItem(17, this.makeItem(Material.BOOK, Message.format("&2&lWhat is guilds?"), Message.format(whatAreGuilds)));
        final List<String> guildCommands = new ArrayList<String>();
        guildCommands.add("&a/guilds create <name> &7- Create a new guild");
        guildCommands.add("&a/guilds disband &7- Disband/Delete your guild");
        guildCommands.add("&a/guilds upgrade &7- Upgrade/level up your guild");
        guildCommands.add("&a/guilds warp <warpName> &7- Go to a warp");
        guildCommands.add("&a/guilds setwarp <name> &7- Set a warp");
        guildCommands.add("&a/guilds invite <player> &7- Invite a player");
        guildCommands.add("&a/guilds accept <guild> &7- Accept an invite");
        guildCommands.add("&a/guilds leave &7- Leave your guild");
        guildCommands.add("&a/guilds list &7- List the top guilds");
        this.inventory.setItem(35, this.makeItem(Material.OAK_SIGN, Message.format("&2&lGuild Commands:"), Message.format(guildCommands)));
        this.inventory.setItem(16, this.makeItem(Material.WHITE_STAINED_GLASS_PANE, Message.format("&f"), new String[0]));
        this.inventory.setItem(25, this.makeItem(Material.WHITE_STAINED_GLASS_PANE, Message.format("&f"), new String[0]));
        this.inventory.setItem(34, this.makeItem(Material.WHITE_STAINED_GLASS_PANE, Message.format("&f"), new String[0]));
        int online = 0;
        for (final UUID member2 : this.menuUtil.getGuild().getMembers().keySet()) {
            if (Bukkit.getPlayer(member2) != null) {
                ++online;
            }
        }
        this.inventory.setItem(28, this.makeItem(Material.GREEN_DYE, Message.format("&2&lOnline: &f" + online + "/" + this.menuUtil.getGuild().getMembers().size()), new String[0]));
        this.inventory.setItem(30, this.makeItem(Material.FEATHER, Message.format("&2&lDescription: &f" + this.menuUtil.getGuild().getDescription()), new String[0]));
        this.inventory.setItem(32, this.makeItem(Material.EXPERIENCE_BOTTLE, Message.format("&2&lLevel: &f" + this.menuUtil.getGuild().getLevel() + "/" + this.menuUtil.getGuild().getMaxLevel()), new String[0]));
        for (int slot = 0; slot < this.inventory.getSize(); ++slot) {
            if (this.inventory.getItem(slot) == null) {
                this.inventory.setItem(slot, this.makeItem(Material.GRAY_STAINED_GLASS_PANE, Message.format("&8"), new String[0]));
            }
        }
    }
}
