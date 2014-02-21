package gameframework.game;

import gameframework.base.Direction;

public interface MoveBlockerChecker {
	
	public void addMoveBlocker(MoveBlocker p);

	public void removeMoveBlocker(MoveBlocker p);

	public void setMoveBlockerRules(MoveBlockerRulesApplier moveBlockerRules);

	public boolean moveValidation(GameMovable m, Direction mov);
}
