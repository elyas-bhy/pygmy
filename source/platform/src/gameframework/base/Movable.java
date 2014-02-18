package gameframework.base;

import java.awt.Point;

/**
 * Has a current position, a {@link Direction} and a bounding box.
 */
public interface Movable extends ObjectWithBoundedBox {
	
	public Point getPosition();

	public Direction getSpeedVector();

	public void setSpeedVector(Direction m);

	public void oneStepMove();
}
