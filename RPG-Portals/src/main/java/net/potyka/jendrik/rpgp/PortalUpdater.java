package net.potyka.jendrik.rpgp;


import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class PortalUpdater extends BukkitRunnable
{
    private App app;

    public PortalUpdater(App app)
    {
        this.app = app;
    }

    @Override
    public void run()
    {
        this.app.getPortalManager().update();
    }
}