package gameframework.game;

import gameframework.base.Movable;

import java.awt.Point;

public abstract class GameMovable implements Movable {

	private Player player;
	private Point source = new Point();
	private Point destination = new Point();
	
	public Point getPosition() {
		return source;
	}
	
	public void setPosition(Point p) {
		source = (Point) p.clone();
	}
	
	@Override
	public Point getDestination() {
		return (Point) destination.clone();
	}

	@Override
	public void setDestination(Point p) {
		this.destination = (Point) p.clone();
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public void setPlayer(Player player) {
		this.player = player;
	}

	@Override
	public void oneStepMove(Point p) {
		destination.setLocation(p);
		source.translate((int) destination.getX(), (int) destination.getY());
		oneStepMoveAddedBehavior();
	}
	
	public abstract boolean isLegalMove(GameMove move);
	
	public abstract void oneStepMoveAddedBehavior();
	
}
