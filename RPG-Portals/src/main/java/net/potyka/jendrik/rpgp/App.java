package net.potyka.jendrik.rpgp;
import net.potyka.jendrik.rpgp.events.ClickEvent;
import net.potyka.jendrik.rpgp.TownyWrapper;
import net.potyka.jendrik.rpgp.PortalUpdater;


import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.InvalidConfigurationException;

import java.io.*;


public final class App extends JavaPlugin
{

    private boolean usetowny = false;
    private TownyWrapper townywrapper = null;

    private PortalManager portalmanager;
    private BukkitRunnable runportalupdater;
    private BukkitTask taskportalupdater;

    private InvManager invmanager;

    private File customConfigFile;
    private FileConfiguration customConfig;

    private String netherworldname;


    public App()
    { 
        this.portalmanager = new PortalManager(this);
        this.invmanager =  new InvManager(this);
    }
 
    @Override
    public void onEnable() {
        getLogger().info("Start RPG-Portals");

        // config file
        this.saveDefaultConfig();
        this.customConfigFile = new File(getDataFolder(), "config.yml");
        this.customConfig = new YamlConfiguration();
        try
        {
            customConfig.load(customConfigFile);
        }
        catch (IOException | InvalidConfigurationException e) 
        {
            e.printStackTrace();
        }

        // load custom configs 
        long casttime = (long)this.customConfig.getInt("PortalStatusTimes.Cast");
        long activationtime = (long)this.customConfig.getInt("PortalStatusTimes.Activation");
        long activetime = (long)this.customConfig.getInt("PortalStatusTimes.Active");

        int maxnumberofparticles = this.customConfig.getInt("PortalCastAnimation.MaxNumberOfParticles");
        int numberofrotations = this.customConfig.getInt("PortalCastAnimation.NumberOfRotations");
        int maxradius = this.customConfig.getInt("PortalCastAnimation.MaxRadius");
        double particlespeed = this.customConfig.getDouble("PortalCastAnimation.ParticleSpeed");
        this.portalmanager.setConfig(casttime,activationtime,activetime,maxnumberofparticles,numberofrotations,maxradius,particlespeed);
    
        this.netherworldname = this.customConfig.getString("NetherWorldName");


        // register stuff
        this.getCommand("rpgp").setExecutor(new Rpgp(this));
        getServer().getPluginManager().registerEvents(new ClickEvent(this,this.getCommand("rpgp").getExecutor()), this);

        // enable towny feature
        if(Bukkit.getPluginManager().getPlugin("Towny") != null)
        {
            this.usetowny = true;
            this.townywrapper = new TownyWrapper(this);

            getLogger().info("RPG-Portals is now using Towny.");
        }
        else
        {
            getLogger().info("Towny not found.");
        }

        // start the update loop and save the task 
        this.runportalupdater = new PortalUpdater(this);
        this.taskportalupdater = this.runportalupdater.runTaskTimer(this, 0, 3);
    }

    @Override
    public void onDisable() {
        getLogger().info("Shutdown RPG-Portals");
    }

    public InvManager getIM()
    {
        return this.invmanager;
    }

    public PortalManager getPM()
    {
        return this.portalmanager;
    }

    public String getNetherWorldName()
    {
        return this.netherworldname;
    }


    // external plugins

    public boolean getUseTonwy()
    {
        return usetowny;
    }

    public TownyWrapper getTW()
    {
        return this.townywrapper;
    }

}


