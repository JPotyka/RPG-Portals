package net.potyka.jendrik.rpgp;
import net.potyka.jendrik.rpgp.App;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;








public class Rpgp implements CommandExecutor
{
    private App app;
    
    public Rpgp(App app)
    {
        this.app = app;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {   
        
                // rpgp
        // open GUI
        if(label.equalsIgnoreCase("rpgp"))
        {
            if(!(sender instanceof Player))
            {
                sender.sendMessage("You must be a player to open the rpgp GUI.");
                return true;
            }
            if(sender.hasPermission("rpgp.opengui"))
            {     
                Player player = (Player) sender;
                app.getIM().openMainGUI(player);
                return true;
            }

            sender.sendMessage("You don't have the permission do open the this rpgp GUI.");
            return true;    
        }

        return false;
    }
}