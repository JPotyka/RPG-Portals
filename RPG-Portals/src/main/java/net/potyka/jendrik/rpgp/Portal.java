package net.potyka.jendrik.rpgp;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;

public class Portal 
{
    private int id;
    private ArrayList<Location> portalblockpositions;
    private Location destination;
    private long creationtime;
    private ArrayList<BlockState> originalblockmaterial;
    private Player owner;

    public Portal(int id, ArrayList<Location> portalblockpositions, ArrayList<BlockState> originalblockmaterial, Location destination, long creationtime, Player player)
    {
        this.id = id;
        this.portalblockpositions = portalblockpositions;
        this.originalblockmaterial = originalblockmaterial;
        this.destination = destination;
        this.creationtime = creationtime;
        this.owner = player;

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

    public Location getDestination()
    {
        return this.destination;
    }

    public long getCreationTime()
    {
        return this.creationtime;
    }

    public Player getOwner()
    {
        return this.owner;
    }

}



