package net.potyka.jendrik.rpgp;
import net.potyka.jendrik.rpgp.events.ClickEvent;
import net.potyka.jendrik.rpgp.TownyWrapper;
import net.potyka.jendrik.rpgp.PortalUpdater;


import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;





public final class App extends JavaPlugin
{

    private boolean usetowny = false;
    private TownyWrapper townywrapper = null;

    private PortalManager portalmanager;
    private BukkitRunnable runportalupdater;
    private BukkitTask taskportalupdater;

    private InvManager invmanager;


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
        this.taskportalupdater = this.runportalupdater.runTaskTimer(this, 0, 5);
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


