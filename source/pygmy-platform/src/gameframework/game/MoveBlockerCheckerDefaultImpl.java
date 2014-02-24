package gameframework.game;

import gameframework.base.Direction;

import java.util.Vector;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MoveBlockerCheckerDefaultImpl implements MoveBlockerChecker {
	private ConcurrentLinkedQueue<MoveBlocker> moveBlockers;
	private MoveBlockerRulesApplier moveBlockerRuleApplier;

	public MoveBlockerCheckerDefaultImpl() {
		moveBlockers = new ConcurrentLinkedQueue<MoveBlocker>();
		this.moveBlockerRuleApplier = new MoveBlockerRulesApplierDefaultImpl();
	}

	public void addMoveBlocker(MoveBlocker p) {
		moveBlockers.add(p);
	}

	public void removeMoveBlocker(MoveBlocker p) {
		moveBlockers.remove(p);
	}

	public void setMoveBlockerRules(MoveBlockerRulesApplier moveBlockerRules) {
		this.moveBlockerRuleApplier = moveBlockerRules;
	}

	public boolean moveValidation(GameMovable m, Direction mov) {
		Vector<MoveBlocker> moveBlockersInIntersection = new Vector<MoveBlocker>();

		for (MoveBlocker moveBlocker : moveBlockers) {
			//moveBlockersInIntersection.add(moveBlocker);
		}

		if (!moveBlockersInIntersection.isEmpty()) {
			return moveBlockerRuleApplier.moveValidationProcessing(
					moveBlockersInIntersection, m);
		}

		return true;
	}
}