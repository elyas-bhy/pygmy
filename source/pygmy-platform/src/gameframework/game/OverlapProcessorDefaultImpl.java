package gameframework.game;

import gameframework.base.Direction;
import gameframework.base.Movable;
import gameframework.base.Overlap;
import gameframework.base.Overlappable;

import java.util.Vector;
import java.util.concurrent.ConcurrentLinkedQueue;

public class OverlapProcessorDefaultImpl implements OverlapProcessor {

	/**
	 * These two lists contain all overlappables for which we want to compute
	 * overlaps. We distinguish between movable and non-movable because two
	 * non-movables never overlap.
	 */
	private ConcurrentLinkedQueue<Overlappable> overlappablesNonMovable;
	private ConcurrentLinkedQueue<Overlappable> overlappablesMovable;

	private OverlapRulesApplier overlapRules;
	private GameUniverse universe;

	public OverlapProcessorDefaultImpl() {
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

	public void processOverlap(GameMovable entity, Direction d) {
		universe.getGameEntities().remove(entity.getPosition());
		entity.oneStepMove(d);
		GameEntity dst = universe.getGameEntities().get(entity.getPosition());
		if (entity instanceof Overlappable && dst instanceof Overlappable) {
			Vector<Overlap> overlaps = new Vector<Overlap>();
			overlaps.add(new Overlap((Overlappable)entity, (Overlappable)dst));
			overlapRules.applyOverlapRules(overlaps);
		}
		universe.getGameEntities().put(entity.getPosition(), (GameEntity)entity);
	}
}