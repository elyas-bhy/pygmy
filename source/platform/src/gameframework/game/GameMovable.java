package gameframework.game;

import gameframework.base.Movable;
import gameframework.base.Direction;
import gameframework.base.DirectionDefaultImpl;

import java.awt.Point;

public abstract class GameMovable implements Movable {
	
	GameMovableDriver moveDriver = new GameMovableDriverDefaultImpl();

	Point position = new Point();
	Direction speedVector = DirectionDefaultImpl.createNullVector();

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

	public void setDriver(GameMovableDriver driver) {
		moveDriver = driver;
	}

	public GameMovableDriver getDriver() {
		return moveDriver;
	}

	public void oneStepMove() {
		Direction m = moveDriver.getSpeedVector(this);
		speedVector.setDirection(m.getDirection());
		position.translate((int) speedVector.getDirection().getX(), 
				(int) speedVector.getDirection().getY());
		oneStepMoveAddedBehavior();
	}

	public abstract boolean isLegalMove(String move);
	
	public abstract void oneStepMoveAddedBehavior();
	
}
