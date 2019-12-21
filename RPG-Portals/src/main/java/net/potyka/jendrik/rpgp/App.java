package net.potyka.jendrik.rpgp;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;


import net.potyka.jendrik.rpgp.events.ClickEvent;
import net.potyka.jendrik.rpgp.PortalManager;
import net.potyka.jendrik.rpgp.PortalUpdater;
import net.potyka.jendrik.rpgp.TownyData;


public final class App extends JavaPlugin{

    private PortalManager portalmanager;
    private BukkitRunnable runportalmanager;
    private BukkitTask taskportalmanager;

    private boolean usetowny = false;
    private TownyData townydata = null;

    


    public App()
    {
        this.portalmanager = new PortalManager(this);
        
    }
 
    @Override
    public void onEnable() {
        getLogger().info("Start RPG-Portals");

        this.saveDefaultConfig();

        this.getCommand("rpgp").setExecutor(new Rpgp(this));
        
        getServer().getPluginManager().registerEvents(new ClickEvent(this,this.getCommand("rpgp").getExecutor()), this);

        // enable update loop
        if(this.portalmanager.isActive())
        {
            getLogger().info("Portalmanager enabled!");

            this.runportalmanager = new PortalUpdater(this);
            this.taskportalmanager = this.runportalmanager.runTaskTimer(this,0,5);
        }

        // enable towny feature
        if(Bukkit.getPluginManager().getPlugin("Towny") != null)
        {
            this.usetowny = true;
            this.townydata = new TownyData(this);

            getLogger().info("RPG-Portals is now using Towny.");
        }
        else
        {
            getLogger().info("Towny not found.");
        }

    }
    @Override
    public void onDisable() {
        getLogger().info("Shutdown RPG-Portals");
    }

    public PortalManager getPortalManager()
    {
        return this.portalmanager;
    }

    public boolean getUseTonwy()
    {
        return usetowny;
    }

    public TownyData getTownyData()
    {
        return this.townydata;
    }
}


