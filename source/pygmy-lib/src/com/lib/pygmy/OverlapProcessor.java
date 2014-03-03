package com.lib.pygmy;

import com.lib.pygmy.base.Overlappable;

public interface OverlapProcessor {

	public void setUniverse(GameUniverse universe);
	
	public void addOverlappable(Overlappable p);

	public void removeOverlappable(Overlappable p);

	public void setOverlapRules(OverlapRulesApplier overlapRules);

	public void processOverlap(GameMove move);
}
