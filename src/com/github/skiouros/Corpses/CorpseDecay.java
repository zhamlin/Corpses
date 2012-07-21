package com.github.skiouros.Corpses;

import com.github.skiouros.Corpses.npclib.NPCManager;
import com.github.skiouros.Corpses.npclib.entity.HumanNPC;
import org.bukkit.inventory.ItemStack;

public class CorpseDecay implements Runnable
{
    private HumanNPC npc;
    private String id;
    private NPCManager npcManager;

    public CorpseDecay(NPCManager npcManager, HumanNPC npc, String id)
    {
        this.npcManager = npcManager;
        this.npc = npc;
        this.id = id;
    }

    @Override
    public void run()
    {
        ItemStack[] contents = npc.getInventory().getContents();
        for (ItemStack item : contents) {
            if (item == null) continue;
            npc.getWorld().dropItemNaturally(npc.getLocation(), item);
        }

        npcManager.despawnById(id);
    }
}
