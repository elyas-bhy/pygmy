package gameframework.base;

import java.awt.Point;

/**
 * Has a current position, a {@link Direction}
 */
public interface Movable {

	public Direction getSpeedVector();

	public void setSpeedVector(Direction m);

	public void oneStepMove(Point move);
}
