package net.potyka.jendrik.rpgp;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;


public class Portal 
{
    // portal
    private int id;
    private ArrayList<Location> portalblockpositions;
    private Location destination;
    private long lastupdatetime;
    private ArrayList<BlockState> originalblockmaterial;
    private Location portalcentre;

    // player
    private Player owner;
    private Location castinglocation;

    // difference from castingposition to the portal
    private double vectorx;
    private double vectory;
    private double vectorz;



    

    public enum PortalStatus
    {
        Created,Cast,Activation,Active,ToRemove
    }

    private PortalStatus portalstatus;

    public Portal(int id, ArrayList<Location> portalblockpositions, Location destination, long creationtime, Player player)
    {
        this.id = id;
        this.portalblockpositions = portalblockpositions;
        this.destination = destination;
        this.lastupdatetime = creationtime;
        this.owner = player;
        this.castinglocation = player.getLocation();

        // calculate centre of the portal
        double x = 0;
        double y = 0;
        double z = 0;
        for (int i = 0; i < this.portalblockpositions.size(); i++) 
        {
            x = x + this.portalblockpositions.get(i).getBlockX();
            y = y + this.portalblockpositions.get(i).getBlockY();
            z = z + this.portalblockpositions.get(i).getBlockZ();
        }
        x = x/4;
        y = y/4;
        z = z/4;
        this.portalcentre = new Location(player.getWorld(), x, y, z);

        // calculate vector difference from castinglocation to portal centre
        this.vectorx = this.portalcentre.getX() - this.castinglocation.getX();
        this.vectory = this.portalcentre.getY() - this.castinglocation.getY();
        this.vectorz = this.portalcentre.getZ() - this.castinglocation.getZ();

        this.portalstatus = PortalStatus.Created;
    }

    public int getID()
    {
        return this.id;
    }

    public ArrayList<Location> getPortalBlockPositions()
    {
        return this.portalblockpositions;
    }

    public ArrayList<BlockState> getOriginalBlockMaterial()
    {
        return this.originalblockmaterial;
    }   

    public void setOriginalBlockMaterial(ArrayList<BlockState> originalblockmaterial)
    {
        this.originalblockmaterial = originalblockmaterial;
    }

    public Location getDestination()
    {
        return this.destination;
    }

    public long getLastUpdateTime()
    {
        return this.lastupdatetime;
    }

    public void setUpdateTime(long newupdatetime)
    {
        this.lastupdatetime = newupdatetime;
    }

    public Player getOwner()
    {
        return this.owner;
    }

    public Location getCastingLocation()
    {
        return this.castinglocation;
    }

    public PortalStatus getPortalStatus()
    {
        return portalstatus;
    }

    public void setPortalStatus(PortalStatus portalstatus)
    {
        this.portalstatus = portalstatus;
    }

    public double getDirectionX()
    {
        return this.vectorx;
    }

    public double getDirectionY()
    {
        return this.vectory;
    }

    public double getDirectionZ()
    {
        return this.vectorz;
    }


}



