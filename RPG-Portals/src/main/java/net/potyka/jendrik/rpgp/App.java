package net.potyka.jendrik.rpgp;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * Hello world!
 */
public final class App extends JavaPlugin{
    private App() {
    }
    @Override
    public void onEnable() {
        getLogger().info("Hello, SpigotMC!");
    }
    @Override
    public void onDisable() {
        getLogger().info("See you again, SpigotMC!");
    }
}
