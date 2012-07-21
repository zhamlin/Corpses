package com.github.skiouros.Corpses;

import com.github.skiouros.Corpses.Listeners.EntityListener;
import com.github.skiouros.Corpses.Listeners.PlayerListener;
import com.github.skiouros.Corpses.npclib.NPCManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class Corpses extends JavaPlugin
{
    private NPCManager npcManager;

    public void onEnable()
    {
        getLogger().info("Corpses Enabled");

        setupFiles();
        npcManager = new NPCManager(this);

        new PlayerListener(this, npcManager);
        new EntityListener(this, npcManager);
    }

    public void onDisable()
    {
        getLogger().info("Corpses Disabled");
    }

    private void setupFiles()
    {
        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }

        if(!(new File(this.getDataFolder(), "config.yml")).exists()) {
            saveDefaultConfig();
        }
    }
}
