package gameframework.game;

import gameframework.base.Movable;
import gameframework.base.Direction;
import gameframework.base.DirectionDefaultImpl;

import java.awt.Point;

public abstract class GameMovable implements Movable {

	private Point position = new Point();
	private Direction speedVector = DirectionDefaultImpl.createNullVector();

	public void setPosition(Point p) {
		position = (Point) p.clone();
	}

	public Point getPosition() {
		return position;
	}

	public void setSpeedVector(Direction speedVector) {
		this.speedVector = (Direction) speedVector.clone();
	}

	public Direction getSpeedVector() {
		return (Direction) speedVector.clone();
	}

	public void oneStepMove(Point p) {
		speedVector.setDirection(p);
		position.translate((int) speedVector.getDirection().getX(), 
				(int) speedVector.getDirection().getY());
		oneStepMoveAddedBehavior();
	}

	public abstract boolean isLegalMove(GameMove move);
	
	public abstract void oneStepMoveAddedBehavior();
	
}
