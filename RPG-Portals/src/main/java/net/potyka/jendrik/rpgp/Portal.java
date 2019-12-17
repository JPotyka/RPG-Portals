package net.potyka.jendrik.rpgp;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;

public class Portal 
{
    private int id;
    private ArrayList<Location> portalblockpositions;
    private Location destination;
    private long creationtime;
    private ArrayList<BlockData> originalblockmaterial;
    private Player owner;

    public Portal(int id, ArrayList<Location> portalblockpositions, Location destination, long creationtime, Player player)
    {
        this.id = id;
        this.portalblockpositions = portalblockpositions;
        this.destination = destination;
        this.creationtime = creationtime;
        this.owner = player;

        //for(int i =0; i < this.portalblockpositions.size();i++)
        //{
        //    Bukkit.getServer().getLogger().info(String.valueOf(i));
        //    this.originalblockmaterial.add(this.portalblockpositions.get(i).getBlock().getBlockData());
        //}

    }

    public int getID()
    {
        return this.id;
    }

    public ArrayList<Location> getPortalBlockPositions()
    {
        return this.portalblockpositions;
    }

    public ArrayList<BlockData> getOriginalBlockMaterial()
    {
        return this.originalblockmaterial;
    }   

    public Location getLocation()
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



