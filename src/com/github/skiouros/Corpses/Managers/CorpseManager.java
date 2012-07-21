package com.github.skiouros.Corpses.Managers;

import com.github.skiouros.Corpses.CorpseDecay;
import net.minecraft.server.Packet17EntityLocationAction;
import com.github.skiouros.Corpses.npclib.NPCManager;
import com.github.skiouros.Corpses.npclib.entity.HumanNPC;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class CorpseManager
{
    private Map<Player, HumanNPC> npcMap;
    private Map<Player, Integer> taskMap;

    private JavaPlugin plugin;

    private NPCManager npcManager;


    public CorpseManager(JavaPlugin plugin, NPCManager npcManager)
    {
        npcMap = new HashMap<>();
        taskMap = new HashMap<>();

        this.plugin = plugin;
        this.npcManager = npcManager;

        long updateInterval = plugin.getConfig().getLong("updateInterval");
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable()
        {
            @Override
            public void run()
            {
                updatePlayers();
            }
        }, updateInterval, updateInterval);
    }

    // Resend packets to players showing corpses lying in bed.
    // If not sent corpses will appear to be in the ground.
    public void updatePlayer(Player player)
    {
        for (HumanNPC npc : npcMap.values().toArray(new HumanNPC[0])) {
            Location loc = npc.getLocation();

            Packet17EntityLocationAction packet = new Packet17EntityLocationAction(npc.getEntity(), 0, loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
            ((CraftPlayer)player).getHandle().netServerHandler.sendPacket(packet);
        }
    }

    public void updatePlayers()
    {
        for (Player player : Bukkit.getOnlinePlayers()) {
            updatePlayer(player);
        }
    }

    public void onDeath(Player player)
    {
        // Empty all items on npc if player died again.
        if (npcMap.containsKey(player)) {
            dumpItems(npcMap.get(player));
            npcMap.remove(player);
        }

        String playerId = String.valueOf(player.getUniqueId());
        String name = player.getName() + (player.getName().charAt(player.getName().length() - 1) == 's' ? "'" : "'s") + " CORPSE";
        HumanNPC npc = (HumanNPC) npcManager.spawnHumanNPC(name, player.getLocation(), playerId);

        // All items that don't fit in inventory will be added to list and dropped on ground.
        List<ItemStack> items = new ArrayList<>();
        ItemStack[] armor = player.getInventory().getArmorContents();
        items.addAll(Arrays.asList(armor));


        npc.getInventory().setContents(player.getInventory().getContents());
        Map<Integer, ItemStack> notDroped = npc.getInventory().addItem(items.toArray(new ItemStack[0]));

        // If some items didn't fit drop them.
        if (notDroped != null && notDroped.size() > 0) {
            dumpItems(notDroped.values().toArray(new ItemStack[0]), npc);
        }

        npc.putInBed(player.getLocation());
        npcMap.put(player, npc);

        if (plugin.getConfig().getBoolean("decay")) {
            decay(player, playerId, npc);
        }

    }

    private void decay(Player player, String playerId, HumanNPC npc)
    {
        if (taskMap.containsKey(player))
            removeTask(player);

        // Remove corpse and drop items after set time
        int id = Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new CorpseDecay(npcManager, npc, playerId), plugin.getConfig().getLong("decayTime") * 20 * 60);
        taskMap.put(player, id);
    }

    private void removeTask(Player player)
    {
        int id = taskMap.get(player);
        if (id != 0)
            Bukkit.getScheduler().cancelTask(id);
    }

    private void dumpItems(HumanNPC npc)
    {
        ItemStack[] itemStacks = npc.getInventory().getContents();
        dumpItems(itemStacks, npc);
    }

    private void dumpItems(ItemStack[] itemStacks, HumanNPC npc)
    {
        for (ItemStack stack : itemStacks) {
            if (stack != null) {
                npc.getWorld().dropItemNaturally(npc.getLocation(), stack);
            }
        }
    }

}
