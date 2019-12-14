package net.potyka.jendrik.rpgp;

import org.bukkit.Location;

public class Portal 
{
    int id;
    Location location;
    Location destination;

    public Portal(int id, Location location, Location destination)
    {
        this.id = id;
        this.location = location;
        this.destination = destination;
    }
}



