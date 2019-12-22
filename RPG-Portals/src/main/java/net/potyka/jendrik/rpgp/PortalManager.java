package net.potyka.jendrik.rpgp;
import net.potyka.jendrik.rpgp.App;
import net.potyka.jendrik.rpgp.Portal.PortalStatus;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.block.BlockState;
import org.bukkit.Material;
import org.bukkit.Bukkit;



public class PortalManager
{
    private App app;
    private ArrayList<Portal> portallist;
    private int nextid;

    // portal status times
    private long activetime;
    //private long deadtime;

    public PortalManager(App app)
    {
        this.app = app;
        this.portallist = new ArrayList<>();
        this.nextid = 0;

        this.activetime = 30000;
        //this.deadtime = 5000;
    }

    public void setPortalStatusTimes(long activetime)
    {
        this.activetime = activetime;
    }

    public long getPortalActivTime()
    {
        return this.activetime;
    }

    public boolean createPortal(Player player, Location destination)
    {
        Location playerlocation = player.getLocation();
        float playeryaw = playerlocation.getYaw();

        ArrayList<Location> portalblockpositions = createPortalBlockPosition(Math.abs(playeryaw),playerlocation);

        if(portalblockpositions != null)
        {
            this.portallist.add(new Portal(this.nextid, portalblockpositions, destination, System.currentTimeMillis(), player));
            this.nextid = this.nextid + 1;
            return true;
        }

        return false;
    }

    public void update()
    {
        // teleport
        teleportEntitys();

        // update portals
        updatePortals();

    }

    private ArrayList<Location> createPortalBlockPosition(float rotation, Location playerlocation)
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

        return null;
    }

    private void teleportEntitys()
    {
        // currently only players
        if(this.portallist.size() > 0 || Bukkit.getOnlinePlayers().size() > 0)
        {
            ArrayList<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
            for(int i = 0; i < this.portallist.size(); i++)
            {
                if(this.portallist.get(i).getPortalStatus() == PortalStatus.Active)
                {
                    for(int n = 0; n < players.size(); n++)
                    {
                        if(players.get(n).hasPermission("rpgp.portal"))
                        {                    
                            Location blocklocation;
                            for(int m =0; m < this.portallist.get(i).getPortalBlockPositions().size(); m++)
                            {
                                blocklocation = this.portallist.get(i).getPortalBlockPositions().get(m);
                                int px =  players.get(n).getLocation().getBlockX();
                                int py =  players.get(n).getLocation().getBlockY() + 1;
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

    private void updatePortals()
    {
        for(int i = this.portallist.size() - 1 ; i > -1; i--)
        {
            PortalStatus portalstatus = this.portallist.get(i).getPortalStatus(); 
            
            switch (portalstatus)
            {
                case Created:
                    this.portallist.get(i).setPortalStatus(PortalStatus.Active);
                    placePortalBlocks(i);
                    this.portallist.get(i).setUpdateTime(System.currentTimeMillis());
                break;
                
                case Active:
                    if((System.currentTimeMillis() - this.portallist.get(i).getLastUpdateTime()) > this.activetime)
                    {
                        this.portallist.get(i).setPortalStatus(PortalStatus.ToRemove);
                        replacePortalBlocks(i);
                        this.portallist.get(i).setUpdateTime(System.currentTimeMillis());

                        //fance message to portal owner
                        this.portallist.get(i).getOwner().sendMessage("Your portal is now closed.");
                    }
                break;

                case ToRemove:
                    this.portallist.remove(i);                   
                break;

                default:

                break;
            }

        }
    }

    private boolean placePortalBlocks(int n)
    {

        ArrayList<BlockState> originalblockmaterial = new ArrayList<>();

        for(int i =0; i < this.portallist.get(n).getPortalBlockPositions().size();i++)
        {
            originalblockmaterial.add(this.portallist.get(n).getPortalBlockPositions().get(i).getBlock().getState());
        }

        this.portallist.get(n).setOriginalBlockMaterial(originalblockmaterial);

        for (int i = 0; i < this.portallist.get(n).getPortalBlockPositions().size(); i++) 
        {
            this.portallist.get(n).getPortalBlockPositions().get(i).getBlock().setType(Material.END_GATEWAY);
        }

        return true;
    }

    private boolean replacePortalBlocks(int i)
    {
        //reset portalblocks to previous block state
        for(int n = 0; n < this.portallist.get(i).getOriginalBlockMaterial().size(); n++)
        {            
            this.portallist.get(i).getOriginalBlockMaterial().get(n).update(true);
        }
        
        return true;   
    }


}
