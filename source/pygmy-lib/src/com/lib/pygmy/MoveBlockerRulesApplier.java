package com.lib.pygmy;

import com.lib.pygmy.base.Movable;

import java.util.Vector;

public interface MoveBlockerRulesApplier {
	
	public boolean moveValidationProcessing(Vector<MoveBlocker> obs, Movable m);
}
