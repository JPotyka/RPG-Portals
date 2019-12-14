package net.potyka.jendrik.rpgp;

import net.potyka.jendrik.rpgp.App;
import net.potyka.jendrik.rpgp.Portal;

import java.util.ArrayList;
import java.lang.Math; 

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.Material.*;



public class PortalManager
{
    App app;
    ArrayList<Portal> portallist;
    int nextid;

    public PortalManager(App app)
    {
        this.app = app;
        this.portallist = new ArrayList<>();
        this.nextid = 0;
    }

    public boolean isActive()
    {
        return true;
    }


	public boolean createPortal(Player player, Location destination)
    {
        Location playerlocation = player.getLocation();
        float playeryaw = playerlocation.getYaw();

        ArrayList<Location> portalblockpositions = createPortalBlockPosition(Math.abs(playeryaw),playerlocation);


        player.sendMessage(String.valueOf(playeryaw));

        if(portalblockpositions.size() != 0)
        {
            for (int i = 0; i < portalblockpositions.size(); i++) 
            {
                portalblockpositions.get(i).getBlock().setType(Material.END_GATEWAY);
            }

            //Portal portal = new Portal(this.nextid, location, destination);
            this.nextid = this.nextid + 1;

            return true;
        }

        return false;
    } 

    ArrayList<Location> createPortalBlockPosition(float rotation, Location playerlocation)
    {
        ArrayList<Location> portalblockpositions = new ArrayList<>();

        if (0 <= rotation && rotation < 22.5) {
            portalblockpositions.add(playerlocation.clone().add(-1, 1, 3));
            portalblockpositions.add(playerlocation.clone().add(0, 1, 3));
            portalblockpositions.add(playerlocation.clone().add(-1, 2, 3));
            portalblockpositions.add(playerlocation.clone().add(0, 2, 3));
            return portalblockpositions;            
        }
        else if (337.5 <= rotation && rotation <= 360) {
            portalblockpositions.add(playerlocation.clone().add(-1, 1, 3));
            portalblockpositions.add(playerlocation.clone().add(0, 1, 3));
            portalblockpositions.add(playerlocation.clone().add(-1, 2, 3));
            portalblockpositions.add(playerlocation.clone().add(0, 2, 3));
            return portalblockpositions;   
        }
        else if (22.5 <= rotation && rotation < 67.5) {
            portalblockpositions.add(playerlocation.clone().add(-3, 1, 2));
            portalblockpositions.add(playerlocation.clone().add(-2, 1, 3));
            portalblockpositions.add(playerlocation.clone().add(-3, 2, 2));
            portalblockpositions.add(playerlocation.clone().add(-2, 2, 3));
            return portalblockpositions;   
        }
        else if (67.5 <= rotation && rotation < 112.5) {
            portalblockpositions.add(playerlocation.clone().add(-3, 1, 0));
            portalblockpositions.add(playerlocation.clone().add(-3, 1, -1));
            portalblockpositions.add(playerlocation.clone().add(-3, 2, 0));
            portalblockpositions.add(playerlocation.clone().add(-3, 2, -1));
            return portalblockpositions;   
        }
        else if (112.5 <= rotation && rotation < 157.5) {
            portalblockpositions.add(playerlocation.clone().add(-2, 1, -3));
            portalblockpositions.add(playerlocation.clone().add(-3, 1, -2));
            portalblockpositions.add(playerlocation.clone().add(-2, 2, -3));
            portalblockpositions.add(playerlocation.clone().add(-3, 2, -2));
            return portalblockpositions;  
        }
        else if (157.5 <= rotation && rotation < 202.5) {
            portalblockpositions.add(playerlocation.clone().add(1, 1, -3));
            portalblockpositions.add(playerlocation.clone().add(0, 1, -3));
            portalblockpositions.add(playerlocation.clone().add(1, 2, 3));
            portalblockpositions.add(playerlocation.clone().add(0, 2, 2));
            return portalblockpositions;  
        }
        else if (202.5 <= rotation && rotation < 247.5) {
            portalblockpositions.add(playerlocation.clone().add(3, 1, -2));
            portalblockpositions.add(playerlocation.clone().add(2, 1, -3));
            portalblockpositions.add(playerlocation.clone().add(3, 2, -2));
            portalblockpositions.add(playerlocation.clone().add(2, 2, -3));
            return portalblockpositions;  
        }
        else if (247.5 <= rotation && rotation < 292.5) {
            portalblockpositions.add(playerlocation.clone().add(3, 0, 0));
            portalblockpositions.add(playerlocation.clone().add(3, 0, 1));
            portalblockpositions.add(playerlocation.clone().add(3, 1, 0));
            portalblockpositions.add(playerlocation.clone().add(3, 1, 1));
            return portalblockpositions;   
        }
        else if (292.5 <= rotation && rotation < 337.5) {
            portalblockpositions.add(playerlocation.clone().add(2, 0, 3));
            portalblockpositions.add(playerlocation.clone().add(3, 0, 2));
            portalblockpositions.add(playerlocation.clone().add(2, 1, 3));
            portalblockpositions.add(playerlocation.clone().add(3, 1, 2));
            return portalblockpositions;  
        }

        //empty ArrayList => no portal creation
        return portalblockpositions;
    }

}