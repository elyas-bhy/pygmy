package com.client.pygmy;

import java.util.Vector;

import com.lib.pygmy.AbstractOverlapRulesApplier;
import com.lib.pygmy.GameUniverse;
import com.lib.pygmy.base.Overlap;

public class DemoOverlapRules extends AbstractOverlapRulesApplier {
	
	protected GameUniverse universe;

	public DemoOverlapRules() {
		
	}

	public void setUniverse(GameUniverse universe) {
		this.universe = universe;
	}

	@Override
	public void applyOverlapRules(Vector<Overlap> overlappables) {
		super.applyOverlapRules(overlappables);
	}
	
//	public void overlapRule(Pacman p, Pacgum pg) {
//		universe.removeGameEntity(pg);
//	}
}
