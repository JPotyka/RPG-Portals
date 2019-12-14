package net.potyka.jendrik.rpgp;

import org.bukkit.plugin.java.JavaPlugin;
import net.potyka.jendrik.rpgp.events.ClickEvent;
import net.potyka.jendrik.rpgp.PortalManager;

/**
 * Hello world!
 */
public final class App extends JavaPlugin{

    PortalManager portalmanager;

    public App()
    {
        this.portalmanager = new PortalManager(this); 
    }
 
    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        this.getCommand("rpgp").setExecutor(new Rpgp(this));
        getServer().getPluginManager().registerEvents(new ClickEvent(this), this);

        getLogger().info("Hello, SpigotMC!");

        if(this.portalmanager.isActive())
        {
            getLogger().info("Portalmanager enabled!");
        }
    }
    @Override
    public void onDisable() {
        getLogger().info("See you again, SpigotMC!");
    }

    public PortalManager getPortalManager()
    {
        return this.portalmanager;
    }


}
