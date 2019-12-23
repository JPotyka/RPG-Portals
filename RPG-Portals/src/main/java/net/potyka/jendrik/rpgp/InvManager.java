package net.potyka.jendrik.rpgp;
import net.potyka.jendrik.rpgp.App;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.Material;
import org.bukkit.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.Location;

public class InvManager
{

    private App app;
    private ArrayList<Material> randomicons;
    private Random randomgenerator;

    public InvManager(App app)
    {
        this.app = app;

        randomicons = new ArrayList<>();
        randomicons.add(Material.GRAY_BANNER);
        randomicons.add(Material.LIGHT_BLUE_BANNER);
        randomicons.add(Material.MAGENTA_BANNER);
        randomicons.add(Material.YELLOW_BANNER);
        randomicons.add(Material.ORANGE_BANNER);
        randomicons.add(Material.CYAN_BANNER);
        randomicons.add(Material.BLACK_BANNER);
        randomicons.add(Material.GREEN_BANNER);       
        randomicons.add(Material.PINK_BANNER);
        randomicons.add(Material.LIME_BANNER);
        randomicons.add(Material.PURPLE_BANNER);    
        
        randomgenerator = new Random();

    }

    public boolean openMainGUI(Player player)
    {
        Inventory inv = Bukkit.createInventory(null, 36, ChatColor.DARK_AQUA+"RPG-Portals: Main menu");
        if(setMainGUI(player, inv))
        {
            player.openInventory(inv);             
            return true;
        }
        // useless if and boolean returns
        return false;
    }

    public boolean openPublicTowns(Player player, int page)
    {
        Inventory inv = Bukkit.createInventory(null, 36, ChatColor.DARK_AQUA+"RPG-Portals: Public towns");
        if(setPublicTowns(inv, page))
        {
            player.openInventory(inv);  
            return true;
        }
        player.sendMessage("No public towns available!");
        return false;
    }

    public ArrayList<Material> getRandomIcons()
    {
        return randomicons;
    }

    private boolean setMainGUI(Player player, Inventory inv)
    {
        
        // button to close the GUI
        ItemStack exititem = new ItemStack(Material.REDSTONE_BLOCK);
        ItemMeta meta = exititem.getItemMeta();
        meta.setDisplayName(ChatColor.RED + "Exit");
        exititem.setItemMeta(meta);
        inv.setItem(31, exititem);

        // button for portal to own home
        ItemStack homeportal = new ItemStack(Material.RED_BED);
        meta =  homeportal.getItemMeta();
        meta.setDisplayName(ChatColor.AQUA + "Portal to your Home.");
        ArrayList<String> homeportal_lore = new ArrayList<>();
        homeportal_lore.add(ChatColor.DARK_AQUA + "You need:");
        meta.setLore(homeportal_lore);
        homeportal.setItemMeta(meta);
        inv.setItem(0, homeportal);

        // button for portal to the end
        ItemStack endportal = new ItemStack(Material.ENDER_EYE);
        meta =  endportal.getItemMeta();
        meta.setDisplayName(ChatColor.AQUA + "Portal to the end.");
        ArrayList<String> endportal_lore = new ArrayList<>();
        endportal_lore.add(ChatColor.DARK_AQUA + "You need:");
        meta.setLore(endportal_lore);
        endportal.setItemMeta(meta);
        inv.setItem(1, endportal);

        if(this.app.getUseTonwy())
        {    
            // button for portal to player town
            ItemStack hometownportal = new ItemStack(Material.BLUE_BANNER);
            meta =  hometownportal.getItemMeta();
            ArrayList<String> hometownportal_lore = new ArrayList<>();

            if(this.app.getTW().playerIsTownMember(player) == true)
            {                    
                meta.setDisplayName(ChatColor.AQUA + "Portal to your home town: " + this.app.getTW().playersTownName(player));
                hometownportal_lore.add(ChatColor.DARK_AQUA + "You need:");
            }
            else
            {
                meta.setDisplayName(ChatColor.RED + "You don't belong to any town!");
                hometownportal_lore.add(ChatColor.DARK_AQUA + "Maybe join a town?");
            }

            meta.setLore(hometownportal_lore);
            hometownportal.setItemMeta(meta);
            inv.setItem(2, hometownportal);
            player.openInventory(inv); 


            // button for other town list
            ItemStack othertownportal = new ItemStack(Material.RED_BANNER);
            meta =  othertownportal.getItemMeta();
            meta.setDisplayName(ChatColor.AQUA + "List of other public towns.");
            ArrayList<String> othertownportal_lore = new ArrayList<>();
            othertownportal_lore.add(ChatColor.DARK_AQUA + "Click to open it.");
            meta.setLore(othertownportal_lore);
            othertownportal.setItemMeta(meta);
            inv.setItem(2+9, othertownportal);
        }   

        return true;
        //currently useless    
    }

    private boolean setPublicTowns(Inventory inv, int pagenumber)
    {
        ArrayList<String> townnames = new ArrayList<>();
        ArrayList<Location> townspawns = new ArrayList<>();
        
        if(this.app.getTW().getTownNames().size() == this.app.getTW().getTownSpawns().size())
        {
            for (int i = 0; i < this.app.getTW().getTownSpawns().size(); i++) 
            {
                if(this.app.getTW().getTownSpawns().get(i) != null)
                {
                    townnames.add(this.app.getTW().getTownNames().get(i));
                    townspawns.add(this.app.getTW().getTownSpawns().get(i));
                }
            }
        }

        if(townnames.size() > 0)
        {
            int maxpages = townnames.size() / 27;
            if((townnames.size() % 27) != 0)
            {
                maxpages++;
            }
            // button to go back to the main GUI
            ItemStack item = new ItemStack(Material.REDSTONE_BLOCK);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.RED + "Back");
            item.setItemMeta(meta);
            inv.setItem(31, item);  

            // button to next page
            item = new ItemStack(Material.REDSTONE_BLOCK);
            meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.RED + "Next page");
            item.setItemMeta(meta);
            inv.setItem(31+4, item);     
            
            // button to previous page
            item = new ItemStack(Material.REDSTONE_BLOCK);
            meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.RED + "Previous page");
            item.setItemMeta(meta);
            inv.setItem(31-4, item);   
            
            // pagenumber
            item = new ItemStack(Material.LAPIS_BLOCK);
            meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.BLUE +"Page: "+ String.valueOf(pagenumber+1)+ "/" + String.valueOf(maxpages));
            item.setItemMeta(meta);
            inv.setItem(31-2, item);
            
            // create public town entries
            for (int i = 0; i < 27; i++) 
            {
                if(townnames.size() > i + pagenumber*27)
                {
                    item = new ItemStack(randomicons.get(randomgenerator.nextInt(randomicons.size())));
                    meta = item.getItemMeta();
                    meta.setDisplayName(ChatColor.AQUA + townnames.get(i+pagenumber*27));
                    item.setItemMeta(meta);
                    inv.setItem(i+pagenumber*27, item); 
                }
                else
                {
                    break;
                }
            }
            return true;
        }
        return false;
    }
}