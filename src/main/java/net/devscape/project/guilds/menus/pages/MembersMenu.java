package net.devscape.project.guilds.menus.pages;

import net.devscape.project.guilds.Guilds;
import net.devscape.project.guilds.menus.Menu;
import net.devscape.project.guilds.menus.MenuUtil;
import net.devscape.project.guilds.util.Message;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.UUID;

import static net.devscape.project.guilds.util.Message.deformat;
import static net.devscape.project.guilds.util.Message.format;

public class MembersMenu extends Menu {

    public MembersMenu(MenuUtil menuUtil) {
        super(menuUtil);
    }

    @Override
    public String getMenuName() {
        return "Guild > Members";
    }

    @Override
    public int getSlots() {
        return 45;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        e.setCancelled(true);

        if (e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getType().equals(Material.PLAYER_HEAD)) {
            String member = deformat(e.getCurrentItem().getItemMeta().getDisplayName());

            OfflinePlayer m = Bukkit.getOfflinePlayer(member);
            OfflinePlayer o = Bukkit.getOfflinePlayer(menuUtil.getGuild().getOwner());

            if (menuUtil.getGuild().getMembers().containsKey(m.getUniqueId())) {
                if (!m.getUniqueId().equals(o.getUniqueId())) {
                    menuUtil.getGuild().removeMember(m.getUniqueId());
                    Guilds.getInstance().getData().saveGuild(menuUtil.getGuild());
                    player.closeInventory();
                    new MembersMenu(Guilds.getMenuUtil((Player) e.getWhoClicked(), menuUtil.getGuild())).open();

                    player.sendMessage(format("&aThis player has been removed."));
                } else {
                    player.sendMessage(format("&cThe owner of a guild cannot be removed."));
                }
            }
        }
    }

    @Override
    public void setMenuItems() {
        for (UUID uuid : menuUtil.getGuild().getMembers().keySet()) {
            OfflinePlayer member = Bukkit.getOfflinePlayer(uuid);

            OfflinePlayer m = Bukkit.getOfflinePlayer(uuid);
            OfflinePlayer o = Bukkit.getOfflinePlayer(menuUtil.getGuild().getOwner());

            if (!m.getUniqueId().equals(o.getUniqueId())) {
                this.inventory.addItem(this.makeItem(Material.PLAYER_HEAD, Message.format("&a" + member.getName()), format("&7Right Click to Remove!")));
            }

            if (inventory.isEmpty()) {
                this.inventory.setItem(22, this.makeItem(Material.HOPPER, Message.format("&cYou have no members yet!")));
            }
        }
    }
}