package net.potyka.jendrik.rpgp;
import net.potyka.jendrik.rpgp.App;
import net.potyka.jendrik.rpgp.Portal.PortalStatus;

import java.util.ArrayList;
import java.util.Random;

import javax.lang.model.util.ElementScanner6;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.block.BlockState;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;






public class PortalManager
{
    private App app;
    private ArrayList<Portal> portallist;
    private int nextid;

    private Random randomgenerator;

    // portal status times
    private long casttime;
    private long activationtime;
    private long activetime;

    // animation settings
    private int maxanimationparticles;
    private double numberrotations; // number of the rotations around the player in one cast time
    private double timeforrotation; // the time a particle have for one rotation 
    private double maxradius; // max radius for the particles from the player
    private double particlespeed; // speed of particles in the rotation
    private ArrayList<Double> angleoffset;



    public PortalManager(App app)
    {
        this.app = app;
        this.portallist = new ArrayList<>();
        this.nextid = 0;

        this.randomgenerator = new Random();

        this.casttime = 15000;
        this.activationtime = 5000;
        this.activetime = 30000;


        this.maxanimationparticles = 12;
        this.numberrotations = 2;
        this.maxradius = 3;
        this.particlespeed = 0.01;
        this.angleoffset = new ArrayList<>();

        updateParticlePositions();

    }

    public void setConfig( long casttime, long activationtime, long activetime, int maxanimationparticles, int numberrotations, int maxradius, double particlespeed)
    {
        this.activetime = activetime;
        this.casttime = casttime;

        this.maxanimationparticles = maxanimationparticles;
        this.numberrotations = numberrotations;
        this.maxradius = maxradius;
        this.particlespeed = particlespeed;

        updateParticlePositions();
    }

    public long getPortalActivTime()
    {
        return this.activetime;
    }

    public boolean createPortal(Player player, Location destination)
    {
        Location playerlocation = player.getLocation();
        float playeryaw = playerlocation.getYaw();

        ArrayList<Location> portalblockpositions = createPortalBlockPosition(Math.abs(playeryaw),playerlocation);

        if(portalblockpositions != null)
        {
            this.portallist.add(new Portal(this.nextid, portalblockpositions, destination, System.currentTimeMillis(), player));
            this.nextid = this.nextid + 1;
            return true;
        }

        return false;
    }

    public void update()
    {
        // teleport
        teleportEntitys();

        // update portals
        updatePortals();

    }

    private ArrayList<Location> createPortalBlockPosition(float rotation, Location playerlocation)
    {
        ArrayList<Location> portalblockpositions = new ArrayList<>();

        if (0 <= rotation && rotation < 22.5) {
            portalblockpositions.add(playerlocation.clone().add(-1, 1, 3));
            portalblockpositions.add(playerlocation.clone().add(0,  1, 3));
            portalblockpositions.add(playerlocation.clone().add(-1, 2, 3));
            portalblockpositions.add(playerlocation.clone().add(0,  2, 3));
            return portalblockpositions;            
        }
        else if (337.5 <= rotation && rotation <= 360) {
            portalblockpositions.add(playerlocation.clone().add(-1, 1, 3));
            portalblockpositions.add(playerlocation.clone().add(0,  1, 3));
            portalblockpositions.add(playerlocation.clone().add(-1, 2, 3));
            portalblockpositions.add(playerlocation.clone().add(0,  2, 3));
            return portalblockpositions;   
        }
        else if (22.5 <= rotation && rotation < 67.5) {
            portalblockpositions.add(playerlocation.clone().add(3, 1, 2));
            portalblockpositions.add(playerlocation.clone().add(2, 1, 3));
            portalblockpositions.add(playerlocation.clone().add(3, 2, 2));
            portalblockpositions.add(playerlocation.clone().add(2, 2, 3));
            return portalblockpositions;   
        }
        else if (67.5 <= rotation && rotation < 112.5) {
            portalblockpositions.add(playerlocation.clone().add(3, 1, 0));
            portalblockpositions.add(playerlocation.clone().add(3, 1, -1));
            portalblockpositions.add(playerlocation.clone().add(3, 2, 0));
            portalblockpositions.add(playerlocation.clone().add(3, 2, -1));
            return portalblockpositions;   
        }
        else if (112.5 <= rotation && rotation < 157.5) {
            portalblockpositions.add(playerlocation.clone().add(2, 1, -3));
            portalblockpositions.add(playerlocation.clone().add(3, 1, -2));
            portalblockpositions.add(playerlocation.clone().add(2, 2, -3));
            portalblockpositions.add(playerlocation.clone().add(3, 2, -2));
            return portalblockpositions;  
        }
        else if (157.5 <= rotation && rotation < 202.5) {
            portalblockpositions.add(playerlocation.clone().add(1, 1, -3));
            portalblockpositions.add(playerlocation.clone().add(0, 1, -3));
            portalblockpositions.add(playerlocation.clone().add(1, 2, -3));
            portalblockpositions.add(playerlocation.clone().add(0, 2, -3));
            return portalblockpositions;  
        }
        else if (202.5 <= rotation && rotation < 247.5) {
            portalblockpositions.add(playerlocation.clone().add(-3, 1, -2));
            portalblockpositions.add(playerlocation.clone().add(-2, 1, -3));
            portalblockpositions.add(playerlocation.clone().add(-3, 2, -2));
            portalblockpositions.add(playerlocation.clone().add(-2, 2, -3));
            return portalblockpositions;  
        }
        else if (247.5 <= rotation && rotation < 292.5) {
            portalblockpositions.add(playerlocation.clone().add(-3, 1, 0));
            portalblockpositions.add(playerlocation.clone().add(-3, 1, 1));
            portalblockpositions.add(playerlocation.clone().add(-3, 2, 0));
            portalblockpositions.add(playerlocation.clone().add(-3, 2, 1));
            return portalblockpositions;   
        }
        else if (292.5 <= rotation && rotation < 337.5) {
            portalblockpositions.add(playerlocation.clone().add(-2, 1, 3));
            portalblockpositions.add(playerlocation.clone().add(-3, 1, 2));
            portalblockpositions.add(playerlocation.clone().add(-2, 2, 3));
            portalblockpositions.add(playerlocation.clone().add(-3, 2, 2));
            return portalblockpositions;  
        }

        return null;
    }

    private void teleportEntitys()
    {
        // currently only players
        if(this.portallist.size() > 0 || Bukkit.getOnlinePlayers().size() > 0)
        {
            ArrayList<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
            for(int i = 0; i < this.portallist.size(); i++)
            {
                if(this.portallist.get(i).getPortalStatus() == PortalStatus.Active)
                {
                    for(int n = 0; n < players.size(); n++)
                    {
                        if(players.get(n).hasPermission("rpgp.portal"))
                        {                    
                            Location blocklocation;
                            for(int m =0; m < this.portallist.get(i).getPortalBlockPositions().size(); m++)
                            {
                                blocklocation = this.portallist.get(i).getPortalBlockPositions().get(m);
                                int px =  players.get(n).getLocation().getBlockX();
                                int py =  players.get(n).getLocation().getBlockY() + 1;
                                int pz =  players.get(n).getLocation().getBlockZ();  

                                if(blocklocation.getBlockX() == px && blocklocation.getBlockY() == py && blocklocation.getBlockZ() == pz)
                                {
                                    players.get(n).teleport(this.portallist.get(i).getDestination());
                                }
                            }
                        }
                    }
                }
            }
        }  
    }   

    private void updatePortals()
    {
        for(int i = this.portallist.size() - 1 ; i > -1; i--)
        {
            PortalStatus portalstatus = this.portallist.get(i).getPortalStatus(); 
            
            switch (portalstatus)
            {
                case Created:
                    this.portallist.get(i).setPortalStatus(PortalStatus.Cast);
                break;

                case Cast:
                    if((System.currentTimeMillis()-this.portallist.get(i).getLastUpdateTime())>this.casttime)
                    {
                        this.portallist.get(i).getOwner().spigot().sendMessage(ChatMessageType.ACTION_BAR,TextComponent.fromLegacyText("Portal incantation: "+ChatColor.GOLD+"100%"));
                        this.portallist.get(i).setPortalStatus(PortalStatus.Activation);
                        this.portallist.get(i).setUpdateTime(System.currentTimeMillis());
                    }
                    else
                    {
                        if(this.portallist.get(i).getCastingLocation().getBlockX() == this.portallist.get(i).getOwner().getLocation().getBlockX() &&
                        this.portallist.get(i).getCastingLocation().getBlockY() == this.portallist.get(i).getOwner().getLocation().getBlockY() && 
                        this.portallist.get(i).getCastingLocation().getBlockZ() == this.portallist.get(i).getOwner().getLocation().getBlockZ())
                        {
                            castAnimation(i);
                        }
                        else
                        {
                            this.portallist.get(i).getOwner().spigot().sendMessage(ChatMessageType.ACTION_BAR,TextComponent.fromLegacyText("Protal incantation aborted, you must stand still to concentrate!"));
                            this.portallist.get(i).setPortalStatus(PortalStatus.ToRemove);
                            this.portallist.get(i).setUpdateTime(System.currentTimeMillis());
                        }
                    }
                break;

                case Activation:
                    if((System.currentTimeMillis()-this.portallist.get(i).getLastUpdateTime())>this.activationtime)
                    {
                        this.portallist.get(i).setPortalStatus(PortalStatus.Active);
                        placePortalBlocks(i);
                        this.portallist.get(i).setUpdateTime(System.currentTimeMillis());
                        this.portallist.get(i).getOwner().spigot().sendMessage(ChatMessageType.ACTION_BAR,TextComponent.fromLegacyText("Portal is now for "+ChatColor.GOLD+String.valueOf((int)this.activetime/1000)+" seconds"+ChatColor.WHITE+" open."));
                    }
                    else
                    {
                        activationAnimation(i);
                    }
                break;
                
                case Active:
                    if((System.currentTimeMillis() - this.portallist.get(i).getLastUpdateTime()) > this.activetime)
                    {
                        this.portallist.get(i).setPortalStatus(PortalStatus.ToRemove);
                        replacePortalBlocks(i);
                        this.portallist.get(i).setUpdateTime(System.currentTimeMillis());

                        //fance message to portal owner
                        this.portallist.get(i).getOwner().sendMessage("Your portal is now closed.");
                    }
                break;

                case ToRemove:
                    this.portallist.remove(i);                   
                break;

                default:

                break;
            }

        }
    }


    private void updateParticlePositions()
    {
        this.angleoffset.clear();
        this.timeforrotation = this.casttime/this.numberrotations;
        double stepsize = 2*Math.PI/this.maxanimationparticles;
        for (int i = 0; i < this.maxanimationparticles; i++) 
        {
            this.angleoffset.add(stepsize*i);
        }
    }

    // Cast portal
    private boolean castAnimation(int i) // i portallist index
    {
        int time = (int)System.currentTimeMillis()-(int)this.portallist.get(i).getLastUpdateTime();
        double percentage = (double)time/(int)this.casttime;
        this.portallist.get(i).getOwner().spigot().sendMessage(ChatMessageType.ACTION_BAR,TextComponent.fromLegacyText("Portal casting: "+ChatColor.GOLD+String.valueOf((int)(100*percentage))+"%"));

        World world = this.portallist.get(i).getOwner().getWorld();

        for(int np = 0; np < this.angleoffset.size(); np++)
        {
            // partictle spawn position in a celindirc shape around the player with increasing radius
            double angle = 2*Math.PI*time/this.timeforrotation+this.angleoffset.get(np);
            double r = this.maxradius * percentage;
            double x = r*Math.cos(angle) + this.portallist.get(i).getCastingLocation().getBlockX() + 0.5;
            double z = r*Math.sin(angle) + this.portallist.get(i).getCastingLocation().getBlockZ() + 0.5;
            double y = this.portallist.get(i).getCastingLocation().getBlockY() + 0.01*randomgenerator.nextInt(300);

            // particle movement direction
            double vectorx = -Math.sin(angle) ;
            double vectory = 0;
            double vectorz = Math.cos(angle);

            world.spawnParticle(Particle.DRAGON_BREATH, x, y, z, 0, vectorx, vectory, vectorz, this.particlespeed);
        }
        return true;
    }

    // Activation (animation) of the portal 
    private boolean activationAnimation(int i) //i portal index
    {
        int time = (int)System.currentTimeMillis()-(int)this.portallist.get(i).getLastUpdateTime();
        double percentage = (double)time/(int)this.activationtime;
        World world = this.portallist.get(i).getOwner().getWorld();

        for(int np = 0; np < this.angleoffset.size(); np++)
        {
            // partictle spawn position in a celindirc shape around the player with increasing radius
            double angle = 2*Math.PI*time/this.timeforrotation+this.angleoffset.get(np);
            double r = this.maxradius *(1-percentage);
            double x = r*Math.cos(angle) + this.portallist.get(i).getCastingLocation().getBlockX() + 0.5 + (this.portallist.get(i).getDirectionX()+0.5)*percentage;
            double z = r*Math.sin(angle) + this.portallist.get(i).getCastingLocation().getBlockZ() + 0.5 + (this.portallist.get(i).getDirectionZ()+0.5)*percentage;
            double y = this.portallist.get(i).getCastingLocation().getBlockY() + 0.01*randomgenerator.nextInt((int)(300*(1-percentage))) + (this.portallist.get(i).getDirectionY()+0.5)*percentage;

            // particle movement direction
            double vectorx = -Math.sin(angle) ;
            double vectory = 0;
            double vectorz = Math.cos(angle);

            world.spawnParticle(Particle.DRAGON_BREATH, x, y, z, 0, vectorx, vectory, vectorz, this.particlespeed*(1-percentage));
        }
        return true;
    }

    // Active portal
    private boolean placePortalBlocks(int n) // n portallist index
    {

        ArrayList<BlockState> originalblockmaterial = new ArrayList<>();

        for(int i =0; i < this.portallist.get(n).getPortalBlockPositions().size();i++)
        {
            originalblockmaterial.add(this.portallist.get(n).getPortalBlockPositions().get(i).getBlock().getState());
        }

        this.portallist.get(n).setOriginalBlockMaterial(originalblockmaterial);

        for (int i = 0; i < this.portallist.get(n).getPortalBlockPositions().size(); i++) 
        {
            this.portallist.get(n).getPortalBlockPositions().get(i).getBlock().setType(Material.END_GATEWAY);
        }

        return true;
    }

    // ToRemove portal
    private boolean replacePortalBlocks(int i) // i portallist
    {
        //reset portalblocks to previous block state
        for(int n = 0; n < this.portallist.get(i).getOriginalBlockMaterial().size(); n++)
        {            
            this.portallist.get(i).getOriginalBlockMaterial().get(n).update(true);
        }
        
        return true;   
    }


}
