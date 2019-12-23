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

    // player
    private Player owner;
    private Location castinglcocation;


    

    public enum PortalStatus
    {
        Created,Cast,Active,ToRemove
    }

    private PortalStatus portalstatus;

    public Portal(int id, ArrayList<Location> portalblockpositions, Location destination, long creationtime, Player player)
    {
        this.id = id;
        this.portalblockpositions = portalblockpositions;
        this.destination = destination;
        this.lastupdatetime = creationtime;
        this.owner = player;
        this.castinglcocation = player.getLocation();

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
        return this.castinglcocation;
    }

    public PortalStatus getPortalStatus()
    {
        return portalstatus;
    }

    public void setPortalStatus(PortalStatus portalstatus)
    {
        this.portalstatus = portalstatus;
    }

}



