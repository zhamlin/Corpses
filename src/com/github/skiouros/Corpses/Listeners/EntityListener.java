package com.github.skiouros.Corpses.Listeners;

import com.github.skiouros.Corpses.npclib.NPCManager;
import com.github.skiouros.Corpses.npclib.entity.HumanNPC;
import com.github.skiouros.Corpses.npclib.entity.NPC;
import com.github.skiouros.Corpses.npclib.nms.NpcEntityTargetEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class EntityListener implements Listener
{
    private NPCManager npcManager;

    public EntityListener(JavaPlugin plugin, NPCManager npcManager)
    {
        this.npcManager = npcManager;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onEntityTarget(EntityTargetEvent event)
    {
        if (event instanceof NpcEntityTargetEvent) {
            NpcEntityTargetEvent npcEvent = (NpcEntityTargetEvent) event;

            Player p = (Player) event.getTarget();
            String id = npcManager.getNPCIdFromEntity(event.getEntity());
            NPC npc = npcManager.getNPC(id);

            if (npcEvent.getNpcReason() == NpcEntityTargetEvent.NpcTargetReason.NPC_RIGHTCLICKED) {

                if (npc instanceof HumanNPC) {
                    p.openInventory(((HumanNPC) npc).getInventory());
                }
            }
        } else if (event.getTarget() != null && npcManager.isNPC(event.getTarget())) {
            // Don't allow mobs to target npcs
            event.setCancelled(true);
        }

    }
}
