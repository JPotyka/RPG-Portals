package net.potyka.jendrik.rpgp;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * Hello world!
 */
public final class App extends JavaPlugin{
 
    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        this.getCommand("rpgp").setExecutor(new Rpgp(this));

        getLogger().info("Hello, SpigotMC!");
    }
    @Override
    public void onDisable() {
        getLogger().info("See you again, SpigotMC!");
    }


}
