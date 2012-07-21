package com.github.skiouros.Corpses.Listeners;

import com.github.skiouros.Corpses.Managers.CorpseManager;
import com.github.skiouros.Corpses.npclib.NPCManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;


public class PlayerListener implements Listener
{
    private CorpseManager corpseManager;

    public PlayerListener(JavaPlugin plugin, NPCManager npcManager)
    {
        corpseManager = new CorpseManager(plugin, npcManager);
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event)
    {
        Player player = event.getEntity();

        event.getDrops().clear();
        corpseManager.onDeath(player);
    }


}
