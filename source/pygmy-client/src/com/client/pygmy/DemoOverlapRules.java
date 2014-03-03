package com.client.pygmy;

import com.lib.pygmy.base.Overlap;
import com.lib.pygmy.AbstractOverlapRulesApplier;
import com.lib.pygmy.GameUniverse;

import java.awt.Point;
import java.util.Vector;

public class DemoOverlapRules extends AbstractOverlapRulesApplier {
	
	protected GameUniverse universe;

	// Time duration during which pacman is invulnerable and during which ghosts
	// can be eaten (in number of cycles)
	static final int INVULNERABLE_DURATION = 60;
	protected Point pacManStartPos;
	protected Point ghostStartPos;
	protected boolean managePacmanDeath;

	public DemoOverlapRules(Point pacPos, Point gPos) {
		pacManStartPos = (Point) pacPos.clone();
		ghostStartPos = (Point) gPos.clone();
	}

	public void setUniverse(GameUniverse universe) {
		this.universe = universe;
	}

	@Override
	public void applyOverlapRules(Vector<Overlap> overlappables) {
		managePacmanDeath = true;
		super.applyOverlapRules(overlappables);
	}
	
//	public void overlapRule(Pacman p, Pacgum pg) {
//		universe.removeGameEntity(pg);
//	}
}
