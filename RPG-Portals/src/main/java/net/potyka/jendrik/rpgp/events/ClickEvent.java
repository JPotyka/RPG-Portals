package net.potyka.jendrik.rpgp.events;
import net.potyka.jendrik.rpgp.App;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.command.CommandExecutor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;


public class ClickEvent implements Listener
{
    private App app;

    public ClickEvent(App app, CommandExecutor commandexecutor)
    {
        this.app = app;
    }

    @EventHandler
    public void clickEvent(InventoryClickEvent e)
    {
        if(e.getView().getTitle().equalsIgnoreCase(ChatColor.DARK_AQUA+"RPG-Portals: Main menu"))
        {    
            clicksMainGUI(e);
        }
        else if(e.getView().getTitle().equalsIgnoreCase(ChatColor.DARK_AQUA+"RPG-Portals: Public towns"))
        {
            clicksPublicTowns(e);            
        }
    }

    private void clicksMainGUI(InventoryClickEvent e)
    {
        Player player = (Player) e.getWhoClicked();

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
                                if(app.getPM().createPortal(player, bedspawnlocation))
                                {
                                    player.sendMessage(ChatColor.GREEN+"Casting portal to your bed spawn location.");
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
                        e.setCancelled(true);                      
                        player.closeInventory();
                    break;

                    case ENDER_EYE:
                        if(player.hasPermission("rpgp.end"))
                        {
                            if(app.getPM().createPortal(player, Bukkit.getWorld(app.getNetherWorldName()).getSpawnLocation()))
                            {
                                player.sendMessage(ChatColor.GREEN+"Casting portal to the end.");
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
                        e.setCancelled(true);   
                        player.closeInventory();
                    break;        

                    case BLUE_BANNER:
                        if(player.hasPermission("rpgp.hometown"))
                        {
                            if(app.getTW().playerIsTownMember(player) == true)
                            {

                                if(app.getPM().createPortal(player, app.getTW().playersTownSpawn(player)))
                                {
                                    player.sendMessage(ChatColor.GREEN+"Casting portal to " + app.getTW().playersTownName(player)+ ".");
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
                        if(player.hasPermission("rpgp.publictown"))
                        {
                            e.setCancelled(true);   
                            player.closeInventory();   
                            if(app.getIM().openPublicTowns(player, 0) == false)
                            {
                               app.getIM().openMainGUI(player);
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

    private void clicksPublicTowns(InventoryClickEvent e)
    {
        Player player = (Player) e.getWhoClicked();

        if(e.getCurrentItem()!=null)
        {          
            switch(e.getCurrentItem().getType())
            {
                case REDSTONE_BLOCK:
                    player.closeInventory();
                    app.getIM().openMainGUI(player);
                break;

                default: 
                    Material material = e.getCurrentItem().getType();    
                    boolean israndomicon = false;  

                    for (int i = 0; i < this.app.getIM().getRandomIcons().size(); i++) 
                    {
                        if(material == this.app.getIM().getRandomIcons().get(i))    
                        {
                            israndomicon = true;
                            break;
                        }
                    }
                    if(israndomicon)
                    {
                        String townname = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
                        Location location = app.getTW().getTownSpawn(townname);

                        if(app.getPM().createPortal(player,location))
                        {
                            player.sendMessage(ChatColor.GREEN+"Casting portal to " + townname + ".");
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




