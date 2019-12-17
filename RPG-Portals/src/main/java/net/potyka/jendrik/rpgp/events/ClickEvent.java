package net.potyka.jendrik.rpgp.events;

import net.potyka.jendrik.rpgp.App;
import net.potyka.jendrik.rpgp.PortalManager;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.material.*;

public class ClickEvent implements Listener
{

    private App app;

    public ClickEvent(App app)
    {
        this.app = app;
    }

    @EventHandler
    public void clickEvent(InventoryClickEvent e)
    {
        Player player = (Player) e.getWhoClicked();

        if(e.getView().getTitle().equalsIgnoreCase(ChatColor.DARK_AQUA+"RPG-Portals GUI"))
        {    
            if(e.getCurrentItem()!=null)
            {          
                switch(e.getCurrentItem().getType())
                {
                    case RED_BED:                    
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

    }
}

