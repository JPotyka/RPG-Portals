package net.potyka.jendrik.rpgp;

import org.bukkit.command.CommandExecutor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.*;
import org.bukkit.ChatColor;

public class Rpgp implements CommandExecutor
{
    public Rpgp(App app)
    {

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
                openGUI(player);
                return true;
            }

            sender.sendMessage("You don't have the permission do open the rpgp GUI.");
            return true;    
        }
        
        return false;
    }
    
    public void openGUI(Player player)
    {
        Inventory inv = Bukkit.createInventory(null, 36, "RPG-Portals GUI");

        // button to close the GUI
        ItemStack exititem = new ItemStack(Material.REDSTONE_BLOCK);
        ItemMeta meta = exititem.getItemMeta();

        meta.setDisplayName(ChatColor.RED + "Exit");
        exititem.setItemMeta(meta);
        inv.setItem(31, exititem);

        player.openInventory(inv);



    }


}