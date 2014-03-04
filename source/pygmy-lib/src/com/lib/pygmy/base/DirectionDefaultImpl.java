package com.lib.pygmy.base;

import android.graphics.Point;

/**
 * This is the most basic implementation of {@link Direction} which is nothing
 * more than a plain structure with getters and setters.
 */
public class DirectionDefaultImpl implements Direction {

	private Point direction;

	public static Direction createNullVector() {
		return new DirectionDefaultImpl(new Point(0, 0));
	}

	public DirectionDefaultImpl(Point direction) {
		this.direction = direction;
	}

	public Point getDirection() {
		return direction;
	}

	public void setDirection(Point direction) {
		this.direction = direction;
	}

	@Override
	public Object clone() {
		return new DirectionDefaultImpl(direction);
	}
}
