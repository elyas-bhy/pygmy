package com.lib.pygmy.base;

import android.graphics.Point;

/**
 * Indicates a 2D direction
 */
public interface Direction extends Cloneable {
	
	public Point getDirection();

	public void setDirection(Point p);

	public Object clone();
}
