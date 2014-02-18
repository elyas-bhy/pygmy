package gameframework.game;

import gameframework.base.Movable;
import gameframework.base.Direction;

/**
 * Applies moveBlocker checker and moving strategies
 */
public interface GameMovableDriver {
	public Direction getSpeedVector(Movable m);
}
