package com.github.skiouros.Corpses.npclib.pathing;

import org.bukkit.Location;

import java.util.ArrayList;

public class NPCPath {

	private ArrayList<Node> path;
	private NPCPathFinder pathFinder;
	private Location end;

	public NPCPath(NPCPathFinder npcPathFinder, ArrayList<Node> path, Location end) {
		this.path = path;
		this.end = end;
		pathFinder = npcPathFinder;
	}

	public Location getEnd() {
		return end;
	}

	public ArrayList<Node> getPath() {
		return path;
	}

	public boolean checkPath(Node node, Node parent, boolean update) {
		return pathFinder.checkPath(node, parent, update);
	}

}
