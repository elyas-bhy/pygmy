package com.lib.pygmy.base;

import com.lib.pygmy.util.Point;

/**
 * Has a current position
 */
public interface Movable {

	public Point getDestination();

	public void setDestination(Point p);

	public void oneStepMove(Point move);
}
