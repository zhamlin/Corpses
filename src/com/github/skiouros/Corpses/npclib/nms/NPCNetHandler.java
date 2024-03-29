package com.github.skiouros.Corpses.npclib.nms;

import net.minecraft.server.*;
import com.github.skiouros.Corpses.npclib.NPCManager;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.entity.CraftPlayer;


/**
 *
 * @author martin
 */
public class NPCNetHandler extends NetServerHandler {

	public NPCNetHandler(NPCManager npcManager, EntityPlayer entityplayer) {
		super(npcManager.getServer().getMCServer(), npcManager.getNPCNetworkManager(), entityplayer);
	}

	@Override
	public CraftPlayer getPlayer() {
		return new CraftPlayer((CraftServer) Bukkit.getServer(), player); //Fake player prevents spout NPEs
	}

	@Override
	public void a() {
	};

	@Override
	public void a(Packet10Flying packet10flying) {
	};

	@Override
	public void sendMessage(String s) {
	};

	@Override
	public void a(double d0, double d1, double d2, float f, float f1) {
	};

	@Override
	public void a(Packet14BlockDig packet14blockdig) {
	};

	@Override
	public void a(Packet15Place packet15place) {
	};

	@Override
	public void a(String s, Object[] aobject) {
	};

	@Override
	public void onUnhandledPacket(Packet packet) {
	};

	@Override
	public void a(Packet16BlockItemSwitch packet16blockitemswitch) {
	};

	@Override
	public void a(Packet3Chat packet3chat) {
	};

	@Override
	public void a(Packet18ArmAnimation packet18armanimation) {
	};

	@Override
	public void a(Packet19EntityAction packet19entityaction) {
	};

	@Override
	public void a(Packet255KickDisconnect packet255kickdisconnect) {
	};

	@Override
	public void sendPacket(Packet packet) {
	};

	@Override
	public void a(Packet7UseEntity packet7useentity) {
	};

	@Override
	public void a(Packet9Respawn packet9respawn) {
	};

	@Override
	public void handleContainerClose(Packet101CloseWindow packet101closewindow) {
	};

	@Override
	public void a(Packet102WindowClick packet102windowclick) {
	};

	@Override
	public void a(Packet106Transaction packet106transaction) {
	};

	@Override
	public int lowPriorityCount() {
		return super.lowPriorityCount();
	}

	@Override
	public void a(Packet130UpdateSign packet130updatesign) {
	};

}
