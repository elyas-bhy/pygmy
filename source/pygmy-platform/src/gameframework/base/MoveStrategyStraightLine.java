package gameframework.base;

import java.awt.Point;

public class MoveStrategyStraightLine implements MoveStrategy {

	Point goal, currentPosition;

	public MoveStrategyStraightLine(Point pos, Point goal) {
		this.goal = goal;
		this.currentPosition = pos;
	}

	public Direction getSpeedVector() {
		double dist = currentPosition.distance(goal);
		int xDirection = (int) Math.rint((goal.getX() - currentPosition.getX())
				/ dist);
		int yDirection = (int) Math.rint((goal.getY() - currentPosition.getY())
				/ dist);
		Direction move = new DirectionDefaultImpl(new Point(xDirection,
				yDirection));
		return move;
	}
}
