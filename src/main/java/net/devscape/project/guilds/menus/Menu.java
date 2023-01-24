package net.devscape.project.guilds.menus;

import org.bukkit.inventory.meta.ItemMeta;
import java.util.List;
import java.util.Arrays;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public abstract class Menu implements InventoryHolder {

    protected Inventory inventory;
    protected MenuUtil menuUtil;
    
    public Menu(final MenuUtil menuUtil) {
        this.menuUtil = menuUtil;
    }
    
    public abstract String getMenuName();
    
    public abstract int getSlots();
    
    public abstract void handleMenu(final InventoryClickEvent p0);
    
    public abstract void setMenuItems();
    
    public void open() {
        this.inventory = Bukkit.createInventory((InventoryHolder)this, this.getSlots(), this.getMenuName());
        this.setMenuItems();
        this.menuUtil.getOwner().openInventory(this.inventory);
    }
    
    public void refresh() {
        this.menuUtil.getOwner().updateInventory();
    }
    
    public Inventory getInventory() {
        return this.inventory;
    }
    
    public ItemStack makeItem(final Material material, final String displayName, final String... lore) {
        final ItemStack item = new ItemStack(material);
        final ItemMeta itemMeta = item.getItemMeta();
        assert itemMeta != null;
        itemMeta.setDisplayName(displayName);
        itemMeta.setLore((List)Arrays.asList(lore));
        item.setItemMeta(itemMeta);
        return item;
    }
    
    public ItemStack makeItem(final Material material, final String displayName, final List<String> lore) {
        final ItemStack item = new ItemStack(material);
        final ItemMeta itemMeta = item.getItemMeta();
        assert itemMeta != null;
        itemMeta.setDisplayName(displayName);
        itemMeta.setLore((List)lore);
        item.setItemMeta(itemMeta);
        return item;
    }
}
