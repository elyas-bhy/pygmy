package com.lib.pygmy;

import com.lib.pygmy.base.Movable;
import com.lib.pygmy.base.Overlap;
import com.lib.pygmy.base.Overlappable;

import java.util.Vector;
import java.util.concurrent.ConcurrentLinkedQueue;

public class OverlapProcessorImpl implements OverlapProcessor {

	/**
	 * These two lists contain all overlappables for which we want to compute
	 * overlaps. We distinguish between movable and non-movable because two
	 * non-movables never overlap.
	 */
	private ConcurrentLinkedQueue<Overlappable> overlappablesNonMovable;
	private ConcurrentLinkedQueue<Overlappable> overlappablesMovable;

	private OverlapRulesApplier overlapRules;
	private GameUniverse universe;

	public OverlapProcessorImpl() {
		overlappablesNonMovable = new ConcurrentLinkedQueue<Overlappable>();
		overlappablesMovable = new ConcurrentLinkedQueue<Overlappable>();
	}

	@Override
	public void setUniverse(GameUniverse universe) {
		this.universe = universe;
	}

	public void addOverlappable(Overlappable p) {
		if (p instanceof Movable) {
			overlappablesMovable.add(p);
		} else {
			overlappablesNonMovable.add(p);
		}
	}

	public void removeOverlappable(Overlappable p) {
		if (p instanceof Movable) {
			overlappablesMovable.remove(p);
		} else {
			overlappablesNonMovable.remove(p);
		}
	}

	public void setOverlapRules(OverlapRulesApplier overlapRules) {
		this.overlapRules = overlapRules;
	}

	public void processOverlap(GameMove move) {
		GameEntity entity = move.getEntity();
		universe.getGameEntities().remove(entity.getCurrentTile());
		entity.oneStepMove(move);
		GameEntity dst = universe.getGameEntities().get(entity.getCurrentTile());
		if (entity instanceof Overlappable && dst instanceof Overlappable) {
			Vector<Overlap> overlaps = new Vector<Overlap>();
			overlaps.add(new Overlap((Overlappable)entity, (Overlappable)dst));
			overlapRules.applyOverlapRules(overlaps);
		}
		universe.getGameEntities().put(entity.getCurrentTile(), entity);
	}
}
