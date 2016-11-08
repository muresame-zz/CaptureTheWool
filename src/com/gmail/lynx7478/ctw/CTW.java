package com.gmail.lynx7478.ctw;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.lynx7478.ctw.commands.AFKCommand;
import com.gmail.lynx7478.ctw.commands.CTWCommand;
import com.gmail.lynx7478.ctw.commands.RoleCommand;
import com.gmail.lynx7478.ctw.commands.TeamCommand;
import com.gmail.lynx7478.ctw.game.CTWPlayer;
import com.gmail.lynx7478.ctw.game.GameListeners;
import com.gmail.lynx7478.ctw.game.GameVars;
import com.gmail.lynx7478.ctw.game.Lobby;
import com.gmail.lynx7478.ctw.game.ScoreboardAPI;
import com.gmail.lynx7478.ctw.game.autorespawn.RespawnHandler;
import com.gmail.lynx7478.ctw.game.roles.Role;
import com.gmail.lynx7478.ctw.game.roles.RoleListener;
import com.gmail.lynx7478.ctw.game.roles.RoleManager;
import com.gmail.lynx7478.ctw.managers.AFKManager;
import com.gmail.lynx7478.ctw.mapbuilder.MapBuilder;
import com.gmail.lynx7478.ctw.menus.MenuManager;

/**
 * Created by SKA4 on 02/08/2016.
 */
public class CTW extends JavaPlugin {
	
	// JGG is a crack.

    private static CTW instance;

    public static CTW getInstance()
    {
        return instance;
    }
    
    private File worldDirectory;

    @Override
    public void onEnable()
    {
        instance = this;

        this.getDataFolder().mkdirs();

        // The game listeners.
        new GameListeners(this);

        // Loading the main configuration file.
        //TODO: Fix config, out of place.
        try {
            GameVars.loadConfiguration();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // Sets up the worlds folder.
        (this.worldDirectory = new File(this.getDataFolder()+File.separator+"Worlds")).mkdirs();

        // Lobby configuration.
        new Lobby();
        
        // For players that are online.
        for(Player p : Bukkit.getOnlinePlayers())
        {
            new CTWPlayer(p);
            p.setScoreboard(ScoreboardAPI.getScoreboard());
            Lobby.sendToLobby(p);
        }
        
        // Auto-respawn handler.
        RespawnHandler.register(this);
        
        new RoleManager();

        // Registering the interact event on the map builder.
        // This also initializes the mapbuilder.
        this.getServer().getPluginManager().registerEvents(new MapBuilder(), this);
        // Registering the role interact listener.
        this.getServer().getPluginManager().registerEvents(new RoleListener(), this);
        // Menu manager.
        this.getServer().getPluginManager().registerEvents(new MenuManager(), this);
        // Commands.
        this.getCommand("team").setExecutor(new TeamCommand());
        this.getCommand("ctw").setExecutor(new CTWCommand());
        this.getCommand("roles").setExecutor(new RoleCommand());
        this.getCommand("afk").setExecutor(new AFKCommand());
        new AFKManager(this);
        
        // Logs all the loaded roles to console, for debug purposes.
        this.getServer().getScheduler().runTaskLater(this, new Runnable()
        		{
        	public void run()
        	{
                // Initialize role manager.
                CTW.getInstance().getLogger().log(Level.INFO, "Loaded roles:");
                for(Role r : RoleManager.getRoles())
                {
                	CTW.getInstance().getLogger().log(Level.INFO, "--"+r.getName());
                }
        	}
        		}, 40L);
    }

    public void onDisable()
    {

    }
    
    public File getWorldDirectory()
    {
    	return this.worldDirectory;
    }
}
