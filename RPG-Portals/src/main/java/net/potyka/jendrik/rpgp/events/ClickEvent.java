package net.potyka.jendrik.rpgp.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.data.BlockData;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.material.*;
import org.bukkit.Material;

import com.palmergames.bukkit.towny.object.Town;

import net.potyka.jendrik.rpgp.App;
import net.potyka.jendrik.rpgp.PortalManager;
import net.potyka.jendrik.rpgp.Rpgp;
import net.potyka.jendrik.rpgp.TownyData;




public class ClickEvent implements Listener
{

    private App app;
    private Rpgp rpgp;

    public ClickEvent(App app, CommandExecutor commandexecutor)
    {
        this.app = app;
        this.rpgp = (Rpgp)commandexecutor;
    }

    @EventHandler
    public void clickEvent(InventoryClickEvent e)
    {
        Player player = (Player) e.getWhoClicked();

        if(e.getView().getTitle().equalsIgnoreCase(ChatColor.DARK_AQUA+"RPG-Portals: Main menu"))
        {    
            if(e.getCurrentItem()!=null)
            {          
                switch(e.getCurrentItem().getType())
                {
                    case RED_BED:
                        if(player.hasPermission("rpgp.bedspawn"))
                        {
                            Location bedspawnlocation = player.getBedSpawnLocation();
                            if(bedspawnlocation == null)
                            {    
                                player.sendMessage(ChatColor.RED+"You have no bed to spawn.");        
                            }
                            else
                            {
                                if(app.getPortalManager().createPortal(player, bedspawnlocation))
                                {
                                    player.sendMessage(ChatColor.GREEN+"Portal to your bed spawn location is now für 30 seconds open.");
                                }
                                else
                                {
                                    player.sendMessage(ChatColor.RED+"Portal could not be created!");
                                }
                            }
                        }
                        else
                        {
                            player.sendMessage("You don't have the permission to create a portal to your home.");
                        }                        
                        player.closeInventory();
                    break;

                    case ENDER_EYE:
                        if(player.hasPermission("rpgp.end"))
                        {
                            if(app.getPortalManager().createPortal(player, Bukkit.getWorld("world_the_end").getSpawnLocation()))
                            {
                                player.sendMessage(ChatColor.GREEN+"Portal to the end is now für 30 seconds open.");
                            }
                            else
                            {
                                player.sendMessage(ChatColor.RED+"Portal could not be created!");
                            }

                        }
                        else
                        {
                            player.sendMessage("You don't have the permission to create a portal to the end.");
                        }
                        player.closeInventory();
                    break;        

                    case BLUE_BANNER:
                        if(player.hasPermission("rpgp.hometown"))
                        {
                            if(app.getTownyData().playerIsTownMember(player) == true)
                            {

                                if(app.getPortalManager().createPortal(player, app.getTownyData().playersTownSpawn(player)))
                                {
                                    player.sendMessage(ChatColor.GREEN+"Portal to " + app.getTownyData().playersTownName(player)+ " is open für 30 seconds open.");
                                }
                                else
                                {
                                    player.sendMessage(ChatColor.RED+"Portal could not be created!");
                                }
                            }
                        }
                        else
                        {
                            player.sendMessage("You don't have the permission to create a portal to your home town.");                            
                        }
                        player.closeInventory();
                    break;

                    case RED_BANNER:
                        if(player.hasPermission("rpgp.hometown"))
                        {
                            player.closeInventory();   
                            if(rpgp.openTownList(player, 0) == false)
                            {
                                rpgp.openMainGUI(player);
                            }    
                        }
                        else
                        {
                            player.sendMessage("You don't have the permission to create a portal to a public town.");                            
                        }
                    break;                                        

                    case REDSTONE_BLOCK:
                        player.closeInventory();
                    break;

                    default:
                        player.sendMessage(ChatColor.RED + "Button error.");
                    break;
                }
                e.setCancelled(true);        
            }
        }


        if(e.getView().getTitle().equalsIgnoreCase(ChatColor.DARK_AQUA+"RPG-Portals: Public towns"))
        {    
            if(e.getCurrentItem()!=null)
            {          
                switch(e.getCurrentItem().getType())
                {
                    case REDSTONE_BLOCK:
                        player.closeInventory();
                        rpgp.openMainGUI(player);
                    break;

                    default: 
                        Material material = e.getCurrentItem().getType();    
                        boolean israndomicon = false;  

                        for (int i = 0; i < rpgp.getRandomIcons().size(); i++) 
                        {
                            if(material == rpgp.getRandomIcons().get(i))    
                            {
                                israndomicon = true;
                                break;
                            }
                        }
                        if(israndomicon)
                        {
                            String townname = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
                            Location location = app.getTownyData().getTownSpawn(townname);

                            if(app.getPortalManager().createPortal(player,location))
                            {
                                player.sendMessage(ChatColor.GREEN+"Portal to " + townname + " is open für 30 seconds open.");
                            }
                            else
                            {
                                player.sendMessage(ChatColor.RED+"Portal could not be created!");
                            }
                            player.closeInventory();
                        }
                        else
                        {
                            player.sendMessage(ChatColor.RED + "Button error.");
                        }
                    break;
                }
                e.setCancelled(true);        
            }
        }

    }

    

}

