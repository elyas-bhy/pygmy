package com.lib.pygmy.base;

import java.awt.Point;

/**
 * Has a current position, a {@link Direction}
 */
public interface Movable {

	public Point getDestination();

	public void setDestination(Point p);

	public void oneStepMove(Point move);
}