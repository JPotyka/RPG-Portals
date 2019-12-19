package net.potyka.jendrik.rpgp;

import net.potyka.jendrik.rpgp.App;
import net.potyka.jendrik.rpgp.Portal;

import java.util.ArrayList;
import java.lang.Math; 

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.Material.*;
import org.bukkit.block.BlockState;



public class PortalManager
{
    private App app;
    private ArrayList<Portal> portallist;
    private int nextid;

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

            ArrayList<BlockState> originalblockmaterial = new ArrayList<>();

            for(int i =0; i < portalblockpositions.size();i++)
            {
                // remove: Bukkit.getServer().getLogger().info(String.valueOf(i));
                originalblockmaterial.add(portalblockpositions.get(i).getBlock().getState());
            }

            for (int i = 0; i < portalblockpositions.size(); i++) 
            {
                portalblockpositions.get(i).getBlock().setType(Material.END_GATEWAY);
            }

            this.portallist.add(new Portal(this.nextid, portalblockpositions, originalblockmaterial, destination, System.currentTimeMillis(), player));
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
            portalblockpositions.add(playerlocation.clone().add(0,  1, 3));
            portalblockpositions.add(playerlocation.clone().add(-1, 2, 3));
            portalblockpositions.add(playerlocation.clone().add(0,  2, 3));
            return portalblockpositions;            
        }
        else if (337.5 <= rotation && rotation <= 360) {
            portalblockpositions.add(playerlocation.clone().add(-1, 1, 3));
            portalblockpositions.add(playerlocation.clone().add(0,  1, 3));
            portalblockpositions.add(playerlocation.clone().add(-1, 2, 3));
            portalblockpositions.add(playerlocation.clone().add(0,  2, 3));
            return portalblockpositions;   
        }
        else if (22.5 <= rotation && rotation < 67.5) {
            portalblockpositions.add(playerlocation.clone().add(3, 1, 2));
            portalblockpositions.add(playerlocation.clone().add(2, 1, 3));
            portalblockpositions.add(playerlocation.clone().add(3, 2, 2));
            portalblockpositions.add(playerlocation.clone().add(2, 2, 3));
            return portalblockpositions;   
        }
        else if (67.5 <= rotation && rotation < 112.5) {
            portalblockpositions.add(playerlocation.clone().add(3, 1, 0));
            portalblockpositions.add(playerlocation.clone().add(3, 1, -1));
            portalblockpositions.add(playerlocation.clone().add(3, 2, 0));
            portalblockpositions.add(playerlocation.clone().add(3, 2, -1));
            return portalblockpositions;   
        }
        else if (112.5 <= rotation && rotation < 157.5) {
            portalblockpositions.add(playerlocation.clone().add(2, 1, -3));
            portalblockpositions.add(playerlocation.clone().add(3, 1, -2));
            portalblockpositions.add(playerlocation.clone().add(2, 2, -3));
            portalblockpositions.add(playerlocation.clone().add(3, 2, -2));
            return portalblockpositions;  
        }
        else if (157.5 <= rotation && rotation < 202.5) {
            portalblockpositions.add(playerlocation.clone().add(1, 1, -3));
            portalblockpositions.add(playerlocation.clone().add(0, 1, -3));
            portalblockpositions.add(playerlocation.clone().add(1, 2, -3));
            portalblockpositions.add(playerlocation.clone().add(0, 2, -3));
            return portalblockpositions;  
        }
        else if (202.5 <= rotation && rotation < 247.5) {
            portalblockpositions.add(playerlocation.clone().add(-3, 1, -2));
            portalblockpositions.add(playerlocation.clone().add(-2, 1, -3));
            portalblockpositions.add(playerlocation.clone().add(-3, 2, -2));
            portalblockpositions.add(playerlocation.clone().add(-2, 2, -3));
            return portalblockpositions;  
        }
        else if (247.5 <= rotation && rotation < 292.5) {
            portalblockpositions.add(playerlocation.clone().add(-3, 1, 0));
            portalblockpositions.add(playerlocation.clone().add(-3, 1, 1));
            portalblockpositions.add(playerlocation.clone().add(-3, 2, 0));
            portalblockpositions.add(playerlocation.clone().add(-3, 2, 1));
            return portalblockpositions;   
        }
        else if (292.5 <= rotation && rotation < 337.5) {
            portalblockpositions.add(playerlocation.clone().add(-2, 1, 3));
            portalblockpositions.add(playerlocation.clone().add(-3, 1, 2));
            portalblockpositions.add(playerlocation.clone().add(-2, 2, 3));
            portalblockpositions.add(playerlocation.clone().add(-3, 2, 2));
            return portalblockpositions;  
        }

        //empty ArrayList => no portal creation
        return portalblockpositions;
    }

    // call this function only as BukkitTask
    public void update()
    {
        //remove old portals and teleport players thru portals
        for(int i = this.portallist.size() - 1 ; i > -1; i--)
        {
            if((System.currentTimeMillis() - this.portallist.get(i).getCreationTime()) > 30000)
            {
                //reset portalblocks
                for(int n = 0; n < this.portallist.get(i).getOriginalBlockMaterial().size(); n++)
                {            
                    this.portallist.get(i).getOriginalBlockMaterial().get(n).update(true);
                }

                //fance message to portal owner
                this.portallist.get(i).getOwner().sendMessage("Your portal is now closed.");
                
                //remove the portal from stack
                this.portallist.remove(i);
            }
        }

        if(this.portallist.size() > 0 || Bukkit.getOnlinePlayers().size() > 0)
        {
            ArrayList<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
            for(int i = 0; i < this.portallist.size(); i++)
            {
                for(int n = 0; n < players.size(); n++)
                {
                    if(players.get(i).hasPermission("rpgp.portal"))
                    {                    
                        Location blocklocation;
                        for(int m =0; m < this.portallist.get(i).getPortalBlockPositions().size(); m++)
                        {
                            blocklocation = this.portallist.get(i).getPortalBlockPositions().get(m);
                            int px =  players.get(n).getLocation().getBlockX();
                            int py =  players.get(n).getLocation().getBlockY();
                            int pz =  players.get(n).getLocation().getBlockZ();  

                            if(blocklocation.getBlockX() == px && blocklocation.getBlockY() == py && blocklocation.getBlockZ() == pz)
                            {
                                players.get(n).teleport(this.portallist.get(i).getDestination());
                            }
                        }
                    }
                }
            }
        }  
    }

}