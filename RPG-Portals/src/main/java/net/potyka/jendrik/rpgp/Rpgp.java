package net.potyka.jendrik.rpgp;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.command.CommandExecutor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.*;
import org.bukkit.ChatColor;
import org.bukkit.Location;

import net.potyka.jendrik.rpgp.TownyData;

public class Rpgp implements CommandExecutor
{
    private App app;
    private ArrayList<Material> publictownlisticons;
    private Random randomgenerator;

    public Rpgp(App app)
    {
        this.app = app;

        publictownlisticons = new ArrayList<>();
        publictownlisticons.add(Material.GRAY_BANNER);
        publictownlisticons.add(Material.LIGHT_BLUE_BANNER);
        publictownlisticons.add(Material.MAGENTA_BANNER);
        publictownlisticons.add(Material.YELLOW_BANNER);
        publictownlisticons.add(Material.ORANGE_BANNER);
        publictownlisticons.add(Material.CYAN_BANNER);
        publictownlisticons.add(Material.BLACK_BANNER);
        publictownlisticons.add(Material.GREEN_BANNER);       
        publictownlisticons.add(Material.PINK_BANNER);
        publictownlisticons.add(Material.LIME_BANNER);
        publictownlisticons.add(Material.PURPLE_BANNER);    
        
        randomgenerator = new Random();

    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {   

        // rpgp
        // open GUI
        if(label.equalsIgnoreCase("rpgp"))
        {
            if(!(sender instanceof Player))
            {
                sender.sendMessage("You must be a player to open the rpgp GUI.");
                return true;
            }
            if(sender.hasPermission("rpgp.opengui"))
            {     
                Player player = (Player) sender;
                openMainGUI(player);
                return true;
            }

            sender.sendMessage("You don't have the permission do open the this rpgp GUI.");
            return true;    
        }
        
        return false;
    }
    
    public void openMainGUI(Player player)
    {
        Inventory inv = Bukkit.createInventory(null, 36, ChatColor.DARK_AQUA+"RPG-Portals: Main menu");

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

        if(app.getUseTonwy())
        {    
            // button for portal to player town
            ItemStack hometownportal = new ItemStack(Material.BLUE_BANNER);
            meta =  hometownportal.getItemMeta();
            ArrayList<String> hometownportal_lore = new ArrayList<>();

            if(app.getTownyData().playerIsTownMember(player) == true)
            {                    
                meta.setDisplayName(ChatColor.AQUA + "Portal to your home town: " + app.getTownyData().playersTownName(player));
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

        player.openInventory(inv);  
    }

    public boolean openTownList(Player player,int pagenumber)
    {
        ArrayList<String> townnames = new ArrayList<>();
        ArrayList<Location> townspawns = new ArrayList<>();
        
        if(app.getTownyData().getTownNames().size() == app.getTownyData().getTownSpawns().size())
        {
            for (int i = 0; i < app.getTownyData().getTownSpawns().size(); i++) 
            {
                if(app.getTownyData().getTownSpawns().get(i) != null)
                {
                    townnames.add(app.getTownyData().getTownNames().get(i));
                    townspawns.add(app.getTownyData().getTownSpawns().get(i));
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

            Inventory inv = Bukkit.createInventory(null, 36, ChatColor.DARK_AQUA+"RPG-Portals: Public towns");

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
                    item = new ItemStack(publictownlisticons.get(randomgenerator.nextInt(publictownlisticons.size())));
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

            player.openInventory(inv);  

            return true;
        }

        return false;
    }

}