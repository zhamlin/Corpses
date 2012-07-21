package com.github.skiouros.Corpses.npclib.entity;

import net.minecraft.server.EntityPlayer;
import net.minecraft.server.Packet17EntityLocationAction;
import net.minecraft.server.Packet18ArmAnimation;
import net.minecraft.server.WorldServer;
import com.github.skiouros.Corpses.npclib.nms.NPCEntity;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;


public class HumanNPC extends NPC {

	public HumanNPC(NPCEntity npcEntity) {
		super(npcEntity);
	}

	public void animateArmSwing() {
		((WorldServer) getEntity().world).tracker.a(getEntity(), new Packet18ArmAnimation(getEntity(), 1));
	}

	public void actAsHurt() {
		((WorldServer) getEntity().world).tracker.a(getEntity(), new Packet18ArmAnimation(getEntity(), 2));
	}

	public void setItemInHand(Material m) {
		setItemInHand(m, (short) 0);
	}

	public void setItemInHand(Material m, short damage) {
		((HumanEntity) getEntity().getBukkitEntity()).setItemInHand(new ItemStack(m, 1, damage));
	}

	public void setName(String name) {
		((NPCEntity) getEntity()).name = name;
	}

	public String getName() {
		return ((NPCEntity) getEntity()).name;
	}

    public World getWorld() {
        return getEntity().getBukkitEntity().getWorld();
    }

    public Location getLocation() {
        return getEntity().getBukkitEntity().getLocation();
    }

	public PlayerInventory getInventory() {
		return ((HumanEntity) getEntity().getBukkitEntity()).getInventory();
	}

    // Removed orginal putInBed code to allow for lying in bed anywhere.
	public void putInBed(Location bed) {

        Packet17EntityLocationAction packet = new Packet17EntityLocationAction(getEntity(), 0, bed.getBlockX(), bed.getBlockY(), bed.getBlockZ());

        for(Player p : bed.getWorld().getPlayers())
            ((CraftPlayer)p).getHandle().netServerHandler.sendPacket(packet);

		getEntity().setPosition(bed.getX(), bed.getY(), bed.getZ());
	}

	public void getOutOfBed() {
		((NPCEntity) getEntity()).a(true, true, true);
	}

	public void setSneaking() {
		getEntity().setSneak(true);
	}

	public void lookAtPoint(Location point) {
		if (getEntity().getBukkitEntity().getWorld() != point.getWorld()) {
			return;
		}
		Location npcLoc = ((LivingEntity) getEntity().getBukkitEntity()).getEyeLocation();
		double xDiff = point.getX() - npcLoc.getX();
		double yDiff = point.getY() - npcLoc.getY();
		double zDiff = point.getZ() - npcLoc.getZ();
		double DistanceXZ = Math.sqrt(xDiff * xDiff + zDiff * zDiff);
		double DistanceY = Math.sqrt(DistanceXZ * DistanceXZ + yDiff * yDiff);
		double newYaw = Math.acos(xDiff / DistanceXZ) * 180 / Math.PI;
		double newPitch = Math.acos(yDiff / DistanceY) * 180 / Math.PI - 90;
		if (zDiff < 0.0) {
			newYaw = newYaw + Math.abs(180 - newYaw) * 2;
		}
		getEntity().yaw = (float) (newYaw - 90);
		getEntity().pitch = (float) newPitch;
		((EntityPlayer)getEntity()).X = (float)(newYaw - 90);
	}
}
